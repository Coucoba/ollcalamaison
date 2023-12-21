package com.univrouen.ollcalamaison.controllers;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryDto;
import com.univrouen.ollcalamaison.dto.output.TourDto;
import com.univrouen.ollcalamaison.services.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/deliveries")
@Tag(name = "Delivery", description = "The delivery Api")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Operation(
            summary = "Get all delivery",
            description = "Get all existing delivery"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping()
    public ResponseEntity<Page<DeliveryDto>> getAllDeliveries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(deliveryService.getAllDeliveries(page, size));
    }

    @Operation(
            summary = "Create a delivery",
            description = "Create a delivery with given information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful created"),
            @ApiResponse(responseCode = "405", description = "invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<DeliveryDto> create(InputDeliveryDto inputDeliveryDto) {
        DeliveryDto deliveryDto = deliveryService.createDelivery(inputDeliveryDto);
        return ResponseEntity.created(URI.create("/deliveries/" + deliveryDto.getId()))
                .body(deliveryDto);
    }

    @Operation(
            summary = "Update a delivery",
            description = "Update a delivery with given information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "delivery not found"),
            @ApiResponse(responseCode = "405", description = "invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDto> update(InputDeliveryDto inputDeliveryDto, @PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.updateDelivery(inputDeliveryDto, id));
    }

    @Operation(
            summary = "Delete a delivery",
            description = "Delete a delivery with given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "delivery not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Find a delivery",
            description = "Find a delivery with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "delivery not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.findById(id));
    }



}
