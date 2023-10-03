package persistence;

import model.Cat;
import model.CatBreed;
import model.Shelter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Source attribution: JsonWriterTest.java
// Represents a reader that reads shelter from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads shelter from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Shelter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShelter(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses shelter from JSON object and returns it
    private Shelter parseShelter(JSONObject jsonObject) {
        String name = jsonObject.getString("shelter name");
        Shelter st = new Shelter();
        addAllCats(st, jsonObject);
        return st;
    }

    // MODIFIES: st
    // EFFECTS: parses allCats from JSON object and adds them to shelter
    private void addAllCats(Shelter st, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cats");
        for (Object json : jsonArray) {
            JSONObject nextCat = (JSONObject) json;
            addCat(st, nextCat);
        }
    }

    // MODIFIES: st
    // EFFECTS: parses cat from JSON object and adds it to shelter
    private void addCat(Shelter st, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int age  = jsonObject.getInt("age");
        CatBreed catBreed = CatBreed.valueOf(jsonObject.getString("breed"));
        int price = jsonObject.getInt("price");
        boolean status = jsonObject.getBoolean("status");
        Cat newCat = new Cat(name, age, catBreed);
//        newCat.setCost(catBreed);
        if (status) {
            newCat.setStatus();
        }
        st.addNewCat(newCat);
    }
}
