package com.queen.adapters.web.dto;

import java.util.Date;

//@NOTE the application will have logic if this is first day of period or next day and the day after. If user forget to send the data by the end of the day, the app will do it.
//Meaning it will create record in the cache db at midnight and sync the data. Pain level will be taken from previous day.
//PUT endpoint will be created for modifying pain levels in the app retrospectively.
public record PeriodMonitorRequest(Integer painLevel, String notes, Date startDate) {
}
