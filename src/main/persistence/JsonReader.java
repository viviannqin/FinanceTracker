package persistence;

import model.FinanceEntry;
import model.FinanceList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads finance list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FinanceList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFinanceList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses finance list from JSON object and returns it
    private FinanceList parseFinanceList(JSONObject jsonObject) {
        FinanceList fl = new FinanceList();
        addFinances(fl, jsonObject);
        return fl;
    }

    // MODIFIES: fl
    // EFFECTS: parses finances from JSON object and adds them to finance list
    private void addFinances(FinanceList fl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("finances");
        for (Object json : jsonArray) {
            JSONObject nextFinance = (JSONObject) json;
            addFinanceEntry(fl, nextFinance);
        }
    }

    // MODIFIES: fl
    // EFFECTS: parses finance from JSON object and adds it to finance list
    private void addFinanceEntry(FinanceList fl, JSONObject jsonObject) {
        Double amount = jsonObject.getDouble("amount");
        String date = jsonObject.getString("date");
        String card = jsonObject.getString("card");
        String description = jsonObject.getString("description");

        FinanceEntry financeEntry = new FinanceEntry(amount, date, card, description);
        fl.addEntry(financeEntry);
    }


}
