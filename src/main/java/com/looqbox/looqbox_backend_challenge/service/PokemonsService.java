package com.looqbox.looqbox_backend_challenge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.looqbox.looqbox_backend_challenge.entity.enums.SortType;
import com.looqbox.looqbox_backend_challenge.utils.SortUtils;
import com.looqbox.looqbox_backend_challenge.utils.Urls;

@Service
public class PokemonsService { // Service with the methods wich integrate with poké-api
    // It uses Single Responsibility Principle (SOLID)

    private final String POKE_API_URL = "https://pokeapi.co/api/v2/pokemon";

    public List<String> getPokemons(String pokemonName, SortType sortType) {
        try {
            String result = Urls.getFrom(POKE_API_URL
                    .concat("?limit=100000&offset=0"));

            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("results");

            if (pokemonName != null && pokemonName.length() > 0) {
                return sortList(getFilteredNames(jsonArray, pokemonName), sortType);
            }
            return sortList(getAllPokemonsNames(jsonArray), sortType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não foi possível efetuar sua busca.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não foi possível efetuar sua busca.");
        }
    }

    public List<Map<String, String>> getHighLightPokemonNames(String pokemonName, SortType sortType) {

        try {
            String result = Urls.getFrom(POKE_API_URL
                    .concat("?limit=100000&offset=0"));

            JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("results");

            if (pokemonName != null && pokemonName.length() > 0) {
                return sortMapList(filterPokemonNamesToMapList(jsonArray, pokemonName), sortType, pokemonName);
            }
            return sortMapList(getAllPokemonNamesToMapList(jsonArray), sortType, pokemonName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não foi possível efetuar sua busca.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não foi possível efetuar sua busca.");
        }

    }

    // I choose using "stream()"" instead of a "for" to search in the Json response
    // Because I think this way is more clean and essentialy concise to compare the
    // Strings

    private List<String> getAllPokemonsNames(JsonArray jsonArray) {

        return StreamSupport.stream(jsonArray.spliterator(), false)
                .map(element -> element.getAsJsonObject().get("name").getAsString())
                .collect(Collectors.toList());

    }

    private List<String> getFilteredNames(JsonArray jsonArray, String pokemonName) {

        return StreamSupport.stream(jsonArray.spliterator(), false)
                .map(element -> element.getAsJsonObject().get("name").getAsString())
                .filter(name -> name.toLowerCase().contains(pokemonName.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> filterPokemonNamesToMapList(JsonArray jsonArray, String pokemonName) {
        List<Map<String, String>> pokemonMapsList = new ArrayList<>();

        // By using Map List, I choose to use for loop, is more simple and easy for
        // understand for me
        for (JsonElement element : jsonArray) {
            String name = element.getAsJsonObject().get("name").getAsString();
            if (name.toLowerCase().contains(pokemonName.toLowerCase())) {
                Map<String, String> pokemonMap = new HashMap<>();
                pokemonMap.put("name", name);
                pokemonMapsList.add(pokemonMap);
            }
        }
        return pokemonMapsList;
    }

    private List<Map<String, String>> getAllPokemonNamesToMapList(JsonArray jsonArray) {
        List<Map<String, String>> pokemonMapsList = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            String name = element.getAsJsonObject().get("name").getAsString();
            Map<String, String> pokemonMap = new HashMap<>();
            pokemonMap.put("name", name);
            pokemonMapsList.add(pokemonMap);
        }
        return pokemonMapsList;
    }

    private List<String> sortList(List<String> names, SortType sortType) {
        if (sortType.equals(SortType.LENGTH)) {
            SortUtils.stringQuickSort(names, true);
            return names;
        }
        SortUtils.stringQuickSort(names, false);
        return names;
    }

    private List<Map<String, String>> sortMapList(List<Map<String, String>> pokemonMapList, SortType sortType,
            String pokemonName) {
        if (sortType.equals(SortType.LENGTH)) {
            SortUtils.mapQuickSort(pokemonMapList, true, pokemonName);
            return pokemonMapList;
        }
        SortUtils.mapQuickSort(pokemonMapList, false, pokemonName);
        return pokemonMapList;
    }
}
