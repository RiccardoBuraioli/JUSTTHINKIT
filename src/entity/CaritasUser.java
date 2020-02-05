package entity;

public class CaritasUser {

    private int id;
    private String nomeCaritas;
    private String password;
    private String indirizzoCaritas;
    private String tipologia;
    private String recapitoTelefonico;
    private String email;


    public CaritasUser() {
    }

    public CaritasUser(String nomeCaritas, String password, String indirizzoCaritas, String tipologia, String recapitoTelefonico, String email) {
        this.nomeCaritas = nomeCaritas;
        this.password = password;
        this.indirizzoCaritas = indirizzoCaritas;
        this.tipologia = tipologia;
        this.recapitoTelefonico = recapitoTelefonico;
        this.email = email;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNomeCaritas() {
        return nomeCaritas;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndirizzoCaritas() {
        return indirizzoCaritas;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    public String getEmail() {
        return email;
    }

    public void setNomeCaritas(String nomeCaritas) {
        this.nomeCaritas = nomeCaritas;
    }

    public void setIndirizzoCaritas(String indirizzoCaritas) {
        this.indirizzoCaritas = indirizzoCaritas;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setRecapitoTelefonico(String recapitoTelefonico) {
        this.recapitoTelefonico = recapitoTelefonico;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CaritasUser{" +
                "nomeCaritas='" + nomeCaritas + '\'' +
                ", indirizzoCaritas='" + indirizzoCaritas + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", recapitoTelefonico='" + recapitoTelefonico + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
