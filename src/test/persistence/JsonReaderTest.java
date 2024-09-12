package persistence;

import model.FinanceEntry;
import model.FinanceList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FinanceList fl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFinanceList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFinanceList.json");
        try {
            FinanceList fl = reader.read();
            assertEquals(0, fl.getNumberOfEntries());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFinanceList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFinanceList.json");
        try {
            FinanceList fl = reader.read();
            List<FinanceEntry> finances = fl.getEntries();
            assertEquals(2, finances.size());
            checkFinanceEntry(5.0, "04/20/2020", "BMO", "test", finances.get(0));
            checkFinanceEntry(-10.0, "01/20/2020", "TD", "test2", finances.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
