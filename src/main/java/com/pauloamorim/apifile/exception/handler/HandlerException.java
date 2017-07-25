package com.pauloamorim.apifile.exception.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class HandlerException {

	@ExceptionHandler
	public ResponseEntity<String> handleServicoException(IOException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File writing error, trying upload again ");
	}
	
}
