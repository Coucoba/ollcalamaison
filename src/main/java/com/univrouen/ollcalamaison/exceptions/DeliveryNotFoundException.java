package com.univrouen.ollcalamaison.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "UNKNOWN_DELIVERY")
public class DeliveryNotFoundException extends RuntimeException{}
