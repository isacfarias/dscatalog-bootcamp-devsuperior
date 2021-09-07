package com.devsuperior.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.factory.ProductFactory;
import com.devsuperior.dscatalog.resource.ProductResource;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private ProductService service;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	private Long existingId;
	private Long nonExistingId;
	private Long dependenteId;
	private ProductDTO newProductDTO;
	private ProductDTO existingProductDTO;
	private PageImpl<ProductDTO> page;

	private String username;
	private String password;

	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 2L;
		dependenteId = 3L;
		username = "alex@gmail.com";
		password = "123456";
		newProductDTO = ProductFactory.createProductDTO(null);
		existingProductDTO = ProductFactory.createProductDTO(existingId);
		page = new PageImpl<>(List.of(existingProductDTO));

		when(service.findById(existingId)).thenReturn(existingProductDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		when(service.findAllPaged(any(), anyString(), any())).thenReturn(page);
		
		when(service.save(any())).thenReturn(existingProductDTO);
		
		when(service.update(eq(existingId), any())).thenReturn(existingProductDTO);
		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		
		doNothing().when(service).delete(existingId);
		doThrow(DataBaseException.class).when(service).delete(dependenteId);
		doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
	}

	@Test
	void findByIdShouldReturnProductWhenIdExist() throws Exception {
		String token = "Bearer "+ obtainAccessToken(username, password);
		this.mockMvc.perform(get("/produtcs/{id}", existingId)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
			    .andExpect(jsonPath("$.id").exists())
			    .andExpect(jsonPath("$.id").value(existingId));
	}

	@Test
	void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		String token = "Bearer "+ obtainAccessToken(username, password);
		this.mockMvc.perform(get("/produtcs/{id}", nonExistingId)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isNotFound());
	}

	@Test
	void updateShouldReturnNotFoundWhenIdDosNotExist() throws Exception {
		newProductDTO.setDate(Instant.now());
		var jsonBody = mapper.writeValueAsString(newProductDTO);
		String token = "Bearer "+ obtainAccessToken(username, password);
		ResultActions result = mockMvc.perform(put("/produtcs/{id}", nonExistingId)
				.header("Authorization", token)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		        result.andExpect(status().isNotFound());
	}
	
	@Test
	void updateShouldReturnProductDTOWhenIdExist() throws Exception {
		newProductDTO.setDate(Instant.now());
		var jsonBody = mapper.writeValueAsString(newProductDTO);
		String token = "Bearer "+ obtainAccessToken(username, password);
		
		var expectedName = newProductDTO.getName();
		var expectedPrice = newProductDTO.getPrice();
		
		ResultActions result = mockMvc.perform(put("/produtcs/{id}", existingId)
				.header("Authorization", token)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		        result.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").exists())
		        .andExpect(jsonPath("$.id").value(existingId))
		        .andExpect(jsonPath("$.name").value(expectedName))
		        .andExpect(jsonPath("$.price").value(expectedPrice));
	}
	
	@Test
	void insertShouldReturnUnProcessableEntityWhenNegativePrice() throws Exception {
		newProductDTO.setDate(Instant.now());
		newProductDTO.setPrice(newProductDTO.getPrice()*-1);
		var jsonBody = mapper.writeValueAsString(newProductDTO);
		String token = "Bearer "+ obtainAccessToken(username, password);
		ResultActions result = mockMvc.perform(post("/produtcs")
				.header("Authorization", token)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		        result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	void insertShouldReturnCreateProductDTOWhenValidData() throws Exception {
		newProductDTO.setDate(Instant.now());
		var jsonBody = mapper.writeValueAsString(newProductDTO);
		String token = "Bearer "+ obtainAccessToken(username, password);
		
		var expectedName = newProductDTO.getName();
		var expectedPrice = newProductDTO.getPrice();
		
		ResultActions result = mockMvc.perform(post("/produtcs")
				.header("Authorization", token)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		        result.andExpect(status().isCreated())
		        .andExpect(jsonPath("$.id").exists())
		        .andExpect(jsonPath("$.id").value(existingId))
		        .andExpect(jsonPath("$.name").value(expectedName))
		        .andExpect(jsonPath("$.price").value(expectedPrice));
	}
	
	@Test
	void deleteShouldReturnNoContentWhenIdExist() throws Exception {
		newProductDTO.setDate(Instant.now());
		var jsonBody = mapper.writeValueAsString(newProductDTO);
		String token = "Bearer "+ obtainAccessToken(username, password);
		
		ResultActions result = mockMvc.perform(delete("/produtcs/{id}", existingId)
				.header("Authorization", token)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		        result.andExpect(status().isNoContent());
	}
	
	@Test
	void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		newProductDTO.setDate(Instant.now());
		var jsonBody = mapper.writeValueAsString(newProductDTO);
		String token = "Bearer "+ obtainAccessToken(username, password);
		
		ResultActions result = mockMvc.perform(delete("/produtcs/{id}", nonExistingId)
				.header("Authorization", token)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		        result.andExpect(status().isNotFound());
	}
	
	@Test
	void findAllShouldReturnPage() throws Exception {
		String token = "Bearer "+ obtainAccessToken(username, password);
		this.mockMvc.perform(get("/produtcs")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.content").exists());
	}

	private String obtainAccessToken(String username, String password) throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", clientId);
		params.add("username", username);
		params.add("password", password);

		ResultActions result = mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(clientId, clientSecret))
						.accept("application/json;charset=UTF-8"))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

}
