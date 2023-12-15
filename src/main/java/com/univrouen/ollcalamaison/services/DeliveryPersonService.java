package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.DeliveryPersonDto;
import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.repositories.DeliveryPersonRepository;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<DeliveryPersonDto> findAllDeliveryPerson(){

        return deliveryPersonRepository.findAll().stream().map(e -> modelMapper.map(e, DeliveryPersonDto.class)).toList();
    }

    public DeliveryPersonDto findByIdDeliveryPerson(Long id) throws DeliveryPersonNotFoundException {
        if(!deliveryPersonRepository.existsById(id)){
            throw new DeliveryPersonNotFoundException();
        }
        return modelMapper.map(deliveryPersonRepository.findById(id), DeliveryPersonDto.class);
    }

    public List<DeliveryPersonDto> findByIsAvailable() {
        return deliveryPersonRepository.findBy()
    }

    public void deleteByIdDeliveryPerson(Long id) throws DeliveryPersonNotFoundException{
        if(!deliveryPersonRepository.existsById(id)){
            throw new DeliveryPersonNotFoundException();
        }
        deliveryPersonRepository.deleteById(id);
    }

    public DeliveryPersonDto updateByIdDeliveryPerson(DeliveryPersonDto deliveryPersonDto, Long id) throws DeliveryPersonNotFoundException {
        if(!deliveryPersonRepository.existsById(id)){
            throw new DeliveryPersonNotFoundException();
        }
        DeliveryPersonEntity actualDeliveryPeronEntity =
                deliveryPersonRepository.findById(id).orElseThrow(DeliveryPersonNotFoundException::new);

        actualDeliveryPeronEntity.setAvailable(deliveryPersonDto.isAvailable());
        actualDeliveryPeronEntity.setFirst_name(deliveryPersonDto.getFirstName());
        actualDeliveryPeronEntity.setLast_name(deliveryPersonDto.getLastName());

        DeliveryPersonEntity updateDeliveryPersonEntity = deliveryPersonRepository.save(actualDeliveryPeronEntity);

        return modelMapper.map(updateDeliveryPersonEntity, DeliveryPersonDto.class);
    }
}
