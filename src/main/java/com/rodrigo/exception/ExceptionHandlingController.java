package com.rodrigo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.rodrigo.util.ValidationUtil;

/**
 * This class handles the exceptions thrown by the controller layer.
 * 
 * @author Tiago Melo (tiagoharris@gmail.com)
 *
 */
@ControllerAdvice
public class ExceptionHandlingController {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

  /**
   * This exception is thrown when a resource is not found
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
    ExceptionResponse response = new ExceptionResponse();
    response.setErrorCode("Not Found");
    response.setErrorMessage(ex.getMessage());

    LOGGER.error(String.format("ResourceNotFoundException: %s", ex.getMessage()));
    
    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
  }

  /**
   * This exception is thrown when inputs are invalid
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> invalidInput(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    ExceptionResponse response = new ExceptionResponse();
    response.setErrorCode("Bad Request");
    response.setErrorMessage("Invalid inputs");
    response.setErrors(new ValidationUtil().fromBindingErrors(result));
    
    LOGGER.error(String.format("MethodArgumentNotValidException: %s", ex.getMessage()));
    
    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * This exception is thrown when query string parameter is missing
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ExceptionResponse> missingRequestParameter(MissingServletRequestParameterException ex) {
    ExceptionResponse response = new ExceptionResponse();
    response.setErrorCode("Bad Request");
    response.setErrorMessage(ex.getMessage());
    
    LOGGER.error(String.format("MissingServletRequestParameterException: %s", ex.getMessage()));

    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * This exception is thrown when an error occurs when parsing input JSON
   * or if it's missing
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponse> invalidRequestData(HttpMessageNotReadableException ex) {
    Throwable mostSpecificCause = ex.getMostSpecificCause();

    ExceptionResponse response = new ExceptionResponse();
    response.setErrorCode("Bad Request");

    if (mostSpecificCause != null) {
      String message = mostSpecificCause.getMessage();
      
      if(message.matches("(.*)Required request body is missing(.*)")) {
        response.setErrorMessage("Missing request body");
      } else {
        response.setErrorMessage(mostSpecificCause.getMessage());
      }
    } else {
      response.setErrorMessage(ex.getMessage());
    }
    
    LOGGER.error(String.format("HttpMessageNotReadableException: %s", ex.getMessage()));

    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * This exception is thrown when an error occurs while parsing the value
   * of a query string parameter
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ExceptionResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName();
    String type = ex.getRequiredType().getSimpleName();
    Object value = ex.getValue();
    String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);

    ExceptionResponse response = new ExceptionResponse();
    response.setErrorCode("Bad Request");
    response.setErrorMessage(message);
    
    LOGGER.error(String.format("MethodArgumentTypeMismatchException: %s", ex.getMessage()));
    
    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * This exception is thrown when the new record conflicts with an
   * existing record in the database
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ExceptionResponse> constraintViolation(DataIntegrityViolationException ex) {
    ExceptionResponse response = new ExceptionResponse();
    
    response.setErrorCode("Conflict");
    response.setErrorMessage("This produto is already registered");
    
    LOGGER.error(String.format("DataIntegrityViolationException: %s", ex.getMessage()));

    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
  }

  /**
   * This is a general catching exception
   * 
   * @param ex
   * @return {@link ExceptionResponse}
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
    ExceptionResponse response = new ExceptionResponse();
    response.setErrorCode("error");
    response.setErrorMessage(ex.getMessage());

    LOGGER.error(String.format("Exception: %s", ex.getMessage()));
    
    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
