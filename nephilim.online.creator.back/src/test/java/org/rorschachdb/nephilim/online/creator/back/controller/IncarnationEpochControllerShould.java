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
                .andExpect(jsonPath("$.name", is("Fall of Atlantis")))
                .andExpect(jsonPath("$.cost", is(0)))
                .andExpect(jsonPath("$.era", is("LOST_ERA")))
                .andExpect(jsonPath("$.locations", not(nullValue())))
                .andExpect(jsonPath("$.locations", hasSize(1)))
                .andExpect(jsonPath("$.locations[0]", is("Atlantis")))
        ;

    }

    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(get(INCARNATION_EPOCH_URI))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Fall of Atlantis")))
                .andExpect(jsonPath("$.content[0].cost", is(0)))
                .andExpect(jsonPath("$.content[0].era", is("LOST_ERA")))
                .andExpect(jsonPath("$.content[0].locations", not(nullValue())))
                .andExpect(jsonPath("$.content[0].locations", hasSize(1)))
                .andExpect(jsonPath("$.content[0].locations[0]", is("Atlantis")))
                .andExpect(jsonPath("$.content[1].name", is("The Deluge")))
                .andExpect(jsonPath("$.content[1].cost", is(2)))
                .andExpect(jsonPath("$.content[1].era", is("ELEMENTARY_WARS")))
                .andExpect(jsonPath("$.content[1].locations", anyOf(nullValue(), hasSize(0))))

        ;
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}