package com.rodrigo.lojavirtual.Service;

import java.util.List;

import com.rodrigo.lojavirtual.Entity.Produto;
import com.rodrigo.lojavirtual.Exception.ResourceNotFoundException;

/**
 * Service to manage students.
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
   * Find all students like %nome
   * 
   * @param nome
   * @return the list of produtos
   */
  List<Produto> findProdutosWithPartOfName(String nome);
  
  /**
   * Find all Produtos like
   * 
   * @return the list of produtos
   */
  List<Produto> findAll();
  
  /**
   * Saves a produto
   * 
   * @param student to be saved
   * @return the saved produto
   */
  Produto save(Produto produto);
  
  /**
   * Deletes a produto
   * 
   * @param id
   * @throws ResourceNotFoundException if no {@link Produto} is found
   */
  void delete(Integer id);
}
