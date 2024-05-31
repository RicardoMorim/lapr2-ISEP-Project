package pt.ipp.isep.dei.esoft.project.domain;

public class Address {
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

    @Override
    public String toString() {
        return
                "Address: " + streetName +
                        ", " + city + "\n";
    }
}