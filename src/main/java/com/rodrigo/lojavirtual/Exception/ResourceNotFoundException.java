package com.rodrigo.lojavirtual.Exception;

/**
 * Exception class that is thrown when it's not possible to find a resource.
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
 */
public class ResourceNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
  }
}
