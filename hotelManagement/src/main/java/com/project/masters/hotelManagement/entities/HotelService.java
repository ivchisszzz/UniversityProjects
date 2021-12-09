package com.project.masters.hotelManagement.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hotel_service")
public class HotelService implements Serializable {
  @Id
  @Column(name = "service_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long hotelServiceId;

  @Column(name = "name", length = 255, nullable = false)
  private String hotelServiceName;

  @Column(name = "price", length = 10, precision = 2)
  private Double price;

  @ManyToMany(mappedBy = "hotelServices")
  private List<RoomCategory> categories;

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Long getHotelServiceId() {
    return hotelServiceId;
  }

  public List<RoomCategory> getCategories() {
    return categories;
  }

  public void setCategories(List<RoomCategory> categories) {
    this.categories = categories;
  }

  public void setHotelServiceId(Long hotelServiceId) {
    this.hotelServiceId = hotelServiceId;
  }

  public String getHotelServiceName() {
    return hotelServiceName;
  }

  public void setHotelServiceName(String hotelServiceName) {
    this.hotelServiceName = hotelServiceName;
  }



}
