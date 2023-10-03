package persistence;

import model.Cat;
import model.CatBreed;
import model.Shelter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Source attribution: JsonWriterTest.java
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Shelter st = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShelter() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShelter.json");
        try {
            Shelter st = reader.read();
            assertEquals("Meowie Rescue Shelter", st.getName());
            assertEquals(0, st.getAllCats().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShelter() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShelter.json");
        try {
            Shelter st = reader.read();
            assertEquals("Meowie Rescue Shelter", st.getName());
            List<Cat> allCats = st.getAllCats();
            assertEquals(2, allCats.size());
            checkCat("1", 1, CatBreed.AmericanShortHair, 500, true, allCats.get(0));
            checkCat("2", 2, CatBreed.BritishShortHair, 400, false, allCats.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
