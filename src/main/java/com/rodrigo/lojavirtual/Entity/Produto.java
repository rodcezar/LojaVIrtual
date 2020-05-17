package com.rodrigo.lojavirtual.Entity;

import java.util.Objects;

import javax.persistence.*;

/**
 * Entity for table "Produto" 
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
 */
@Entity(name = "Produto")
public class Produto {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String nome;
  
  private Integer quantidade;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    
    if (o == null) return false;
    
    if (this.getClass() != o.getClass()) return false;
    
    Produto produto = (Produto) o;

    return Objects.equals(getId(), produto.getId())
      && Objects.equals(getNome(), produto.getNome())
      && Objects.equals(getQuantidade(), produto.getQuantidade());
  }

  @Override
  public int hashCode() {
    int hash = 7;
    
    hash = 31 * hash + Objects.hashCode(id);
    hash = 31 * hash + Objects.hashCode(nome);
    hash = 31 * hash + Objects.hashCode(quantidade);
    return hash;
  }
}
