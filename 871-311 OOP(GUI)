import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

class Person {
    String name;
    double amount;

    Person(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}

class Transaction {
    String from;
    String to;
    double amount;

    Transaction(String from, String to, double amount) {
        this.from = from;
        this.to = to;
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
        return people.size() > 0 ? getTotal() / people.size() : 0;
    }

    String getBalanceReport() {
        double avg = getAverage();
        StringBuilder sb = new StringBuilder();
        for (Person p : people) {
            double balance = p.amount - avg;
            if (balance > 0.01)
                sb.append("💚 " + p.name + " should receive " + String.format("%.2f", balance) + " Baht\n");
            else if (balance < -0.01)
                sb.append("💸 " + p.name + " should pay " + String.format("%.2f", -balance) + " Baht\n");
            else
                sb.append("✅ " + p.name + " is perfectly settled!\n");
        }
        return sb.toString();
    }

    ArrayList<Transaction> getOptimalTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        double avg = getAverage();
        
        ArrayList<Person> creditors = new ArrayList<>();
        ArrayList<Person> debtors = new ArrayList<>();
        
        for (Person p : people) {
            double balance = p.amount - avg;
            if (balance > 0.01) {
                creditors.add(new Person(p.name, balance));
            } else if (balance < -0.01) {
                debtors.add(new Person(p.name, -balance));
            }
        }
        
        int i = 0, j = 0;
        while (i < creditors.size() && j < debtors.size()) {
            Person creditor = creditors.get(i);
            Person debtor = debtors.get(j);
            
            double amount = Math.min(creditor.amount, debtor.amount);
            transactions.add(new Transaction(debtor.name, creditor.name, amount));
            
            creditor.amount -= amount;
            debtor.amount -= amount;
            
            if (creditor.amount < 0.01) i++;
            if (debtor.amount < 0.01) j++;
        }
        
        return transactions;
    }
}

public class TravelExpenseSplitter extends JFrame implements ActionListener {
    private JTextField nameField, amountField;
    private JButton addBtn, calcBtn, clearBtn, removeBtn, exportBtn;
    private JTextArea resultArea;
    private JList<String> personList;
    private DefaultListModel<String> listModel;
    private JLabel totalLabel, avgLabel, countLabel;

    private ArrayList<Person> people = new ArrayList<>();

