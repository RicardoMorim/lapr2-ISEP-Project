package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.Scanner;

/**
 * The type Add green space ui.
 */
public class AddGreenSpaceUI implements Runnable {
    /**
     * The Controller.
     */
    GreenSpaceController controller;
    private String name;
    private double area;
    private Address address;
    private String street;
    private String zipCode;
    private String city;
    private Type type;
    /**
     * The Input.
     */
    Scanner input = new Scanner(System.in);

    /**
     * Instantiates a new Add green space ui.
     */
    public AddGreenSpaceUI() {
        this.controller = new GreenSpaceController();
    }

    private void requestData(){
        do {
            name = Utils.readLineFromConsole("Name: ");
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            }
        } while (name.isEmpty());

        do {
            while (controller.checkIfGreenSpaceNameExists(name)) {
                System.out.println("A Green Space with this name already exists.");
                name = Utils.readLineFromConsole("Name: ");
            }
            address = requestAddress();
            while (controller.checkIfGreenSpaceAddressExists(address)) {
                System.out.println("A Green Space with this address already exists.");
                address = requestAddress();
            }
            area = requestArea();
            while (area <= 0) {
                System.out.println("Area must be greater than 0.");
                area = requestArea();
            }
            type = requestType();
        } while (type == null);
    }

    /**
     * Validate zip code boolean.
     *
     * @param zipCode the zip code
     * @return the boolean
     */
    public boolean validateZipCode(String zipCode) {
        if (zipCode.isEmpty()) {
            return false;
        }
        String zipPattern = "^[0-9]{4}-[0-9]{3}$";
        return zipCode.matches(zipPattern);
    }

    private Address requestAddress(){
        do {
            street = Utils.readLineFromConsole("Street: ");
            if (street.isEmpty()) {
                System.out.println("Street cannot be empty.");
            }
        } while (street.isEmpty());

        do {
            zipCode = Utils.readLineFromConsole("Zip Code: ");
            while (!validateZipCode(zipCode)) {
                System.out.println("Invalid Zip Code. Please enter a Zip Code with the following format (0000-000).");
                zipCode = Utils.readLineFromConsole("Zip Code: ");
            }
        } while (zipCode.isEmpty());

        do {
            city = Utils.readLineFromConsole("City: ");
            if (city.isEmpty()) {
                System.out.println("City cannot be empty.");
            }
        } while (city.isEmpty());

        return address = new Address(street, city,  zipCode);
    }

    private double requestArea() {
        double area = 0;
        boolean validInput = false;
        do {
            System.out.print("Area: ");
            if (input.hasNextDouble()) {
                area = input.nextDouble();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a double value.");
                input.nextLine(); // discard the invalid input
            }
        } while (!validInput);
        return area;
    }

    private Type requestType() {
        Type[] types = Type.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        System.out.print("Select a type: ");
        int selected = input.nextInt();
        return types[selected - 1];
    }

    private void submitData() {
        if (Utils.confirm("Do you want to register the green space? (S/N)")) {
            try {
                controller.addGreenSpace(name, address, area, type, Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId());
                System.out.println("The green space was successfully registered.");
            } catch (IllegalArgumentException e) {
                System.out.println("The green space was not registered.\nThere was an error registering it.\n");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Registration Cancelled.");
        }
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Register Green Space ------------------------");

        requestData();

        submitData();

        for (GreenSpace g: controller.getGreenSpaceList()) {
            System.out.println(g);
        }
    }
}
