package com.rodrigo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that makes possible to inject the beans listed here.
 * 
 * @author Tiago Melo (tiagoharris@gmail.com)
 *
 */
@Configuration
public class CrudExceptionhandlingExampleApplicationConfiguration {

  @Bean
  public ModelMapper modelMapper() {
      return new ModelMapper();
  }
}
