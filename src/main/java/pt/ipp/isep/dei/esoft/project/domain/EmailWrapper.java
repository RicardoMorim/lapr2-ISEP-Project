package pt.ipp.isep.dei.esoft.project.domain;

import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.io.Serializable;

public class EmailWrapper implements Serializable {
    private transient Email email;

    public EmailWrapper(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

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
}