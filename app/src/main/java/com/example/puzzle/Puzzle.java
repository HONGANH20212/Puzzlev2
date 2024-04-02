package com.example.puzzle;

import java.util.Random;

public class Puzzle {
    private String[] parts;
    private Random random = new Random();

    public static final int NUMBER_PARTS = 5;

    public Puzzle() {
        parts = new String[NUMBER_PARTS];
        parts[0] = "I LOVE";
        parts[1] = "JAVA";
        parts[2] = "MOBILE";
        parts[3] = "PROGRAMMING";
        parts[4] = "USING";
    }

    public boolean solved(String[] solution) {
        if (solution != null && solution.length == parts.length) {
            for (int i = 0; i < parts.length; i++) {
                if (!solution[i].equals(parts[i]))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public String[] scramble() {
        String[] scrambled = new String[parts.length];
        for (int i = 0; i < scrambled.length; i++)
            scrambled[i] = parts[i];

        // Check if puzzle is solved initially
        if (solved(scrambled)) {
            return scrambled; // If solved, return as is
        }

        // If not solved, scramble the parts
        while (solved(scrambled)) {
            for (int i = 0; i < scrambled.length; i++) {
                int n = random.nextInt(scrambled.length);
                String temp = scrambled[i];
                scrambled[i] = scrambled[n];
                scrambled[n] = temp;
            }
        }
        return scrambled;
    }

    public String getPartAtIndex(int index) {
        if (index >= 0 && index < parts.length) {
            return parts[index];
        } else {
            return null; // hoặc xử lý trường hợp vượt ra khỏi phạm vi theo cách phù hợp
        }
    }

    public int getNumberOfParts() {
        return parts.length;
    }

    public String wordToChange() {
        return "MOBILE"; // Trả về từ cần thay đổi
    }

    public String replacementWord() {
        // Thực hiện logic để trả về từ thay thế
        return "replacement_word"; // Thay "replacement_word" bằng từ bạn muốn thay thế
    }

    public Object getTextViewText(int index) {
        return null;
    }

    public void setTextViewText(int index, String s) {
    }
}


