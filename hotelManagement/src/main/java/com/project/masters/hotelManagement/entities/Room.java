package com.project.masters.hotelManagement.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room implements Serializable {
  @Id
  @Column(name = "room_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomId;

  @Column(name = "number", nullable = false)
  private int roomNumber;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private RoomCategory roomCategory;

  @Column(name = "available")
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

  public RoomCategory getRoomCategory() {
    return roomCategory;
  }

  public void setRoomCategoryId(RoomCategory roomCategory) {
    this.roomCategory = roomCategory;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }


}
