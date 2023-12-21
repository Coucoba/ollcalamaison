package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryDto;
import com.univrouen.ollcalamaison.entities.DeliveryEntity;
import com.univrouen.ollcalamaison.exceptions.DeliveryNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import com.univrouen.ollcalamaison.repositories.DeliveryRepository;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryService {

    private DeliveryRepository deliveryRepository;
    private Validator validator;
    private ModelMapper modelMapper;

    public DeliveryDto createDelivery(InputDeliveryDto inputDeliveryDto) {
        validateDto(inputDeliveryDto);
        DeliveryEntity deliveryEntity = modelMapper.map(inputDeliveryDto, DeliveryEntity.class);
        return modelMapper.map(deliveryRepository.save(deliveryEntity), DeliveryDto.class);
    }

    public DeliveryDto updateDelivery (InputDeliveryDto inputDeliveryDto, Long id) {
        validateDto(inputDeliveryDto);
        DeliveryEntity deliveryEntity = deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
        deliveryEntity.setDepositAddress(inputDeliveryDto.getDepositAddress());
        deliveryEntity.setPickupAddress(inputDeliveryDto.getPickupAddress());
        return modelMapper.map(deliveryRepository.save(deliveryEntity), DeliveryDto.class);
    }

    public void deleteDelivery (Long id) {
        if (deliveryRepository.existsById(id)) {
            deliveryRepository.deleteById(id);
        } else {
            throw new DeliveryNotFoundException();
        }
    }

    public Page<DeliveryDto> getAllDeliveries(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return deliveryRepository.findAll(request).map(d -> modelMapper.map(d, DeliveryDto.class));
    }

    public DeliveryDto findById(Long id) {
        return modelMapper.map(deliveryRepository.findById(id), DeliveryDto.class);
    }

    private <T> void validateDto(T dto) throws DtoNotValidException {
        var exception = validator.validate(dto);
        if(!exception.isEmpty()){
            throw new DtoNotValidException();
        }
    }
}
