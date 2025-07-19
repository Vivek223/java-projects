package com.vivekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryManagementSystem extends JFrame {

    private JTextArea outputArea;
    private ArrayList<String> bookList = new ArrayList<>();
    private ArrayList<String> issuedBooks = new ArrayList<>();

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMenuBar();
        setupMainPanel();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu bookMenu = new JMenu("Books");
        JMenuItem addBookItem = new JMenuItem("Add Book");
        JMenuItem viewBooksItem = new JMenuItem("View All Books");

        addBookItem.addActionListener(e -> addBook());
        viewBooksItem.addActionListener(e -> viewBooks());

        bookMenu.add(addBookItem);
        bookMenu.add(viewBooksItem);

        JMenu transactionMenu = new JMenu("Transactions");
        JMenuItem issueBookItem = new JMenuItem("Issue Book");
        JMenuItem returnBookItem = new JMenuItem("Return Book");

        issueBookItem.addActionListener(e -> issueBook());
        returnBookItem.addActionListener(e -> returnBook());

        transactionMenu.add(issueBookItem);
        transactionMenu.add(returnBookItem);

        JMenu exitMenu = new JMenu("Exit");
        JMenuItem exitItem = new JMenuItem("Exit App");
        exitItem.addActionListener(e -> System.exit(0));
        exitMenu.add(exitItem);

        menuBar.add(bookMenu);
        menuBar.add(transactionMenu);
        menuBar.add(exitMenu);

        setJMenuBar(menuBar);
    }

    private void setupMainPanel() {
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void addBook() {
        String bookName = JOptionPane.showInputDialog(this, "Enter book name:");
        if (bookName != null && !bookName.trim().isEmpty()) {
            bookList.add(bookName.trim());
            outputArea.append("Added book: " + bookName + "\n");
        }
    }

    private void viewBooks() {
        outputArea.append("\n--- Book List ---\n");
        if (bookList.isEmpty()) {
            outputArea.append("No books available.\n");
        } else {
            for (String book : bookList) {
                String status = issuedBooks.contains(book) ? " (Issued)" : " (Available)";
                outputArea.append(book + status + "\n");
            }
        }
    }

    private void issueBook() {
        String bookName = JOptionPane.showInputDialog(this, "Enter book name to issue:");
        if (bookName != null && bookList.contains(bookName)) {
            if (issuedBooks.contains(bookName)) {
                outputArea.append("Book already issued: " + bookName + "\n");
            } else {
                issuedBooks.add(bookName);
                outputArea.append("Book issued: " + bookName + "\n");
            }
        } else {
            outputArea.append("Book not found: " + bookName + "\n");
        }
    }

    private void returnBook() {
        String bookName = JOptionPane.showInputDialog(this, "Enter book name to return:");
        if (bookName != null && issuedBooks.contains(bookName)) {
            issuedBooks.remove(bookName);
            outputArea.append("Book returned: " + bookName + "\n");
        } else {
            outputArea.append("Book not found or not issued: " + bookName + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManagementSystem app = new LibraryManagementSystem();
            app.setVisible(true);
        });
    }
}
