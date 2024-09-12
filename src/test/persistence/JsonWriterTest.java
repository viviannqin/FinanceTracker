package persistence;

import model.FinanceEntry;
import model.FinanceList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            FinanceList fl = new FinanceList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFinanceList() {
        try {
            FinanceList fl = new FinanceList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFinanceList.json");
            writer.open();
            writer.write(fl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFinanceList.json");
            fl = reader.read();
            assertEquals(0, fl.getNumberOfEntries());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFinanceList() {
        try {
            FinanceList fl = new FinanceList();
            fl.addEntry(new FinanceEntry(5.0, "04/20/2020", "BMO", "test"));
            fl.addEntry(new FinanceEntry(-10.0, "01/20/2020", "TD", "test2"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFinanceList.json");
            writer.open();
            writer.write(fl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFinanceList.json");
            fl = reader.read();
            List<FinanceEntry> finances = fl.getEntries();
            assertEquals(2, finances.size());
            checkFinanceEntry(5.0, "04/20/2020", "BMO", "test", finances.get(0));
            checkFinanceEntry(-10.0, "01/20/2020", "TD", "test2", finances.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
