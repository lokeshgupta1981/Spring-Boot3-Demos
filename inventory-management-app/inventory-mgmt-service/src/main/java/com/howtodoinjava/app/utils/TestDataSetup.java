package com.howtodoinjava.app.utils;

import com.howtodoinjava.app.dao.ItemRepository;
import com.howtodoinjava.app.dao.entity.Item;
import com.howtodoinjava.app.dao.entity.ItemCategory;
import com.howtodoinjava.app.dao.entity.ItemDetail;
import com.howtodoinjava.app.dao.entity.Seller;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class TestDataSetup implements CommandLineRunner {

  @Autowired
  private ItemRepository itemRepository;

  @Override
  public void run(String... args) throws Exception {

    Iterator iterator = itemRepository.findAll().iterator();
    //If there is already some test data, leave setup.
    if(iterator.hasNext()) {
      return;
    }

    Seller seller = new Seller(null, "Book Publishing Co.", true);
    ItemDetail itemDetail = new ItemDetail(null, seller, ItemCategory.BOOKS);
    Item item = new Item(null, "Harry Potter", "Fiction and Magic for Kids", 100, itemDetail, true);

    itemRepository.save(item);

    seller = new Seller(null, "Old Publishing Co.", true);
    itemDetail = new ItemDetail(null, seller, ItemCategory.BOOKS);
    item = new Item(null, "Wednesday", "Fiction and Magic for Kids", 100, itemDetail, true);

    itemRepository.save(item);
  }
}
