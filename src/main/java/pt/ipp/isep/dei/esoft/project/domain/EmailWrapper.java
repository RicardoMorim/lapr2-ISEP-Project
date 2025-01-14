package pt.ipp.isep.dei.esoft.project.domain;

import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Email wrapper.
 */
public class EmailWrapper implements Serializable {
    private transient Email email;

    /**
     * Instantiates a new Email wrapper.
     *
     * @param email the email
     */
    public EmailWrapper(Email email) {
        this.email = email;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException, IOException {
        out.defaultWriteObject();
        out.writeObject(email.getEmail());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        email = new Email((String) in.readObject());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailWrapper that = (EmailWrapper) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}