package com.rodrigo.exception;

/**
 * Exception class that is thrown when it's not possible to find a resource.
 * 
 * @author Tiago Melo (tiagoharris@gmail.com)
 *
 */
public class ResourceNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
  }
}
