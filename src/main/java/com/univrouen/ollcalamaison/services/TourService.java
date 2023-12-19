package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.DeliveryDto;
import com.univrouen.ollcalamaison.dto.TourDto;
import com.univrouen.ollcalamaison.entities.DeliveryEntity;
import com.univrouen.ollcalamaison.entities.TourEntity;
import com.univrouen.ollcalamaison.exceptions.DtoNotValidException;
import com.univrouen.ollcalamaison.exceptions.OverlappingTourException;
import com.univrouen.ollcalamaison.exceptions.TourNotFoundException;
import com.univrouen.ollcalamaison.repositories.TourRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TourService {

    private TourRepository tourRepository;
    private ModelMapper modelMapper;
    private Validator validator;

    public TourDto createTour(TourDto tourDto) throws DtoNotValidException, OverlappingTourException {
        validateDto(tourDto);
        validateTourDates(tourDto);

        TourEntity entity = modelMapper.map(tourDto, TourEntity.class);
        return modelMapper.map(tourRepository.save(entity), TourDto.class);
    }

    public TourDto updateByIdTour(TourDto tourDto, Long id) throws TourNotFoundException, DtoNotValidException, OverlappingTourException {
        validateDto(tourDto);
        validateTourDates(tourDto);
        checkTourExists(id);

        TourEntity actualTourEntity =
                tourRepository.findById(id).orElseThrow(TourNotFoundException::new);

        actualTourEntity.setName(tourDto.getName());
        actualTourEntity.setStartDate(tourDto.getStart());
        actualTourEntity.setEndDate(tourDto.getEnd());

        TourEntity updateTourEntity = tourRepository.save(actualTourEntity);

        return modelMapper.map(updateTourEntity, TourDto.class);
    }

    public void deleteByIdTour(Long id) throws TourNotFoundException{
        checkTourExists(id);
        tourRepository.deleteById(id);
    }

    public TourDto addDeliveryToTour(Long id, List<DeliveryDto> deliveryDtos) throws TourNotFoundException, DtoNotValidException {
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

    public Page<TourDto> getToursByDeliveryPersonPaged(Long deliveryPersonId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<TourEntity> tourEntities = tourRepository.findByDeliveryPersonId(deliveryPersonId, pageRequest);

        return tourEntities.map(entity -> {
            TourDto tourDto = modelMapper.map(entity, TourDto.class);
            tourDto.setNumberOfDeliveries(entity.getDeliveries().size());
            return tourDto;
        });
    }

    public List<TourDto> getToursByDate(Instant searchDate) {
        List<TourEntity> tourEntities = tourRepository.findByDate(searchDate);

        return tourEntities.stream()
                .map(entity -> modelMapper.map(entity, TourDto.class))
                .collect(Collectors.toList());
    }

    public TourDto findByIdTour(Long id) throws TourNotFoundException{
        checkTourExists(id);
        return modelMapper.map(tourRepository.findById(id), TourDto.class);
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

    private void validateTourDates(TourDto tourDto) throws OverlappingTourException, DtoNotValidException {
        validateDto(tourDto);
        Instant tourStart = tourDto.getStart();
        Instant tourEnd = tourDto.getEnd();

        List<TourEntity> overlappingTours = tourRepository.findOverlappingTours(tourDto.getDeliveryPersonId(), tourStart, tourEnd);

        if (!overlappingTours.isEmpty()) {
            throw new OverlappingTourException();
        }
    }

}
