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
import com.project.masters.hotelManagement.dto.HotelServiceDto;
import com.project.masters.hotelManagement.entities.HotelService;
import com.project.masters.hotelManagement.entities.RoomCategory;
import com.project.masters.hotelManagement.repositories.HotelServiceRepository;

@RestController
@RequestMapping("/hotelServices")
public class HotelServiceController {

  HotelServiceRepository hotelServiceRepository;

  HotelServiceController(HotelServiceRepository hotelServiceRepository) {
    this.hotelServiceRepository = hotelServiceRepository;
  }

  @GetMapping(path = "/getAll")
  public ResponseEntity<List<HotelServiceDto>> getAllServices() {
    List<HotelService> services = hotelServiceRepository.findAll();
    List<HotelServiceDto> serviceDtoList = new ArrayList<HotelServiceDto>();
    for (int i = 0; i < services.size(); i++) {
      HotelServiceDto dto = new HotelServiceDto();
      dto.setHotelServiceName(services.get(i).getHotelServiceName());
      dto.setPrice(services.get(i).getPrice());
      dto.setServiceId(services.get(i).getHotelServiceId());
      serviceDtoList.add(dto);
    }
    return new ResponseEntity<List<HotelServiceDto>>(serviceDtoList, HttpStatus.OK);
  }

  @GetMapping(path = "/getService")
  public ResponseEntity<HotelServiceDto> getServiceById(@RequestParam Long id) {
    Optional<HotelService> service = hotelServiceRepository.findById(id);
    HotelServiceDto dto = new HotelServiceDto();
    dto.setHotelServiceName(service.get().getHotelServiceName());
    dto.setPrice(service.get().getPrice());
    dto.setServiceId(service.get().getHotelServiceId());
    return new ResponseEntity<HotelServiceDto>(dto, HttpStatus.OK);
  }

  @PostMapping(path = "/createService")
  public ResponseEntity createHotelService(HotelService hotelService) {
    List<String> errorMsg =
        validations(hotelService.getHotelServiceName(), hotelService.getPrice());
    if (!errorMsg.isEmpty()) {
      return ResponseEntity.badRequest().body(errorMsg);
    }
    hotelServiceRepository.save(hotelService);
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/updateService")
  public ResponseEntity updateHotelService(HotelServiceDto dto) {
    List<String> errorMsg = validationsUpdate(dto.getHotelServiceName(), dto.getPrice());
    if (!errorMsg.isEmpty()) {
      return ResponseEntity.badRequest().body(errorMsg);
    }
    HotelService service = hotelServiceRepository.getById(dto.getServiceId());
    service.setHotelServiceName(dto.getHotelServiceName());
    service.setPrice(dto.getPrice());
    hotelServiceRepository.save(service);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(path = "/deleteService")
  public ResponseEntity<Void> deleteService(@RequestParam Long id) {
    Optional<HotelService> service = hotelServiceRepository.findById(id);
    HotelService s = service.get();
    List<RoomCategory> categories = s.getCategories();
    categories.forEach(category -> category.getHotelServices().remove(s));
    hotelServiceRepository.deleteById(id);
    return ResponseEntity.ok().build();

  }

  private List<String> validations(String hotelServiceName, Double price) {
    List<String> errors = new ArrayList<String>();
    if (!StringUtils.hasText(hotelServiceName)) {
      errors.add("Hotel service name can not be empty!");
    } else {
      HotelService service = hotelServiceRepository.findByHotelServiceName(hotelServiceName);
      if (service != null) {
        errors.add("Hotel service with this name already exists!");
      }
    }
    if (price == null) {
      errors.add("Price can not be empty");
    }
    return errors;

  }

  private List<String> validationsUpdate(String hotelServiceName, Double price) {
    List<String> errors = new ArrayList<String>();
    if (!StringUtils.hasText(hotelServiceName)) {
      errors.add("Hotel service name can not be empty!");
    }
    if (price == null) {
      errors.add("Price can not be empty");
    }
    return errors;

  }



}
