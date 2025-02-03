package com.looqbox.looqbox_backend_challenge.pokemons;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PokemonsHighLightTest {
    public void shouldReturnStatusOkAfterGet(MockMvc mockMvc) throws Exception {
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/highlight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String result = actions.andReturn().getResponse().getContentAsString();

        assertNotNull(result);
        assertTrue(result.contains("charizard"));
    }

    public void shouldReturnStatusOkAfterGetWithRightParameters(MockMvc mockMvc) throws Exception {
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/highlight?pokemonName=char&sortType=LENGTH")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        String result = actions.andReturn().getResponse().getContentAsString();

        assertNotNull(result);
        assertTrue(result.contains("<pre>"));
    }

    public void shouldReturnBadRequestWithWrongParametersAndInvalidSortType(MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/highlight?")
                .param("sortType", "INVALID_SORT")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
