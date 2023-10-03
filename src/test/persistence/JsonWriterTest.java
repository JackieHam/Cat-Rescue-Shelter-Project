package persistence;

import model.Cat;
import model.CatBreed;
import model.Shelter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Source attribution: JsonWriterTest.java
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Shelter st = new Shelter();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShelter() {
        try {
            Shelter st = new Shelter();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShelter.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShelter.json");
            st = reader.read();
            assertEquals("Meowie Rescue Shelter", st.getName());
            assertEquals(0, st.getAllCats().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Shelter st = new Shelter();
            Cat catThree = new Cat("3", 3, CatBreed.Tabby);
            st.addNewCat(catThree);

            Cat catFour = new Cat("4", 4, CatBreed.Others);
            catFour.setStatus();
            st.addNewCat(catFour);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShelter.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShelter.json");
            st = reader.read();
            assertEquals("Meowie Rescue Shelter", st.getName());
            List<Cat> allCats = st.getAllCats();
            assertEquals(2, allCats.size());
            checkCat("3", 3, CatBreed.Tabby, 300, false, allCats.get(0));
            checkCat("4", 4, CatBreed.Others, 300, true, allCats.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}