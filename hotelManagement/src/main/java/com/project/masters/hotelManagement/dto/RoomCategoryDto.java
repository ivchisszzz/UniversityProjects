package com.project.masters.hotelManagement.dto;

import java.util.List;

public class RoomCategoryDto {
  private Long categoryId;
  private String roomCategoryName;
  private Double pricePerNight;
  private Integer maxPersons;
  private List<String> hotelServiceNames;

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getRoomCategoryName() {
    return roomCategoryName;
  }

  public void setRoomCategoryName(String roomCategoryName) {
    this.roomCategoryName = roomCategoryName;
  }

  public Double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(Double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  public Integer getMaxPersons() {
    return maxPersons;
  }

  public void setMaxPersons(Integer maxPersons) {
    this.maxPersons = maxPersons;
  }

  public List<String> getHotelServiceNames() {
    return hotelServiceNames;
  }

  public void setHotelServiceNames(List<String> hotelServiceNames) {
    this.hotelServiceNames = hotelServiceNames;
  }

}
