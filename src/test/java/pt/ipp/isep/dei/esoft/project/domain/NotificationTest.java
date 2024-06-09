package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Notification test.
 */
class NotificationTest {

    private Notification notification;
    private EmailWrapper recipient;
    private LocalDateTime timestamp;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        recipient = new EmailWrapper(new Email("test@example.com"));
        timestamp = LocalDateTime.now();
        notification = new Notification("Title", "Message", recipient, "CollaboratorName");
    }

    /**
     * Test constructor.
     */
    @Test
    void testConstructor() {
        assertEquals("Title", notification.getTitle());
        assertEquals("Message", notification.getMessage());
        assertEquals(recipient, notification.getRecipient());
        assertFalse(notification.isReadStatus());
    }

    /**
     * Test set title.
     */
    @Test
    void testSetTitle() {
        notification.setTitle("New Title");
        assertEquals("New Title", notification.getTitle());
    }

    /**
     * Test set message.
     */
    @Test
    void testSetMessage() {
        notification.setMessage("New Message");
        assertEquals("New Message", notification.getMessage());
    }

    /**
     * Test set timestamp.
     */
    @Test
    void testSetTimestamp() {
        LocalDateTime newTimestamp = LocalDateTime.now().plusDays(1);
        notification.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, notification.getTimestamp());
    }

    /**
     * Test set read status.
     */
    @Test
    void testSetReadStatus() {
        notification.setReadStatus(true);
        assertTrue(notification.isReadStatus());
    }

    /**
     * Test set recipient.
     */
    @Test
    void testSetRecipient() {
        EmailWrapper newRecipient = new EmailWrapper(new Email("new@example.com"));
        notification.setRecipient(newRecipient);
        assertEquals(newRecipient, notification.getRecipient());
    }
}