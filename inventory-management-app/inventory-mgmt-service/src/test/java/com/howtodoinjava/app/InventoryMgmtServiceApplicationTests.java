package com.howtodoinjava.app;

import com.howtodoinjava.app.dao.ItemRepository;
import com.howtodoinjava.app.dao.entity.Item;
import com.howtodoinjava.app.dao.entity.ItemCategory;
import com.howtodoinjava.app.dao.entity.ItemDetail;
import com.howtodoinjava.app.dao.entity.Seller;
import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InventoryMgmtServiceApplicationTests {

	@Autowired private TestEntityManager testEntityManager;
	@Autowired private DataSource dataSource;
	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private EntityManager entityManager;
	@Autowired private ItemRepository itemRepository;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(dataSource);
		Assertions.assertNotNull(jdbcTemplate);
		Assertions.assertNotNull(entityManager);
		Assertions.assertNotNull(testEntityManager);
		Assertions.assertNotNull(itemRepository);
	}

	@Test
	void testInsertDelete() {

		Seller seller = new Seller(null, "Book Publishing Co.", true);
		ItemDetail itemDetail = new ItemDetail(null, seller, ItemCategory.BOOKS);
		Item item = new Item(null, "Harry Potter", "Fiction and Magic for Kids", 100, itemDetail, true);

		testEntityManager.persist(seller);
		testEntityManager.persist(itemDetail);
		testEntityManager.persist(item);
		testEntityManager.flush();

    //Detach so we can query fresh from database
		testEntityManager.detach(item);

		item = testEntityManager.find(Item.class, item.getId());

		Assertions.assertEquals("Harry Potter", item.getName());
		Assertions.assertEquals("Book Publishing Co.", item.getItemDetail().getSeller().getName());
		Assertions.assertEquals(ItemCategory.BOOKS, item.getItemDetail().getCategory());
	}

}
