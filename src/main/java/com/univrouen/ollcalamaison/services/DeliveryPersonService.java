package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.DeliveryPersonDto;
import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.repositories.DeliveryPersonRepository;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryPersonService {

    private DeliveryPersonRepository deliveryPersonRepository;
    private ModelMapper modelMapper;
    private Validator validator;

    public DeliveryPersonDto createDeliveryPerson(DeliveryPersonDto dto) {
        var exception = validator.validate(dto);
        DeliveryPersonEntity entity = modelMapper.map(dto, DeliveryPersonEntity.class);
        return modelMapper.map(deliveryPersonRepository.save(entity), DeliveryPersonDto.class);
    }

}
