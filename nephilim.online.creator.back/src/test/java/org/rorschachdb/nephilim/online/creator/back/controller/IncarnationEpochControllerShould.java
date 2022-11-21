package org.rorschachdb.nephilim.online.creator.back.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.rorschachdb.nephilim.online.creator.back.controller.ControllerConstants.INCARNATION_EPOCH_URI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IncarnationEpochControllerShould {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findOne() throws Exception {
        this.mockMvc.perform(get(INCARNATION_EPOCH_URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("La chute de l'atlantide")))
                .andExpect(jsonPath("$.cost", is(0)))
                .andExpect(jsonPath("$.era", is("LOST_ERA")))
                .andExpect(jsonPath("$.locations", not(nullValue())))
                .andExpect(jsonPath("$.locations", hasSize(1)))
                .andExpect(jsonPath("$.locations[0]", is("L'Atlantide")))
                .andExpect(jsonPath("$.magicEffects", hasSize(1)))
                .andExpect(jsonPath("$.magicEffects[0].occultScience", is("MAGIC")))
                .andExpect(jsonPath("$.magicEffects[0].quantity", is(2)))
                .andExpect(jsonPath("$.degreeValues", hasSize(10)))
        ;

    }

    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(get(INCARNATION_EPOCH_URI).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("La chute de l'atlantide")))
                .andExpect(jsonPath("$.content[0].cost", is(0)))
                .andExpect(jsonPath("$.content[0].era", is("LOST_ERA")))
                .andExpect(jsonPath("$.content[0].locations", not(nullValue())))
                .andExpect(jsonPath("$.content[0].locations", hasSize(1)))
                .andExpect(jsonPath("$.content[0].locations[0]", is("L'Atlantide")))
                .andExpect(jsonPath("$.content[0].magicEffects", hasSize(1)))
                .andExpect(jsonPath("$.content[0].magicEffects[0].occultScience", is("MAGIC")))
                .andExpect(jsonPath("$.content[0].magicEffects[0].quantity", is(2)))
                .andExpect(jsonPath("$.content[0].degreeValues", hasSize(10)))
                .andExpect(jsonPath("$.content[1].name", is("Le Déluge")))
                .andExpect(jsonPath("$.content[1].cost", is(2)))
                .andExpect(jsonPath("$.content[1].era", is("ELEMENTARY_WARS")))
                .andExpect(jsonPath("$.content[1].locations", anyOf(nullValue(), hasSize(0))))
                .andExpect(jsonPath("$.content[1].magicEffects", hasSize(1)))
                .andExpect(jsonPath("$.content[1].magicEffects[0].occultScience", is("MAGIC")))
                .andExpect(jsonPath("$.content[1].magicEffects[0].quantity", is(2)))
                .andExpect(jsonPath("$.content[1].degreeValues", hasSize(14)))

        ;
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}