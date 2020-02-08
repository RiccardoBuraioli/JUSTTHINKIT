package entity;

import java.sql.Date;

public class VolunteerUser {

    private int id;
    private String nome;
    private String cognome;
    private String password;
    private String indirizzoVolontario;
    private String civico;
    private String citta;
    private String cap;
    private String recapitoTel;
    private String email;
    private String cartaDiCredito;
    private String profileImage;
    private Date dataNascita;
    
    
	public Date getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	private static final int VOLUNTEER_CODE = 1;
    private static final String TABLE_NAME = "Volontario";

    public VolunteerUser() {
        //Costruttore vuoto, troppi attributi da istanziare
    }


    
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndirizzoVolontario() {
        return indirizzoVolontario;
    }

    public void setIndirizzoVolontario(String indirizzoVolontario) {
        this.indirizzoVolontario = indirizzoVolontario;
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

    public String getRecapitoTel() {
        return recapitoTel;
    }

    public void setRecapitoTel(String recapitoTel) {
        this.recapitoTel = recapitoTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCartaDiCredito() {
        return cartaDiCredito;
    }

    public void setCartaDiCredito(String cartaDiCredito) {
        this.cartaDiCredito = cartaDiCredito;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public static int getVolunteerCode() {
        return VOLUNTEER_CODE;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String toString() {
        return "VolunteerUser{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + password + '\'' +
                ", indirizzo='" + indirizzoVolontario + '\'' +
                ", civico='" + civico + '\'' +
                ", citta='" + citta + '\'' +
                ", cap='" + cap + '\'' +
                ", recapitoTel='" + recapitoTel + '\'' +
                ", email='" + email + '\'' +
                ", cartaDiCredito='" + cartaDiCredito + '\'' +
                '}';
    }
}
