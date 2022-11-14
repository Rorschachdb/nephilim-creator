package org.rorschachdb.nephilim.online.creator.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.mappers.DegreeMapper;
import org.rorschachdb.nephilim.online.creator.back.model.entities.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.enums.DegreeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.*;
import static org.rorschachdb.nephilim.online.creator.back.controller.ControllerConstants.DEGREE_URI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DegreeControllerShould {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Test
    void findOne() throws Exception {
        this.mockMvc.perform(get(DEGREE_URI + "/2001"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Héros")))
                .andExpect(jsonPath("$.description", is("")))
                .andExpect(jsonPath("$.type", is("SIMULACRUM")))
        ;

    }

    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(get(DEGREE_URI))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(21)))
        .andExpect(jsonPath("$.content[1].type", is("ESOTERIC_KNOWLEDGE")))
        ;
    }

    @Test
    void findAllSorted() throws Exception{
        this.mockMvc.perform((get(DEGREE_URI).queryParam("sort", "name")))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name", is("666")))
                ;
    }

    @Test
    void save() throws Exception {
        final Degree.DegreeBuilder degreeBuilder = Degree.builder();
        final Degree degree = degreeBuilder
                .name("Fetch Quest")
                .description(null)
                .type(DegreeTypeEnum.OCCULT_ART)
                .build();

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post(DEGREE_URI)
                        .content(asJsonString(degree))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void update() {
    }
}