package com.project.masters.hotelManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.masters.hotelManagement.dto.RoomDto;
import com.project.masters.hotelManagement.entities.Room;
import com.project.masters.hotelManagement.entities.RoomCategory;
import com.project.masters.hotelManagement.repositories.FilterRepository;
import com.project.masters.hotelManagement.repositories.RoomCategoryRepository;
import com.project.masters.hotelManagement.repositories.RoomRepository;


@RestController
@RequestMapping("/rooms")
public class RoomController {

  RoomRepository roomRepository;

  RoomCategoryRepository repository;

  FilterRepository filterRepository;

  RoomController(RoomRepository roomRepository, RoomCategoryRepository repository,
      FilterRepository filterRepository) {
    this.roomRepository = roomRepository;
    this.repository = repository;
    this.filterRepository = filterRepository;
  }

  @GetMapping(path = "/getAll")
  public ResponseEntity<List<RoomDto>> getAllRooms(
      @RequestParam(required = false) String roomCategory,
      @RequestParam(required = false) Integer priceLessThan,
      @RequestParam(required = false) Integer priceGreaterThan) {
    // List<Room> rooms = roomRepository.findAll();
    List<Room> rooms =
        filterRepository.getRoomByCategoryAndPrice(roomCategory, priceLessThan, priceGreaterThan);
    List<RoomDto> dtoList = new ArrayList<RoomDto>();
    for (Room room : rooms) {
      RoomDto roomDto = new RoomDto();
      roomDto.setRoomNumber(room.getRoomNumber());
      roomDto.setRoomId(room.getRoomId());
      roomDto.setAvailable(room.isAvailable());
      roomDto.setRoomCategory(room.getRoomCategory().getRoomCategoryName());
      dtoList.add(roomDto);
    }
    return new ResponseEntity<List<RoomDto>>(dtoList, HttpStatus.OK);
  }

  @GetMapping(path = "/getRoom")
  public ResponseEntity<RoomDto> getRoomById(@RequestParam Long id) {
    Optional<Room> room = roomRepository.findById(id);
    RoomDto dto = new RoomDto();
    dto.setRoomCategory(room.get().getRoomCategory().getRoomCategoryName());
    dto.setAvailable(room.get().isAvailable());
    dto.setRoomId(room.get().getRoomId());
    dto.setRoomNumber(room.get().getRoomNumber());
    return new ResponseEntity<RoomDto>(dto, HttpStatus.OK);
  }

  @PostMapping(path = "/createRoom")
  public ResponseEntity<Void> createRoom(RoomDto room) {
    RoomCategory category = repository.findByRoomCategoryName(room.getRoomCategory());
    Room newRoom = new Room();
    newRoom.setAvailable(room.isAvailable());
    newRoom.setRoomNumber(room.getRoomNumber());
    newRoom.setRoomCategoryId(category);
    roomRepository.save(newRoom);
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/updateRoom")
  public ResponseEntity<Void> updateRoom(RoomDto roomDto) {
    Room room = roomRepository.getById(roomDto.getRoomId());
    RoomCategory category = repository.findByRoomCategoryName(roomDto.getRoomCategory());
    room.setAvailable(roomDto.isAvailable());
    room.setRoomNumber(roomDto.getRoomNumber());
    room.setRoomCategoryId(category);
    roomRepository.save(room);
    return ResponseEntity.ok().build();

  }

  @DeleteMapping(path = "/deleteRoom")
  public ResponseEntity<Void> deleteRoom(@RequestParam Long id) {
    roomRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }



}
