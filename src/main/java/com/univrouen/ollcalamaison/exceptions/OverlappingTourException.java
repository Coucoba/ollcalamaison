package com.univrouen.ollcalamaison.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "CANNOT_ASSIGN_DELIVERY_PERSON")
public class OverlappingTourException extends RuntimeException{}
