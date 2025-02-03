package com.looqbox.looqbox_backend_challenge.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.looqbox.looqbox_backend_challenge.entity.ResponsePattern;
import com.looqbox.looqbox_backend_challenge.entity.enums.SortType;
import com.looqbox.looqbox_backend_challenge.service.PokemonsService;

@RestController
@RequestMapping(value = "/pokemons")
public class PokemonsController {

    private final PokemonsService pokemonsService;

    public PokemonsController(PokemonsService pokemonsService) {
        this.pokemonsService = pokemonsService;
    }

    @GetMapping
    public ResponseEntity<ResponsePattern<List<String>>> returnSearchedPokemons(
            @RequestParam(required = false, defaultValue = "") String pokemonName,
            @RequestParam(required = false, defaultValue = "ALPHABETICAL") SortType sortType) {
        return ResponseEntity.ok(ResponsePattern.getResponseObject(
                pokemonsService.getPokemons(pokemonName, sortType)));
    }

    @GetMapping(path = "/highlight")
    public ResponseEntity<ResponsePattern<List<Map<String, String>>>> returnHighlightedPokemons(
            @RequestParam(required = false, defaultValue = "") String pokemonName,
            @RequestParam(required = false, defaultValue = "ALPHABETICAL") SortType sortType) {
        return ResponseEntity.ok(ResponsePattern.getResponseObject(
                pokemonsService.getHighLightPokemonNames(pokemonName, sortType)));
    }

}
