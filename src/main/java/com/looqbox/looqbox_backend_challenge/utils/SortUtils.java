package com.looqbox.looqbox_backend_challenge.utils;

import java.util.List;
import java.util.Map;

public class SortUtils { // Utils to use/create create sort algorithms

    // Why I choose Quick Sort O(n log n):
    // Use this algorithm cause is more fresh in my memory how it works, so I used
    // that tool,
    // I'm using String in my comparison, and in this list there are no equal name's
    // pokémons
    // So, I'm working with lists with several lenghts, and it's more useful for
    // this case, I think.
    public static void stringQuickSort(List<String> list, boolean byLength) {
        stringQuickSortHelper(list, 0, list.size() - 1, byLength);
    }

    private static void stringQuickSortHelper(List<String> list, int low, int high, boolean byLength) {
        if (low < high) {
            int pi = stringPartition(list, low, high, byLength);
            stringQuickSortHelper(list, low, pi - 1, byLength);
            stringQuickSortHelper(list, pi + 1, high, byLength);
        }
    }

    private static int stringPartition(List<String> list, int low, int high, boolean byLength) {
        String pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (stringShouldSwap(list.get(j), pivot, byLength)) {
                i++;
                stringSwapper(list, i, j);
            }
        }
        stringSwapper(list, i + 1, high);
        return i + 1;
    }

    // Comparing functions
    private static boolean stringShouldSwap(String str1, String str2, boolean byLength) {
        if (byLength) {
            if (str1.length() != str2.length()) {
                return str1.length() < str2.length();
            }
        }
        return str1.compareToIgnoreCase(str2) < 0;
    }

    private static void stringSwapper(List<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void mapQuickSort(List<Map<String, String>> pokemonMaps, boolean byLenght, String pokemonName) {
        // I choose to set the HighLight in the sort process
        // It will provide a good memory control with big lists cases
        if (pokemonName != null && pokemonName.length() > 0) {
            mapHightLightQuickSortHelper(pokemonMaps, 0, pokemonMaps.size() - 1, byLenght, pokemonName);
        }
        mapQuickSortHelper(pokemonMaps, 0, pokemonMaps.size() - 1, byLenght);
    }

    private static void mapQuickSortHelper(List<Map<String, String>> list, int low, int high, boolean byLenght) {
        if (low < high) {
            int pi = partition(list, low, high, byLenght);

            mapQuickSortHelper(list, low, pi - 1, byLenght);
            mapQuickSortHelper(list, pi + 1, high, byLenght);
        }
    }

    private static void mapHightLightQuickSortHelper(List<Map<String, String>> list, int low, int high,
            boolean byLenght, String searchTerm) {
        if (low < high) {
            int pi = partition(list, low, high, byLenght);

            // Apply the highlight only on filtered lists
            for (int i = low; i <= high; i++) {
                Map<String, String> pokemon = list.get(i);
                pokemon.put("highlight", createNameHighlight(pokemon.get("name"), searchTerm));
            }

            mapHightLightQuickSortHelper(list, low, pi - 1, byLenght, searchTerm);
            mapHightLightQuickSortHelper(list, pi + 1, high, byLenght, searchTerm);
        }
    }

    private static int partition(List<Map<String, String>> list, int low, int high, boolean byLenght) {
        Map<String, String> pivot = list.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (mapShouldSwap(list.get(j), pivot, byLenght)) {
                i++;
                mapSwapper(list, i, j);
            }
        }

        mapSwapper(list, i + 1, high);
        return i + 1;
    }

    private static boolean mapShouldSwap(Map<String, String> map1, Map<String, String> map2, boolean byLenght) {
        String str1 = map1.get("name");
        String str2 = map2.get("name");

        if (byLenght) {
            if (str1.length() != str2.length()) {
                return str1.length() < str2.length();
            }
            return str1.compareToIgnoreCase(str2) < 0;
        }
        return str1.compareToIgnoreCase(str2) < 0;
    }

    private static void mapSwapper(List<Map<String, String>> list, int i, int j) {
        Map<String, String> temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private static String createNameHighlight(String name, String searchTerm) {
        int startIndex = name.toLowerCase().indexOf(searchTerm.toLowerCase());
        if (startIndex == -1)
            return name;

        String before = name.substring(0, startIndex);
        String match = name.substring(startIndex, startIndex + searchTerm.length());
        String after = name.substring(startIndex + searchTerm.length());

        return before + "<pre>" + match + "</pre>" + after;
    }
}
