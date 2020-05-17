package com.rodrigo.lojavirtual.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that makes possible to inject the beans listed here.
 * 
 * @author rodrigo cezar (rodrigo.cezar@gmail.com)
 *
 */
@Configuration
public class CrudExceptionhandlingExampleApplicationConfiguration {

  @Bean
  public ModelMapper modelMapper() {
      return new ModelMapper();
  }
}
