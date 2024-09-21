package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("periods")
public class Period implements Persistable<String> {
	@Id
	@Column("period_id")
	private String id;
	private Long userId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer cycleLength;

	@Override
	public boolean isNew() {
		return id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}
}
