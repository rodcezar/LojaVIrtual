package com.rodrigo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO with Produto information.
 * 
 * @author Tiago Melo (tiagoharris@gmail.com)
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO {

  private Integer id;
  
  @NotBlank(message = "'name' property is missing")
  private String name;
  
  @NotBlank(message = "'email' property is missing")
  @Email
  private String email;
  
  @NotNull(message = "'birthDate' property is missing")
  private LocalDate birthDate;
  
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }
  
  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
  
}
