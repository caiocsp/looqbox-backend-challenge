package com.looqbox.looqbox_backend_challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.looqbox.looqbox_backend_challenge.controller.PokemonsController;
import com.looqbox.looqbox_backend_challenge.pokemons.PokemonsHighLightTest;
import com.looqbox.looqbox_backend_challenge.pokemons.PokemonsTest;
import com.looqbox.looqbox_backend_challenge.service.PokemonsService;

@SpringBootTest
@AutoConfigureMockMvc
class LooqboxBackendChallengeApplicationTests {

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		PokemonsService pokemonsService = new PokemonsService(); // Você precisará instanciar com as dependências
																	// necessárias
		PokemonsController pokemonsController = new PokemonsController(pokemonsService);

		this.mockMvc = MockMvcBuilders.standaloneSetup(pokemonsController)
				.setControllerAdvice() // Se você tiver @ControllerAdvice
				.addFilters() // Se você tiver filtros
				.build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void checkPokemonsGetWithSuccess() throws Exception {
		PokemonsTest pokemonsTest = new PokemonsTest();
		pokemonsTest.shouldReturnStatusOkAfterGet(mockMvc);
		pokemonsTest.shouldReturnStatusOkAfterGetWithParameters(mockMvc);
	}

	@Test
	public void checkPokemonsGetWithError() throws Exception {
		PokemonsTest pokemonsTest = new PokemonsTest();
		pokemonsTest.shouldReturnBadRequestWithInvalidSortType(mockMvc);
		pokemonsTest.shouldReturnErrorAfterGetWithParameters(mockMvc);

	}

	@Test
	public void checkPokemonsGetHighLightWithSuccess() throws Exception {
		PokemonsHighLightTest pokemonsHighLightTest = new PokemonsHighLightTest();
		pokemonsHighLightTest.shouldReturnStatusOkAfterGet(mockMvc);
		pokemonsHighLightTest.shouldReturnStatusOkAfterGetWithRightParameters(mockMvc);
	}

	@Test
	public void checkPokemonsGetHighLightWithError() throws Exception {
		PokemonsHighLightTest pokemonsHighLightTest = new PokemonsHighLightTest();
		pokemonsHighLightTest.shouldReturnBadRequestWithWrongParametersAndInvalidSortType(mockMvc);

	}
}
