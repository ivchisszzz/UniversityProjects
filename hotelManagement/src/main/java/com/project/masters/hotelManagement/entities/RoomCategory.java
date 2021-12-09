package com.project.masters.hotelManagement.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room_category")
public class RoomCategory implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomCategoryId;

  @Column(name = "name", length = 255, nullable = false)
  private String roomCategoryName;

  @Column(name = "price_per_night", length = 10, precision = 2)
  private double pricePerNight;

  @Column(name = "max_persons", length = 30)
  private int maxPersons;

  @ManyToMany
  @JoinTable(name = "category_services", joinColumns = @JoinColumn(name = "id"),
      inverseJoinColumns = @JoinColumn(name = "service_id"))
  private List<HotelService> hotelServices;


  public Long getRoomCategoryId() {
    return roomCategoryId;
  }

  public void setRoomCategoryId(Long roomCategoryId) {
    this.roomCategoryId = roomCategoryId;
  }

  public String getRoomCategoryName() {
    return roomCategoryName;
  }

  public void setRoomCategoryName(String roomCategoryName) {
    this.roomCategoryName = roomCategoryName;
  }

  public double getPricePerNight() {
    return pricePerNight;
  }

  public void setPricePerNight(double pricePerNight) {
    this.pricePerNight = pricePerNight;
  }

  public int getMaxPersons() {
    return maxPersons;
  }

  public void setMaxPersons(int maxPersons) {
    this.maxPersons = maxPersons;
  }

  public List<HotelService> getHotelServices() {
    return hotelServices;
  }

  public void setHotelServices(List<HotelService> hotelServices) {
    this.hotelServices = hotelServices;
  }



}
