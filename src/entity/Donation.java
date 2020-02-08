package entity;

public class Donation {

    private String type;
    private int idVolunteer;
    private String targetCaritasName;
    private String Description;

    public Donation(String type, int idVolunteer, String targetCaritasName, String description) {
        this.type = type;
        this.idVolunteer = idVolunteer;
        this.targetCaritasName = targetCaritasName;
        this.Description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdVolunteer() {
        return idVolunteer;
    }

    public void setIdVolunteer(int idVolunteer) {
        this.idVolunteer = idVolunteer;
    }

    public String getTargetCaritasName() {
        return targetCaritasName;
    }

    public void setTargetCaritasName(String targetCaritasName) {
        this.targetCaritasName = targetCaritasName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

