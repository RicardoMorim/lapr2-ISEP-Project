package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

/**
 * The type Entry.
 */
public class Entry implements Serializable {


    private Status state;

    private GreenSpace greenSpace;

    private String title;

    private String description;

    private Urgency urgency;

    private float expectedDuration;

    /**
     * Instantiates a new Entry.
     *
     * @param greenSpace       the green space
     * @param title            the title
     * @param description      the description
     * @param urgency          the urgency
     * @param expectedDuration the expected duration
     */
    public Entry(GreenSpace greenSpace, String title, String description, Urgency urgency, float expectedDuration) {
        this.state = Status.TO_BE_DONE;
        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.expectedDuration = expectedDuration;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public Status getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(Status state) {
        this.state = state;
    }

    /**
     * Gets green space.
     *
     * @return the green space
     */
    public GreenSpace getGreenSpace() {
        return greenSpace;
    }

    /**
     * Sets green space.
     *
     * @param greenSpace the green space
     */
    public void setGreenSpace(GreenSpace greenSpace) {
        this.greenSpace = greenSpace;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets urgency.
     *
     * @return the urgency
     */
    public Urgency getUrgency() {
        return urgency;
    }

    /**
     * Sets urgency.
     *
     * @param urgency the urgency
     */
    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    /**
     * Gets expected duration.
     *
     * @return the expected duration
     */
    public float getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * Sets expected duration.
     *
     * @param expectedDuration the expected duration
     */
    public void setExpectedDuration(float expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    @Override
    public String toString() {
        return title + '\'' + description + '\'' + urgency + '\'' + expectedDuration;
    }


}
