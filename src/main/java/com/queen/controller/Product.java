package com.queen.controller;

import java.io.Serializable;

public class Product implements Serializable {
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
