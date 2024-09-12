package model;

import org.json.JSONObject;
import persistence.Writable;

// This class represents an entry(expense or earning) that affects their money balance between multiple accounts
// that is logged into the finance tracker
public class FinanceEntry implements Writable {
    private double amount;
    private String date;
    private String card;
    private String description;

    // EFFECTS: initializes the fields to values
    public FinanceEntry(double amount, String date, String card, String description) {
        this.amount = amount;
        this.date = date;
        this.card = card;
        this.description = description;
    }

    //EFFECTS: returns amount of money in or out of accounts, if expense(move leaving account) amount is negative
    //         if earning(money going into account) amount is positive
    public double getAmount() {
        return amount;
    }

    //EFFECTS: returns when money was put in or taken out of accounts
    public String getDate() {
        return date;
    }

    //EFFECTS: returns which card was used
    public String getCard() {
        return card;
    }

    //EFFECTS: returns description of purchase
    public String getDescription() {
        return description;
    }

    //EFFECTS: returns object information in a string
    public String toString() {
        String amountStr = String.format("%.2f", amount);
        return "ENTRY:" + " amount = " + amountStr + ", date = " + date
                + ", card = " + card + ", description = " + description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("date", date);
        json.put("card", card);
        json.put("description", description);
        return json;
    }
}
