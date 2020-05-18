package com.rodrigo.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.entity.Produto;
import com.rodrigo.exception.ResourceNotFoundException;
import com.rodrigo.repository.ProdutoRepository;
import com.rodrigo.service.ProdutoService;

/**
 * Implements {@link ProdutoService} interface
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
 */
@Service
public class ProdutoServiceImpl implements ProdutoService {
  
  @Autowired
  ProdutoRepository repository;

  /* (non-Javadoc)
   * @see com.rodrigo.service.ProdutoService#findById(java.lang.Long)
   */
  @Override
  public Produto findById(Integer id) {
    Produto produto = repository.findById(id).orElse(null);
    
    if (produto == null) {
      throw new ResourceNotFoundException(Produto.class.getSimpleName(), "id", id);
    }
    
    return produto;
  }

  /* (non-Javadoc)
   * @see com.rodrigo.service.ProdutoService#findProdutosWithPartOfName(String)
   */
  @Override
  public List<Produto> findProdutosWithPartOfName(String name) {
    return repository.findProdutosWithPartOfName(name);
  } 
  
  /* (non-Javadoc)
   * @see com.rodrigo.service.ProdutoService#findAll()
   */
  @Override
  public List<Produto> findAll() {
    return repository.findAll();
  }

  /* (non-Javadoc)
   * @see com.rodrigo.service.ProdutoService#save(com.rodrigo.entity.Produto)
   */
  @Override
  public Produto save(Produto produto) {
    return repository.save(produto);
  }

  /* (non-Javadoc)
   * @see com.rodrigo.service.ProdutoService#delete(java.lang.Long)
   */
  @Override
  public void delete(Integer id) {
    Produto produto = findById(id);
    
    repository.delete(produto);
  }

@Override
public int buyProduto(Integer id) {
	return repository.buyProduto(id);
	
	}
}
