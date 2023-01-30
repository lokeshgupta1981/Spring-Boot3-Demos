package com.howtodoinjava.app;

import com.howtodoinjava.app.dao.ItemRepository;
import com.howtodoinjava.app.dao.entity.Item;
import com.howtodoinjava.app.dao.entity.ItemCategory;
import com.howtodoinjava.app.dao.entity.ItemDetail;
import com.howtodoinjava.app.dao.entity.Seller;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class InventoryMgmtServiceApplication implements CommandLineRunner {

	@Autowired
	private ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryMgmtServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Iterator iterator = itemRepository.findAll().iterator();
		//If there is already some test data, leave it.
		if(!iterator.hasNext()) {
			Seller seller = new Seller(null, "Book Publishing Co.", true);
			ItemDetail itemDetail = new ItemDetail(null, seller, ItemCategory.BOOKS);
			Item item = new Item(null, "Harry Potter", "Fiction and Magic for Kids", 100, itemDetail, true);

			itemRepository.save(item);
		}

		Item item = itemRepository.findById(1L).get();
		log.info("Item read :: " + item);
	}
}
