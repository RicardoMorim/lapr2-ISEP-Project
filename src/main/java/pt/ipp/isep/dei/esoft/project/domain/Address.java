package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Address.
 */
public class Address implements Serializable {


    private String streetName;
    private String city;
    private String zipCode;

    /**
     * Instantiates a new Address.
     *
     * @param streetName the street name
     * @param city       the city
     * @param zipCode    the zip code
     */
    public Address(String streetName, String city, String zipCode) {
        this.streetName = streetName;
        this.city = city;
        this.zipCode = zipCode;
        validateAdress();
    }

    /**
     * Validate adress.
     */
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

    /**
     * Gets street name.
     *
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets street name.
     *
     * @param streetName the street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets zip code.
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets zip code.
     *
     * @param zipCode the zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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