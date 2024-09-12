package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FinanceListTest {
    private FinanceEntry f1;
    private FinanceEntry f2;
    private FinanceEntry f3;
    private FinanceEntry f4;
    private FinanceList testFinanceList;
    private ArrayList<FinanceEntry> finances;

    @BeforeEach
    void runBefore() {
        f1 = new FinanceEntry(-5.00, "09/05/2012", "BMO", "food");
        f2 = new FinanceEntry(2.00, "01/03/2014", "TD", "e-transfer");
        f3 = new FinanceEntry(0.00, "05/02/2010", "TD", "nothing");
        f4 = new FinanceEntry(10.00, "06/02/2015", "AMEX", "salary");
        testFinanceList = new FinanceList();
        finances = testFinanceList.getFinanceLog();
    }

    @Test
    void addFinanceTest() {
        testFinanceList.addEntry(f1);
        assertEquals(1, testFinanceList.getNumberOfEntries());
        testFinanceList.addEntry(f2);
        assertEquals(2, testFinanceList.getNumberOfEntries());
        testFinanceList.addEntry(f3);
        assertEquals(2, testFinanceList.getNumberOfEntries());
    }

    @Test
    void containsFinanceTest() {
        testFinanceList.addEntry(f1);
        assertTrue(testFinanceList.containsEntry(f1));
    }

    @Test
    void removeFinanceTest() {
        testFinanceList.addEntry(f1);
        assertEquals(1, testFinanceList.getNumberOfEntries());
        assertTrue(testFinanceList.containsEntry(f1));

        testFinanceList.removeEntry(f1);
        assertEquals(0, testFinanceList.getNumberOfEntries());

        testFinanceList.addEntry(f1);
        assertFalse(testFinanceList.getEntries().contains(f2));
        assertEquals(1, testFinanceList.getNumberOfEntries());
        testFinanceList.removeEntry(f2);
        assertTrue(testFinanceList.containsEntry(f1));
        assertEquals(1, testFinanceList.getNumberOfEntries());
    }

    @Test
    void netAmountTest() {
        testFinanceList.addEntry(f1);
        testFinanceList.addEntry(f2);
        assertTrue(testFinanceList.containsEntry(f1));
        assertTrue(testFinanceList.containsEntry(f2));

        assertEquals(-3.00, testFinanceList.getNetAmount());
    }

    @Test
    void savingGoalTest() {
        testFinanceList.addEntry(f1);
        testFinanceList.addEntry(f2);
        assertTrue(testFinanceList.containsEntry(f1));
        assertTrue(testFinanceList.containsEntry(f2));

        assertEquals(null, testFinanceList.budget(5.00));

        testFinanceList.addEntry(f4);
        assertTrue(testFinanceList.containsEntry(f4));
        assertEquals(2.00, testFinanceList.budget(5.00));
    }

    @Test
    void removeFinanceFromLogTest() {
        testFinanceList.addEntry(f1);
        testFinanceList.addEntry(f2);

        assertFalse(testFinanceList.removeFinanceFromLog(2));
        assertEquals(-3.00, testFinanceList.getNetAmount());
        assertEquals(2, testFinanceList.getNumberOfEntries());

        assertEquals(-3.00, testFinanceList.getNetAmount());
        assertEquals(2, testFinanceList.getNumberOfEntries());

        assertTrue(testFinanceList.removeFinanceFromLog(1));
        assertEquals(1, testFinanceList.getNumberOfEntries());
        assertEquals(-5.00, testFinanceList.getNetAmount());

        assertTrue(testFinanceList.removeFinanceFromLog(0));
        assertEquals(0, testFinanceList.getNumberOfEntries());
        assertEquals(0, testFinanceList.getNetAmount());
    }

    @Test
    void storeFinanceInDatabaseTest() {
        testFinanceList.storeFinanceInDatabase(f1);
    }

    @Test
    public void testAddMealToLogTwoDifferentMeal() {
        testFinanceList.addFinanceToLogAndDatabase(f1);
        testFinanceList.addFinanceToLogAndDatabase(f2);

        assertEquals(2, finances.size());
    }

}