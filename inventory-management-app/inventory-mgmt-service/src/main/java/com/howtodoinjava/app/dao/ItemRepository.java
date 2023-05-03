package com.howtodoinjava.app.dao;

import com.howtodoinjava.app.dao.entity.Item;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

  List<Item> findByNameContaining(String searchTerm, Pageable pageable);
}
