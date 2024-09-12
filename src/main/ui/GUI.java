package ui;

import model.EventLog;
import model.Event;
import model.FinanceEntry;
import model.FinanceList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;

// Represents graphical user interface
public class GUI extends JFrame {

    private static final String JSON_STORE = "./data/financelist.json";

    private DefaultListModel<FinanceEntry> financeListModel;

    private JList<FinanceEntry> financeLogJList;
    private JLabel netAmountLabel;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private FinanceList listOfFinances;

    // EFFECTS: creates and sets up main application
    public GUI() {
        super("FinanceTracker");
        initializeComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes main model and data fields
    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        listOfFinances = new FinanceList();
        financeListModel = new DefaultListModel<>();

        createHeader();
        createFinanceList();
        createButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startLoadPrompt();
        exitSavePrompt();
    }

    // MODIFIES: this
    // EFFECTS: initializes header, amount, and image
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(50, 50, 50));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        ImageIcon originalIcon = new ImageIcon("./data/financeIcon.jpg");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        headerPanel.add(imageLabel, BorderLayout.WEST);

        // Add the net amount label
        netAmountLabel = new JLabel(" Total Amount: ");
        netAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        netAmountLabel.setForeground(Color.WHITE);
        headerPanel.add(netAmountLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes a finance list panel
    private void createFinanceList() {
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        financeLogJList = new JList<>(financeListModel);
        JScrollPane scrollPane = new JScrollPane(financeLogJList);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(listPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    private void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Finance");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(new Color(74, 176, 186));
        addButton.setForeground(Color.BLACK);
        addButton.addActionListener(e -> addFinanceAction());
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Remove Finance");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 16));
        deleteButton.setBackground(new Color(69, 210, 125));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.addActionListener(e -> deleteFinanceAction());
        buttonPanel.add(deleteButton);

        JButton budgetButton = new JButton("Budget Window");
        budgetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        budgetButton.setBackground(new Color(30, 144, 255));
        budgetButton.setForeground(Color.BLACK);
        budgetButton.addActionListener(e -> doBudgetAction());
        buttonPanel.add(budgetButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes load prompt window on start
    private void startLoadPrompt() {
        int loadOption = JOptionPane.showConfirmDialog(null,
                "Would you like to load your last entry?", "Load File",
                JOptionPane.YES_NO_OPTION);

        if (loadOption == JOptionPane.YES_OPTION) {
            try {
                listOfFinances = jsonReader.read();
                updateFinances();
            } catch (IOException e) {
                System.out.println("Unable to read from file " + JSON_STORE);
            }
            EventLog.getInstance().logEvent(new Event("Finance list loaded"));
        }
    }

    // EFFECTS: initializes save prompt window when quitting
    private void exitSavePrompt() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int saveOption = JOptionPane.showConfirmDialog(null,
                        "Would you like to save your log before exiting?", "Save File Prompt",
                        JOptionPane.YES_NO_OPTION);
                if (saveOption == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(listOfFinances);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }

                    EventLog.getInstance().logEvent(new Event("Finances Saved"));
                }
                dispose();
                EventLog.getInstance().logEvent(new Event("Finances Not Saved"));

                printEventLog();
            }
        });
    }

    private void printEventLog() {
        EventLog eventLog = EventLog.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        for (Event event : eventLog) {
            stringBuilder.append(event.toString()).append("\n");
        }
        // Print event log
        System.out.println("Event Log:");
        System.out.println(stringBuilder.toString());
    }

    // EFFECTS: updates list of finances
    private void updateFinances() {
        financeListModel.clear();
        List<FinanceEntry> financeLog = listOfFinances.getEntries();
        for (FinanceEntry finance : financeLog) {
            financeListModel.addElement(finance);
        }
        updateAmount();
    }

    // EFFECTS: updates amount within account
    private void updateAmount() {
        double amount = listOfFinances.getNetAmount();
        netAmountLabel.setText("Total Amount: $" + String.format("%.2f", amount));
    }

    // MODIFIES: this
    // EFFECTS: creates popup to add finance entry
    private void addFinanceAction() {
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount: "));
            String date = JOptionPane.showInputDialog("Enter date (MM/DD/YYYY): ");
            String card = JOptionPane.showInputDialog("Enter card: ");
            String description = JOptionPane.showInputDialog("Enter description: ");

            FinanceEntry newFinance = new FinanceEntry(amount, date, card, description);
            listOfFinances.addFinanceToLogAndDatabase(newFinance);
            updateFinances();

            EventLog.getInstance().logEvent(new Event("Added Finance: " + newFinance.getAmount()));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input format.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the finance entry selected
    private void deleteFinanceAction() {
        int selectedIndex = financeLogJList.getSelectedIndex();
        if (selectedIndex != -1) {
            listOfFinances.removeFinanceFromLog(selectedIndex);
            updateFinances();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a finance entry to remove.",
                    "No Entry Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    //EFFECTS: calculates how much user can still spend according to their saving goals
    private void doBudgetAction() {
        try {
            double savingGoal = Double.parseDouble(JOptionPane.showInputDialog("Enter Saving Goal: "));

            int option = JOptionPane.showOptionDialog(null,
                    "Would you like to calculate something?",
                    "Calculate", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Calculate", "Cancel"}, "Calculate");

            if (option == JOptionPane.YES_OPTION) {
                double availableAmount = listOfFinances.getNetAmount() - savingGoal;
                String message = (availableAmount >= 0) ? "You can still spend: $"
                        + String.format("%.2f", availableAmount) : "STOP SPENDING";
                JOptionPane.showMessageDialog(null, message,
                        "Budget Result", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input format.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
