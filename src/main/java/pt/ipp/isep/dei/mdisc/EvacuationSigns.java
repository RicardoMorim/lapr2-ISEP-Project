package pt.ipp.isep.dei.mdisc;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class EvacuationSigns {
    private int graph[][];
    private String locationNames[];
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EvacuationSigns es = new EvacuationSigns();
        boolean validFile = false;
        while (!validFile) {
            System.out.println("Please enter the name of the file you want to read the costs from:");
            String filePath = scanner.nextLine();
            if (filePath.isEmpty()) {
                System.out.println("File name cannot be empty.");
                continue;
            }
            String defaultPath = "src/main/java/pt/ipp/isep/dei/mdisc/database/input/";
            File file1 = new File(defaultPath + filePath);
            if (file1.exists()) {
                es.readCSVContainingCosts(defaultPath + filePath);
                validFile = true;
            } else {
                System.out.println("File does not exist. Please enter a valid file name.");
            }
        }
        validFile = false;
        while (!validFile) {
            System.out.println("Please enter the name of the file you want to read the names from:");
            String filePath = scanner.nextLine();
            if (filePath.isEmpty()) {
                System.out.println("File name cannot be empty.");
                continue;
            }
            String defaultPath = "src/main/java/pt/ipp/isep/dei/mdisc/database/input/";
            File file1 = new File(defaultPath + filePath);
            if (file1.exists()) {
                es.readCSVContainingNames(defaultPath + filePath);
                validFile = true;
            } else {
                System.out.println("File does not exist. Please enter a valid file name.");
            }
        }
        es.findShortestPathToAP();
    }

    public void readCSVContainingCosts(String fileName) {
        List<String[]> values = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                values.add(line.split(";"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int n = values.size();
        graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] row = values.get(i);
            for (int j = 0; j < n; j++) {
                graph[i][j] = Integer.parseInt(row[j]);
            }
        }
    }

    public void readCSVContainingNames(String fileName) {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = br.readLine()) != null) {
                String[] nameArray = line.split(";");
                names.addAll(Arrays.asList(nameArray));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        locationNames = names.toArray(new String[0]);
    }

    public DijkstraResult dijkstra(int source) {
        int n = graph.length;
        int[] shortestDistances = new int[n];
        boolean[] added = new boolean[n];
        for (int vertexIndex = 0; vertexIndex < n; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
        shortestDistances[source] = 0;
        int[] parents = new int[n];
        parents[source] = -1;
        for (int i = 1; i < n; i++) {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < n; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            added[nearestVertex] = true;
            for (int vertexIndex = 0; vertexIndex < n; vertexIndex++) {
                int edgeDistance = graph[nearestVertex][vertexIndex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
        return new DijkstraResult(parents, shortestDistances);
    }


    public void printShortestPath(int startVertex, int endVertex, int[] parents) {
        if (startVertex == endVertex) {
            System.out.print(locationNames[startVertex]);
        } else {
            printShortestPath(startVertex, parents[endVertex], parents);
            System.out.print(" -> " + locationNames[endVertex]);
        }
    }

    public void findShortestPathToAP() {
        int apIndex = -1;
        for (int i = 0; i < locationNames.length; i++) {
            if (locationNames[i].equals("AP")) {
                apIndex = i;
                break;
            }
        }
        if (apIndex == -1) {
            System.out.println("AP not found");
            return;
        }
        for (int i = 0; i < locationNames.length; i++) {
            if (i != apIndex) {
                DijkstraResult result = dijkstra(i);
                int[] parents = result.getParents();
                int[] shortestDistances = result.getShortestDistances();
                System.out.print("Path from " + locationNames[i] + " to AP: ");
                printShortestPath(i, apIndex, parents);
                System.out.println(" with cost " + shortestDistances[apIndex]);
            }
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


}