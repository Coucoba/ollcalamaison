package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.output.DeliveryPersonDto;
import com.univrouen.ollcalamaison.dto.input.InputDeliveryPersonDto;
import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeliveryPersonService {

    private DeliveryPersonRepository deliveryPersonRepository;
    private ModelMapper modelMapper;
    private Validator validator;


    public DeliveryPersonDto createDeliveryPerson(InputDeliveryPersonDto dto) throws DtoNotValidException {
        validateDto(dto);
        DeliveryPersonEntity deliveryPerson = modelMapper.map(dto, DeliveryPersonEntity.class);
        deliveryPerson.setCreation(Instant.now());
        return modelMapper.map(deliveryPersonRepository.save(deliveryPerson), DeliveryPersonDto.class);
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

    public DeliveryPersonDto updateByIdDeliveryPerson(InputDeliveryPersonDto deliveryPersonDto, Long id) throws DeliveryPersonNotFoundException, DtoNotValidException {
        validateDto(deliveryPersonDto);

        DeliveryPersonEntity actualDeliveryPeronEntity =
                deliveryPersonRepository.findById(id).orElseThrow(DeliveryPersonNotFoundException::new);

        actualDeliveryPeronEntity.setAvailable(deliveryPersonDto.isAvailable());
        actualDeliveryPeronEntity.setFirst_name(deliveryPersonDto.getFirst_Name());
        actualDeliveryPeronEntity.setLast_name(deliveryPersonDto.getLast_Name());

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
        PageRequest pageRequest = PageRequest.of(page, size);
        return deliveryPersonRepository.findAllByOrderByLastNameDesc(pageRequest).map(e -> modelMapper.map(e, DeliveryPersonDto.class));
    }

    public Page<DeliveryPersonDto> getAllDeliveryPersonsSortedByCreationPaged(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return deliveryPersonRepository.findAllByOrderByCreationDesc(pageRequest).map(e -> modelMapper.map(e, DeliveryPersonDto.class));
    }

    public Page<DeliveryPersonDto> getAllDeliveryPersonsSortedByNumberOfToursPaged(int page, int size) {


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
