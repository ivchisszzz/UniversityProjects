package com.project.masters.hotelManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.masters.hotelManagement.entities.RoomCategory;

@Repository
public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Long> {

  public RoomCategory findByRoomCategoryName(String roomCategoryName);


}
