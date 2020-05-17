package com.rodrigo.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class ProdutoTest {

  private Produto produto = buildProduto();
  
  @Test
  public void whenTwoProdutoObjectsAreTheSameObject_thenReturnsTrue() {
    Produto produtoTwo = produto;
    
    assertTrue(produto.equals(produtoTwo));
    assertTrue(produto.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenTwoProdutoObjectsHaveTheSameId_thenReturnsTrue() {
    Produto produtoOne = new Produto();
    produtoOne.setId(1);
    
    Produto produtoTwo = new Produto();
    produtoTwo.setId(1);
    
    assertTrue(produtoOne.equals(produtoTwo));
    assertTrue(produtoOne.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenTwoProdutoObjectsHaveSameProperties_thenReturnsTrue() {
    Produto produtoTwo = buildProduto();
    
    assertTrue(produto.equals(produtoTwo));
    assertTrue(produto.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenTwoProdutoObjectsHaveDifferentIds_thenReturnsFalse() {
    Produto produtoTwo = new Produto();
    produtoTwo.setId(2);
    produtoTwo.setName(produto.getName());
    produtoTwo.setEmail(produto.getEmail());
    produtoTwo.setBirthDate(produto.getBirthDate());
    
    assertFalse(produto.equals(produtoTwo));
    assertFalse(produto.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenTwoProdutoObjectsHaveDifferentNames_thenReturnsFalse() {
    Produto produtoTwo = new Produto();
    produtoTwo.setId(produto.getId());
    produtoTwo.setName("other rodrigo");
    produtoTwo.setEmail(produto.getEmail());
    produtoTwo.setBirthDate(produto.getBirthDate());
    
    assertFalse(produto.equals(produtoTwo));
    assertFalse(produto.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenTwoProdutoObjectsHaveDifferentEmails_thenReturnsFalse() {
    Produto produtoTwo = new Produto();
    produtoTwo.setId(produto.getId());
    produtoTwo.setName(produto.getName());
    produtoTwo.setEmail("other_email@email.com");
    produtoTwo.setBirthDate(produto.getBirthDate());
    
    assertFalse(produto.equals(produtoTwo));
    assertFalse(produto.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenTwoProdutoObjectsHaveDifferentBirthDates_thenReturnsFalse() {
    Produto produtoTwo = new Produto();
    produtoTwo.setId(produto.getId());
    produtoTwo.setName(produto.getName());
    produtoTwo.setEmail(produto.getEmail());
    produtoTwo.setBirthDate(LocalDate.parse("2000-02-02"));
    
    assertFalse(produto.equals(produtoTwo));
    assertFalse(produto.hashCode() == produtoTwo.hashCode());
  }
  
  @Test
  public void whenComparingToNull_thenReturnsFalse() {
    assertFalse(produto.equals(null));
  }
  
  @Test
  public void whenComparedToAnotherRandomObject_thenReturnsFalse() {
    assertFalse(produto.equals(new Object()));
  }
  
  private Produto buildProduto() {
    LocalDate birthDate = LocalDate.parse("2000-01-01");
    
    Produto produto = new Produto();
    produto.setId(1);
    produto.setName("rodrigo");
    produto.setEmail("rodrigo@email.com");
    produto.setBirthDate(birthDate);
    
    return produto;
  }
}
