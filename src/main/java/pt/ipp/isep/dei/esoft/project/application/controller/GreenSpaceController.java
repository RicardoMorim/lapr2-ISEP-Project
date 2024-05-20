package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class GreenSpaceController {
    private GreenSpaceRepository greenSpaceRepository;

    public GreenSpaceController() {
        greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
    }

    public GreenSpaceController(GreenSpaceRepository rep) {
        greenSpaceRepository = rep;
    }

    public void addGreenSpace(GreenSpace greenSpace) {
        greenSpaceRepository.addGreenSpace(greenSpace);
    }

    public List<GreenSpace> getGreenSpaceList() {
        return greenSpaceRepository.getGreenSpaces();
    }

    public void removeGreenSpace(GreenSpace greenSpace) {
        greenSpaceRepository.removeGreenSpace(greenSpace);
    }

    public void updateGreenSpace(GreenSpace oldGreenSpace, GreenSpace newGreenSpace) {
        greenSpaceRepository.updateGreenSpace(oldGreenSpace, newGreenSpace);
    }

}
