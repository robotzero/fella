package com.queen.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Date;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = MonitorRequest.PeriodMonitorRequest.class, name = "periodMonitorRequest")
})
@JsonTypeName("monitorRequest")
public sealed interface MonitorRequest {
	//@NOTE the application will have logic if this is first day of period or next day and the day after. If user forget to send the data by the end of the day, the app will do it.
	//Meaning it will create record in the cache db at midnight and sync the data. Pain level will be taken from previous day.
	//PUT endpoint will be created for modifying pain levels in the app retrospectively.
	@JsonTypeName("periodMonitorRequest")
	record PeriodMonitorRequest(Integer painLevel, String notes, Date startDate) implements MonitorRequest {}
	record StomachRequest() implements MonitorRequest {}
	record MigraneRequest() implements MonitorRequest {}
}
