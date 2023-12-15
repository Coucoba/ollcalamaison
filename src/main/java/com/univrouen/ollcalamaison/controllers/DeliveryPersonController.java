package com.univrouen.ollcalamaison.controllers;

import com.univrouen.ollcalamaison.dto.DeliveryPersonDto;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.services.DeliveryPersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/delivreryperson")
public class DeliveryPersonController {

    private DeliveryPersonService deliveryPersonService;

    @GetMapping()
    public ResponseEntity<List<DeliveryPersonDto>> getAllDeliveryPersons(){
        List<DeliveryPersonDto> deliveryPersons = deliveryPersonService.findAllDeliveryPerson();
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
}
