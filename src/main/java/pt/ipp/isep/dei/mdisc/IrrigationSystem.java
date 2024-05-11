package pt.ipp.isep.dei.mdisc;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing.util.SwingFileSinkImages;

import java.io.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class IrrigationSystem {
    private int[][] graph;

    private int totalEdges;

    public void importCsv(String filename) {
        List<String> nodes = new ArrayList<>();
        int totalEdges = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                totalEdges++; // Count each line as an edge
                String[] values = line.split(";");
                if (!nodes.contains(values[0])) {
                    nodes.add(values[0]);
                }
                if (!nodes.contains(values[1])) {
                    nodes.add(values[1]);
                }
            }
            int size = nodes.size();
            graph = new int[size][size];
            this.totalEdges = totalEdges; // Store the total number of edges
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] values = line.split(";");
                int pointX = nodes.indexOf(values[0]);
                int pointY = nodes.indexOf(values[1]);
                int distance = Integer.parseInt(values[2]);
                graph[pointX][pointY] = distance;
                graph[pointY][pointX] = distance; // The graph is undirected
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void planIrrigationSystem(String inputFilePath, boolean openGUI) {
        int vertices = graph.length;
        int[] parent = new int[vertices]; // Store the parent of each vertex
        int[] key = new int[vertices]; // Store the key values of the vertices
        boolean[] inMST = new boolean[vertices]; // Store whether a vertex is included in the MST

        // Initialize all keys as infinite and all vertices as not included in the MST
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(inMST, false);

        // The key of the first vertex is 0 and it has no parent
        key[0] = 0;
        parent[0] = -1;

        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Add all vertices to the priority queue
        for (int i = 0; i < vertices; i++) {
            pq.add(new Edge(i, i, key[i]));
        }

        while (!pq.isEmpty()) {
            // Get the vertex with the minimum key value that's not in the MST
            int u = pq.poll().dest;

            // Include the vertex in the MST
            inMST[u] = true;

            // Update the key values of the adjacent vertices of the chosen vertex
            for (int v = 0; v < vertices; v++) {
                if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
                    // Update the key value and parent of the adjacent vertex
                    key[v] = graph[u][v];
                    parent[v] = u;

                    // Update the key value in the priority queue
                    pq.add(new Edge(v, v, key[v]));
                }
            }
        }
        printCSV(parent, key, inputFilePath);
        printMST(key, inputFilePath);
        // Visualize the input graph
        visualizeGraph(graph, "Input Graph", openGUI, inputFilePath);

        // Construct the MST
        int[][] mst = new int[vertices][vertices];
        for (int i = 1; i < vertices; i++) {
            mst[parent[i]][i] = graph[parent[i]][i];
            mst[i][parent[i]] = graph[i][parent[i]];
        }

        // Visualize the MST
        visualizeGraph(mst, "Minimum Spanning Tree", openGUI, inputFilePath);

    }

    private void printCSV(int[] parent, int[] key, String inputFilePath) {
        try {
            Path inputPath = Paths.get(inputFilePath);
            String inputFileName = inputPath.getFileName().toString();
            // Replace the extension with .csv
            String outputFileName = "solution_" + inputFileName;
            String outputFilePath = "src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/" + outputFileName;

            Files.createDirectories(Paths.get("src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/"));
            try (PrintWriter writer = new PrintWriter(outputFilePath, StandardCharsets.UTF_8)) {
                int totalCost = 0;
                for (int i = 1; i < graph.length; i++) {
                    totalCost += key[i];
                    writer.println(parent[i] + ";" + i + ";" + key[i]);
                }
                writer.println("Total cost: " + totalCost);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the directories.");
            e.printStackTrace();
        }
    }


    public void visualizeGraph(int[][] graph, String title, boolean openGUI, String inputFilePath) {
        Graph g = new SingleGraph(title);

        for (int i = 0; i < graph.length; i++) {
            g.addNode(Integer.toString(i));
        }

        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                if (graph[i][j] != 0) {
                    g.addEdge(i + "-" + j, Integer.toString(i), Integer.toString(j));
                }
            }
        }

        if (openGUI) {
            g.display();
        }
        SwingFileSinkImages pic = new SwingFileSinkImages();
        pic.setLayoutPolicy(SwingFileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
        pic.setResolution(1280, 720);
        try {
            Path inputPath = Paths.get(inputFilePath);
            String inputFileName = inputPath.getFileName().toString();

            String outputFilePath = "src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/";

            Files.createDirectories(Paths.get(outputFilePath));
            pic.writeAll(g, outputFilePath + title + ".png");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private void printMST(int[] key, String inputFilePath) {
        try {
            Path inputPath = Paths.get(inputFilePath);
            String inputFileName = inputPath.getFileName().toString();

            String outputFileName = "solution_" + inputFileName.replace(".csv", ".txt");
            String outputFilePath = "src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/" + outputFileName;

            Files.createDirectories(Paths.get(outputFilePath.replace(outputFileName, "")));
            try (PrintWriter writer = new PrintWriter(outputFilePath, StandardCharsets.UTF_8)) {
                int totalCost = 0;
                for (int i = 1; i < graph.length; i++) {
                    totalCost += key[i];
                }
                writer.println("Graph Dimension = " + totalEdges + " : Graph Order = " + graph.length + " : Minimum cost = " + totalCost);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the directories.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        IrrigationSystem irrigationSystem = new IrrigationSystem();

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            System.out.println("Please choose an option:");
            System.out.println("1. Run for a single file");
            System.out.println("2. Exit");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    boolean validFile = false;
                    while (!validFile) {
                        System.out.println("Please enter the file name:");
                        String filePath = scanner.nextLine();
                        String defaultPath = "src/main/java/pt/ipp/isep/dei/mdisc/database/";
                        File file = new File(defaultPath + filePath);
                        if (file.exists()) {
                            irrigationSystem.importCsv(defaultPath + filePath);
                            irrigationSystem.planIrrigationSystem(filePath, true);
                            validFile = true;
                        } else {
                            System.out.println("File does not exist. Please enter a valid file name.");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 3.");
                    break;
            }
        }

        scanner.close();
    }
}
