package com.univrouen.ollcalamaison.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "INVALID_DTO")
public class DtoNotValidException extends RuntimeException {}
