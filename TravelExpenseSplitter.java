import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Person {
    String name;
    double amount;

    Person(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}

class ExpenseCalculator {
    private ArrayList<Person> people;

    ExpenseCalculator(ArrayList<Person> people) {
        this.people = people;
    }

    double getTotal() {
        double sum = 0;
        for (Person p : people)
            sum += p.amount;
        return sum;
    }

    double getAverage() {
        return getTotal() / people.size();
    }

    String getBalanceReport() {
        double avg = getAverage();
        StringBuilder sb = new StringBuilder();
        for (Person p : people) {
            double balance = p.amount - avg;
            if (balance > 0)
                sb.append("💚 " + p.name + " should receive " + String.format("%.2f", balance) + " Baht\n");
            else if (balance < 0)
                sb.append("💸 " + p.name + " should pay " + String.format("%.2f", -balance) + " Baht\n");
            else
                sb.append("✅ " + p.name + " is perfectly settled!\n");
        }
        return sb.toString();
    }
}

public class TravelExpenseSplitter extends Frame implements ActionListener, WindowListener {
    Label lb1, lb2;
    TextField nameField, amountField;
    Button addBtn, calcBtn, clearBtn;
    TextArea resultArea;

    ArrayList<Person> people = new ArrayList<>();

    public TravelExpenseSplitter() {
        // Set modern frame properties
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255)); // Alice Blue background

        // Create title panel
        Panel titlePanel = new Panel();
        titlePanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        Label titleLabel = new Label("💰 Travel Expense Splitter 💰", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Create and style labels with modern fonts
        lb1 = new Label("👤 Name:");
        lb1.setFont(new Font("Arial", Font.BOLD, 14));
        lb1.setForeground(new Color(25, 25, 112)); // Midnight Blue

        lb2 = new Label("💵 Amount Spent (Baht):");
        lb2.setFont(new Font("Arial", Font.BOLD, 14));
        lb2.setForeground(new Color(25, 25, 112)); // Midnight Blue

        // Create and style text fields
        nameField = new TextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBackground(Color.WHITE);

        amountField = new TextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField.setBackground(Color.WHITE);

        // Create input panel with better spacing
        Panel inputPanel = new Panel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 248, 255)); // Alice Blue
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(lb1, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(lb2, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(amountField, gbc);

        // Create and style buttons with modern colors
        addBtn = new Button("➕ Add Person");
        addBtn.setFont(new Font("Arial", Font.BOLD, 12));
        addBtn.setBackground(new Color(34, 139, 34)); // Forest Green
        addBtn.setForeground(Color.WHITE);

        calcBtn = new Button("📊 Calculate Split");
        calcBtn.setFont(new Font("Arial", Font.BOLD, 12));
        calcBtn.setBackground(new Color(30, 144, 255)); // Dodger Blue
        calcBtn.setForeground(Color.WHITE);

        clearBtn = new Button("🗑️ Clear All");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 12));
        clearBtn.setBackground(new Color(220, 20, 60)); // Crimson
        clearBtn.setForeground(Color.WHITE);

        // Create button panel with proper spacing
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255)); // Alice Blue
        buttonPanel.add(addBtn);
        buttonPanel.add(calcBtn);
        buttonPanel.add(clearBtn);

        // Create and style result area
        resultArea = new TextArea(12, 50);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setBackground(new Color(255, 255, 255)); // White
        resultArea.setForeground(new Color(25, 25, 112)); // Midnight Blue
        resultArea.setEditable(false);
        resultArea.setText("Welcome! Add people and their expenses, then calculate the split.\n\n" +
                "💡 Tips:\n" +
                "• Enter each person's name and amount spent\n" +
                "• Click 'Calculate Split' to see who owes what\n" +
                "• Use 'Clear All' to start over");

        // Create main content panel
        Panel contentPanel = new Panel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 248, 255));
        contentPanel.add(inputPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add components to frame
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(resultArea, BorderLayout.SOUTH);

        // Set up event listeners
        addBtn.addActionListener(this);
        calcBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        addWindowListener(this);

        // Set frame properties
        setSize(600, 550);
        setTitle("💰 Travel Expense Splitter - Split Bills Fairly!");
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    resultArea.append("❌ Please enter a name.\n");
                    return;
                }

                String amountText = amountField.getText().trim();
                if (amountText.isEmpty()) {
                    resultArea.append("❌ Please enter an amount.\n");
                    return;
                }

                double amt = Double.parseDouble(amountText);
                if (amt < 0) {
                    resultArea.append("❌ Amount cannot be negative.\n");
                    return;
                }

                people.add(new Person(name, amt));
                resultArea.append("✅ " + name + " added with " + String.format("%.2f", amt) + " Baht\n");
                nameField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.append("❌ Please enter a valid number for amount.\n");
            }
        } else if (e.getSource() == calcBtn) {
            if (people.size() > 0) {
                ExpenseCalculator calc = new ExpenseCalculator(people);
                String report = "🧮 === EXPENSE CALCULATION RESULTS ===\n\n" +
                        "💰 Total Expenses: " + String.format("%.2f", calc.getTotal()) + " Baht\n" +
                        "⚖️  Average per Person: " + String.format("%.2f", calc.getAverage()) + " Baht\n" +
                        "👥 Number of People: " + people.size() + "\n\n" +
                        "💸 PAYMENT ADJUSTMENTS:\n" +
                        "═══════════════════════════════════\n" +
                        calc.getBalanceReport() +
                        "═══════════════════════════════════\n" +
                        "✨ Split calculated successfully!";
                resultArea.setText(report);
            } else {
                resultArea.setText("❌ Please add at least one person first.\n");
            }
        } else if (e.getSource() == clearBtn) {
            people.clear();
            resultArea.setText("🗑️ All data cleared! Ready for new calculations.\n\n" +
                    "💡 Tips:\n" +
                    "• Enter each person's name and amount spent\n" +
                    "• Click 'Calculate Split' to see who owes what\n" +
                    "• Use 'Clear All' to start over");
            nameField.setText("");
            amountField.setText("");
        }
    }

    // Window events
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public static void main(String[] args) {
        new TravelExpenseSplitter();
    }
}
