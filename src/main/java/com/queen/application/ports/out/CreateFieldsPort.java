package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Fields;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CreateFieldsPort {
	void createFields(@NotNull List<Fields> fieldsType);
}
