package com.rodrigo.lojavirtual.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rodrigo.lojavirtual.Entity.Produto;

/**
 * Repository for {@link Produto} entity.
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
*/
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> { 
  
  /**
   * Find all products with nome like
   * 
   * @param nome
   * @return the list of products
   */

  //@Query("SELECT u.username FROM User u WHERE u.username LIKE CONCAT('%',:username,'%')")
  //List<String> findUsersWithPartOfName(@Param("username") String username);
	
	
  //@Query("SELECT p.nome FROM Produto p WHERE p.nome LIKE CONCAT('%',:nome,'%')")
  //List<Produto> findProdutosWithPartOfName(@Param("nome") String nome);
  
  @Query("SELECT p FROM Produto p")
  List<Produto> findProdutosWithPartOfName(@Param("nome") String nome);	

}
