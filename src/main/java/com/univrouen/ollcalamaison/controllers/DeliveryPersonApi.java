package com.univrouen.ollcalamaison.controllers;

import com.univrouen.ollcalamaison.dto.DeliveryPersonDto;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@Tag(name = "DeliveryPerson", description = "The delivery person Api")
public interface DeliveryPersonApi {

    @Operation(
            summary = "Find all delivery persons",
            description = "Find all delivery person entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<Page<DeliveryPersonDto>> getAllDeliveryPersonsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "Add a new delivery person",
            description = "Add a new delivery person to the list of delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful added a delivery person"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    ResponseEntity<DeliveryPersonDto> create(@RequestBody DeliveryPersonDto deliveryPersonDto) throws DtoNotValidException;

    @Operation(
            summary = "Find a delivery person",
            description = "Find a delivery person entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    ResponseEntity<DeliveryPersonDto> getDeliveryPersonById(@PathVariable Long id) throws DeliveryPersonNotFoundException;

    @Operation(summary = "Update an existing delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")
    })
   ResponseEntity<DeliveryPersonDto> updateDeliveryPersonById(@PathVariable Long id, @RequestBody DeliveryPersonDto deliveryPersonDto) throws DeliveryPersonNotFoundException;

    @Operation(summary = "Delete a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    ResponseEntity<Void> deleteDeliveryPersonById(@PathVariable Long id) throws DeliveryPersonNotFoundException;

    @Operation(
            summary = "Find a  delivery person with filter",
            description = "Filter can be one or multiple on isAvailable, BeforeDate, AfterDate or between two dates"
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    ResponseEntity<List<DeliveryPersonDto>> findDeliveryPersonsWithFilter(
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) Instant createdAfter,
            @RequestParam(required = false) Instant createdBefore
    );

    @Operation(summary = "Find all existing delivery person sorted by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    ResponseEntity<Page<DeliveryPersonDto>> getAllDeliveryPersonsSortedByNamePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Find all existing delivery person sorted by creation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    ResponseEntity<Page<DeliveryPersonDto>> getAllDeliveryPersonsSortedByCreationPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );
}
