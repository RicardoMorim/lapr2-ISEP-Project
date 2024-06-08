package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

/**
 * The enum Status.
 */
public enum Status implements Serializable {
    /**
     * To be done status.
     */
    TO_BE_DONE,
    /**
     * Planned status.
     */
    PLANNED,
    /**
     * In progress status.
     */
    IN_PROGRESS,
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