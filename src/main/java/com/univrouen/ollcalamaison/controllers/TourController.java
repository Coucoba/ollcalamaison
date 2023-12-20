package com.univrouen.ollcalamaison.controllers;

import com.univrouen.ollcalamaison.dto.input.InputTourDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryPersonDto;
import com.univrouen.ollcalamaison.dto.output.TourDto;
import com.univrouen.ollcalamaison.entities.TourEntity;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import com.univrouen.ollcalamaison.exceptions.OverlappingTourException;
import com.univrouen.ollcalamaison.exceptions.TourNotFoundException;
import com.univrouen.ollcalamaison.services.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/tours")
@Tag(name = "Tours", description = "The tours person Api")
public class TourController {

    private TourService tourService;

    @Operation(
            summary = "Find all tours",
            description = "Find all tours entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping()
    public ResponseEntity<Page<TourDto>> getAllToursPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TourDto> tours = tourService.getAll(page, size);
        return new ResponseEntity<>(tours, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a tour",
            description = "Create a tour with given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation"),
            @ApiResponse(responseCode = "405", description = "invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<TourDto> createTour(InputTourDto inputTourDto) throws DtoNotValidException {
        TourDto tourDto = tourService.createTour(inputTourDto);
        return ResponseEntity.created(URI.create("/tours/" + tourDto.getId()))
        .body(tourDto);
    }

    @Operation(
            summary = "Update a tour",
            description = "Update a tour with given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Tour not found"),
            @ApiResponse(responseCode = "405", description = "invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TourDto> updateTour(InputTourDto inputTourDto, @PathVariable Long id) throws DtoNotValidException, TourNotFoundException, OverlappingTourException {
        return ResponseEntity.ok(tourService.updateTourById(inputTourDto,id));
    }

    @Operation(
            summary = "Delete a tour",
            description = "Delete a tour with given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Invalid Id")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) throws TourNotFoundException {
        tourService.deleteByIdTour(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Find a tour",
            description = "Find a tour with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Invalid Id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TourDto> findById(@PathVariable Long id) throws TourNotFoundException {
        return ResponseEntity.ok(tourService.findTourById(id));
    }

    @GetMapping("/deliveryPersonTour")
    public ResponseEntity<Page<TourDto>> findTourByDeliveryPerson(
            String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws DeliveryPersonNotFoundException {
        return ResponseEntity.ok(tourService.getToursByDeliveryPersonPaged(name, page, size));
    }

}
