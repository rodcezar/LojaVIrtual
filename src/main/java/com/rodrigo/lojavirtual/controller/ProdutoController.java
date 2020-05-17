package com.rodrigo.lojavirtual.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.lojavirtual.DTO.ProdutoDTO;
import com.rodrigo.lojavirtual.Entity.Produto;
import com.rodrigo.lojavirtual.Service.ProdutoService;

/**
 * Controller RESTFul para gerenciamento de produtos
 * 
 * @author Rodrigo Cezar (rodrigo.cezar@gmail.com)
 *
 */
@RestController
@RequestMapping("/api")
public class ProdutoController {
  
  @Autowired
  ModelMapper modelMapper;

  @Autowired
  ProdutoService service;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);
  
  /**
   * Get all produtos
   * 
   * @return the list of produtos
   */
  @GetMapping("/produtos")
  public List<ProdutoDTO> getAllProdutos() {
    List<Produto> produtos = service.findAll();

    LOGGER.info(String.format("getAllProdutos() returned %s records", produtos.size()));
    
    return produtos.stream().map(produto -> convertToDTO(produto)).collect(Collectors.toList());
  }
  
  /**
   * Get all produtos with name like 
   * 
   * @param nome
   * @return the list of prodtuos
   */
  @GetMapping(path = "/produtos/nome")
  public List<ProdutoDTO> findProdutosWithPartOfName(
      @RequestParam(value = "nome") String nome) {
    List<Produto> produtos = service.findProdutosWithPartOfName(nome);

    LOGGER.info(String.format("findProdutosWithPartOfName() with nome [%s] returned %s records", nome, produtos.size()));
    
    return produtos.stream().map(produto -> convertToDTO(produto)).collect(Collectors.toList());
  }

  /**
   * Cria um produto
   * 
   * @param ProdutoDTO
   * @return the created produto
   */
  @PostMapping("/produto")
  public ProdutoDTO createProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
	  Produto produto = convertToEntity(produtoDTO);

    LOGGER.info("createProduto() called");
    
    return convertToDTO(service.save(produto));
  }
  
  /**
   * Updates a produto
   * 
   * @param produtoId
   * @param produtoDTO
   * @return the updated produto
   */
  @PutMapping("/produto/{id}")
  public ProdutoDTO updateProduto(@PathVariable(value = "id", required = true) Integer produtoId, 
      @Valid @RequestBody ProdutoDTO produtoDTO) {
	  produtoDTO.setId(produtoId);
    Produto produto = convertToEntity(produtoDTO);

    LOGGER.info(String.format("updateProduto() called with id [%s]", produtoId));
    
    return convertToDTO(service.save(produto));
  }
  
  /**
   * Deletes a produto
   * 
   * @param produtoId
   * @return 200 OK
   */
  @DeleteMapping("/produto/{id}")
  public ResponseEntity<?> deleteProduto(@PathVariable(value = "id") Integer produtoId) {
    service.delete(produtoId);
    
    LOGGER.info(String.format("deleteProduto() called with id [%s]", produtoId));
    
    return ResponseEntity.ok().build();
  }
  
  private ProdutoDTO convertToDTO(Produto produto) {
    return modelMapper.map(produto, ProdutoDTO.class);
  }
  
  private Produto convertToEntity(ProdutoDTO produtoDTO) {
	  Produto produto = null;
    
    if(produtoDTO.getId() != null) {
    	produto = service.findById(produtoDTO.getId());
    }
    
    produto = modelMapper.map(produtoDTO, Produto.class);
    
    return produto;
  }
}