    public TravelExpenseSplitter() {
        setTitle("💰 Travel Expense Splitter - Split Bills Fairly!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 248, 255));

        // Create main panels
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("💰 Travel Expense Splitter 💰", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Split bills fairly among friends", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(230, 240, 255));

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        return titlePanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        centerPanel.add(createLeftPanel());
        centerPanel.add(createRightPanel());

        return centerPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Input section
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel inputTitle = new JLabel("📝 Add Person", SwingConstants.CENTER);
        inputTitle.setFont(new Font("Arial", Font.BOLD, 16));
        inputTitle.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(inputTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("👤 Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel amountLabel = new JLabel("💵 Amount (฿):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        addBtn = createStyledButton("➕ Add Person", new Color(34, 139, 34));
        addBtn.addActionListener(this);
        inputPanel.add(addBtn, gbc);

        // List section
        JPanel listPanel = new JPanel(new BorderLayout(5, 5));
        listPanel.setBackground(Color.WHITE);

        JLabel listTitle = new JLabel("👥 People List", SwingConstants.CENTER);
        listTitle.setFont(new Font("Arial", Font.BOLD, 16));
        listTitle.setForeground(new Color(70, 130, 180));
        listTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        listModel = new DefaultListModel<>();
        personList = new JList<>(listModel);
        personList.setFont(new Font("Consolas", Font.PLAIN, 13));
        personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(personList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        removeBtn = createStyledButton("🗑️ Remove Selected", new Color(220, 20, 60));
        removeBtn.addActionListener(this);

        listPanel.add(listTitle, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(removeBtn, BorderLayout.SOUTH);

        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(listPanel, BorderLayout.CENTER);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel resultTitle = new JLabel("📊 Results", SwingConstants.CENTER);
        resultTitle.setFont(new Font("Arial", Font.BOLD, 16));
        resultTitle.setForeground(new Color(70, 130, 180));

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Welcome! 👋\n\n" +
                "💡 How to use:\n" +
                "1. Enter each person's name and amount spent\n" +
                "2. Click 'Add Person' to add them to the list\n" +
                "3. Click 'Calculate Split' to see the results\n" +
                "4. Use 'Clear All' to start over\n\n" +
                "Happy splitting! 🎉");

        JScrollPane scrollPane = new JScrollPane(resultArea);

        rightPanel.add(resultTitle, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        return rightPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBackground(new Color(240, 248, 255));

        totalLabel = createStatLabel("💰 Total: 0.00 ฿");
        avgLabel = createStatLabel("⚖️ Average: 0.00 ฿");
        countLabel = createStatLabel("👥 People: 0");

        statsPanel.add(totalLabel);
        statsPanel.add(avgLabel);
        statsPanel.add(countLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        calcBtn = createStyledButton("📊 Calculate Split", new Color(30, 144, 255));
        calcBtn.addActionListener(this);

        exportBtn = createStyledButton("📄 Export Report", new Color(255, 140, 0));
        exportBtn.addActionListener(this);

        clearBtn = createStyledButton("🔄 Clear All", new Color(220, 20, 60));
        clearBtn.addActionListener(this);

        buttonPanel.add(calcBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(clearBtn);

        bottomPanel.add(statsPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        return bottomPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        return button;
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return label;
    }

    private void updateStats() {
        if (people.size() > 0) {
            ExpenseCalculator calc = new ExpenseCalculator(people);
            totalLabel.setText("💰 Total: " + String.format("%.2f", calc.getTotal()) + " ฿");
            avgLabel.setText("⚖️ Average: " + String.format("%.2f", calc.getAverage()) + " ฿");
            countLabel.setText("👥 People: " + people.size());
        } else {
            totalLabel.setText("💰 Total: 0.00 ฿");
            avgLabel.setText("⚖️ Average: 0.00 ฿");
            countLabel.setText("👥 People: 0");
        }
    }

    private void updateList() {
        listModel.clear();
        for (Person p : people) {
            listModel.addElement(p.name + " - " + String.format("%.2f", p.amount) + " ฿");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            addPerson();
        } else if (e.getSource() == calcBtn) {
            calculateSplit();
        } else if (e.getSource() == clearBtn) {
            clearAll();
        } else if (e.getSource() == removeBtn) {
            removePerson();
        } else if (e.getSource() == exportBtn) {
            exportReport();
        }
    }

    private void addPerson() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an amount!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double amt = Double.parseDouble(amountText);
            if (amt < 0) {
                JOptionPane.showMessageDialog(this, "Amount cannot be negative!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            people.add(new Person(name, amt));
            updateList();
            updateStats();
            nameField.setText("");
            amountField.setText("");
            nameField.requestFocus();
            
            resultArea.append("✅ " + name + " added: " + String.format("%.2f", amt) + " ฿\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removePerson() {
        int selectedIndex = personList.getSelectedIndex();
        if (selectedIndex != -1) {
            Person removed = people.remove(selectedIndex);
            updateList();
            updateStats();
            resultArea.append("🗑️ " + removed.name + " removed\n");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a person to remove!", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void calculateSplit() {
        if (people.size() == 0) {
            JOptionPane.showMessageDialog(this, "Please add at least one person first!", "No Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ExpenseCalculator calc = new ExpenseCalculator(people);
        ArrayList<Transaction> transactions = calc.getOptimalTransactions();

        StringBuilder report = new StringBuilder();
        report.append("╔══════════════════════════════════════╗\n");
        report.append("║   🧮 EXPENSE CALCULATION RESULTS    ║\n");
        report.append("╚══════════════════════════════════════╝\n\n");
        
        report.append("💰 Total Expenses: " + String.format("%.2f", calc.getTotal()) + " ฿\n");
        report.append("⚖️  Average per Person: " + String.format("%.2f", calc.getAverage()) + " ฿\n");
        report.append("👥 Number of People: " + people.size() + "\n\n");
        
        report.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        report.append("💸 INDIVIDUAL BALANCES:\n");
        report.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        report.append(calc.getBalanceReport());
        
        if (transactions.size() > 0) {
            report.append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            report.append("💱 OPTIMAL TRANSACTIONS:\n");
            report.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            for (Transaction t : transactions) {
                report.append(String.format("💵 %s → %s: %.2f ฿\n", t.from, t.to, t.amount));
            }
        }
        
        report.append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        report.append("✨ Calculation complete! Have a great trip! ✨\n");

        resultArea.setText(report.toString());
    }

    private void exportReport() {
        if (people.size() == 0) {
            JOptionPane.showMessageDialog(this, "No data to export!", "Export Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String report = resultArea.getText();
        JOptionPane.showMessageDialog(this, 
            "Report copied to clipboard!\n\nYou can paste it anywhere you need.", 
            "Export Successful", 
            JOptionPane.INFORMATION_MESSAGE);
        
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new java.awt.datatransfer.StringSelection(report), null);
    }

    private void clearAll() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear all data?", 
            "Confirm Clear", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            people.clear();
            listModel.clear();
            updateStats();
            resultArea.setText("🗑️ All data cleared!\n\n" +
                    "Ready for new calculations. 🎉\n\n" +
                    "💡 Add people and their expenses to start!");
            nameField.setText("");
            amountField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TravelExpenseSplitter());
    }
}
