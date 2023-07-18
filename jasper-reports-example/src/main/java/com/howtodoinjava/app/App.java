package com.howtodoinjava.app;

import com.howtodoinjava.app.dao.ItemRepository;
import com.howtodoinjava.app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

  @Autowired
  ItemRepository itemRepository;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    itemRepository.save(new Item("Item 1"));
    itemRepository.save(new Item("Item 2"));
    itemRepository.save(new Item("Item 3"));
    itemRepository.save(new Item("Item 4"));
    itemRepository.save(new Item("Item 5"));
  }
}
