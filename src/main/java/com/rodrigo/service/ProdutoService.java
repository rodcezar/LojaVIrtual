package com.rodrigo.service;

import java.util.List;

import com.rodrigo.entity.Produto;
import com.rodrigo.exception.ResourceNotFoundException;

/**
 * Service to manage produtos.
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
 */
public interface ProdutoService {

  /**
   * Finds a Produto by id
   * 
   * @param id
   * @return {@link Produto}
   * @throws ResourceNotFoundException if no {@link Produto} is found
   */
	Produto findById(Integer id);
  
  /**
   * Find all Produtos with name like %name
   * 
   * @param name
   * @return the list of Produtos
   */
  List<Produto> findProdutosWithPartOfName(String name); 
 
  /**
   * Decrements 1 to Produto amount given the id
   * 
   * @param id
   * @throws ResourceNotFoundException if no {@link Produto} is found
   */
  int buyProduto(Integer id);
 
  /**
   * Find all produto
   * 
   * @return the list of produto
   */
  
  List<Produto> findAll();
  
  /**
   * Saves a produto
   * 
   * @param Produto to be saved
   * @return the saved produto
   */
  Produto save(Produto produto);
  
  /**
   * Deletes a Produto
   * 
   * @param id
   * @throws ResourceNotFoundException if no {@link Produto} is found
   */
  void delete(Integer id);
}
