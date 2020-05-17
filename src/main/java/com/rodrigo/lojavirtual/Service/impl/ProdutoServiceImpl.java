package com.rodrigo.lojavirtual.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.lojavirtual.Entity.Produto;
import com.rodrigo.lojavirtual.Exception.ResourceNotFoundException;
import com.rodrigo.lojavirtual.Repository.ProdutoRepository;
import com.rodrigo.lojavirtual.Service.ProdutoService;

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

  @Override
  public Produto findById(Integer id) {
	  Produto produto = repository.findById(id).orElse(null);
    
    if (produto == null) {
      throw new ResourceNotFoundException(Produto.class.getSimpleName(), "id", id);
    }
    
    return produto;
  }

  @Override
  public List<Produto> findProdutosWithPartOfName(String nome) {
    return repository.findProdutosWithPartOfName(nome);
  }

  @Override
  public List<Produto> findAll() {
    return repository.findAll();
  }

  @Override
  public Produto save(Produto produto) {
    return repository.save(produto);
  }

  @Override
  public void delete(Integer id) {
	  Produto produto = findById(id);
    
    repository.delete(produto);
  }
}
