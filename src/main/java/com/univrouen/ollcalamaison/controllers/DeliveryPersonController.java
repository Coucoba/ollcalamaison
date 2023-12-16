package com.univrouen.ollcalamaison.controllers;

import com.univrouen.ollcalamaison.dto.DeliveryPersonDto;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.services.DeliveryPersonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/delivrerypersons")
public class DeliveryPersonController {

    private DeliveryPersonService deliveryPersonService;

    @GetMapping()
    public ResponseEntity<Page<DeliveryPersonDto>> getAllDeliveryPersonsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<DeliveryPersonDto> deliveryPersons = deliveryPersonService.findAllDeliveryPersonsPaged(page, size);
        return new ResponseEntity<>(deliveryPersons, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<DeliveryPersonDto> create(@RequestBody DeliveryPersonDto deliveryPersonDto){
        var deliveryPerson = deliveryPersonService.createDeliveryPerson(deliveryPersonDto);
        return new ResponseEntity<>(deliveryPerson, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPersonDto> getDeliveryPersonById(@PathVariable Long id) throws DeliveryPersonNotFoundException {
        DeliveryPersonDto deliveryPerson = deliveryPersonService.findByIdDeliveryPerson(id);
        return new ResponseEntity<>(deliveryPerson, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPersonDto> updateDeliveryPersonById(@PathVariable Long id, @RequestBody DeliveryPersonDto deliveryPersonDto) throws DeliveryPersonNotFoundException {
        DeliveryPersonDto updateDeliveryPerson = deliveryPersonService.updateByIdDeliveryPerson(deliveryPersonDto, id);
        return new ResponseEntity<>(updateDeliveryPerson, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryPersonById(@PathVariable Long id) throws DeliveryPersonNotFoundException {
        deliveryPersonService.deleteByIdDeliveryPerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DeliveryPersonDto>> findDeliveryPersonsWithFilter(
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) Instant createdAfter,
            @RequestParam(required = false) Instant createdBefore
    ) {
        List<DeliveryPersonDto> filteredDeliveryPersons = deliveryPersonService
                .findDeliveryPersonsWithFilter(isAvailable, createdAfter, createdBefore);
        return new ResponseEntity<>(filteredDeliveryPersons, HttpStatus.OK);
    }

    @GetMapping("/sorted/name")
    public ResponseEntity<Page<DeliveryPersonDto>> getAllDeliveryPersonsSortedByNamePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<DeliveryPersonDto> sortedDeliveryPersons = deliveryPersonService.getAllDeliveryPersonsSortedByNamePaged(page, size);
        return new ResponseEntity<>(sortedDeliveryPersons, HttpStatus.OK);
    }

    @GetMapping("/sorted/creation")
    public ResponseEntity<Page<DeliveryPersonDto>> getAllDeliveryPersonsSortedByCreationPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<DeliveryPersonDto> sortedDeliveryPersons = deliveryPersonService.getAllDeliveryPersonsSortedByCreationPaged(page, size);
        return new ResponseEntity<>(sortedDeliveryPersons, HttpStatus.OK);
    }
}
