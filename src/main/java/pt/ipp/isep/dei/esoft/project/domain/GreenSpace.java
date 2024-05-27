package pt.ipp.isep.dei.esoft.project.domain;

import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GreenSpace {
    private String type; // TODO - utilizar uma class enum para o type
    private String name;
    private float area;
    private String address;
    private Email user;


    public GreenSpace(String name, float area, String address, String type, Email user) {
        this.name = name;
        this.type = type;
        this.area = area;
        this.address = address;
        this.user = user;
        validatePark();
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        validatePark();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        String old = this.type;
        this.type = type;

        try {
            validatePark();
        } catch (IllegalArgumentException e) {
            this.name = old;
            throw e;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        String old = this.address;
        this.address = address;

        try {
            validatePark();
        } catch (IllegalArgumentException e) {
            this.address = old;
            throw e;
        }
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        float old = this.area;
        this.area = area;

        try {
            validatePark();
        } catch (IllegalArgumentException e) {
            this.area = old;
            throw e;
        }
    }

    public List<String> getParkValues() {
        List<String> values = new ArrayList<String>();
        values.add(this.name);
        values.add(Float.toString(this.area));
        values.add(this.address);
        values.add(this.type);
        return values;
    }


    @Override
    public String toString() {
        return
                "Park Name = '" + name + "' - Type = '" + type + "'" + " - Area = '" + area + "'" + " - Address = '" + address + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenSpace greenSpace = (GreenSpace) o;
        return Objects.equals(type, greenSpace.type) && Objects.equals(name, greenSpace.name) && Objects.equals(area, greenSpace.area) && Objects.equals(address, greenSpace.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, area, address);
    }

    public boolean validatePark() throws IllegalArgumentException {
        validateNotEmptyFields();
        validateArea();
        return true;
    }

    private void validateNotEmptyFields() {
        if (this.name.isEmpty() || this.type.isEmpty() || this.area == 0 || this.address.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
    }

    private void validateArea() {
        if (this.area < 0) {
            throw new IllegalArgumentException("Area must be a positive number.");
        }
    }
}