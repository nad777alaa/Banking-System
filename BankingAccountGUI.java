/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bankingaccountgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingAccountGUI extends JFrame {
    private JTextField accountNumberField;
    private JTextField nameField;
    private JTextField balanceField;
    private JTextArea reportArea;
    private JTextField amountField;

    private BankingAccount account;

    public BankingAccountGUI() {
        account = new BankingAccount();

        setTitle("Banking Account GUI");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panels and layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        // Account Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        accountNumberField = new JTextField(15);
        panel.add(accountNumberField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        nameField = new JTextField(15);
        panel.add(nameField, gbc);

        // Balance
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Balance:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        balanceField = new JTextField(15);
        panel.add(balanceField, gbc);

        // Amount
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        amountField = new JTextField(15);
        panel.add(amountField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterDetails();
            }
        });
        buttonPanel.add(enterButton);

        JButton reportButton = new JButton("Report");
        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
        buttonPanel.add(reportButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                depositMoney();
            }
        });
        buttonPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdrawMoney();
            }
        });
        buttonPanel.add(withdrawButton);

        JButton interestButton = new JButton("Interest");
        interestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInterest();
            }
        });
        buttonPanel.add(interestButton);

        panel.add(buttonPanel, gbc);

        // Report Area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void enterDetails() {
        try {
            int accountNumber = Integer.parseInt(accountNumberField.getText());
            String name = nameField.getText();
            double balance = Double.parseDouble(balanceField.getText());

            account = new BankingAccount(accountNumber, name, balance);

            JOptionPane.showMessageDialog(this, "Details entered successfully");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReport() {
        reportArea.setText("");
        reportArea.append("Account number: " + account.accountNumber + "\n");
        reportArea.append("Account name: " + account.Name + "\n");
        reportArea.append("Account Balance: " + account.Balance + "\n");
    }

    private void depositMoney() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                account.deposit(amount);
                JOptionPane.showMessageDialog(this, "Amount deposited successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdrawMoney() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0 && account.Balance >= amount) {
                account.withdraw(amount);
                JOptionPane.showMessageDialog(this, "Amount withdrawn successfully");
            } else if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showInterest() {
        double interestEarned = account.calculateInterestEarned();
        JOptionPane.showMessageDialog(this, "Interest Earned: " + interestEarned);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankingAccountGUI().setVisible(true);
            }
        });
    }

    class BankingAccount {
        int accountNumber;
        String Name;
        double Balance;

        BankingAccount() {}

        BankingAccount(int accountNumber, String Name, double Balance) {
            this.accountNumber = accountNumber;
            this.Name = Name;
            this.Balance = Balance;
        }

        public void deposit(double amount) {
            Balance += amount;
        }

        public void withdraw(double amount) {
            Balance -= amount;
        }

        public double calculateInterestEarned() {
            double interest;
            if (Balance >= 10000) {
                interest = 0.05;
            } else if (Balance >= 5000) {
                interest = 0.03;
            } else if (Balance >= 1500) {
                interest = 0.02;
            } else {
                interest = 0.005;
            }
            return interest * Balance;
        }
    }
}


