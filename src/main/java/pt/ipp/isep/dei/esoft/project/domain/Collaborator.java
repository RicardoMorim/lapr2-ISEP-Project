package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Collaborator {
    private String email;
    private String name;
    private String address;
    private String phone;
    private Job job;
    private List<Skill> skills;
    private Date birthDate;
    private Date admissionDate;
    private String IDtype;
    private int taxpayerNumber;
    private int citizenNumber;
    boolean isFree = true;

    public Collaborator(String email, String name, String address, String phone, Job job, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber, int citizenNumber) throws IllegalArgumentException {

        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.job = job;
        this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.IDtype = IDtype;
        this.taxpayerNumber = taxpayerNumber;
        this.citizenNumber = citizenNumber;
        this.skills = new ArrayList<Skill>();
        isValid();
    }

    public Collaborator(String email, String name, String address, String phone, Job job, Date birthDate, Date admissiondate, String IDtype, int taxpayerNumber, int citizenNumber, List<Skill> skills) throws IllegalArgumentException {

        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.job = job;
        this.birthDate = birthDate;
        this.admissionDate = admissiondate;
        this.IDtype = IDtype;
        this.taxpayerNumber = taxpayerNumber;
        this.citizenNumber = citizenNumber;
        this.skills = skills;
        isValid();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getCitizenNumber() {
        return citizenNumber;
    }

    public void setCitizenNumber(int citizenNumber) {
        this.citizenNumber = citizenNumber;
    }

    public int getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(int taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getIDtype() {
        return IDtype;
    }

    public void setIDtype(String IDtype) {
        this.IDtype = IDtype;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        this.isFree = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaborator)) {
            return false;
        }
        Collaborator collaborator = (Collaborator) o;
        return email.equals(collaborator.email) || phone.equals(collaborator.phone) || taxpayerNumber == collaborator.taxpayerNumber || citizenNumber == collaborator.citizenNumber;
    }


    public boolean isValid() throws IllegalArgumentException {
        if (email == null || name == null || address == null || phone == null || job == null || birthDate == null || admissionDate == null || IDtype == null) {
            throw new IllegalArgumentException("No parameter of the collaborator can be null");
        }

        if (email.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty() || IDtype.isEmpty() || taxpayerNumber == 0 || citizenNumber == 0) {
            throw new IllegalArgumentException("No parameter of the collaborator can be null or empty");
        }
        if (taxpayerNumber < 0 || citizenNumber < 0) {
            throw new IllegalArgumentException("Taxpayer number and citizen number must be positive");
        }

        String[] emailSplit = this.email.split("@");
        if (emailSplit.length != 2) {
            throw new IllegalArgumentException("Email must contain '@'");
        }

        if (emailSplit[0].isEmpty() || emailSplit[1].isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (emailSplit[0].contains(" ") || emailSplit[1].contains(" ")) {
            throw new IllegalArgumentException("Email cannot contain spaces");
        }

        if (emailSplit[0].length() < 3 || emailSplit[1].length() < 3) {
            throw new IllegalArgumentException("Email must have at least 3 characters before and after the '@'");
        }

        return true;

    }


    @Override
    public int hashCode() {
        return Objects.hash(email, name, address, phone, job, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber, skills);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Skill> addSkill(Skill skill) {
        if (this.skills.contains(skill)) {
            throw new IllegalArgumentException("Collaborator already contains the skill");
        }
        if (skill.getName() == null || skill.getShortDescription() == null) {
            throw new IllegalArgumentException("The name and short description cannot be null");
        }
        this.skills.add(skill);

        return this.skills;
    }

    public void removeSkill(Skill skill) {
        if (!this.skills.contains(skill)) {
            throw new IllegalArgumentException("Collaborator does not contain the skill");
        }
        this.skills.remove(skill);
    }

    @Override
    public String toString() {
        StringBuilder skillString = new StringBuilder();

        for (Skill skill : skills) {
            skillString.append("\n    skill='").append(skill.toString()).append("'\n");
        }

        return
                "Name: '" + name + "',\n" + "Email: '" + email + "'\n";
    }

    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Collaborator clone() {
        return new Collaborator(this.email, this.name, this.address, this.phone, this.job, this.birthDate, this.admissionDate, this.IDtype, this.taxpayerNumber, this.citizenNumber, new ArrayList<>(this.skills));
    }


}