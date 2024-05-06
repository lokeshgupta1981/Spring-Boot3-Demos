package com.howtodoinjava.web;

import com.howtodoinjava.dao.model.Item;
import com.howtodoinjava.dao.model.ItemRepository;
import com.howtodoinjava.web.errors.ItemNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

  @Autowired
  ItemRepository itemRepository;

  @GetMapping("/items")
  List<Item> all() {
    return itemRepository.findAll();
  }

  @GetMapping("/items/{id}")
  Item getById(@PathVariable Long id) {

    return itemRepository.findById(id)
        .orElseThrow(() -> new ItemNotFoundException(id));
  }

  @PostMapping("/items")
  Item createNew(@Valid @RequestBody Item newItem) {
    return itemRepository.save(newItem);
  }

  @DeleteMapping("/items/{id}")
  void delete(@PathVariable Long id) {
    itemRepository.deleteById(id);
  }

  @PutMapping("/items/{id}")
  Item updateOrCreate(@RequestBody Item newItem, @PathVariable Long id) {

    return itemRepository.findById(id)
        .map(item -> {
          item.setName(newItem.getName());
          return itemRepository.save(item);
        })
        .orElseGet(() -> {
          newItem.setId(id);
          return itemRepository.save(newItem);
        });
  }
}
