package co.fullstacklabs.cuboid.challenge.controller;

import co.fullstacklabs.cuboid.challenge.ApplicationConfig;
import co.fullstacklabs.cuboid.challenge.dto.NewBagDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
class BagControllerTest {
    private static final String BAG_PATH = "/bags";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldFetchAllBags() throws Exception {
        this.mockMvc.perform(get(BAG_PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Is.is("Title 1")))
                .andExpect(jsonPath("$[0].volume", Is.is(20.0)))
                .andExpect(jsonPath("$[1].title", Is.is("Title 2")))
                .andExpect(jsonPath("$[1].volume", Is.is(30.0)));
    }

    @Test
    void shouldFetchOneBagById() throws Exception {
        long id = 1L;
        this.mockMvc.perform(get(BAG_PATH + "/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Is.is("Title 1")));
    }

    @Test
    void shouldGetErrorWhenFetchBagById() throws Exception {
        long id = 99999L;
        this.mockMvc.perform(get(BAG_PATH + "/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewBag() throws Exception {
        NewBagDTO resource = NewBagDTO.builder().title("title new").volume(10d).build();

        this.mockMvc.perform(post(BAG_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", Is.is(resource.getTitle())));
    }

    @Test
    void invalidInputShouldReturnError() throws Exception {
        NewBagDTO resource = NewBagDTO.builder().build();

        this.mockMvc.perform(post(BAG_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertThat(
                        result.getResponse().getContentAsString()).contains("Bag volume can't be null."));
    }

    @Test
    void invalidTitleInputShouldReturnError() throws Exception {
        NewBagDTO resource = NewBagDTO.builder().title(" big text over 100".repeat(10)).build();

        this.mockMvc.perform(post(BAG_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertThat(
                        result.getResponse().getContentAsString()).contains("Bag title maximum size is 100 characters."));
    }

}