package pt.ipp.isep.dei.mdisc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


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
            this.totalEdges = totalEdges;
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

    public void sortEdges(List<Edge> edges) {
        int n = edges.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (edges.get(j).weight > edges.get(j + 1).weight) {
                    // Swap edges[j+1] and edges[i]
                    Edge temp = edges.get(j);
                    edges.set(j, edges.get(j + 1));
                    edges.set(j + 1, temp);
                }
            }
        }
    }

    public void planIrrigationSystem(String inputFilePath, boolean openGUI) {
        int vertices = graph.length;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (graph[i][j] != 0) {
                    edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }

        // Sort the edges in ascending order of weight
        sortEdges(edges);

        DisjointSet ds = new DisjointSet(vertices);

        int[][] mst = new int[vertices][vertices];
        int totalCost = 0;

        for (Edge edge : edges) {
            int root1 = ds.find(edge.src);
            int root2 = ds.find(edge.dest);
            if (root1 != root2) {
                mst[edge.src][edge.dest] = edge.weight;
                mst[edge.dest][edge.src] = edge.weight;
                totalCost += edge.weight;
                ds.union(root1, root2);
            }
        }

        printCSV(mst, totalCost, inputFilePath);
        printMST(totalCost, inputFilePath);

        // Visualize the input graph
        visualizeGraph(graph, "Input Graph", openGUI, inputFilePath);

        // Visualize the MST
        visualizeGraph(mst, "Minimum Spanning Tree", openGUI, inputFilePath);
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

        if (!openGUI) {
            return;
        }

        g.display();

        try {
            Path inputPath = Paths.get(inputFilePath);
            String inputFileName = inputPath.getFileName().toString();

            String outputFilePath = "src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/";

            Files.createDirectories(Paths.get(outputFilePath));

            try (PrintWriter writer = new PrintWriter(outputFilePath + title + ".dot", StandardCharsets.UTF_8)) {
                writer.println("graph G {");
                for (int i = 0; i < graph.length; i++) {
                    for (int j = i + 1; j < graph.length; j++) {
                        if (graph[i][j] != 0) {
                            writer.println((i+1) + " -- " + (j + 1) + " [label=\"" + graph[i][j] + "\"];");
                        }
                    }
                }
                writer.println("}");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }


            // New code to generate SVG from DOT
            String dotFilePath = outputFilePath + title + ".dot";
            String svgFilePath = outputFilePath + title + ".jpg";
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tjpg", dotFilePath, "-o", svgFilePath);
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while creating the SVG file.");
            e.printStackTrace();
        }
    }


    private void printCSV(int[][] mst, int totalCost, String inputFilePath) {
        try {
            Path inputPath = Paths.get(inputFilePath);
            String inputFileName = inputPath.getFileName().toString();
            // Replace the extension with .csv
            String outputFileName = "solution_" + inputFileName;
            String outputFilePath = "src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/" + outputFileName;

            Files.createDirectories(Paths.get("src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/"));
            try (PrintWriter writer = new PrintWriter(outputFilePath, StandardCharsets.UTF_8)) {
                for (int i = 0; i < mst.length; i++) {
                    for (int j = 0; j < mst[i].length; j++) {
                        if (mst[i][j] != 0) {
                            writer.println(i + ";" + j + ";" + mst[i][j]);
                        }
                    }
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

    private void printMST(int totalCost, String inputFilePath) {
        try {
            Path inputPath = Paths.get(inputFilePath);
            String inputFileName = inputPath.getFileName().toString();

            String outputFileName = "solution_" + inputFileName.replace(".csv", ".txt");
            String outputFilePath = "src/main/java/pt/ipp/isep/dei/mdisc/output/" + inputFileName.replace(".csv", "") + "/" + outputFileName;

            Files.createDirectories(Paths.get(outputFilePath.replace(outputFileName, "")));
            try (PrintWriter writer = new PrintWriter(outputFilePath, StandardCharsets.UTF_8)) {
                writer.println("Graph Dimension = " + totalEdges + " : Graph Order = " + graph.length + " : Cost of a Minimum spanning tree = " + totalCost);
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
            System.out.println("2. Run testExecutionTime function");
            System.out.println("3. Exit");

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
                    System.out.println("Please enter the folder path:");
                    String folderPath = scanner.nextLine();
                    if (folderPath.isEmpty()) {
                        folderPath = "src/main/java/pt/ipp/isep/dei/mdisc/database/";
                    }
                    irrigationSystem.testExecutionTime(folderPath);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 3.");
                    break;
            }
        }

        scanner.close();
    }


    public void testExecutionTime(String folderPath) {
        try {
            // Create the output directory if it does not exist
            String outputPath;
            if (!folderPath.isEmpty()) {
                int lastSlashIndex = folderPath.lastIndexOf("/");
                int secondLastSlashIndex = folderPath.lastIndexOf("/", lastSlashIndex - 1);
                outputPath = folderPath.substring(0, secondLastSlashIndex) + "/output/";
            } else {
                outputPath = "src/main/java/pt/ipp/isep/dei/mdisc/output/"; // Default path
            }
            Files.createDirectories(Paths.get(outputPath));

            try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
                try (PrintWriter writer = new PrintWriter(outputPath + "execution_times.csv", StandardCharsets.UTF_8)) {
                    paths.filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".csv"))
                            .filter(p -> !p.toString().endsWith("execution_times.csv"))
                            .filter(p -> !p.toString().contains("solution"))
                            .forEach(p -> {
                                System.out.println("Working on file: " + p);
                                String filename = p.toString();
                                importCsv(filename);
                                long startTime = System.nanoTime();
                                planIrrigationSystem(filename, false);
                                long endTime = System.nanoTime();

                                long executionTimeNano = endTime - startTime;
                                double executionTimeSec = (double) executionTimeNano / 1000000.0; // Convert to milliseconds
                                System.out.println("Execution time for " + filename + ": " + executionTimeSec + " milliseconds");

                                writer.println(filename + "," + graph.length + "," + executionTimeSec); // Write to CSV
                                generateExecutionTimeGraph(outputPath);
                                writer.flush(); // Flush the PrintWriter
                            });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateExecutionTimeGraph(String outputPath) {
        XYSeries series = new XYSeries("Execution Time");

        // Read the execution times from the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(outputPath + "execution_times.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int inputSize = Integer.parseInt(values[1]);
                double executionTime = Double.parseDouble(values[2]);
                series.add(inputSize, executionTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Execution Time vs Input Size",
                "Input Size",
                "Execution Time (milliseconds)",
                dataset
        );

        try {
            ChartUtils.saveChartAsPNG(new File(outputPath + "execution_time_graph.png"), chart, 720, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}