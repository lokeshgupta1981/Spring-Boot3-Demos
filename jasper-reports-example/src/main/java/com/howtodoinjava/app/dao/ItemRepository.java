package com.howtodoinjava.app.dao;

import com.howtodoinjava.app.model.Item;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ListCrudRepository<Item, Long> {

}
