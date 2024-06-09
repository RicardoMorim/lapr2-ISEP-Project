package pt.ipp.isep.dei.esoft.project.domain;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a GreenSpace with a name, address, area, type, and user.
 */
public class GreenSpace implements Serializable {
    private Type type;
    private String name;
    private double area;
    private Address address;
    private EmailWrapper user;


    /**
     * Constructs a new GreenSpace with the specified name, address, area, type, and user.
     *
     * @param name    the name of the GreenSpace
     * @param address the address of the GreenSpace
     * @param area    the area of the GreenSpace
     * @param type    the type of the GreenSpace
     * @param user    the user associated with the GreenSpace
     */
    public GreenSpace(String name, Address address, double area, Type type, Email user) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.area = area;
        this.user = new EmailWrapper(user);
        validatePark();
    }

    /**
     * Returns the user associated with the GreenSpace.
     *
     * @return the user
     */
    public EmailWrapper getUser() {
        return user;
    }

    /**
     * Returns the name of the GreenSpace.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the GreenSpace.
     *
     * @param name the new name
     * @throws IllegalArgumentException if the name is invalid
     */
    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
        validatePark();
    }

    /**
     * Returns the type of the GreenSpace.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of the GreenSpace.
     *
     * @param type the new type
     * @throws IllegalArgumentException if the type is invalid
     */
    public void setType(Type type) {
        Type old = this.type;
        this.type = type;

        try {
            validatePark();
        } catch (IllegalArgumentException e) {
            this.type = old;
            throw e;
        }
    }

    /**
     * Returns the address of the GreenSpace.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the GreenSpace.
     *
     * @param address the new address
     * @throws IllegalArgumentException if the address is invalid
     */
    public void setAddress(Address address) {
        Address old = this.address;
        this.address = address;

        try {
            validatePark();
        } catch (IllegalArgumentException e) {
            this.address = old;
            throw e;
        }
    }

    /**
     * Returns the area of the GreenSpace.
     *
     * @return the area
     */
    public double getArea() {
        return area;
    }


    /**
     * Sets the area of the GreenSpace.
     *
     * @param area the new area
     * @throws IllegalArgumentException if the area is invalid
     */
    public void setArea(double area) {
        double old = this.area;
        this.area = area;

        try {
            validatePark();
        } catch (IllegalArgumentException e) {
            this.area = old;
            throw e;
        }
    }

    /**
     * Returns a list of the GreenSpace's values.
     *
     * @return a list of values
     */
    public List<String> getParkValues() {
        List<String> values = new ArrayList<String>();
        values.add(this.name);
        values.add(Double.toString(this.area));
        values.add(this.address.toString());
        values.add(this.type.toString());
        return values;
    }

    /**
     * Returns a string representation of the GreenSpace.
     * @return a string representation of the GreenSpace
     */
    @Override
    public String toString() {
        return
                "Park Name = " + name + " - Address = " + address  + " - Area = " + area + " - Type = " + type;
    }

    /**
     * Returns a hash code value for the GreenSpace.
     * @return a hash code value for the GreenSpace
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, name, area, address);
    }

    /**
     * Validates the GreenSpace.
     *
     * @return true if the GreenSpace is valid
     * @throws IllegalArgumentException if the GreenSpace is invalid
     */
    public boolean validatePark() throws IllegalArgumentException {
        validateNotEmptyFields();
        validateArea();
        return true;
    }

    /**
     * Validates the non-empty fields of the GreenSpace.
     * @throws IllegalArgumentException if any field is empty
     */
    private void validateNotEmptyFields() {
        if (this.name.isEmpty() || this.area == 0 || this.address == null) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
    }

    /**
     * Validates the area of the GreenSpace.
     * @throws IllegalArgumentException if the area is invalid
     */
    private void validateArea() {
        if (this.area < 0) {
            throw new IllegalArgumentException("Area must be a positive number.");
        }
    }


    /**
     * Indicates whether some other object is "equal to" this GreenSpace.
     * @param o the reference object with which to compare
     * @return true if this GreenSpace is the same as the o argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenSpace that = (GreenSpace) o;
        return name.equals(that.name) ||
                address.equals(that.address);
    }
}