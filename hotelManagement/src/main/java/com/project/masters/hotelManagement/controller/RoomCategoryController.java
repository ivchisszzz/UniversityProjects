package com.project.masters.hotelManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.masters.hotelManagement.dto.RoomCategoryDto;
import com.project.masters.hotelManagement.entities.HotelService;
import com.project.masters.hotelManagement.entities.RoomCategory;
import com.project.masters.hotelManagement.repositories.HotelServiceRepository;
import com.project.masters.hotelManagement.repositories.RoomCategoryRepository;

@RestController
@RequestMapping("/roomsCategory")
public class RoomCategoryController {

  RoomCategoryRepository roomCategoryRepository;

  HotelServiceRepository hotelServiceRepository;

  RoomCategoryController(RoomCategoryRepository roomCategoryRepository,
      HotelServiceRepository hotelServiceRepository) {
    this.roomCategoryRepository = roomCategoryRepository;
    this.hotelServiceRepository = hotelServiceRepository;
  }

  @GetMapping(path = "/getAll")
  public ResponseEntity<List<RoomCategoryDto>> getAllRoomCategories() {
    List<RoomCategory> categories = roomCategoryRepository.findAll();
    List<RoomCategoryDto> dtoList = new ArrayList<RoomCategoryDto>();
    for (RoomCategory category : categories) {
      RoomCategoryDto dto = new RoomCategoryDto();
      dto.setRoomCategoryName(category.getRoomCategoryName());
      dto.setMaxPersons(category.getMaxPersons());
      dto.setPricePerNight(category.getPricePerNight());
      dto.setCategoryId(category.getRoomCategoryId());
      List<String> services = new ArrayList<String>();
      for (HotelService h : category.getHotelServices()) {
        services.add(h.getHotelServiceName());
      }
      dto.setHotelServiceNames(services);
      dtoList.add(dto);

    }
    return new ResponseEntity<List<RoomCategoryDto>>(dtoList, HttpStatus.OK);
  }

  @GetMapping(path = "/getCategory")
  public ResponseEntity<RoomCategoryDto> getRoomCategoryById(@RequestParam Long id) {
    Optional<RoomCategory> category = roomCategoryRepository.findById(id);
    RoomCategoryDto dto = new RoomCategoryDto();
    dto.setCategoryId(category.get().getRoomCategoryId());
    dto.setMaxPersons(category.get().getMaxPersons());
    dto.setPricePerNight(category.get().getPricePerNight());
    dto.setRoomCategoryName(category.get().getRoomCategoryName());
    List<String> serviceList = new ArrayList<String>();
    for (HotelService h : category.get().getHotelServices()) {
      serviceList.add(h.getHotelServiceName());
    }
    dto.setHotelServiceNames(serviceList);
    return new ResponseEntity<RoomCategoryDto>(dto, HttpStatus.OK);
  }

  @GetMapping(path = "/getCategoryByName")
  public ResponseEntity<RoomCategoryDto> getRoomCategoryByName(@RequestParam String name) {
    RoomCategory category = roomCategoryRepository.findByRoomCategoryName(name);
    RoomCategoryDto categoryDto = new RoomCategoryDto();
    categoryDto.setRoomCategoryName(category.getRoomCategoryName());
    categoryDto.setCategoryId(category.getRoomCategoryId());
    categoryDto.setMaxPersons(category.getMaxPersons());
    categoryDto.setPricePerNight(category.getPricePerNight());
    List<String> services = new ArrayList<String>();
    for (HotelService h : category.getHotelServices()) {
      services.add(h.getHotelServiceName());
    }
    categoryDto.setHotelServiceNames(services);

    return new ResponseEntity<RoomCategoryDto>(categoryDto, HttpStatus.OK);
  }

  @PostMapping(path = "/createRoomCategory")
  public ResponseEntity createRoomCategories(RoomCategoryDto category) {
    List<String> errorMsg = validations(category.getRoomCategoryName(), category.getPricePerNight(),
        category.getMaxPersons(), category.getHotelServiceNames());
    if (!errorMsg.isEmpty()) {
      return ResponseEntity.badRequest().body(errorMsg);
    }
    List<HotelService> serviceList = new ArrayList<HotelService>();
    for (String s : category.getHotelServiceNames()) {
      serviceList.add(hotelServiceRepository.findByHotelServiceName(s));
    }
    RoomCategory roomCategory = new RoomCategory();
    roomCategory.setMaxPersons(category.getMaxPersons());
    roomCategory.setPricePerNight(category.getPricePerNight());
    roomCategory.setRoomCategoryName(category.getRoomCategoryName());
    roomCategory.setHotelServices(serviceList);
    roomCategoryRepository.save(roomCategory);
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/updateRoomCategory")
  public ResponseEntity updateRoomCategory(RoomCategoryDto categoryDto) {
    List<String> errorMsg =
        validationsUpdate(categoryDto.getRoomCategoryName(), categoryDto.getPricePerNight(),
            categoryDto.getMaxPersons(), categoryDto.getHotelServiceNames());
    if (!errorMsg.isEmpty()) {
      return ResponseEntity.badRequest().body(errorMsg);
    }
    RoomCategory category = roomCategoryRepository.getById(categoryDto.getCategoryId());
    List<HotelService> serviceList = new ArrayList<HotelService>();
    for (String s : categoryDto.getHotelServiceNames()) {
      serviceList.add(hotelServiceRepository.findByHotelServiceName(s));
    }
    category.setMaxPersons(categoryDto.getMaxPersons());
    category.setPricePerNight(categoryDto.getPricePerNight());
    category.setRoomCategoryName(categoryDto.getRoomCategoryName());
    category.setHotelServices(serviceList);
    roomCategoryRepository.save(category);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(path = "/deleteCategory")
  public ResponseEntity<Void> deleteCategory(@RequestParam Long id) {
    roomCategoryRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  private List<String> validations(String roomCategoryName, Double pricePerNight,
      Integer maxPersons, List<String> hotelServiceNames) {
    List<String> errors = new ArrayList<String>();
    if (!StringUtils.hasText(roomCategoryName)) {
      errors.add("Category name can not be empty!");
    } else {
      RoomCategory category = roomCategoryRepository.findByRoomCategoryName(roomCategoryName);
      if (category != null) {
        errors.add("Category with this name already exists!");
      }
    }
    if (pricePerNight == null) {
      errors.add("Price can not be empty");
    }
    if (maxPersons == null) {
      errors.add("Max guests can not be empty");
    }
    if (hotelServiceNames.isEmpty()) {
      errors.add("Hotel Service can not be empty");
    }
    return errors;

  }

  private List<String> validationsUpdate(String roomCategoryName, Double pricePerNight,
      Integer maxPersons, List<String> hotelServiceNames) {
    List<String> errors = new ArrayList<String>();
    if (!StringUtils.hasText(roomCategoryName)) {
      errors.add("Category name can not be empty!");
    }
    if (pricePerNight == null) {
      errors.add("Price can not be empty");
    }
    if (maxPersons == null) {
      errors.add("Max guests can not be empty");
    }
    if (hotelServiceNames.isEmpty()) {
      errors.add("Hotel Service can not be empty");
    }
    return errors;

  }

}


