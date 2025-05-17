package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDataModel {

  private ResourceResponseModel data;

  public ResourceResponseModel getData() {
    return data;
  }

  public void setData(ResourceResponseModel data) {
    this.data = data;
  }
}
