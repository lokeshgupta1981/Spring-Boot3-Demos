package com.howtodoinjava.app.dao.entity;

public enum ItemCategory {

  FASHION(1), FOOD(2), ELECTRONICS(3), MOBILE(4), BOOKS(5);

  int id;

  ItemCategory(int id) {
    this.id = id;
  }
}
