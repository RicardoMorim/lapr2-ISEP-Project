package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Represents a repository for managing GreenSpace objects.
 */
public class GreenSpaceRepository implements Serializable {
    private List<GreenSpace> greenSpaces;
    private final List<String> typeList = List.of("garden", "medium-sized park", "large-sized park");

    /**
     * Constructs a new GreenSpaceRepository with the specified list of GreenSpaces.
     * @param greenSpaces the list of GreenSpaces
     */
    public GreenSpaceRepository(List<GreenSpace> greenSpaces) {
        this.greenSpaces = greenSpaces;
    }

    /**
     * Constructs a new GreenSpaceRepository with an empty list of GreenSpaces.
     */
    public GreenSpaceRepository() {
        this.greenSpaces = new ArrayList<>();
    }

    /**
     * Updates the specified GreenSpace with the given parameters.
     * @param g the GreenSpace to update
     * @param name the new name
     * @param type the new type
     * @param area the new area
     * @param address the new address
     * @return an Optional containing the updated GreenSpace if it exists, otherwise an empty Optional
     * @throws IllegalArgumentException if the GreenSpace does not exist
     */
    public Optional<GreenSpace> updateGreenSpace(GreenSpace g, String name, Type type, double area, Address address) {
        for (GreenSpace gs : this.greenSpaces) {
            if (gs.equals(g)) {
                gs.setName(name);
                gs.setType(type);
                gs.setArea(area);
                gs.setAddress(address);
                return Optional.of(g);
            }
        }

        throw new IllegalArgumentException("Green Space not found.");
    }

    /**
     * Adds the specified GreenSpace to the repository.
     * @param greenSpace the GreenSpace to add
     * @throws IllegalArgumentException if the GreenSpace is null or already exists
     */
    public void addGreenSpace(GreenSpace greenSpace) {
        if (greenSpace == null) {
            throw new IllegalArgumentException("GreenSpace cannot be null");
        }
        if (greenSpaces.contains(greenSpace))
            throw new IllegalArgumentException("Green space already exists");
        greenSpaces.add(greenSpace);
    }

    /**
     * Removes the specified GreenSpace from the repository.
     * @param greenSpace the GreenSpace to remove
     * @throws IllegalArgumentException if the GreenSpace does not exist
     */
    public void removeGreenSpace(GreenSpace greenSpace) {
        if (!greenSpaces.contains(greenSpace))
            throw new IllegalArgumentException("Green space does not exist");
        greenSpaces.remove(greenSpace);
    }

    /**
     * Updates the specified old GreenSpace with the new GreenSpace in the repository.
     * @param old_greenSpace the old GreenSpace to update
     * @param new_greenSpace the new GreenSpace to replace the old one
     * @return the new GreenSpace
     * @throws IllegalArgumentException if the old GreenSpace does not exist
     */
    public GreenSpace updateGreenSpace(GreenSpace old_greenSpace, GreenSpace new_greenSpace) {
        if (!greenSpaces.contains(old_greenSpace))
            throw new IllegalArgumentException("Green space does not exist");
        greenSpaces.remove(old_greenSpace);
        greenSpaces.add(new_greenSpace);
        return new_greenSpace;
    }

    /**
     * Returns a list of all GreenSpaces in the repository.
     * @return a list of all GreenSpaces
     */
    public List<GreenSpace> getGreenSpaces() {
        return greenSpaces;
    }

    /**
     * Returns the GreenSpace with the specified name.
     * @param name the name of the GreenSpace to return
     * @return the GreenSpace with the specified name
     * @throws IllegalArgumentException if the GreenSpace does not exist
     */
    public GreenSpace getGreenSpaceByDesignation(String name) {
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equals(name)) {
                return greenSpace;
            }
        }
        throw new IllegalArgumentException("Green Space not found.");
    }

    /**
     * Checks if a GreenSpace with the specified name exists in the repository.
     * @param name the name of the GreenSpace to check
     * @return true if the GreenSpace exists, false otherwise
     */
    public boolean checkIfGreenSpaceNameExists(String name) {
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a GreenSpace with the specified address exists in the repository.
     * @param address the address of the GreenSpace to check
     * @return true if the GreenSpace exists, false otherwise
     */
    public boolean checkIfGreenSpaceAddressExists(Address address) {
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the specified zip code.
     * @param zipCode the zip code to validate
     * @return true if the zip code is valid, false otherwise
     */
    public boolean validateZipCode(String zipCode) {
        if (zipCode.isEmpty()) {
            return false;
        }
        String zipPattern = "^[0-9]{4}-[0-9]{3}$";
        return zipCode.matches(zipPattern);
    }

    /**
     * Sets the list of GreenSpaces in the repository.
     * @param greenSpaces the new list of GreenSpaces
     */
    public void setGreenSpaces(List<GreenSpace> greenSpaces) {
        this.greenSpaces = greenSpaces;
    }


    public List<GreenSpace> getGreenSpacesManagedByUser(EmailWrapper user) {
        List<GreenSpace> greenSpacesManaged = new ArrayList<>();
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getUser().equals(user)) {
                greenSpacesManaged.add(greenSpace);
            }
        }

        // Load the properties file
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the sorting algorithm from the properties file
        String sortingAlgorithm = prop.getProperty("SortingAlgorithm");

        // Sort the list based on the specified algorithm
        if ("BubbleSort".equals(sortingAlgorithm)) {
            bubbleSort(greenSpacesManaged);
        } else if ("QuickSort".equals(sortingAlgorithm)) {
            quickSort(greenSpacesManaged, 0, greenSpacesManaged.size() - 1);
        }

        return greenSpacesManaged;
    }

    private void bubbleSort(List<GreenSpace> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getArea() < list.get(j + 1).getArea()) {
                    // Swap list[j+1] and list[j]
                    GreenSpace temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    private void quickSort(List<GreenSpace> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private int partition(List<GreenSpace> list, int low, int high) {
        double pivot = list.get(high).getArea();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j).getArea() > pivot) {
                i++;
                GreenSpace temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        GreenSpace temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
}