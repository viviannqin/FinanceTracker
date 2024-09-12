package model;

import java.util.ArrayList;
import java.util.List;

//A log of finances store for later or future use
public class FinanceDatabase {

    private final List<FinanceEntry> financeDatabase;

    public FinanceDatabase() {
        this.financeDatabase = new ArrayList<>();
    }

    public Boolean storeEntry(FinanceEntry finance) {
        if (!financeDatabase.contains(finance)) {
            financeDatabase.add(finance);
            return true;
        } else {
            return false;
        }
    }

    public List<FinanceEntry> getFinanceDatabase() {
        return financeDatabase;
    }

}
