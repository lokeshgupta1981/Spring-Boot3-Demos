package com.howtodoinjava.app.web;

import com.howtodoinjava.app.dao.ItemRepository;
import com.howtodoinjava.app.dao.entity.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

  @Autowired
  private ItemRepository itemRepository;

  @GetMapping
  public List<Item> getAll(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "asc") String order,
      @RequestParam(required = false) String searchTerm){

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
    return itemRepository.findByNameContaining(searchTerm, pageable);
  }

}
