package com.queen.domain.fieldtype;

import java.util.Arrays;

public enum FieldTypeValue {
	DATE(1), PAIN_LEVEL(2), FLOW_LEVEL(3), NOTES(4), TAGS(5);

	private final int type;

	FieldTypeValue(final int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static FieldTypeValue getByType(final int type) {
		return Arrays.stream(values()).filter(value -> {
			return value.type == type;
		}).findFirst().orElseThrow(() -> new RuntimeException("Unrecognized type for field type value"));
	}
}
