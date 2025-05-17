package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataModel {

  private UserResponseModel data;

  public UserResponseModel getData() {
    return data;
  }

  public void setData(UserResponseModel data) {
    this.data = data;
  }
}
