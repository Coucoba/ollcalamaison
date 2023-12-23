package com.univrouen.ollcalamaison.controllers;

import com.univrouen.ollcalamaison.dto.SearchResultDto;
import com.univrouen.ollcalamaison.dto.input.InputDeliveryPersonDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryPersonDto;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import com.univrouen.ollcalamaison.services.DeliveryPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/delivrerypersons")
@Tag(name = "DeliveryPerson", description = "The delivery person Api")
public class DeliveryPersonController{

    private DeliveryPersonService deliveryPersonService;

    @Operation(
            summary = "Find all delivery persons",
            description = "Find all delivery person entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping()
    public ResponseEntity<SearchResultDto<DeliveryPersonDto>> getAllDeliveryPersonsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        SearchResultDto<DeliveryPersonDto> deliveryPersons = deliveryPersonService.findAllDeliveryPersonsPaged(page, size, sortDirection, sortBy);
        return new ResponseEntity<>(deliveryPersons, HttpStatus.OK);
    }

    @Operation(
            summary = "Add a new delivery person",
            description = "Add a new delivery person to the list of delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful added a delivery person"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @PostMapping()
    public ResponseEntity<DeliveryPersonDto> create(@RequestBody InputDeliveryPersonDto deliveryPersonDto) throws DtoNotValidException {
        var deliveryPerson = deliveryPersonService.createDeliveryPerson(deliveryPersonDto);
        return new ResponseEntity<>(deliveryPerson, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Find a delivery person",
            description = "Find a delivery person entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPersonDto> getDeliveryPersonById(@PathVariable Long id) throws DeliveryPersonNotFoundException {
        DeliveryPersonDto deliveryPerson = deliveryPersonService.findByIdDeliveryPerson(id);
        return new ResponseEntity<>(deliveryPerson, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPersonDto> updateDeliveryPersonById(@PathVariable Long id, @RequestBody InputDeliveryPersonDto deliveryPersonDto) throws DeliveryPersonNotFoundException, DtoNotValidException {
        DeliveryPersonDto updateDeliveryPerson = deliveryPersonService.updateByIdDeliveryPerson(deliveryPersonDto, id);
        return new ResponseEntity<>(updateDeliveryPerson, HttpStatus.OK);
    }

    @Operation(summary = "Delete a delivery person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryPersonById(@PathVariable Long id) throws DeliveryPersonNotFoundException {
        deliveryPersonService.deleteByIdDeliveryPerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Find a  delivery person with filter",
            description = "Filter can be one or multiple on isAvailable, BeforeDate, AfterDate or between two dates"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    @GetMapping("/filter")
    public ResponseEntity<SearchResultDto<DeliveryPersonDto>> findDeliveryPersonsWithFilter(
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) Instant createdAfter,
            @RequestParam(required = false) Instant createdBefore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SearchResultDto<DeliveryPersonDto> filteredDeliveryPersons = deliveryPersonService
                .findDeliveryPersonsWithFilter(isAvailable, createdAfter, createdBefore, page, size);
        return new ResponseEntity<>(filteredDeliveryPersons, HttpStatus.OK);
    }

    @Operation(summary = "Find all existing delivery person sorted by number of tours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    @GetMapping("/sorted/tours")
    public ResponseEntity<SearchResultDto<DeliveryPersonDto>> getAllDeliveryPersonsSortedByNumberOfToursPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        SearchResultDto<DeliveryPersonDto> sortedDeliveryPersons = deliveryPersonService.getAllDeliveryPersonsSortedByNumberOfToursPaged(page, size);
        return new ResponseEntity<>(sortedDeliveryPersons, HttpStatus.OK);
    }
}
