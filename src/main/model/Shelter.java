package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Represents a shelter with list of all cats, list of all cat names, list of cats available for adoption,
//                      and list of names of cats available for adoption

public class Shelter extends JPanel implements Writable {
    private List<Cat> allCats;
    private final String shelterName;

    public Shelter() {
        allCats = new ArrayList<>();
        shelterName = "Meowie Rescue Shelter";
    }

    // MODIFIES: this
    // EFFECTS: adds a new cat to the list of allCats, regardless of its availability, log the event in event logger
    public void addNewCat(Cat c) {
        allCats.add(c);
        EventLog.getInstance().logEvent(new Event("New cat named " + c.getName() + " added to the shelter."));
    }

    // MODIFIES: this
    // EFFECTS: removes an adopted cat from allCats, allCatNames, availableCats and availableCatNames, log the event in
    // event logger
    public void removeAdoptedCat(Cat c) {
        allCats.remove(c);
        EventLog.getInstance().logEvent(new Event("Adopted cat " + c.getName() + " removed from the shelter."));
    }

    public String getName() {
        return this.shelterName;
    }

    public List<Cat> getAllCats() {
        return this.allCats;
    }

    public List<String> getAllCatNames() {
        return catsToCatNames(allCats);
    }

    public List<Cat> getAvailableCats() {
        List<Cat> availableCats = new ArrayList<>();
        for (Cat c : allCats) {
            if (c.getStatus()) {
                availableCats.add(c);
            }
        }
        return availableCats;
    }

    public List<String> getAvailableCatNames() {
        List<Cat> availableCats = getAvailableCats();
        return catsToCatNames(availableCats);
    }

    // EFFECTS: returns true if name given to a cat newly added to the rescue shelter is valid
    public Boolean isValidName(String s) {
        List<String> allCatNames = catsToCatNames(allCats);
        return (!allCatNames.contains(s) && !s.isEmpty());
    }

    // EFFECTS: returns list of cat names based on list of cats consumed
    public List<String> catsToCatNames(List<Cat> list) {
        List<String> allCatNames = new ArrayList<>();
        for (Cat c : list) {
            allCatNames.add(c.getName());
        }
        return allCatNames;
    }


    // EFFECTS: returns true if there are no cats in the shelter
    public Boolean isEmptyShelter() {
        return (allCats.isEmpty());
    }

    // EFFECTS: returns true if there are no cats available for adoption in the shelter
    //         (there might be cats in the shelter that are unavailable for adoption)
    public Boolean isEmptyAvailability() {
        List<Cat> availableCats = getAvailableCats();
        return (availableCats.isEmpty());
    }

    // Source attribution: JsonWriterTest.java
    // Effects: convert the entire shelter as a JSON array
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("shelter name", shelterName);
        json.put("cats", allCatsToJson());
        return json;

    }

    // Source attribution: JsonWriterTest.java
    // EFFECTS: returns allCats in this shelter as a JSON array
    private JSONArray allCatsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cat c : allCats) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
