package com.project.masters.hotelManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.masters.hotelManagement.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  public Room findByRoomNumber(int roomNumber);


}
