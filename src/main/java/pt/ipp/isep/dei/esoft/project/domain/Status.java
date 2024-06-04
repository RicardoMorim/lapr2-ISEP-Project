package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

/**
 * The enum Status.
 */
public enum Status implements Serializable {
    /**
     * Planned status.
     */
    PLANNED,
    /**
     * Postponed status.
     */
    POSTPONED,
    /**
     * Canceled status.
     */
    CANCELED,
    /**
     * Done status.
     */
    DONE
}