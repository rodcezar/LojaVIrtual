package com.rodrigo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rodrigo.CrudExceptionhandlingExampleApplication;
import com.rodrigo.dto.ProdutoDTO;
import com.rodrigo.entity.Produto;
import com.rodrigo.exception.ResourceNotFoundException;
import com.rodrigo.service.ProdutoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CrudExceptionhandlingExampleApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ProdutoControllerTest {

  @TestConfiguration
  static class ProdutoControllerTestContextConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
  }
  
  @Autowired
  ModelMapper modelMapper;
  
  @Autowired
  ObjectMapper objectMapper;
  
  @Autowired
  private MockMvc mvc;

  @MockBean
  private ProdutoService service;
  
  private Produto produto = buildProduto();
  
  private static final String BAD_REQUEST_ERROR_CODE = "Bad Request";
  
  private static final String NOT_FOUND_ERROR_CODE = "Not Found";
  
  private static final String CONFLICT_ERROR_CODE = "Conflict";
  
  private static final String JSON_ERROR_MESSAGE_TEMPLATE = "{\"errorCode\":\"%s\",\"errorMessage\":\"%s\"}";
  
  private static final String JSON_MISSING_PROPERTY_MESSAGE_TEMPLATE = "{\"errorCode\":\"%s\",\"errorMessage\":\"Invalid inputs\",\"errors\":[%s]}";
  
  @Test
  public void whenGetProdutos_thenReturnProdutoList() throws Exception {
    Mockito.when(
        service.findAll())
        .thenReturn(buildProdutoList());
    
    mvc.perform(get("/api/produtos")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id", is(produto.getId())))
      .andExpect(jsonPath("$[0].name", is(produto.getName())))
      .andExpect(jsonPath("$[0].amount", is(produto.getAmount())));
  }
  
  @Test
  public void whenPostAValidProduto_thenReturnSuccess() throws Exception {
    Mockito.when(
        service.save(any()))
        .thenReturn(produto);
    
    mvc.perform(post("/api/produto")
        .content(newProdutoDTOJsonString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(produtoDTOJsonString()))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void whenPostExistingProduto_thenReturnError() throws Exception {
    Mockito.when(
        service.save(any()))
        .thenThrow(DataIntegrityViolationException.class);
    
    mvc.perform(post("/api/produto")
        .content(newProdutoDTOJsonString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(userIsAlreadyRegisteredErrorMessage()))
        .andExpect(status().is4xxClientError());
  }
  
  @Test
  public void whenPostWithoutBodyRequest_thenReturnError() throws Exception {
    mvc.perform(post("/api/produto")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(missingRequestBodyErrorMessage()))
        .andExpect(status().is4xxClientError());
  }
  
  @Test
  public void whenPostWithoutNameProperty_thenReturnError() throws Exception {
    mvc.perform(post("/api/produto")
        .content(produtoDTOWithoutNamePropertyJsonString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(missingPropertyErrorMessage("name")))
        .andExpect(status().is4xxClientError());
  }
  
  @Test
  public void whenPutExistentProduto_thenReturnSuccess() throws Exception {
    Mockito.when(
        service.findById(1))
        .thenReturn(produto);
    
    Mockito.when(
        service.save(any()))
        .thenReturn(produto);
    
    mvc.perform(put("/api/produto/1")
        .content(produtoDTOJsonString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }
  
  @Test
  public void whenPutInexistentProduto_thenReturnError() throws Exception {
    Integer inexistentId = 666;
    
    ResourceNotFoundException ex = new ResourceNotFoundException(Produto.class.getSimpleName(), "id", inexistentId);
    
    Mockito.when(
        service.findById(any()))
        .thenThrow(ex);
    
    mvc.perform(put("/api/produto/" + inexistentId)
        .content(produtoDTOJsonString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(produtoNotFoundWithIdErrorMessage(inexistentId)))
        .andExpect(status().is4xxClientError());
  }
  
  public void whenPutWithoutIdRequestParameter_thenReturnError() throws Exception {
    mvc.perform(put("/api/produto/")
        .content(produtoDTOJsonString())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());
  }
  
  @Test
  public void whenDeleteInexistentProduto_thenReturnError() throws Exception {
    Integer inexistentId = 666;
    
    ResourceNotFoundException ex = new ResourceNotFoundException(Produto.class.getSimpleName(), "id", inexistentId);
    
    doThrow(ex).when(service).delete(inexistentId);
    
    mvc.perform(delete("/api/produto/" + inexistentId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(produtoNotFoundWithIdErrorMessage(inexistentId)))
        .andExpect(status().is4xxClientError());
  }
  
  @Test
  public void whenDeleteWithoutIdRequestParameter_thenReturnError() throws Exception {
    mvc.perform(delete("/api/produto/")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError());
  }
  
  @Test
  public void whenDeleteExistingProduto_thenReturnSuccess() throws Exception {
    Mockito.doNothing().when(service).delete(1);
    
    mvc.perform(delete("/api/produto/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }
  
  private String missingRequestBodyErrorMessage() {
    return String.format(JSON_ERROR_MESSAGE_TEMPLATE, BAD_REQUEST_ERROR_CODE, "Missing request body");
  }
  
  private String userIsAlreadyRegisteredErrorMessage() {
    return String.format(JSON_ERROR_MESSAGE_TEMPLATE, CONFLICT_ERROR_CODE, "This produto is already registered");
  }
  
  private String missingPropertyErrorMessage(String propertyName) {
    return String.format(JSON_MISSING_PROPERTY_MESSAGE_TEMPLATE, BAD_REQUEST_ERROR_CODE, "\"'" + propertyName + "' property is missing\"");
  }
  
  private String produtoNotFoundWithIdErrorMessage(Integer inexistentId) {
    return String.format(JSON_ERROR_MESSAGE_TEMPLATE, NOT_FOUND_ERROR_CODE, "Produto not found with id: '" + inexistentId + "'");
  }
  
  private String newProdutoDTOJsonString() throws JsonProcessingException {
    ProdutoDTO newProdutoDTO = buildProdutoDTO();
    
    newProdutoDTO.setId(null);
    
    return objectMapper.writeValueAsString(newProdutoDTO);
  }
  
  private String produtoDTOJsonString() throws JsonProcessingException {
    return objectMapper.writeValueAsString(buildProdutoDTO());
  }
  
  private String produtoDTOWithoutNamePropertyJsonString() throws JsonProcessingException {
    ProdutoDTO produtoDTO = buildProdutoDTO();
    
    produtoDTO.setName(null);
    
    return objectMapper.writeValueAsString(produtoDTO);
  }
  
   private Produto buildProduto() {
    Produto produto = new Produto();

    produto.setId(1);
    produto.setName("Rodrigo");
    produto.setAmount(8);

    return produto;
  }
  
  private ProdutoDTO buildProdutoDTO() {
    return modelMapper.map(produto, ProdutoDTO.class);
  }

  private List<Produto> buildProdutoList() {
    List<Produto> produtos = new ArrayList<Produto>();

    produtos.add(buildProduto());

    return produtos;
  }
}
