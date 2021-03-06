// Not used atm.
package main

import (
	"database/sql"
	"fmt"
	_ "github.com/lib/pq"
	"log"
	"os"
	"reflect"
	"strconv"
)

var con *sql.DB

type migration struct {
	Tx *sql.Tx
}

func versionsAvailable() int {
	for i := 1; i <= 100; i++ {
		var m migration
		v := reflect.ValueOf(&m).MethodByName("Migration_" + strconv.Itoa(i))
		if v.IsValid() == false {
			i--
			return i
		}
	}
	return 0
}

func dbVersionNumber(con *sql.Tx) (int, error) {
	_, err := con.Exec("create table if not exists version_number (version_number integer)")
	if err != nil {
		return 0, err
	}
	row := con.QueryRow("SELECT version_number FROM version_number")
	var id int
	err = row.Scan(&id)
	if err == sql.ErrNoRows {
		_, err = con.Exec("insert into version_number values(1)")
		if err != nil {
			return 0, err
		}
		row := con.QueryRow("SELECT version_number FROM version_number")
		var id int
		err = row.Scan(&id)
		return id, err
	} else {
		return id, err
	}
	return id, err
}

func updateVersionNumber(con *sql.Tx, version int) error {
	_, err := con.Exec("delete from version_number")
	if err != nil {
		return err
	}
	_, err = con.Exec("insert into version_number (version_number) values(" + strconv.Itoa(version) + ")")
	return err
}

func main() {
	// Open db
	var err error
	connStr := os.Args[1]
	con, err = sql.Open("postgres", connStr)
	if err != nil {
		log.Fatal(err)
	}
	defer con.Close()
	tx, err := con.Begin()
	if err != nil {
		log.Fatal(err)
	}

	// Get version number
	versionNumber, err := dbVersionNumber(tx)
	if err != nil {
		log.Fatalf("Couldn't get version of db: %v", err)
	}
	fmt.Println("Current db version number:", versionNumber)

	// Get number of versions in script
	versionsAvailable := versionsAvailable()
	fmt.Printf("Versions available: 1-%d\n", versionsAvailable)
	fmt.Println("---")

	// Get migration functions
	for i := versionNumber; i <= versionsAvailable; i++ {
		defer func() {
			if e := recover(); e != nil {
				fmt.Println("Rolling back")
				err := tx.Rollback()
				if err != nil {
					fmt.Println("Rollback error: ", err)
				}
				fmt.Println("Failed migration:", e)
				os.Exit(1)
			}
		}()
		m := migration{tx}
		fmt.Printf("### Running Migration_%d\n", i)
		reflect.ValueOf(&m).MethodByName("Migration_" + strconv.Itoa(i)).Call([]reflect.Value{})
		err := updateVersionNumber(tx, i+1)
		if err != nil {
			panic(err)
		}
	}

	tx.Commit()
}

func (m migration) Migration_1() {
	_, err := m.Tx.Exec("create table sequence (seq_name varchar(50) not null primary key, seq_count int)")
	if err != nil {
		panic(err)
	}
	_, err = m.Tx.Exec("insert into sequence (seq_name, seq_count) values('SEQ_GEN', 1)")
	if err != nil {
		panic(err)
	}
	_, err = m.Tx.Exec("create table example (id bigint not null primary key, talky varchar(1000) not null)")
	if err != nil {
		panic(err)
	}
}

