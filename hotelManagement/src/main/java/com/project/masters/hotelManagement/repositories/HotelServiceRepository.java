package com.project.masters.hotelManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.masters.hotelManagement.entities.HotelService;

@Repository
public interface HotelServiceRepository extends JpaRepository<HotelService, Long> {

  public HotelService findByHotelServiceName(String hotelServiceName);


}
