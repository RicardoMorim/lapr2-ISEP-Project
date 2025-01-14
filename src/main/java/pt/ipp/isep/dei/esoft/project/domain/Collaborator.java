package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * The type Collaborator.
 */
public class Collaborator implements Serializable {
    private String email;
    private String name;
    private Address address;
    private String phone;
    private Job job;
    private List<Skill> skills;
    private Date birthDate;
    private Date admissionDate;
    private String IDtype;
    private int taxpayerNumber;
    private int citizenNumber;
    /**
     * The Is free.
     */
    boolean isFree = true;
    private List<Notification> notifications;

    /**
     * Instantiates a new Collaborator.
     *
     * @param email          the email
     * @param name           the name
     * @param address        the address
     * @param phone          the phone
     * @param job            the job
     * @param birthDate      the birth date
     * @param admissionDate  the admission date
     * @param IDtype         the dtype
     * @param taxpayerNumber the taxpayer number
     * @param citizenNumber  the citizen number
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Collaborator(String email, String name, Address address, String phone, Job job, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber, int citizenNumber) throws IllegalArgumentException {

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
        this.skills = new ArrayList<>();
        this.notifications = new ArrayList<>();
        isValid();
    }

    /**
     * Instantiates a new Collaborator.
     *
     * @param email          the email
     * @param name           the name
     * @param address        the address
     * @param phone          the phone
     * @param job            the job
     * @param birthDate      the birth date
     * @param admissiondate  the admissiondate
     * @param IDtype         the dtype
     * @param taxpayerNumber the taxpayer number
     * @param citizenNumber  the citizen number
     * @param skills         the skills
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Collaborator(String email, String name, Address address, String phone, Job job, Date birthDate, Date admissiondate, String IDtype, int taxpayerNumber, int citizenNumber, List<Skill> skills) throws IllegalArgumentException {

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
        this.skills = new ArrayList<>(skills);
        this.notifications = new ArrayList<>();
        isValid();
    }


    /**
     * Add notification.
     *
     * @param notification the notification
     */
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    /**
     * Gets notifications.
     *
     * @return the notifications
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets job.
     *
     * @return the job
     */
    public Job getJob() {
        return job;
    }

    /**
     * Sets job.
     *
     * @param job the job
     */
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     * Gets skills.
     *
     * @return the skills
     */
    public List<Skill> getSkills() {
        return skills;
    }

    /**
     * Sets skills.
     *
     * @param skills the skills
     */
    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    /**
     * Gets citizen number.
     *
     * @return the citizen number
     */
    public int getCitizenNumber() {
        return citizenNumber;
    }

    /**
     * Sets citizen number.
     *
     * @param citizenNumber the citizen number
     */
    public void setCitizenNumber(int citizenNumber) {
        this.citizenNumber = citizenNumber;
    }

    /**
     * Gets taxpayer number.
     *
     * @return the taxpayer number
     */
    public int getTaxpayerNumber() {
        return taxpayerNumber;
    }

    /**
     * Sets taxpayer number.
     *
     * @param taxpayerNumber the taxpayer number
     */
    public void setTaxpayerNumber(int taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    /**
     * Gets i dtype.
     *
     * @return the i dtype
     */
    public String getIDtype() {
        return IDtype;
    }

    /**
     * Sets i dtype.
     *
     * @param IDtype the dtype
     */
    public void setIDtype(String IDtype) {
        this.IDtype = IDtype;
    }

    /**
     * Gets admission date.
     *
     * @return the admission date
     */
    public Date getAdmissionDate() {
        return admissionDate;
    }

    /**
     * Sets admission date.
     *
     * @param admissionDate the admission date
     */
    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    /**
     * Is free boolean.
     *
     * @return the boolean
     */
    public boolean isFree() {
        return isFree;
    }

    /**
     * Sets free.
     *
     * @param free the free
     */
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


    /**
     * Is valid boolean.
     *
     * @return the boolean
     * @throws IllegalArgumentException the illegal argument exception
     */
    public boolean isValid() throws IllegalArgumentException {
        if (email == null || name == null || address == null || phone == null || job == null || birthDate == null || admissionDate == null || IDtype == null) {
            throw new IllegalArgumentException("No parameter of the collaborator can be null");
        }

        if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || IDtype.isEmpty() || taxpayerNumber == 0 || citizenNumber == 0) {
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

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Add skill list.
     *
     * @param skill the skill
     * @return the list
     */
    public List<Skill> addSkill(Skill skill) {
        if (this.skills.contains(skill)) {
            throw new IllegalArgumentException("Collaborator already contains the skill");
        }
        if (skill.getName() == null || skill.getDescription() == null) {
            throw new IllegalArgumentException("The name and short description cannot be null");
        }
        this.skills.add(skill);

        return this.skills;
    }

    /**
     * Remove skill.
     *
     * @param skill the skill
     */
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