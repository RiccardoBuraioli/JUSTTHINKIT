package entity;

public class ShopUser {

    private int id;
    private String nomeShop;
    private String password;
    private String indirizzoShop;
    private String tipologia;
    private String recapitoTelefonico;
    private String email;


    public ShopUser() {
    }

    public ShopUser(String nomeShop, String password, String indirizzoShop, String tipologia, String recapitoTelefonico, String email) {
        this.nomeShop = nomeShop;
        this.password = password;
        this.indirizzoShop = indirizzoShop;
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

    public String getNomeShop() {
        return nomeShop;
    }

    public void setNomeShop(String nomeShop) {
        this.nomeShop = nomeShop;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndirizzoShop() {
        return indirizzoShop;
    }

    public void setIndirizzoShop(String indirizzoShop) {
        this.indirizzoShop = indirizzoShop;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ShopUser{" +
                "nomeShop='" + nomeShop + '\'' +
                ", indirizzoShop='" + indirizzoShop + '\'' +
                ", tipo='" + tipologia + '\'' +
                ", recapito='" + recapitoTelefonico + '\'' +
                '}';
    }
}
