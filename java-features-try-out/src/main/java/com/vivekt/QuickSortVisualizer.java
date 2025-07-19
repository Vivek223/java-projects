package com.vivekt;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class QuickSortVisualizer extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int BAR_WIDTH = 10;
    private static final int NUM_BARS = WIDTH / BAR_WIDTH;
    private int[] array = new int[NUM_BARS];
    private int delay = 300;

    public QuickSortVisualizer() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        generateRandomArray();
    }

    private void generateRandomArray() {
        Random rand = new Random();
        for (int i = 0; i < NUM_BARS; i++) {
            array[i] = rand.nextInt(HEIGHT);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < NUM_BARS; i++) {
            g.setColor(Color.pink);
            g.fillRect(i * BAR_WIDTH, HEIGHT - array[i], BAR_WIDTH, array[i]);
            g.setColor(Color.black);
            g.drawRect(i * BAR_WIDTH, HEIGHT - array[i], BAR_WIDTH, array[i]);
        }
    }

    public void startSort() {
        new Thread(() -> {
            quickSort(0, array.length - 1);
        }).start();
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(i, j);
                repaintWithDelay();
            }
        }
        swap(i + 1, high);
        repaintWithDelay();
        return i + 1;
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void repaintWithDelay() {
        repaint();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quick Sort Visualizer");
        QuickSortVisualizer visualizer = new QuickSortVisualizer();

        JButton startButton = new JButton("Start Sort");
        startButton.addActionListener(e -> visualizer.startSort());

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);

        frame.setLayout(new BorderLayout());
        frame.add(visualizer, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
