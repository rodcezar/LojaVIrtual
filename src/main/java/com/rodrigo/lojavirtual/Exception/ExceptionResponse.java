package com.rodrigo.lojavirtual.Exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class holds information of a given exception.
 * 
 * @author Tiago Melo (tiagoharris@gmail.com)
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
  private String errorCode;
  private String errorMessage;
  private List<String> errors;

  public ExceptionResponse() {
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }
}
