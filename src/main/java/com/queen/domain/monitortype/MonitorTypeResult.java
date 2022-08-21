package com.queen.domain.monitortype;

import com.queen.domain.fieldtype.FieldType;

import java.util.List;

// @TODO is there a way to remove constructor duplication?
public sealed interface MonitorTypeResult {
	record Period(String id, String name, List<FieldType> fieldTypes) implements MonitorTypeResult {

	}

	record Stomach(String id, String name, List<FieldType> fieldTypes) implements MonitorTypeResult {

	}

	record TabletsTaken(String id, String name, List<FieldType> fieldTypes) implements MonitorTypeResult {

	}
}
