package entity;

public class CaritasUser {

    private int id;
    private String nomeCaritas;
    private String email;
    private String password;
    private String indirizzoCaritas;
    private String civico;
    private String citta;
    private String cap;
    private String tipologia;
    private String recapitoTelefonico;
    private String codiceIban;
    private String profileImage;
    private static final int CARITAS_CODE = 2;
    private static final String TABLE_NAME = "caritas";


    public CaritasUser() {
        //Costruttore vuoto, troppi attributi da istanziare
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) this.id = id;

    }

    public String getNomeCaritas() {
        return nomeCaritas;
    }

    public void setNomeCaritas(String nomeCaritas) {
        this.nomeCaritas = nomeCaritas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setIndirizzoCaritas(String indirizzoCaritas) {
        this.indirizzoCaritas = indirizzoCaritas;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getRecapitoTelefonico() {
        return recapitoTelefonico;
    }

    public void setRecapitoTelefonico(String recapitoTelefonico) {
        this.recapitoTelefonico = recapitoTelefonico;
    }

    public String getCodiceIban() {
        return codiceIban;
    }

    public void setCodiceIban(String codiceIban) {
        this.codiceIban = codiceIban;
    }

    public static int getCaritasCode() {
        return CARITAS_CODE;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String toString() {
        return "CaritasUser{" +
                "id=" + id +
                ", nomeCaritas='" + nomeCaritas + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", indirizzoCaritas='" + indirizzoCaritas + '\'' +
                ", civico='" + civico + '\'' +
                ", citta='" + citta + '\'' +
                ", cap='" + cap + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", recapitoTelefonico='" + recapitoTelefonico + '\'' +
                '}';
    }
}
