package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Email wrapper test.
 */
class EmailWrapperTest {

    /**
     * Test constructor.
     */
    @Test
    void testConstructor() {
        Email email = new Email("test@example.com");
        EmailWrapper emailWrapper = new EmailWrapper(email);
        assertEquals(email, emailWrapper.getEmail());
    }

    /**
     * Test set email.
     */
    @Test
    void testSetEmail() {
        Email email = new Email("test@example.com");
        EmailWrapper emailWrapper = new EmailWrapper(email);
        Email newEmail = new Email("new@example.com");
        emailWrapper.setEmail(newEmail);
        assertEquals(newEmail, emailWrapper.getEmail());
    }

    /**
     * Test equals and hash code.
     */
    @Test
    void testEqualsAndHashCode() {
        Email email1 = new Email("test@example.com");
        EmailWrapper emailWrapper1 = new EmailWrapper(email1);
        Email email2 = new Email("test@example.com");
        EmailWrapper emailWrapper2 = new EmailWrapper(email2);
        assertEquals(emailWrapper1, emailWrapper2);
        assertEquals(emailWrapper1.hashCode(), emailWrapper2.hashCode());
    }

    /**
     * Test not equals to null.
     */
    @Test
    void testNotEqualsToNull() {
        Email email = new Email("test@example.com");
        EmailWrapper emailWrapper = new EmailWrapper(email);
        assertNotEquals(emailWrapper, null);
    }

    /**
     * Test not equals to different class.
     */
    @Test
    void testNotEqualsToDifferentClass() {
        Email email = new Email("test@example.com");
        EmailWrapper emailWrapper = new EmailWrapper(email);
        assertNotEquals(emailWrapper, new Object());
    }

    /**
     * Test not equals to different email.
     */
    @Test
    void testNotEqualsToDifferentEmail() {
        Email email1 = new Email("test1@example.com");
        EmailWrapper emailWrapper1 = new EmailWrapper(email1);
        Email email2 = new Email("test2@example.com");
        EmailWrapper emailWrapper2 = new EmailWrapper(email2);
        assertNotEquals(emailWrapper1, emailWrapper2);
    }
}