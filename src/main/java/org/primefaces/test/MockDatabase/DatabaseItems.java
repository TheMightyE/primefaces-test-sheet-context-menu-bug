package org.primefaces.test.MockDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.primefaces.test.Item;


public class DatabaseItems {
    private static List<Item> items = new ArrayList<>();
    public static final String MOVIE = "movie";
    public static final String BOOK = "book";

    static {
        init();
    }

    public static void init() {
        items = new ArrayList<>();
        items.addAll(Arrays.asList(
                // Movies
                new Item("The Dark Knight", MOVIE),
                new Item("Flushed Away", MOVIE),
                new Item("Boo, Zino & the Snurks", MOVIE),
                // Books
                new Item("Beneath a Scarlet Sky", BOOK),
                new Item("Kite Runner", BOOK),
                new Item("Hamlet", BOOK)));
    }

    public static List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public static void addAll(List<Item> itemsToAdd) {
        items.addAll(itemsToAdd);
    }

    public static void clear(String type) {
        items.removeIf(item -> item.getType().equals(type));
    }

    public static List<Item> getMovies() {
        return items.stream().filter(item -> item.getType().equals(MOVIE)).collect(Collectors.toList());
    }

    public static List<Item> getBooks() {
        return items.stream().filter(item -> item.getType().equals(BOOK)).collect(Collectors.toList());
    }

    public static void update(Item itemToUpdate) {
        Item foundItem = items.stream().filter(item -> item.getId().equals(itemToUpdate.getId()))
                .collect(Collectors.toList()).get(0);
        foundItem.setName(itemToUpdate.getName());
    }
}
