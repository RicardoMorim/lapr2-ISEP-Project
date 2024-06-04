package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

public enum Type implements Serializable {
    GARDEN("garden"),
    MEDIUM_SIZED_PARK("medium-sized park"),
    LARGE_SIZED_PARK("large-sized park");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}