package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
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

    public void addGreenSpace(String name, Address address, double area, Type type) {
        GreenSpace greenSpace = new GreenSpace(name, address, area, type);
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

    public GreenSpace getGreenSpaceByDesignation(String designation) {
        return greenSpaceRepository.getGreenSpaceByDesignation(designation);
    }

    public boolean checkIfGreenSpaceNameExists(String name) {
        return greenSpaceRepository.checkIfGreenSpaceNameExists(name);
    }

    public boolean checkIfGreenSpaceAddressExists(Address address){
        return greenSpaceRepository.checkIfGreenSpaceAddressExists(address);
    }

    public List<GreenSpace>getGreenSpacesManagedByUser(EmailWrapper user){
        return greenSpaceRepository.getGreenSpacesManagedByUser(user);
    }

    public boolean validateZipCode(String zipCode) {
        return greenSpaceRepository.validateZipCode(zipCode);
    }
}
