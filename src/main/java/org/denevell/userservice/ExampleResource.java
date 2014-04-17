package org.denevell.userservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExampleResource {
	
	private String stuff;

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

}
