//package com.queen.adapters.web.dto;
//
//import com.queen.adapters.web.controller.MonitorTypeController;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.http.ResponseEntity;
//import reactor.core.publisher.Mono;
//import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
//
//import java.util.List;
//
//public class MonitorTypeMetadataBuilder {
//	public EntityModel<MonitorTypeDTO> getMetadata(final MonitorTypeDTO monitorTypeDTO) {
//		return EntityModel.of(
//				monitorTypeDTO,
//				linkTo(methodOn(MonitorTypeController.class)).withSelfRel()
//	}
//}
