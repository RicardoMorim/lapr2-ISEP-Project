package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Objects;
public class Address implements Serializable {


    private String streetName;
    private String city;
    private String zipCode;

    public Address(String streetName, String city, String zipCode) {
        this.streetName = streetName;
        this.city = city;
        this.zipCode = zipCode;
        validateAdress();
    }
    public void validateAdress(){
        if (streetName == null || streetName.isEmpty()) {
            throw new IllegalArgumentException("Street name cannot be null or empty");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (zipCode == null || zipCode.isEmpty()) {
            throw new IllegalArgumentException("Zip code cannot be null or empty");
        }
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean validateZipCode(String zipCode) {
        if (zipCode.isEmpty()) {
            return false;
        }
        String zipPattern = "^[0-9]{4}-[0-9]{3}$";
        return zipCode.matches(zipPattern);
    }

    @Override
    public String toString() {
        return
                streetName + " " + zipCode + " " + city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, city, zipCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetName.equalsIgnoreCase(address.streetName) &&
                city.equalsIgnoreCase(address.city) &&
                zipCode.equalsIgnoreCase(address.zipCode);
    }
}