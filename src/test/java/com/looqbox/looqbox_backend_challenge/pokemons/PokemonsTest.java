package com.looqbox.looqbox_backend_challenge.pokemons;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PokemonsTest {

    public void shouldReturnStatusOkAfterGet(MockMvc mockMvc) throws Exception {
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String result = actions.andReturn().getResponse().getContentAsString();

        assertNotNull(result);
        assertTrue(result.contains("charmander"));
    }

    public void shouldReturnStatusOkAfterGetWithParameters(MockMvc mockMvc) throws Exception {
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons?pokemonName=char&sortType=LENGTH")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String result = actions.andReturn().getResponse().getContentAsString();

        assertNotNull(result);
        assertTrue(result.contains("charizard"));
    }

    public void returnErrorAfterGet(MockMvc mockMvc) throws Exception {

    }

    public void shouldReturnErrorAfterGetWithParameters(MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons?pokemon=char&sortType=LENG")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    public void shouldReturnBadRequestWithInvalidSortType(MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons")
                .param("sortType", "INVALID_SORT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
