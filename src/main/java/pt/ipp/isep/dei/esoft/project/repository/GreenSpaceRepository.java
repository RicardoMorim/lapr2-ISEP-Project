package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GreenSpaceRepository implements Serializable {
    private List<GreenSpace> greenSpaces;
    private final List<String> typeList = List.of("garden", "medium-sized park", "large-sized park");

    public GreenSpaceRepository(List<GreenSpace> greenSpaces) {
        this.greenSpaces = greenSpaces;
    }

    public GreenSpaceRepository() {
        this.greenSpaces = new ArrayList<>();
    }


    public Optional<GreenSpace> updateGreenSpace(GreenSpace g, String name, Type type, float area, String address) {
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

    public void addGreenSpace(GreenSpace greenSpace) {
        if (greenSpaces.contains(greenSpace))
            throw new IllegalArgumentException("Green space already exists");
        greenSpaces.add(greenSpace);
    }

    public void removeGreenSpace(GreenSpace greenSpace) {
        if (!greenSpaces.contains(greenSpace))
            throw new IllegalArgumentException("Green space does not exist");
        greenSpaces.remove(greenSpace);
    }

    public GreenSpace updateGreenSpace(GreenSpace old_greenSpace, GreenSpace new_greenSpace) {
        if (!greenSpaces.contains(old_greenSpace))
            throw new IllegalArgumentException("Green space does not exist");
        greenSpaces.remove(old_greenSpace);
        greenSpaces.add(new_greenSpace);
        return new_greenSpace;
    }

    public List<GreenSpace> getGreenSpaces() {
        //This is a defensive copy, so that the repository cannot be modified from the outside.
        return List.copyOf(greenSpaces);
    }


    public GreenSpace getGreenSpaceByDesignation(String name) {
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equals(name)) {
                return greenSpace;
            }
        }
        throw new IllegalArgumentException("Green Space not found.");
    }

    public boolean checkIfGreenSpaceNameExists(String name) {
        for (GreenSpace greenSpace : greenSpaces) {
            if (greenSpace.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void setGreenSpaces(List<GreenSpace> greenSpaces) {
        this.greenSpaces = greenSpaces;
    }
}
