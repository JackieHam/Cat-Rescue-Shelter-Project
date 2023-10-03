package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShelterTest {
    private Shelter s;
    private Cat c1;
    private Cat c2;
    private Cat c3;

    @BeforeEach
    void runBefore() {
        s = new Shelter();
        c1 = new Cat("Jackie", 1, CatBreed.AmericanShortHair);
        c1.setStatus();
        c2 = new Cat("Jerry", 2, CatBreed.BritishShortHair);
        c3 = new Cat("Happy", 3, CatBreed.Others);
        s.addNewCat(c3);
    }

    @Test
    public void testConstructor() {
        assertEquals("Meowie Rescue Shelter", s.getName());
        assertEquals(1, s.getAllCats().size());
        assertEquals(1, s.getAllCatNames().size());
        assertEquals(c3, s.getAllCats().get(0));
        assertEquals("Happy", s.getAllCatNames().get(0));
        assertEquals(0, s.getAvailableCats().size());
        assertEquals(0, s.getAvailableCatNames().size());
    }

    @Test
    public void testIsValidName() {
        assertFalse(s.isValidName(c3.getName()));
        assertFalse(s.isValidName(""));
        assertTrue(s.isValidName(c1.getName()));
    }

    @Test
    public void testAddNewCat() {
        assertEquals(1, s.getAllCats().size());
        s.addNewCat(c2);
        assertEquals(2, s.getAllCats().size());
        assertEquals(0, s.getAvailableCats().size());
        s.addNewCat(c3);
        assertEquals(3, s.getAllCats().size());
        assertEquals(0, s.getAvailableCats().size());
    }


    @Test
    public void testCatsToCatNames() {
        s.addNewCat(c1);
        s.addNewCat(c2);
        List<String> allCatNames = s.catsToCatNames(s.getAllCats());
        assertEquals(allCatNames.size(), 3);
        assertEquals(allCatNames.get(0), "Happy");
        assertEquals(allCatNames.get(2), "Jerry");
    }

    @Test
    public void testRemoveAdoptedCat() {
        s.addNewCat(c1);
        s.addNewCat(c2);
//        s.setAvailableCatsAndNames();
        assertEquals(3, s.getAllCats().size());
        assertEquals(1, s.getAvailableCats().size());
        assertEquals(c3, s.getAllCats().get(0));

        c3.setStatus();
//        s.setAvailableCatsAndNames();
        assertEquals(2, s.getAvailableCats().size());

        s.removeAdoptedCat(c3);
        assertEquals(2, s.getAllCats().size());
        assertEquals(2, s.getAllCatNames().size());
        assertEquals(c1, s.getAllCats().get(0));
        assertEquals(1, s.getAvailableCats().size());
        assertEquals(1, s.getAvailableCatNames().size());
    }

    @Test
    public void testIsEmptyShelter() {
        s.addNewCat(c1);
        assertFalse(s.isEmptyShelter());
        assertEquals(2, s.getAllCats().size());

        c1.setStatus();
//        s.setAvailableCatsAndNames();
        s.removeAdoptedCat(c1);
        assertFalse(s.isEmptyShelter());
        assertEquals(1, s.getAllCats().size());

        c3.setStatus();
//        s.setAvailableCatsAndNames();
        s.removeAdoptedCat(c3);
        assertTrue(s.isEmptyShelter());
    }

    @Test
    public void testIsEmptyAvailability() {
        assertTrue(s.isEmptyAvailability());
        c3.setStatus();
//        s.setAvailableCatsAndNames();
        assertFalse(s.isEmptyAvailability());
    }
}
