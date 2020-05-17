package com.rodrigo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.rodrigo.entity.Produto;
import com.rodrigo.exception.ResourceNotFoundException;
import com.rodrigo.repository.ProdutoRepository;
import com.rodrigo.service.ProdutoService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ProdutoServiceImpl.class })
public class ProdutoServiceImplTest {

  @Autowired
  private ProdutoService service;

  @MockBean
  private ProdutoRepository repository;
  
  private static final Integer EXISTENT_STUDENT_ID = 1;
  
  private static final LocalDate FROM_DATE = LocalDate.parse("2001-01-01");
  
  private static final LocalDate TO_DATE = LocalDate.parse("2001-03-01");
  
  private static final String NAME = "Ma";
  
  @Before
  public void setUp() {
    Mockito.when(repository.findById(EXISTENT_STUDENT_ID)).thenReturn(buildProduto());
    
    Mockito.when(repository.findAllProdutosBornBetween(FROM_DATE, TO_DATE)).thenReturn(buildProdutoList());
    
    Mockito.when(repository.findProdutosWithPartOfName(NAME)).thenReturn(buildProdutoList());
    
    Mockito.when(repository.findAll()).thenReturn(buildProdutoList());
    
    Mockito.when(repository.save(any())).thenReturn(buildProduto().get());

  }
  
  @Test(expected = ResourceNotFoundException.class)
  public void whenFindingProdutoByNonExistingId_thenRaisesResourceNotFoundException() {
    service.findById(null);
  }
  
  @Test
  public void whenFindingProdutoByExistingId_thenReturnProduto() {
    assertNotNull(service.findById(EXISTENT_STUDENT_ID));
  }
  
  @Test
  public void whenFindingProdutosByBirthDateBetween_thenReturnProdutoList() {
    assertEquals(2, service.findByBirthDateBetween(FROM_DATE, TO_DATE).size());
  }
  
  @Test
  public void whenFindingAllProdutos_thenReturnProdutoList() {
    assertEquals(2, service.findAll().size());
  }
  
  @Test
  public void WhenSavingANewProduto_thenReturnNewProduto() {
    Produto newProduto = buildProduto().get();

    Produto savedProduto = service.save(buildProduto().get());

    assertEquals(newProduto, savedProduto);
  }
  
  @Test
  public void WhenDeletingProdutoByExistingId_thenShouldDeleteProduto() {
    Produto produtoToBeDeleted = buildProduto().get();

    service.delete(EXISTENT_STUDENT_ID);

    verify(repository).delete(produtoToBeDeleted);
  }
  
  @Test(expected = ResourceNotFoundException.class)
  public void WhenDeletingProdutoByNonExistingId_thenRaisesResourceNotFoundException() {
    service.delete(null);
  }
  
  private Optional<Produto> buildProduto() {
    Produto produto = new Produto();
    
    produto.setId(EXISTENT_STUDENT_ID);
    produto.setName("Rodrigo");
    produto.setEmail("rodrigo.cezar@email.com");
    produto.setBirthDate(LocalDate.parse("1980-04-22"));
    
    return Optional.of(produto);
  }
  
  private List<Produto> buildProdutoList() {
    List<Produto> produtos = new ArrayList<Produto>();
    
    produtos.add(buildProduto().get());
    produtos.add(buildProduto().get());
    
    return produtos;
  }
}
