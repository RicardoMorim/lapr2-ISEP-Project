package pt.ipp.isep.dei.esoft.project.domain;

import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GreenSpace {
    private Type type;
    private String name;
    private float area;
    private String address;
    private Email user;


    public GreenSpace(String name, String address, float area, Type type, Email user) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.area = area;
        this.user = user;
        validatePark();
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
        validatePark();
    }

    public Type getType() {
        return type;
    }

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
        values.add(this.type.toString());
        return values;
    }


    @Override
    public String toString() {
        return
                "Park Name = '" + name + " - Address = '" + address + "'" + " - Area = '" + area + "'" + "' - Type = '" + type + "'";
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
        if (this.name.isEmpty() || this.area == 0 || this.address.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
    }

    private void validateArea() {
        if (this.area < 0) {
            throw new IllegalArgumentException("Area must be a positive number.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenSpace that = (GreenSpace) o;
        return name.equals(that.name) ||
                address.equals(that.address);
    }
}