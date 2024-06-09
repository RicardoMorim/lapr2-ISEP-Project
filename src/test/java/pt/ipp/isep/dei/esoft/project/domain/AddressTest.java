package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testConstructor() {
        Address address = new Address("Street", "City", "1234-567");
        assertEquals("Street", address.getStreetName());
        assertEquals("City", address.getCity());
        assertEquals("1234-567", address.getZipCode());
    }

    @Test
    void testConstructorWithNullStreetNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(null, "City", "1234-567"));
    }

    @Test
    void testConstructorWithEmptyStreetNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("", "City", "1234-567"));
    }

    @Test
    void testConstructorWithNullCityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", null, "1234-567"));
    }

    @Test
    void testConstructorWithEmptyCityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "", "1234-567"));
    }

    @Test
    void testConstructorWithNullZipCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "City", null));
    }

    @Test
    void testConstructorWithEmptyZipCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "City", ""));
    }

    @Test
    void testSetStreetName() {
        Address address = new Address("Street", "City", "1234-567");
        address.setStreetName("New Street");
        assertEquals("New Street", address.getStreetName());
    }

    @Test
    void testSetCity() {
        Address address = new Address("Street", "City", "1234-567");
        address.setCity("New City");
        assertEquals("New City", address.getCity());
    }

    @Test
    void testSetZipCode() {
        Address address = new Address("Street", "City", "1234-567");
        address.setZipCode("5678-123");
        assertEquals("5678-123", address.getZipCode());
    }

    @Test
    void testValidateZipCode() {
        Address address = new Address("Street", "City", "1234-567");
        assertTrue(address.validateZipCode("1234-567"));
        assertFalse(address.validateZipCode("1234567"));
    }

    @Test
    void testToString() {
        Address address = new Address("Street", "City", "1234-567");
        assertEquals("Street 1234-567 City", address.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Address address1 = new Address("Street", "City", "1234-567");
        Address address2 = new Address("Street", "City", "1234-567");
        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }
}