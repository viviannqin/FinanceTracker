package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

//Represents FinanceTracker Application
public class FinanceTrackerApp {

    private static final String JSON_STORE = "./data/financelist.json";
    private FinanceList financeList;
    private ArrayList<FinanceEntry> finances;
    private FinanceDatabase fdbObject;
    private List<FinanceEntry> financeDB;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private double savingGoal;

    // EFFECTS: constructs a finance tracker and runs the application
    public FinanceTrackerApp() throws FileNotFoundException {
        input = new Scanner(System.in);

        financeList = new FinanceList();
        finances = financeList.getFinanceLog();
        fdbObject = financeList.getFinanceDatabaseObject();
        financeDB = fdbObject.getFinanceDatabase();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runFinanceTracker();
        savingGoal = 0.0;
    }

    //MODIFIES: this
    //EFFECTS: initializes program loops the display method which accepts and processes user inputs
    public void runFinanceTracker() {
        boolean keepRunning = true;
        String command = null;

        initialize();

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\n Goodbye! Happy Financing!");
    }


    //EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals("add")) {
            doAddFinance();
        } else if (command.equals("remove")) {
            doRemoveFinance();
        } else if (command.equals("budget")) {
            doBudgetFinance(savingGoal);
        } else if (command.equals("entries")) {
            doListOfEntries();
        } else if (command.equals("save")) {
            saveFinanceList();
        } else if (command.equals("load")) {
            loadFinanceList();
        } else {
            System.out.println("Invalid selection... please enter a valid one");
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes accounts
    public void initialize() {
        input = new Scanner(System.in);
        financeList = new FinanceList();
    }

    //EFFECTS: displays menu of options to user
    public void displayMenu() {
        System.out.println("\nSelect from: ");
        System.out.println("\tadd -> add entry");
        System.out.println("\tremove -> remove entry");
        System.out.println("\tbudget -> view budget status");
        System.out.println("\tentries -> view entries");
        System.out.println("\tsave -> save work room to file");
        System.out.println("\tload -> load work room from file");
        System.out.println("\tq -> quit application");
    }

    //EFFECTS: adds finance to finance list
    public void doAddFinance() {
        System.out.println("Enter amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        System.out.println("Enter date (MM/DD/YYYY): ");
        String date = input.nextLine();

        System.out.println("Enter card: ");
        String card = input.nextLine();

        System.out.println("Enter description: ");
        String description = input.nextLine();

        FinanceEntry financeEntry = new FinanceEntry(amount, date, card, description);
        financeList.addEntry(financeEntry);
        financeList.addFinanceToLogAndDatabase(financeEntry);
        System.out.println("Entry added");
    }


    //EFFECTS: selects and entry from the list
    public FinanceEntry selectEntry(List<FinanceEntry> financeLogs) {
        for (FinanceEntry finance: financeLogs) {
            System.out.println("For " + finance.getAmount() + " press -> " + financeLogs.indexOf(finance));
        }
        int index = input.nextInt();
        return financeLogs.get(index);
    }

    //EFFECTS: removes finance from finance list
    public void doRemoveFinance() {
        System.out.println("Select a entry to remove by typing its number:");
        FinanceEntry entry = selectEntry(finances);
        financeList.removeEntry(entry);
        System.out.println(entry.getAmount() + " has been removed from your log!");
    }

    //MODIFIES: this
    //EFFECTS: conducts a saving calculation
    public String doBudgetFinance(double savingGoal) {
        double stillAvailable = 0.0;

        if (financeList.getNetAmount() < savingGoal) {
            return "STOP SPENDING";
        } else {
            stillAvailable = financeList.getNetAmount() - savingGoal;
            String stillAvailableStr = String.format("%.2f", stillAvailable);
            return "You can still spend this much: $" + stillAvailableStr;
        }
    }

    //MODIFIES: this
    //EFFECTS: prints out all the finance entries
    public void doListOfEntries() {
        List<FinanceEntry> entries = financeList.getEntries();

        if (entries.isEmpty()) {
            System.out.println("No entries listed");
        } else {
            System.out.println("\nList of Entries");
            for (int i = 0; i < entries.size(); i++) {
                FinanceEntry entry = entries.get(i);
                System.out.println("Entry " + (i + 1) + ":");
                System.out.println("\tAmount: $" + entry.getAmount());
                System.out.println("\tDate: " + entry.getDate());
                System.out.println("\tCard: " + entry.getCard());
                System.out.println("\tDescription " + entry.getDescription());
            }
        }
    }

    // EFFECTS: saves the finance list to file
    private void saveFinanceList() {
        try {
            jsonWriter.open();
            jsonWriter.write(financeList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadFinanceList() {
        try {
            financeList = jsonReader.read();
            finances = financeList.getFinanceLog();
            fdbObject = financeList.getFinanceDatabaseObject();
            financeDB = fdbObject.getFinanceDatabase();

            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



}
