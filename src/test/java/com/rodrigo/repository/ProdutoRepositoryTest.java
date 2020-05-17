package com.rodrigo.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rodrigo.entity.Produto;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ProdutoRepositoryTest {

  @Autowired
  ProdutoRepository repository;
  
  @Test
  public void whenTwoProdutosMeetTheCriteria_thenReturnProdutoList() {
    LocalDate fromDate = LocalDate.parse("2001-01-01");
    LocalDate toDate = LocalDate.parse("2001-03-01");
    
    List<Produto> produtos = repository.findAllProdutosBornBetween(fromDate, toDate);
    
    assertEquals(2, produtos.size());
  }
}
