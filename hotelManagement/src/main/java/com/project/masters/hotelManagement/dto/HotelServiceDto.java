package com.project.masters.hotelManagement.dto;

public class HotelServiceDto {
  private Long serviceId;
  private String hotelServiceName;
  private Double price;

  public String getHotelServiceName() {
    return hotelServiceName;
  }

  public void setHotelServiceName(String hotelServiceName) {
    this.hotelServiceName = hotelServiceName;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Long getServiceId() {
    return serviceId;
  }

  public void setServiceId(Long serviceId) {
    this.serviceId = serviceId;
  }

}
