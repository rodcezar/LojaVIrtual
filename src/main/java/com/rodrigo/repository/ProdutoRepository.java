package com.rodrigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
   * Find all Produtos with name like %name
   * 
   * @param fromDate
   * @param toDate
   * @return the list of Produtos
   */
  
  @Query("SELECT p FROM produto p WHERE p.name LIKE CONCAT('%',:name,'%')")
  List<Produto> findProdutosWithPartOfName(@Param("name") String name);
  
  @Modifying
  @Query("update produto p set p.amount = p.amount - 1 where p.id = ?1")
  int buyProduto(Integer id);
}
