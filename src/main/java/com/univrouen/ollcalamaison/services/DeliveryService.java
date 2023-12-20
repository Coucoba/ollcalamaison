package com.univrouen.ollcalamaison.services;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.repositories.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryService {

    private DeliveryRepository deliveryRepository;

    public void createDelivery(InputDeliveryDto inputDeliveryDto) {

    }

}
