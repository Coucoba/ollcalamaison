package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.dto.input.InputTourDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryDto;
import com.univrouen.ollcalamaison.dto.output.DeliveryPersonDto;
import com.univrouen.ollcalamaison.dto.output.DeliverySimplifiedDto;
import com.univrouen.ollcalamaison.dto.output.TourDto;
import com.univrouen.ollcalamaison.entities.DeliveryEntity;
import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.entities.TourEntity;
import com.univrouen.ollcalamaison.exceptions.*;
import com.univrouen.ollcalamaison.repositories.DeliveryPersonRepository;
import com.univrouen.ollcalamaison.repositories.DeliveryRepository;
import com.univrouen.ollcalamaison.repositories.TourRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TourService {

    private TourRepository tourRepository;
    private DeliveryPersonRepository deliveryPersonRepository;
    private DeliveryRepository deliveryRepository;
    private ModelMapper modelMapper;
    private Validator validator;

    public TourDto createTour(InputTourDto tourDto) throws DtoNotValidException {
        validateDto(tourDto);
        TourEntity entity = modelMapper.map(tourDto, TourEntity.class);
        TourEntity savedTourEntity = tourRepository.save(entity);
        return TourDto.builder()
                .id(savedTourEntity.getId())
                .name(savedTourEntity.getName())
                .startDate(savedTourEntity.getStartDate())
                .endDate(savedTourEntity.getEndDate())
                .deliveries(
                        savedTourEntity.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                )
                .build();
    }

    public TourDto updateTourById(InputTourDto tourDto, Long id) throws TourNotFoundException, DtoNotValidException, OverlappingTourException {
        validateDto(tourDto);
        TourEntity actualTourEntity =
                tourRepository.findById(id).orElseThrow(TourNotFoundException::new);

        actualTourEntity.setName(tourDto.getName());
        actualTourEntity.setStartDate(tourDto.getStartDate());
        actualTourEntity.setEndDate(tourDto.getEndDate());

        TourEntity updateTourEntity = tourRepository.save(actualTourEntity);

        return TourDto.builder()
                .id(updateTourEntity.getId())
                .name(updateTourEntity.getName())
                .startDate(updateTourEntity.getStartDate())
                .endDate(updateTourEntity.getEndDate())
                .deliveries(
                        updateTourEntity.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                )
                .build();
    }

    public void deleteByIdTour(Long id) throws TourNotFoundException{
        checkTourExists(id);
        tourRepository.deleteById(id);
    }

    public TourDto addDeliveryToTour(Long id, Long deliveryId) throws TourNotFoundException, DtoNotValidException {

        TourEntity tourEntity = tourRepository.findById(id)
                .orElseThrow(TourNotFoundException::new);

        DeliveryEntity deliveryEntity = deliveryRepository.findById(deliveryId).orElseThrow(DeliveryNotFoundException::new);
        if (deliveryEntity.getTour() != null) {
            throw new DeliveryAlreadyAssignedException();
        }
        deliveryEntity.setTour(tourEntity);

        tourEntity.getDeliveries().add(deliveryEntity);
        TourEntity savedTourEntity =  tourRepository.save(tourEntity);

        return TourDto.builder()
                .id(savedTourEntity.getId())
                .name(savedTourEntity.getName())
                .startDate(savedTourEntity.getStartDate())
                .endDate(savedTourEntity.getEndDate())
                .deliveries(
                        savedTourEntity.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                )
                .build();
    }

    public Page<TourDto> getToursByDeliveryPersonPaged(String name, int page, int size) throws DeliveryPersonNotFoundException {
        PageRequest pageRequest = PageRequest.of(page, size);
        DeliveryPersonEntity deliveryPerson = deliveryPersonRepository.findByName(name).orElseThrow(DeliveryPersonNotFoundException::new);
        return tourRepository.findAllByDeliveryPerson(deliveryPerson, pageRequest).map(e -> modelMapper.map(e, TourDto.class));
    }

    public TourDto assignTourToDeliveryPerson(Long tourId, Long deliveryPersonId) throws TourNotFoundException, DeliveryPersonNotFoundException, OverlappingTourException {
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
        deliveryPersonEntity.getTours().add(tourEntity);
        TourEntity updatedTourEntity = tourRepository.save(tourEntity);

        return TourDto.builder()
                .id(updatedTourEntity.getId())
                .name(updatedTourEntity.getName())
                .startDate(updatedTourEntity.getStartDate())
                .endDate(updatedTourEntity.getEndDate())
                .deliveries(
                        updatedTourEntity.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                )
                .build();
    }

    public Page<TourDto> getToursByDate(Instant searchDate, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TourEntity> tourEntities = tourRepository.findByDate(searchDate, pageRequest);
        return tourEntities.map(
                e -> TourDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .deliveries(
                                e.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                        )
                        .build()

        );
    }

    public TourDto findTourById(Long id) throws TourNotFoundException{
        return modelMapper.map(tourRepository.findById(id).orElseThrow(TourNotFoundException::new), TourDto.class);
    }

    public Page<TourDto> getAllPaged(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TourEntity> tourEntities = tourRepository.findAll(pageRequest);
        return
                tourEntities.map(
                        e -> TourDto.builder()
                                .id(e.getId())
                                .name(e.getName())
                                .startDate(e.getStartDate())
                                .endDate(e.getEndDate())
                                .deliveries(
                                        e.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                                )
                                .build()

                );
    }

    public List<TourDto> getAll() {
        List<TourEntity> tourList = tourRepository.findAll();
        return
        tourList.stream().map(
                e -> TourDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .deliveries(
                                e.getDeliveries().stream().map(d -> modelMapper.map(d, DeliverySimplifiedDto.class)).toList()
                        )
                        .build()

        ).toList();

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
