package entity;

public class ShopUser {

    private int id;
    private String nomeShop;
    private String email;
    private String password;
    private String indirizzoShop;
    private String civico;
    private String citta;
    private String cap;
    private String tipologia;
    private String recapitoTelefonico;

    private static final int SHOP_CODE = 3;
    private static final String TABLE_NAME = "negozi";

    public ShopUser() {
        //Costruttore Vuoto, troppi attributi da istanziare
    }


    public int getID() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) this.id = id;
    }

    public String getNomeShop() {
        return nomeShop;
    }

    public void setNomeShop(String nomeShop) {
        this.nomeShop = nomeShop;
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

    public String getIndirizzoShop() {
        return indirizzoShop;
    }

    public void setIndirizzoShop(String indirizzoShop) {
        this.indirizzoShop = indirizzoShop;
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

    public static int getShopCode() {
        return SHOP_CODE;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String toString() {
        return "ShopUser{" +
                "id=" + id +
                ", nomeShop='" + nomeShop + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", indirizzoShop='" + indirizzoShop + '\'' +
                ", civico='" + civico + '\'' +
                ", citta='" + citta + '\'' +
                ", cap='" + cap + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", recapitoTelefonico='" + recapitoTelefonico + '\'' +
                '}';
    }
}
