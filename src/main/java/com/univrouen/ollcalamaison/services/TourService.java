package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.dto.input.InputTourDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryPersonDto;
import com.univrouen.ollcalamaison.dto.output.TourDto;
import com.univrouen.ollcalamaison.entities.DeliveryEntity;
import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.entities.TourEntity;
import com.univrouen.ollcalamaison.exceptions.DeliveryPersonNotFoundException;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import com.univrouen.ollcalamaison.exceptions.OverlappingTourException;
import com.univrouen.ollcalamaison.exceptions.TourNotFoundException;
import com.univrouen.ollcalamaison.repositories.DeliveryPersonRepository;
import com.univrouen.ollcalamaison.repositories.TourRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TourService {

    private TourRepository tourRepository;
    private DeliveryPersonRepository deliveryPersonRepository;
    private ModelMapper modelMapper;
    private Validator validator;

    public TourDto createTour(InputTourDto tourDto) throws DtoNotValidException {
        validateDto(tourDto);
        TourEntity entity = modelMapper.map(tourDto, TourEntity.class);
        return modelMapper.map(tourRepository.save(entity), TourDto.class);
    }

    public TourDto updateTourById(InputTourDto tourDto, Long id) throws TourNotFoundException, DtoNotValidException, OverlappingTourException {
        validateDto(tourDto);
        TourEntity actualTourEntity =
                tourRepository.findById(id).orElseThrow(TourNotFoundException::new);

        actualTourEntity.setName(tourDto.getName());
        actualTourEntity.setStartDate(tourDto.getStartDate());
        actualTourEntity.setEndDate(tourDto.getEndDate());

        TourEntity updateTourEntity = tourRepository.save(actualTourEntity);

        return modelMapper.map(updateTourEntity, TourDto.class);
    }

    public void deleteByIdTour(Long id) throws TourNotFoundException{
        checkTourExists(id);
        tourRepository.deleteById(id);
    }

    public TourDto addDeliveryToTour(Long id, List<InputDeliveryDto> deliveryDtos) throws TourNotFoundException, DtoNotValidException {
        validateDto(deliveryDtos);
        checkTourExists(id);

        TourEntity tourEntity = tourRepository.findById(id)
                .orElseThrow(TourNotFoundException::new);

        List<DeliveryEntity> deliveryEntities = deliveryDtos.stream()
                .map(deliveryDto -> {
                    DeliveryEntity deliveryEntity = modelMapper.map(deliveryDto, DeliveryEntity.class);
                    deliveryEntity.setTour(tourEntity);
                    return deliveryEntity;
                })
                .toList();

        tourEntity.getDeliveries().addAll(deliveryEntities);
        tourRepository.save(tourEntity);

        return modelMapper.map(tourEntity, TourDto.class);
    }

    public Page<TourDto> getToursByDeliveryPersonPaged(String name, int page, int size) throws DeliveryPersonNotFoundException {
        PageRequest pageRequest = PageRequest.of(page, size);
        DeliveryPersonEntity deliveryPerson = deliveryPersonRepository.findByName(name).orElseThrow(DeliveryPersonNotFoundException::new);
        return tourRepository.findAllByDeliveryPerson(deliveryPerson, pageRequest).map(e -> modelMapper.map(e, TourDto.class));
    }

    public TourDto associateTourWithDeliveryPerson(Long tourId, Long deliveryPersonId) throws TourNotFoundException, DeliveryPersonNotFoundException, OverlappingTourException {
        TourEntity tourEntity = tourRepository.findById(tourId)
                .orElseThrow(TourNotFoundException::new);

        DeliveryPersonEntity deliveryPersonEntity = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(DeliveryPersonNotFoundException::new);

        boolean hasOverlappingTour = deliveryPersonEntity.getTours().stream()
                .anyMatch(existingTour ->
                        (tourEntity.getStartDate().isBefore(existingTour.getEndDate()) || tourEntity.getStartDate().equals(existingTour.getEndDate()))
                                && (tourEntity.getEndDate().isAfter(existingTour.getStartDate()) || tourEntity.getEndDate().equals(existingTour.getStartDate())));

        if (hasOverlappingTour) {
            throw new OverlappingTourException();
        }

        tourEntity.setDeliveryPerson(deliveryPersonEntity);

        TourEntity updatedTourEntity = tourRepository.save(tourEntity);

        return modelMapper.map(updatedTourEntity, TourDto.class);
    }

    public Page<TourDto> getToursByDate(Instant searchDate, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return tourRepository.findByDate(searchDate, pageRequest).map(e -> modelMapper.map(e, TourDto.class));
    }

    public TourDto findTourById(Long id) throws TourNotFoundException{
        return modelMapper.map(tourRepository.findById(id).orElseThrow(TourNotFoundException::new), TourDto.class);
    }

    public Page<TourDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return tourRepository.findAll(pageRequest).map(e -> modelMapper.map(e, TourDto.class));
    }

    private <T> void validateDto(T dto) throws DtoNotValidException{
        var exception = validator.validate(dto);
        if(!exception.isEmpty()){
            throw new DtoNotValidException();
        }
    }

    private void checkTourExists(Long id) throws TourNotFoundException{
        if(!tourRepository.existsById(id)){
            throw new TourNotFoundException();
        }
    }
}
