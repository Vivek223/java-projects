package com.vivekt;

public class App {
    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.capitalizeWords("how is it going"));
    }


    public  String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Trim the trailing space and return
        return result.toString().trim();
    }


}
