package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

/**
 * Controller for managing GreenSpace objects.
 */
public class GreenSpaceController {
    private GreenSpaceRepository greenSpaceRepository;

    /**
     * Constructs a new GreenSpaceController with the default GreenSpaceRepository.
     */
    public GreenSpaceController() {
        greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
    }

    /**
     * Constructs a new GreenSpaceController with the specified GreenSpaceRepository.
     *
     * @param rep the GreenSpaceRepository to use
     */
    public GreenSpaceController(GreenSpaceRepository rep) {
        greenSpaceRepository = rep;
    }

    /**
     * Adds the specified GreenSpace to the repository.
     *
     * @param greenSpace the GreenSpace to add
     */
    public void addGreenSpace(GreenSpace greenSpace) {
        greenSpaceRepository.addGreenSpace(greenSpace);
    }

    /**
     * Creates a new GreenSpace with the specified parameters and adds it to the repository.
     *
     * @param name    the name of the GreenSpace
     * @param address the address of the GreenSpace
     * @param area    the area of the GreenSpace
     * @param type    the type of the GreenSpace
     * @param user    the user associated with the GreenSpace
     */
    public void addGreenSpace(String name, Address address, double area, Type type, Email user) {
        GreenSpace greenSpace = new GreenSpace(name, address, area, type, user);
        greenSpaceRepository.addGreenSpace(greenSpace);
    }

    /**
     * Returns a list of all GreenSpaces in the repository.
     *
     * @return a list of all GreenSpaces
     */
    public List<GreenSpace> getGreenSpaceList() {
        return greenSpaceRepository.getGreenSpaces();
    }

    /**
     * Removes the specified GreenSpace from the repository.
     *
     * @param greenSpace the GreenSpace to remove
     */
    public void removeGreenSpace(GreenSpace greenSpace) {
        greenSpaceRepository.removeGreenSpace(greenSpace);
    }

    /**
     * Updates the specified old GreenSpace with the new GreenSpace in the repository.
     *
     * @param oldGreenSpace the old GreenSpace to update
     * @param newGreenSpace the new GreenSpace to replace the old one
     */
    public void updateGreenSpace(GreenSpace oldGreenSpace, GreenSpace newGreenSpace) {
        greenSpaceRepository.updateGreenSpace(oldGreenSpace, newGreenSpace);
    }


    /**
     * Checks if a GreenSpace with the specified name exists in the repository.
     *
     * @param name the name of the GreenSpace to check
     * @return true if the GreenSpace exists, false otherwise
     */
    public boolean checkIfGreenSpaceNameExists(String name) {
        return greenSpaceRepository.checkIfGreenSpaceNameExists(name);
    }

    /**
     * Checks if a GreenSpace with the specified address exists in the repository.
     *
     * @param address the address of the GreenSpace to check
     * @return true if the GreenSpace exists, false otherwise
     */
    public boolean checkIfGreenSpaceAddressExists(Address address){
        return greenSpaceRepository.checkIfGreenSpaceAddressExists(address);
    }

    /**
     * Returns a list of GreenSpaces managed by the specified user.
     *
     * @param user the user whose GreenSpaces to return
     * @return a list of GreenSpaces managed by the user
     */
    public List<GreenSpace>getGreenSpacesManagedByUser(EmailWrapper user){
        return greenSpaceRepository.getGreenSpacesManagedByUser(user);
    }

    /**
     * Validates the specified zip code.
     *
     * @param zipCode the zip code to validate
     * @return true if the zip code is valid, false otherwise
     */
    public boolean validateZipCode(String zipCode) {
        return greenSpaceRepository.validateZipCode(zipCode);
    }
}
