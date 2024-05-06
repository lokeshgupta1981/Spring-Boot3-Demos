package com.howtodoinjava.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.dao.model.Item;
import com.howtodoinjava.dao.model.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ItemRepository itemRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllItems() throws Exception {
    when(itemRepository.findAll()).thenReturn(Arrays.asList(new Item(), new Item()));

    mockMvc.perform(MockMvcRequestBuilders.get("/items")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
  }

  @Test
  public void testGetItemById() throws Exception {
    Item item = new Item();
    item.setId(1L);
    when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

    mockMvc.perform(MockMvcRequestBuilders.get("/items/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void testGetItemById_NotFound() throws Exception {
    when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get("/items/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateNewItem() throws Exception {
    Item newItem = new Item();
    newItem.setName("Test Item");

    when(itemRepository.save(any(Item.class))).thenReturn(newItem);

    mockMvc.perform(MockMvcRequestBuilders.post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newItem)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
  }

  @Test
  public void testUpdateOrCreateItem() throws Exception {
    Item newItem = new Item();
    newItem.setId(1L);
    newItem.setName("Updated Item");

    when(itemRepository.findById(1L)).thenReturn(Optional.of(new Item()));
    when(itemRepository.save(any(Item.class))).thenReturn(newItem);

    mockMvc.perform(MockMvcRequestBuilders.put("/items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newItem)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Item"));
  }

  @Test
  public void testDeleteItem() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/items/1"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
