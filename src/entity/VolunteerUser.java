package entity;


public class VolunteerUser {

    private int id;
    private String nome;
    private String cognome;
    private String password;
    private String indirizzo;
    private String recapitoTel;
    private String email;
    private String cartaDiCredito;

    public VolunteerUser() {
    }

    public VolunteerUser(String nome, String cognome, String password, String indirizzo, String recapitoTel, String email, String cartaDiCredito) {
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.indirizzo = indirizzo;
        this.recapitoTel = recapitoTel;
        this.email = email;
        this.cartaDiCredito = cartaDiCredito;
    }

    public int getID(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getRecapitoTel() {
        return recapitoTel;
    }

    public String getEmail() {
        return email;
    }

    public String getCartaDiCredito() {
        return cartaDiCredito;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setRecapitoTel(String recapitoTel) {
        this.recapitoTel = recapitoTel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCartaDiCredito(String cartaDiCredito) {
        this.cartaDiCredito = cartaDiCredito;
    }

    @Override
    public String toString() {
        return "VolunteerUser{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + password + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", recapitoTel='" + recapitoTel + '\'' +
                ", email='" + email + '\'' +
                ", cartaDiCredito='" + cartaDiCredito + '\'' +
                '}';
    }
}
