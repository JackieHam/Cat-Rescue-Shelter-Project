package persistence;

import org.json.JSONObject;

// Source attribution: JsonWriterTest.java
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
