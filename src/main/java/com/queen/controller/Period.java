package com.queen.controller;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Period {
	@Id
	@Column("idperiod")
  private Long id;
	@Column("iduser")
  private final Long userId;

  public Period(Long userId) {
  	this.userId = userId;
  }

	public Long getUserId() {
		return userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
