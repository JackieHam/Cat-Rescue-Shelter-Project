package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a cat with name, age, breed, price for adoption, and status of availability for adoption.
public class Cat implements Writable {
    private String name;
    private int age;
    private CatBreed breed;
    private int priceForAdoption;
    private Boolean status;

    public Cat(String name, int age, CatBreed breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.status = false;

        int priceForAmericanShortHair = 500;
        int priceForBritishShortHair = 400;
        int priceForOther = 300;

        if (this.breed == CatBreed.AmericanShortHair) {
            this.priceForAdoption = priceForAmericanShortHair;
        } else if (breed == CatBreed.BritishShortHair) {
            this.priceForAdoption = priceForBritishShortHair;
        } else {
            this.priceForAdoption = priceForOther;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setAge(int ageSet) {
        this.age = ageSet;
    }

    public int getAge() {
        return this.age;
    }

    public void setBreed(CatBreed breedSet) {
        this.breed = breedSet;
    }

    public CatBreed getBreed() {
        return this.breed;
    }

    public int getCost() {
        return this.priceForAdoption;
    }

    // EFFECTS: log the event in event logger
    public void setStatus() {
        this.status = this.status.equals(false);
        EventLog.getInstance().logEvent(new Event("Adoption status of "
                + this.getName() + " changed to " + this.status + "."));
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Boolean isAvailableForAdoption() {
        return getStatus();
    }

    // Source attribution: JsonWriterTest.java
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("breed", breed);
        json.put("price", priceForAdoption);
        json.put("status", status);
        return json;
    }
}
