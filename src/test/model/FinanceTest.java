package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinanceTest {

    private FinanceEntry f1;
    private FinanceEntry f2;

    @BeforeEach
    void runBefore() {
        f1 = new FinanceEntry(-5.00, "09/05/2012", "BMO", "food");
        f2 = new FinanceEntry(2.00, "01/03/2014", "TD", "e-transfer");
    }


    @Test
    void getAmountTest() {
        assertEquals(-5.00, f1.getAmount());
        assertEquals(2.00, f2.getAmount());
    }

    @Test
    void getDateTest() {
        assertEquals("09/05/2012", f1.getDate());
        assertEquals("01/03/2014", f2.getDate());
    }

    @Test
    void getBankTest() {
        assertEquals("BMO", f1.getCard());
        assertEquals("TD", f2.getCard());
    }

    @Test
    void getWhatTest() {
        assertEquals("food", f1.getDescription());
        assertEquals("e-transfer", f2.getDescription());
    }

    @Test
    void toStringTest() {
        assertEquals("entry: amount = -5.00, date = 09/05/2012, card = BMO, description = food", f1.toString());
    }

}
