package com.howtodoinjava.app.service;

import com.howtodoinjava.app.model.Item;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  public List<Item> getAll() {
    return List.of(new Item(1L, "Item - 1"), new Item(2L, "Item - 2"));
  }
}
