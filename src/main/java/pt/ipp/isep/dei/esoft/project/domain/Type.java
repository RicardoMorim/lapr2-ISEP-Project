package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

/**
 * The enum Type.
 */
public enum Type implements Serializable {
    /**
     * Garden type.
     */
    GARDEN("garden"),
    /**
     * The Medium sized park.
     */
    MEDIUM_SIZED_PARK("medium-sized park"),
    /**
     * The Large sized park.
     */
    LARGE_SIZED_PARK("large-sized park");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}