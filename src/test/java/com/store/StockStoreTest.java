package com.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        LOGGER.info(flowersStore);
        clear();
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
    }

    @Test
    public void load() {
        StockStore flowersStore = new StockStore();
        LOGGER.info(flowersStore.getSupplies());
        flowersStore.load(new File(PATCH_TO_FILES + "test.txt"));
        LOGGER.info(flowersStore.getSupplies());
    }

    @Test
    public void getSpoiledFruits() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        LOGGER.info(flowersStore.getSpoiledFruits(LocalDate.now().plusDays(11)));
    }

    @Test
    public void getAvailableFruits() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        LOGGER.info(flowersStore.getAvailableFruits(LocalDate.now().plusDays(11)));
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
        LOGGER.info(flowersStore.getSpoiledFruits(LocalDate.now().plusDays(11), TypeFruit.APPLE));
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
        LOGGER.info(flowersStore.getAvailableFruits(LocalDate.now().plusDays(7), TypeFruit.PEACH));
    }

    @Test
    public void getAddedFruits() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        LOGGER.info(flowersStore.getAddedFruits(LocalDate.now()));
    }

    @Test
    public void getAddedFruits1() {
        StockStore flowersStore = new StockStore();
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(TypeFruit.APPLE, 5, 1));
        fruits.add(new Fruit(TypeFruit.APRICOT, 10, 2));
        fruits.add(new Fruit(TypeFruit.PEACH, 15, 4));
        flowersStore.setSupplies(fruits);
        LOGGER.info(flowersStore.getAddedFruits(LocalDate.now(), TypeFruit.APPLE));
    }

    public void clear() {
        File[] listOfFiles = new File(PATCH_TO_FILES).listFiles();
        if (listOfFiles != null)
            for (File file : listOfFiles) {
                file.delete();
            }
    }
}
