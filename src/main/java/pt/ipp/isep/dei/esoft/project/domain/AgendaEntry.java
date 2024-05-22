package pt.ipp.isep.dei.esoft.project.domain;

import java.util.List;

public class AgendaEntry {
    private List<Collaborator> team;
    private List<Vehicle> vehiclesEquipment;
    private String duration;
    private String status;
    private Entry entry;

    public AgendaEntry(Entry entry, List<Collaborator> team, List<Vehicle> vehiclesEquipment, String duration, String status) {
        this.entry = entry;
        this.team = team;
        this.vehiclesEquipment = vehiclesEquipment;
        this.duration = duration;
        this.status = status;
    }

    public List<Collaborator> getTeam() {
        return team;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public void setTeam(List<Collaborator> team) {
        this.team = team;
    }

    public List<Vehicle> getVehiclesEquipment() {
        return vehiclesEquipment;
    }

    public void setVehiclesEquipment(List<Vehicle> vehiclesEquipment) {
        this.vehiclesEquipment = vehiclesEquipment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}