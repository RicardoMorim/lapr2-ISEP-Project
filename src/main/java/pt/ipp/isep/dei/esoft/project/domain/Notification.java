package pt.ipp.isep.dei.esoft.project.domain;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification implements Serializable {
    private String message;
    private LocalDateTime timestamp;
    private boolean readStatus;
    private EmailWrapper recipient;
    private static final long serialVersionUID = 1L;
    private String title;
    private String collaboratorName;


    public Notification(String title, String message, EmailWrapper recipient, String CollaboratorName) {
        this.title = title;
        this.message = message;
        this.recipient = recipient;
        this.timestamp = LocalDateTime.now();
        this.readStatus = false;
        this.collaboratorName = CollaboratorName;
        createNotificationFile();
    }

    private void createNotificationFile() {
        String directoryName = this.collaboratorName.replaceAll("\\s+", "_"); // replace spaces with underscores
        Path directoryPath = Paths.get("src/main/resources" + directoryName);
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            String fileName = timestamp.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt";
            Path filePath = directoryPath.resolve(fileName);
            Files.write(filePath, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public EmailWrapper getRecipient() {
        return recipient;
    }

    public void setRecipient(EmailWrapper recipient) {
        this.recipient = recipient;
    }
}