package persistence;

import model.FinanceEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkFinanceEntry(double amount, String date, String card, String description,
                                     FinanceEntry financeEntry) {
        assertEquals(amount, financeEntry.getAmount());
        assertEquals(date, financeEntry.getDate());
        assertEquals(card, financeEntry.getCard());
        assertEquals(description, financeEntry.getDescription());
    }
}
