package com.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static com.store.StockStore.PATCH_TO_FILES;

public class StockStoreTest {
    private static final Logger LOGGER = Logger.getLogger(StockStoreTest.class);
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void addFruits() {
        clear();
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        try {
            mapper.writeValue(new File(PATCH_TO_FILES + System.currentTimeMillis() + ".txt"), fruits);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        flowersStore.addInvoice();
        assertFalse(flowersStore.getSupplies().isEmpty());
        assertEquals(fruits, flowersStore.getSupplies());
    }

    @Test
    public void save() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        flowersStore.save(new File(PATCH_TO_FILES + "test.txt"));

        List<Fruit> expectedFruits = new ArrayList<>();
        try {
            Fruit[] arrayExpected = mapper.readValue(new File(PATCH_TO_FILES + "test.txt"), Fruit[].class);
            expectedFruits.addAll(Arrays.asList(arrayExpected));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        assertEquals(fruits, expectedFruits);
    }

    @Test
    public void load() {
        StockStore flowersStore = new StockStore();
        assertTrue(flowersStore.getSupplies().isEmpty());
        flowersStore.load(new File(PATCH_TO_FILES + "test.txt"));
        assertFalse(flowersStore.getSupplies().isEmpty());
    }

    @Test
    public void getSpoiledFruits() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        List<Fruit> expectedFruits = flowersStore.getSpoiledFruits(LocalDate.now().plusDays(11));
        assertEquals(2, expectedFruits.size());
        assertEquals(expectedFruits.get(0).getTypeFruit(), TypeFruit.APPLE);
        assertEquals(expectedFruits.get(1).getTypeFruit(), TypeFruit.APRICOT);
    }

    @Test
    public void getAvailableFruits() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        List<Fruit> expectedFruits = flowersStore.getAvailableFruits(LocalDate.now().plusDays(11));
        assertEquals(1, expectedFruits.size());
        assertEquals(expectedFruits.get(0).getTypeFruit(), TypeFruit.PEACH);
    }

    @Test
    public void getSpoiledFruits1() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        List<Fruit> expectedFruits = flowersStore.getSpoiledFruits(LocalDate.now().plusDays(11), TypeFruit.APPLE);
        assertEquals(2, expectedFruits.size());
        assertEquals(expectedFruits.get(0).getTypeFruit(), TypeFruit.APPLE);
        assertEquals(expectedFruits.get(1).getTypeFruit(), TypeFruit.APPLE);
    }

    @Test
    public void getAvailableFruits1() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        List<Fruit> expectedFruits = flowersStore.getAvailableFruits(LocalDate.now().plusDays(11), TypeFruit.APPLE);
        assertTrue(expectedFruits.isEmpty());
    }

    @Test
    public void getAddedFruits() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        List<Fruit> expectedFruits = flowersStore.getAddedFruits(LocalDate.now());
        assertEquals(3, expectedFruits.size());
        assertEquals(expectedFruits, fruits);
    }

    @Test
    public void getAddedFruits1() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        List<Fruit> expectedFruits = flowersStore.getAddedFruits(LocalDate.now(), TypeFruit.APPLE);
        assertEquals(1, expectedFruits.size());
        assertEquals(expectedFruits.get(0).getTypeFruit(), TypeFruit.APPLE);
    }

    public void clear() {
        File[] listOfFiles = new File(PATCH_TO_FILES).listFiles();
        if (listOfFiles != null)
            for (File file : listOfFiles) {
                file.delete();
            }
    }
}
