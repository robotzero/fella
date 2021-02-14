package com.queen.adapters.web;

import com.queen.domain.monitor.Monitor;

public class MonitorToDTO {
	public MonitorDTO toDTO(Monitor monitor) {
		return new MonitorDTO(monitor.id(), monitor.name());
	}
}
