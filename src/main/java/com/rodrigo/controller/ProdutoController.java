package com.rodrigo.controller;

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

import com.rodrigo.dto.ProdutoDTO;
import com.rodrigo.entity.Produto;
import com.rodrigo.service.ProdutoService;

/**
 * Restful controller responsible for managing produtos
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
  
  @RequestMapping("/produtos/teste")
  public String teste() {
   
    return "Funcionou!";
  }  
 

  /**
   * Creates a produto
   * 
   * @param produtoDTO
   * @return the created produto
   */
  @PostMapping("/produto")
  public ProdutoDTO createProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
    Produto produto = convertToEntity(produtoDTO);

    LOGGER.info("createProduto() called");
    
    return convertToDTO(service.save(produto));
  }
  
  /**
   * Get all produtos with name like %name
   * 
   * @param name
   * @return the list of produtos
   */
  @GetMapping(path = "/produtos/name")
  public List<ProdutoDTO> getAllProdutosWithPartName(
	  @RequestParam(name = "name") String name) {
    List<Produto> produtos = service.findProdutosWithPartOfName(name);

    LOGGER.info(String.format("getAllProdutosWithPartName() with name [%s] returned %s records", name, produtos.size()));
    
    return produtos.stream().map(produto -> convertToDTO(produto)).collect(Collectors.toList());
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
   * Updates a produto
   * 
   * @param produtoId
   * @param produtoDTO
   * @return the updated produto
   
  @PutMapping("/produto/buy/{id}")
  public Produto buyProduto(@Valid @RequestBody ProdutoDTO produtoDTO, @PathVariable Integer produtoId) {	
    
	  Integer produtoAtualizadoOK = service.buyProduto(produtoId);
	   return repository.findById(produtoId)
	    	      .map(employee -> {
	    	        employee.setName(newEmployee.getName());
	    	        employee.setRole(newEmployee.getRole());
	    	        return repository.save(employee);
	    	      })
	    	      .orElseGet(() -> {
	    	        newEmployee.setId(id);
	    	        return repository.save(newEmployee);
	    	      });

    LOGGER.info(String.format("buyProduto() called with id [%s]", produtoId));
    
    return;
  } **/
  
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
