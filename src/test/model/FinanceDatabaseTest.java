package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceDatabaseTest {

    private FinanceList financeList;
    private FinanceDatabase fdbObject;
    private FinanceEntry f1;
    private FinanceEntry f2;

    @BeforeEach
    public void runBefore() {
        financeList = new FinanceList();
        fdbObject = financeList.getFinanceDatabaseObject();
        f1 = new FinanceEntry(-5.00, "09/05/2012", "BMO", "food");
        f2 = new FinanceEntry(2.00, "01/03/2014", "TD", "e-transfer");
    }

    @Test
    public void testStoreEntry() {
        List<FinanceEntry> financeDatabase = fdbObject.getFinanceDatabase();

        Boolean stored = fdbObject.storeEntry(f1);
        assertEquals(1, financeDatabase.size());
        assertTrue(stored);

        stored = fdbObject.storeEntry(f2);
        assertEquals(2, financeDatabase.size());
        assertTrue(stored);

        stored = fdbObject.storeEntry(f1);
        assertEquals(2, financeDatabase.size());
        assertFalse(stored);
    }

}
