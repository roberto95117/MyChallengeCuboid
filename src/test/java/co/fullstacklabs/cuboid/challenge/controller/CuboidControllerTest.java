package co.fullstacklabs.cuboid.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import co.fullstacklabs.cuboid.challenge.ApplicationConfig;
import co.fullstacklabs.cuboid.challenge.dto.CuboidDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
class CuboidControllerTest {

    private static final String PATH = "/cuboids";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldUpdateCuboid() throws Exception {
    	CuboidDTO cuboidDTO = CuboidDTO.builder()
                .id(1l).width(2f).height(3f).depth(2f).volume(12d).bagId(3L).build();

        this.mockMvc.perform(put(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void invalidInputInUpdateShouldReturnError() throws Exception {
    	CuboidDTO cuboidDTO = CuboidDTO.builder()
                .id(1l).width(2f).height(3f).depth(2f).volume(12d).build();
    	
    	this.mockMvc.perform(put(PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuboidDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.violations", IsNot.not(IsNull.nullValue())));
    }

    @Test
    void shouldGetErrorWhenCuboidByIdIsEmpty() throws Exception {
       this.mockMvc.perform(get(PATH + "/{id}", 5l))
       .andExpect(status().isNotFound())
       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
       .andExpect(jsonPath("$.message", is("Cuboid not found")));
    }

    @Test
    void shouldGetErrorOnUpdateWhenBagIdIsNotFound() throws Exception {
    	CuboidDTO cuboidDTO = CuboidDTO.builder()
                .id(1l).width(2f).height(3f).depth(2f).volume(12d).bagId(5L).build();

        this.mockMvc.perform(put(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message", is("Bag not found")));
    }

    @Test
    void shouldGetErrorOnUpdateWhenBagCantProcessCuboidVolumeChange() throws Exception {
    	CuboidDTO cuboidDTO = CuboidDTO.builder()
                .id(1l).width(10f).height(20f).depth(30f).volume(30d).bagId(1L).build();
    	this.mockMvc.perform(put(PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuboidDTO)))
    	.andExpect(status().isUnprocessableEntity())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message", is("Bag with not enough capacity")));
    	
    }

    @Test
    void shouldDeleteCuboid() throws Exception {
    	  this.mockMvc.perform(delete(PATH + "/{id}", 1l))
          .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteWhenCuboidNotFound() throws Exception {
    	this.mockMvc.perform(delete(PATH + "/{id}", 6l))
    	.andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message", is("Cuboid not found")));
    }
    
    /************************************************************
     *                                                           *
     * DO NOT change the tests BELOW, implement the test ABOVE   *
     *                                                           *
    *************************************************************/
    @Test
    void shouldFetchAllCuboids() throws Exception {
        this.mockMvc.perform(get(PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$", IsNot.not(IsNull.nullValue())))
                .andExpect(result -> Assertions.assertThat(
                                result.getResponse().getContentAsString())
                        .contains("\"id\":3,\"width\":3.0,\"height\":3.0,\"depth\":3.0,\"volume\":27.0,\"bagId\":3"));
    }

    @Test
    void shouldCreateNewCuboid() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder()
                .width(2f).height(3f).depth(2f).volume(12d).bagId(3L).build();

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void invalidInputInPostShouldReturnError() throws Exception {

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CuboidDTO.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.violations", IsNot.not(IsNull.nullValue())));
    }

    @Test
    void shouldGetErrorCreatingWhenBagNotFound() throws Exception {
        CuboidDTO cuboidDTO = CuboidDTO.builder()
                .width(2f).height(3f).depth(2f).volume(12d).bagId(55L).build();

        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetErrorCreatingWhenBagCantProcessNewCuboid() throws Exception {
        long id = 1L;
        CuboidDTO cuboidDTO = CuboidDTO.builder().width(20f).height(5f)
                .depth(5f).volume(50d).bagId(id).build();
        this.mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuboidDTO)))
                .andExpect(status().isUnprocessableEntity());
    }
}