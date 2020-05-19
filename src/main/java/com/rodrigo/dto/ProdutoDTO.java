package com.rodrigo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO with Produto information.
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO {

  private Integer id;
  
  @NotBlank(message = "'name' property is missing")
  private String name;
  
  @NotNull(message = "'amount' property is missing")
  private Integer amount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
  
}
