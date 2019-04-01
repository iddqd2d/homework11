package com.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StockStore {
    private static final Logger LOGGER = Logger.getLogger(StockStore.class);
    public static final String PATCH_TO_FILES = "./src/main/resources/stock/";
    private List<Fruit> supplies;
    private ObjectMapper mapper;

    public StockStore() {
        supplies = new ArrayList<>();
        mapper = new ObjectMapper();
    }

    public List<Fruit> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Fruit> supply) {
        this.supplies.addAll(supply);
    }

    public void addInvoice() {
        File[] listOfFiles = new File(PATCH_TO_FILES).listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    addFruits(file);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    public void addFruits(File pathToJsonFile) throws IOException {
        Fruit[] fruits = mapper.readValue(pathToJsonFile, Fruit[].class);
        if (fruits != null) {
            supplies.addAll(Arrays.asList(fruits));
        }
    }

    public void save(File pathToJsonFile) {
        try {
            mapper.writeValue(pathToJsonFile, supplies);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void load(File pathToJsonFile) {
        supplies.clear();
        try {
            addFruits(pathToJsonFile);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private LocalDate parseExpectedDate(Fruit fruit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        return LocalDate.parse(fruit.getDeliveryDate(), formatter).plusDays(fruit.getShelfLife());
    }

    private LocalDate parseDate(Fruit fruit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        return LocalDate.parse(fruit.getDeliveryDate(), formatter);
    }

    public List<Fruit> getSpoiledFruits(LocalDate date) {
        return supplies.stream().filter(x -> parseExpectedDate(x).isBefore(date)).collect(Collectors.toList());
    }

    public List<Fruit> getAvailableFruits(LocalDate date) {
        return supplies.stream().filter(x -> parseExpectedDate(x).isAfter(date)).collect(Collectors.toList());
    }

    public List<Fruit> getSpoiledFruits(LocalDate date, TypeFruit typeFruit) {
        return supplies.stream().filter(x -> parseExpectedDate(x).isBefore(date) && x.getTypeFruit().equals(typeFruit)).collect(Collectors.toList());
    }

    public List<Fruit> getAvailableFruits(LocalDate date, TypeFruit typeFruit) {
        return supplies.stream().filter(x -> parseExpectedDate(x).isAfter(date) && x.getTypeFruit().equals(typeFruit)).collect(Collectors.toList());
    }

    public List<Fruit> getAddedFruits(LocalDate date) {
        return supplies.stream().filter(x -> parseDate(x).isEqual(date)).collect(Collectors.toList());
    }

    public List<Fruit> getAddedFruits(LocalDate date, TypeFruit typeFruit) {
        return supplies.stream().filter(x -> parseDate(x).isEqual(date) && x.getTypeFruit().equals(typeFruit)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        supplies.forEach(x -> stringBuilder.append(x.toString()));
        return stringBuilder.toString();
    }
}
