package com.queen.adapters.web.dto;

public class PeriodToDTOMapper {
	public PeriodDTO mapToServiceDTO(final String userId, final PeriodRequest periodRequest) {
		return new PeriodDTO(
				periodRequest.startDateOrNow(),
				periodRequest.endDate()
		);
	}
}
