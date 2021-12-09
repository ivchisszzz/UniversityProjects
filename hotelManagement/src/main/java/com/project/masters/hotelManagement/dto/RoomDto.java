package com.project.masters.hotelManagement.dto;

public class RoomDto {
  private Long roomId;
  private int roomNumber;
  private String roomCategory;
  private boolean available;

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String getRoomCategory() {
    return roomCategory;
  }

  public void setRoomCategory(String roomCategory) {
    this.roomCategory = roomCategory;
  }
}
