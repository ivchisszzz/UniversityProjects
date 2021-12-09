package com.project.masters.hotelManagement.repositories;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.project.masters.hotelManagement.entities.Room;
import com.project.masters.hotelManagement.entities.RoomCategory;

@Repository
public class FilterRepository {

  EntityManager entityManger;

  public FilterRepository(EntityManager entityManger) {
    this.entityManger = entityManger;
  }

  public List<Room> getRoomByCategoryAndPrice(String roomCategory, Integer priceLessThan,
      Integer priceGreaterThan) {
    CriteriaBuilder cb = entityManger.getCriteriaBuilder();
    CriteriaQuery<Room> cq = cb.createQuery(Room.class);
    Root<Room> room = cq.from(Room.class);
    Join<Room, RoomCategory> category = room.join("roomCategory");
    List<Predicate> restrictions = new ArrayList<Predicate>();
    if (priceLessThan != null) {
      restrictions.add(cb.lessThan(category.get("pricePerNight"), priceLessThan));
    }
    if (priceGreaterThan != null) {
      restrictions.add(cb.greaterThan(category.get("pricePerNight"), priceGreaterThan));
    }
    if (StringUtils.hasText(roomCategory)) {
      restrictions.add(cb.equal(category.get("roomCategoryName"), roomCategory));
    }
    cq.where(restrictions.toArray(new Predicate[restrictions.size()]));
    TypedQuery<Room> query = entityManger.createQuery(cq);
    return query.getResultList();

  }

}
