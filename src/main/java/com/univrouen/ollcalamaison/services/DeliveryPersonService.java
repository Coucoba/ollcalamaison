package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.DeliveryPersonDto;
import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import com.univrouen.ollcalamaison.exceptions.TourNotFoundException;
import com.univrouen.ollcalamaison.repositories.DeliveryPersonRepository;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeliveryPersonService {

    private DeliveryPersonRepository deliveryPersonRepository;
    private ModelMapper modelMapper;
    private Validator validator;


    public DeliveryPersonDto createDeliveryPerson(DeliveryPersonDto dto) throws DtoNotValidException {
        validateDto(dto);

        DeliveryPersonEntity entity = modelMapper.map(dto, DeliveryPersonEntity.class);
        return modelMapper.map(deliveryPersonRepository.save(entity), DeliveryPersonDto.class);
    }

    public Page<DeliveryPersonDto> findAllDeliveryPersonsPaged(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<DeliveryPersonEntity> deliveryPersonPage = deliveryPersonRepository.findAll(pageRequest);

        return deliveryPersonPage.map(entity -> modelMapper.map(entity, DeliveryPersonDto.class));
    }

    public DeliveryPersonDto findByIdDeliveryPerson(Long id) throws DeliveryPersonNotFoundException {
        checkDeliveryPersonExists(id);
        return modelMapper.map(deliveryPersonRepository.findById(id), DeliveryPersonDto.class);
    }

    public void deleteByIdDeliveryPerson(Long id) throws DeliveryPersonNotFoundException{
        checkDeliveryPersonExists(id);
        deliveryPersonRepository.deleteById(id);
    }

    public DeliveryPersonDto updateByIdDeliveryPerson(DeliveryPersonDto deliveryPersonDto, Long id) throws DeliveryPersonNotFoundException, DtoNotValidException {
        checkDeliveryPersonExists(id);
        validateDto(deliveryPersonDto);

        DeliveryPersonEntity actualDeliveryPeronEntity =
                deliveryPersonRepository.findById(id).orElseThrow(DeliveryPersonNotFoundException::new);

        actualDeliveryPeronEntity.setAvailable(deliveryPersonDto.isAvailable());
        actualDeliveryPeronEntity.setFirst_name(deliveryPersonDto.getFirstName());
        actualDeliveryPeronEntity.setLast_name(deliveryPersonDto.getLastName());

        DeliveryPersonEntity updateDeliveryPersonEntity = deliveryPersonRepository.save(actualDeliveryPeronEntity);

        return modelMapper.map(updateDeliveryPersonEntity, DeliveryPersonDto.class);
    }

    public List<DeliveryPersonDto> findDeliveryPersonsWithFilter(Boolean isAvailable, Instant createdAfter, Instant createdBefore) {
        List<DeliveryPersonEntity> filteredPersons = deliveryPersonRepository.findAll()
                .stream()
                .filter(person -> (isAvailable == null || person.isAvailable() == isAvailable)
                        && (createdAfter == null || person.getCreation().isAfter(createdAfter))
                        && (createdBefore == null || person.getCreation().isBefore(createdBefore)))
                .toList();

        return filteredPersons.stream()
                .map(entity -> modelMapper.map(entity, DeliveryPersonDto.class))
                .collect(Collectors.toList());
    }

    public Page<DeliveryPersonDto> getAllDeliveryPersonsSortedByNamePaged(int page, int size) {
        Sort sort = Sort.by(
                Sort.Order.asc("last_name"),
                Sort.Order.asc("first_name")
        );

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<DeliveryPersonEntity> sortedPersonsPage = deliveryPersonRepository.findAll(pageRequest);

        return sortedPersonsPage.map(entity -> modelMapper.map(entity, DeliveryPersonDto.class));
    }

    public Page<DeliveryPersonDto> getAllDeliveryPersonsSortedByCreationPaged(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("creation"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<DeliveryPersonEntity> sortedPersonsPage = deliveryPersonRepository.findAll(pageRequest);

        return sortedPersonsPage.map(entity -> modelMapper.map(entity, DeliveryPersonDto.class));
    }

    private <T> void validateDto(T dto) throws DtoNotValidException{
        var exception = validator.validate(dto);
        if(!exception.isEmpty()){
            throw new DtoNotValidException();
        }
    }

    private void checkDeliveryPersonExists(Long id) throws DeliveryPersonNotFoundException {
        if(!deliveryPersonRepository.existsById(id)){
            throw new DeliveryPersonNotFoundException();
        }
    }
}
