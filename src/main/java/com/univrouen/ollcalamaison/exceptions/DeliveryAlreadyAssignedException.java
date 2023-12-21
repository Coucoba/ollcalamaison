package com.univrouen.ollcalamaison.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "DELIVERY_ALREADY_ASSIGNED")
public class DeliveryAlreadyAssignedException extends RuntimeException{}
