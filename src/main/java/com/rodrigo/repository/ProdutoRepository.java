package com.rodrigo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rodrigo.entity.Produto;

/**
 * Repository for {@link Produto} entity.
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
*/
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> { 
  
  /**
   * Find all Produtos born between a date range
   * 
   * @param fromDate
   * @param toDate
   * @return the list of Produtos
   */
  @Query("SELECT s FROM produto s WHERE s.birthDate BETWEEN ?1 and ?2")
  List<Produto> findAllProdutosBornBetween(LocalDate fromDate, LocalDate toDate);
  
  @Query("SELECT p FROM produto p WHERE p.name LIKE CONCAT('%',:name,'%')")
  List<Produto> findProdutosWithPartOfName(@Param("name") String name);
}
