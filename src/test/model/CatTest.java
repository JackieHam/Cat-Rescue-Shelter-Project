package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CatTest {
    private Cat c1;
    private Cat c2;
    private Cat c3;

    @BeforeEach
    void runBefore() {
        c1 = new Cat("Jackie", 1, CatBreed.AmericanShortHair);
        c2 = new Cat("Jerry", 2, CatBreed.BritishShortHair);
        c3 = new Cat("Happy",3, CatBreed.Others);
    }

    @Test
    void testConstructor() {
        assertEquals("Jackie", c1.getName());
        assertEquals(1,c1.getAge());
        assertEquals(CatBreed.AmericanShortHair, c1.getBreed());
        assertFalse(c1.getStatus());

        assertEquals("Jerry", c2.getName());
        assertEquals(2, c2.getAge());
        assertEquals(CatBreed.BritishShortHair, c2.getBreed());
        assertFalse(c2.getStatus());

        assertEquals("Happy", c3.getName());
        assertEquals(3, c3.getAge());
        assertEquals(CatBreed.Others, c3.getBreed());
        assertFalse(c3.getStatus());

        assertEquals(500, c1.getCost());
        assertEquals(400, c2.getCost());
        assertEquals(300, c3.getCost());
    }

    @Test
    void testSetStatus() {
        c1.setStatus();
        assertTrue(c1.getStatus());
        assertTrue(c1.isAvailableForAdoption());

        c1.setStatus();
        assertFalse(c2.getStatus());
        assertFalse(c1.isAvailableForAdoption());
    }

    @Test
    void testOtherSetters() {
        c1.setAge(2);
        assertEquals(2, c1.getAge());

        c2.setBreed(CatBreed.Tabby);
        assertEquals(CatBreed.Tabby, c2.getBreed());
    }
}
