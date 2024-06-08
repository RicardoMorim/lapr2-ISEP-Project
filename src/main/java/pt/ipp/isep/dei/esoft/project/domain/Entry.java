package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

public class Entry implements Serializable {


    private Status state;

    private GreenSpace greenSpace;

    private String title;

    private String description;

    private Urgency urgency;

    private float expectedDuration;

    public Entry(GreenSpace greenSpace, String title, String description, Urgency urgency, float expectedDuration) {
        this.state = Status.TO_BE_DONE;
        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.expectedDuration = expectedDuration;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public GreenSpace getGreenSpace() {
        return greenSpace;
    }

    public void setGreenSpace(GreenSpace greenSpace) {
        this.greenSpace = greenSpace;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public float getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(float expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    @Override
    public String toString() {
        return title + '\'' + description + '\'' + urgency + '\'' + expectedDuration;
    }


}
