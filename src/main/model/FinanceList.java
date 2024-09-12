package model;

//list of all finances user has logged

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a finance list having a collection of finance entries
public class FinanceList implements Writable {
    private ArrayList<FinanceEntry> listOfFinances;

    private final FinanceDatabase fdbObject;

    private double netAmount;

    //EFFECTS: constructs an empty list of finances
    public FinanceList() {
        this.listOfFinances = new ArrayList<>();
        this.fdbObject = new FinanceDatabase();
        this.netAmount = 0.0;
    }

    //REQUIRES: The finance entry must not be null
    //EFFECTS: The finance entry is added to both the log and the database
    public void addFinanceToLogAndDatabase(FinanceEntry f) {
        addEntry(f);
        storeFinanceInDatabase(f);
    }

    //MODIFIES: this
    //EFFECTS: adds a finance to list of finances if amount is not 0, if amount is 0 returns false
    public void addEntry(FinanceEntry f) {
        if (f.getAmount() != 0.0) {
            listOfFinances.add(f);
        }
        netAmount += f.getAmount();
    }

    //REQUIRES: The finance entry must not be null
    //EFFECTS: The finance entry stored in database
    public void storeFinanceInDatabase(FinanceEntry f) {
        fdbObject.storeEntry(f);
    }

    //MODIFIES: this
    //EFFECTS: if finance is in list, removes a finance from list of finances and returns true, if not returns false
    public void removeEntry(FinanceEntry f) {
        if (containsEntry(f)) {
            listOfFinances.remove(f);
        }
        netAmount -= f.getAmount();
    }

    //REQUIRES: The index is valid within the list of finances
    //EFFECTS: removes entry from log
    public Boolean removeFinanceFromLog(int index) {
        if (!(index >= listOfFinances.size())) {
            double amount = listOfFinances.get(index).getAmount();
            this.listOfFinances.remove(index);
            netAmount -= amount;
            EventLog.getInstance().logEvent(new Event("Removed Finance: " + amount));
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: returns the finance log
    public ArrayList<FinanceEntry> getFinanceLog() {
        return listOfFinances;
    }

    //EFFECTS: returns the finance database
    public FinanceDatabase getFinanceDatabaseObject() {
        return fdbObject;
    }

    //EFFECTS: returns netAmount
    public double getNetAmount() {
        return netAmount;
    }

    //EFFECTS: returns the number of entries within list
    public int getNumberOfEntries() {
        return listOfFinances.size();
    }

    //EFFECTS: returns true if finance is in list, false if not
    public boolean containsEntry(FinanceEntry f) {
        return listOfFinances.contains(f);
    }

    //REQUIRES: a positive value for saving goal
    //EFFECTS: returns null if user is going past saving goal, returns the amounts user is still able to spend if the
    //         amount in account is greater than saving goal
    public Double budget(double savingGoal) {
        if (netAmount < savingGoal) {
            return null;
        } else {
            return netAmount - savingGoal;
        }
    }

    // EFFECTS: returns an unmodifiable list of finances
    public List<FinanceEntry> getEntries() {
        return Collections.unmodifiableList(listOfFinances);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("finances", listOfFinancesToJson());
        return json;
    }

    // EFFECTS: returns finances in finance list as a JSON array
    private JSONArray listOfFinancesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FinanceEntry f : listOfFinances) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}
