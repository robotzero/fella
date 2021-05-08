package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Fields;

import java.util.List;

public interface CreateFieldsPort {
	void createFields(List<Fields> fieldsType);
}
