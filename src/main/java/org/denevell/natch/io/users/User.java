package org.denevell.natch.io.users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

  private String username;
  private boolean admin;
  private boolean resetPasswordRequest;
  private String recoveryEmail;

  public User() {
  }

  public User(String username, boolean isAdmin) {
    this.username = username;
    this.admin = isAdmin;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean isAdmin) {
    this.admin = isAdmin;
  }

  public boolean isResetPasswordRequest() {
    return resetPasswordRequest;
  }

  public void setResetPasswordRequest(boolean resetPasswordRequest) {
    this.resetPasswordRequest = resetPasswordRequest;
  }

  public String getRecoveryEmail() {
    return recoveryEmail;
  }

  public void setRecoveryEmail(String recoveryEmail) {
    this.recoveryEmail = recoveryEmail;
  }

}
