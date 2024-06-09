package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Address test.
 */
class AddressTest {

    /**
     * Test constructor.
     */
    @Test
    void testConstructor() {
        Address address = new Address("Street", "City", "1234-567");
        assertEquals("Street", address.getStreetName());
        assertEquals("City", address.getCity());
        assertEquals("1234-567", address.getZipCode());
    }

    /**
     * Test constructor with null street name throws exception.
     */
    @Test
    void testConstructorWithNullStreetNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(null, "City", "1234-567"));
    }

    /**
     * Test constructor with empty street name throws exception.
     */
    @Test
    void testConstructorWithEmptyStreetNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("", "City", "1234-567"));
    }

    /**
     * Test constructor with null city throws exception.
     */
    @Test
    void testConstructorWithNullCityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", null, "1234-567"));
    }

    /**
     * Test constructor with empty city throws exception.
     */
    @Test
    void testConstructorWithEmptyCityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "", "1234-567"));
    }

    /**
     * Test constructor with null zip code throws exception.
     */
    @Test
    void testConstructorWithNullZipCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "City", null));
    }

    /**
     * Test constructor with empty zip code throws exception.
     */
    @Test
    void testConstructorWithEmptyZipCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Street", "City", ""));
    }

    /**
     * Test set street name.
     */
    @Test
    void testSetStreetName() {
        Address address = new Address("Street", "City", "1234-567");
        address.setStreetName("New Street");
        assertEquals("New Street", address.getStreetName());
    }

    /**
     * Test set city.
     */
    @Test
    void testSetCity() {
        Address address = new Address("Street", "City", "1234-567");
        address.setCity("New City");
        assertEquals("New City", address.getCity());
    }

    /**
     * Test set zip code.
     */
    @Test
    void testSetZipCode() {
        Address address = new Address("Street", "City", "1234-567");
        address.setZipCode("5678-123");
        assertEquals("5678-123", address.getZipCode());
    }

    /**
     * Test validate zip code.
     */
    @Test
    void testValidateZipCode() {
        Address address = new Address("Street", "City", "1234-567");
        assertTrue(address.validateZipCode("1234-567"));
        assertFalse(address.validateZipCode("1234567"));
    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        Address address = new Address("Street", "City", "1234-567");
        assertEquals("Street 1234-567 City", address.toString());
    }

    /**
     * Test equals and hash code.
     */
    @Test
    void testEqualsAndHashCode() {
        Address address1 = new Address("Street", "City", "1234-567");
        Address address2 = new Address("Street", "City", "1234-567");
        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }
}