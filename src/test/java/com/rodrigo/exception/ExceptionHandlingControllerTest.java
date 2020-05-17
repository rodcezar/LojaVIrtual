package com.rodrigo.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class ExceptionHandlingControllerTest {

  private ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController(); 

  private HttpMessageNotReadableException httpMessageNotReadableException = Mockito.mock(HttpMessageNotReadableException.class);
  
  @Test
  public void whenTheresNoSpecificCause_thenReturnHttpMessageNotReadableExceptionMessage() {
    Mockito.when(
        httpMessageNotReadableException.getCause())
        .thenReturn(null);
    
    Mockito.when(
        httpMessageNotReadableException.getMessage())
        .thenReturn("message");
    
    ResponseEntity<ExceptionResponse> exceptionResponse = exceptionHandlingController.invalidRequestData(httpMessageNotReadableException);
    assertEquals("message", exceptionResponse.getBody().getErrorMessage());
  }
}
