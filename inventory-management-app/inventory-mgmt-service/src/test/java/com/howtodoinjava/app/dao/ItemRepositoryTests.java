package com.howtodoinjava.app.dao;

import com.howtodoinjava.app.dao.entity.Item;
import com.howtodoinjava.app.dao.entity.ItemCategory;
import com.howtodoinjava.app.dao.entity.ItemDetail;
import com.howtodoinjava.app.dao.entity.Seller;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ItemRepositoryTests {

  @Autowired
  private ItemRepository itemRepository;

  @Test
  public void testGetAll(){
    Seller seller = new Seller(null, "Book Publishing Co.", true);
    ItemDetail itemDetail = new ItemDetail(null, seller, ItemCategory.BOOKS);
    Item item = new Item(null, "Jungle Book", "Fiction and Magic for Kids", 100, itemDetail, true);

    itemRepository.save(item);

    long itemId = item.getId();

    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.fromString("asc"), "id"));
    List<Item> itemList = itemRepository.findByNameContaining("Jungle Book", pageable);

    Assertions.assertEquals(1, itemList.size());

    itemList = itemRepository.findByNameContaining("JungleBook", pageable);

    Assertions.assertEquals(0, itemList.size());
  }
}
