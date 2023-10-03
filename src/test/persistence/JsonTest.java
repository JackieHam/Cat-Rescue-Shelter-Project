package persistence;

import model.Cat;
import model.CatBreed;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Source attribution: JsonWriterTest.java
public class JsonTest {
    protected void checkCat(String name, int age, CatBreed breed, int price, Boolean status, Cat cat) {
        assertEquals(name, cat.getName());
        assertEquals(age, cat.getAge());
        assertEquals(breed, cat.getBreed());
        assertEquals(price, cat.getCost());
        assertEquals(status, cat.getStatus());
    }
}