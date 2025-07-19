package com.vivekt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

// Model class
class Person {
    public String name;
    public int age;
    public String email;

    // Getters and Setters (or keep public for brevity)
}

public class PersonViewer extends JFrame {

    private JTable table;

    public PersonViewer(List<Person> persons) {
        setTitle("Person Viewer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Name", "Age", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Person person : persons) {
            Object[] row = {person.name, person.age, person.email};
            tableModel.addRow(row);
        }

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static List<Person> loadPersonsFromJson(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), new TypeReference<List<Person>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to read JSON file: " + e.getMessage());
            return List.of(); // Return empty list on failure
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Person> persons = loadPersonsFromJson("C:\\dev\\tmp\\java-projects\\java-features-try-out\\src\\main\\java\\com\\vivekt\\persons.json");
            PersonViewer viewer = new PersonViewer(persons);
            viewer.setVisible(true);
        });
    }
}
