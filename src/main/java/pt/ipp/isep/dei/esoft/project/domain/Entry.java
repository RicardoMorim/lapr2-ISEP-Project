package pt.ipp.isep.dei.esoft.project.domain;

public class Entry {


    private String state;

    private GreenSpace greenSpace;

    private String title;

    private String description;

    private String urgency;

    private float expectedDuration;

    public Entry(String state, GreenSpace greenSpace, String title, String description, String urgency, float expectedDuration) {
        this.state = state;
        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.expectedDuration = expectedDuration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
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
        return "ToDoList{" +
                "state='" + state + '\'' +
                ", greenSpace=" + greenSpace +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", urgency='" + urgency + '\'' +
                ", expectedDuration=" + expectedDuration +
                '}';
    }



}
