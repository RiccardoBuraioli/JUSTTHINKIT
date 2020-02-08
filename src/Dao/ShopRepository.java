package Dao;

import entity.CaritasUser;
import Dao.RepositoryException;
import connector.Connector;
import entity.ShopUser;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ShopRepository {

    private static final Logger log = Logger.getLogger( ShopRepository.class.getName() );
    private final Connector connector;
    private static final String SUCCESS = "Entry successfully modified!";
    private static final String FAILED = "ERR: Operation Failed!";

    private static final String NOME = "NomeNegozio";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";
    private static final String INDIRIZZO = "Indirizzo";
    private static final String CIVICO = "Civico";
    private static final String CITTA = "Citta";
    private static final String CAP = "Cap";
    private static final String TIPOLOGIA = "Tipologia";
    private static final String RECAPITO_TEL = "RecapitoTel";

    public ShopRepository(Connector connector) {
        this.connector = connector;
    }



    public int insertShop(ShopUser shopUser) {
        Services services = new Services(connector);
        ResultSet resultSet = null;
        if (services.checkIfBanned(shopUser.getEmail())) {
            log.warning("\t ***** THIS ACCOUNT HAS BEEN BANNED *****");
            return -1;
        }else {
            int shopID = 0;

            String sql = "INSERT INTO negozi(NomeNegozio, Email, Password, Indirizzo, Civico, Citta, Cap, Tipologia, RecapitoTel) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, shopUser.getNomeShop());
                pstmt.setString(2, shopUser.getEmail());
                pstmt.setString(3, shopUser.getPassword());
                pstmt.setString(4, shopUser.getIndirizzoShop());
                pstmt.setString(5, shopUser.getCivico());
                pstmt.setString(6, shopUser.getCitta());
                pstmt.setString(7, shopUser.getCap());
                pstmt.setString(8, shopUser.getTipologia());
                pstmt.setString(9, shopUser.getRecapitoTelefonico());

                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {

                    resultSet = pstmt.getGeneratedKeys();
                    if (resultSet.next())
                        shopID = resultSet.getInt(1);
                }
            } catch (SQLException ex) {
                throw new RepositoryException("Error Inserting User", ex);
            } finally {
                DbUtils.resultSetClosing(resultSet);
            }
            return shopID;
        }
    }

    //FOR TAB UTENTI
    public void insertShop2(ShopUser shopUser) {
        Services services = new Services(connector);

        if (services.checkIfBanned(shopUser.getEmail())) log.warning("\t ***** THIS ACCOUNT HAS BEEN BANNED *****");
        else {
            String sql = "INSERT INTO Utenti(ID, Email, Password, UserCode) VALUES(?, ?, ?, ?)";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, shopUser.getID());
                pstmt.setString(2, shopUser.getEmail());
                pstmt.setString(3, shopUser.getPassword());
                pstmt.setInt(4, CaritasUser.getCaritasCode());


                int rowAffected = pstmt.executeUpdate();
                if (rowAffected != 1) log.warning(FAILED);

            } catch (SQLException ex) {
                throw new RepositoryException("Error Inserting User", ex);
            }
        }
    }

    private String getPassword(ShopUser shopUser){
        String sql = "SELECT Password FROM negozi WHERE ID = ?";
        ResultSet resultSet = null;
        String password = "";

        try(Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, shopUser.getID());
            resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                password = resultSet.getString(PASSWORD);
            }
        } catch (SQLException ex){
             throw new RepositoryException("Error Updating", ex);
        }finally {
            DbUtils.resultSetClosing(resultSet);
        }
        if(password.equals("")) return "";
        else return password;
    }


    public void updatePassword(ShopUser shopUser, String newPass, String oldPassword){
        String actPassword = getPassword(shopUser);
        String sql = "UPDATE negozi SET Password = ? WHERE ID = ?";
        int rowAffected;

        if (actPassword.equals(oldPassword)) {
            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newPass);
                pstmt.setInt(2, shopUser.getID());
                rowAffected = pstmt.executeUpdate();

                if (rowAffected == 1) {
                    log.info(SUCCESS);
                } else log.warning(FAILED);

            } catch (SQLException ex) {
                throw new RepositoryException("Error Updating Password", ex);
            }
        } else log.warning(FAILED);

    }

    public List<ShopUser> getAllShops() {
        List<ShopUser> shopUsers = new ArrayList<>();

        String sql = "SELECT * FROM negozi";

        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {

                String nomeShop = resultSet.getString(NOME);
                String email = resultSet.getString(EMAIL);
                String password = resultSet.getString(PASSWORD);
                String indirizzoNegozio = resultSet.getString(INDIRIZZO);
                String civico = resultSet.getString(CIVICO);
                String citta = resultSet.getString(CITTA);
                String cap = resultSet.getString(CAP);
                String tipologia = resultSet.getString(TIPOLOGIA);
                String recapitoTel = resultSet.getString(RECAPITO_TEL);


                ShopUser shopUser = new ShopUser();

                shopUser.setNomeShop(nomeShop);
                shopUser.setEmail(email);
                shopUser.setPassword(password);
                shopUser.setIndirizzoShop(indirizzoNegozio);
                shopUser.setCivico(civico);
                shopUser.setCitta(citta);
                shopUser.setCap(cap);
                shopUser.setTipologia(tipologia);
                shopUser.setRecapitoTelefonico(recapitoTel);
                shopUser.setId(resultSet.getInt("ID"));

                shopUsers.add(shopUser);
            }

        } catch (SQLException ex) {
            throw new RepositoryException("Error Fetching Users", ex);
        }
        return shopUsers;
    }


    public ShopUser getShopByID(int id) {

        String sql = "SELECT ID, NomeNegozio, Email, Password, Indirizzo, Civico, Citta, Cap, Tipologia, RecapitoTel FROM negozi WHERE ID=?";
        ResultSet resultSet = null;
        ShopUser shopUser = new ShopUser();

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                shopUser.setNomeShop(resultSet.getString(NOME));
                shopUser.setEmail(resultSet.getString(EMAIL));
                shopUser.setPassword(resultSet.getString(PASSWORD));
                shopUser.setIndirizzoShop(resultSet.getString(INDIRIZZO));
                shopUser.setCivico(resultSet.getString(CIVICO));
                shopUser.setCitta(resultSet.getString(CITTA));
                shopUser.setCap(resultSet.getString(CAP));
                shopUser.setTipologia(resultSet.getString(TIPOLOGIA));
                shopUser.setRecapitoTelefonico(resultSet.getString(RECAPITO_TEL));
            }

        } catch (SQLException ex) {
              throw new RepositoryException("Error Fetching User", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet);
        } return shopUser;
    }

    public ShopUser getShopByEmail(String email) {

        String sql = "SELECT ID, NomeNegozio, Email, Password, Indirizzo, Civico, Citta, Cap, Tipologia, RecapitoTel FROM negozi WHERE Email=?";
        ResultSet resultSet = null;
        ShopUser shopUser = new ShopUser();

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();


            while (resultSet.next()) {

                shopUser.setNomeShop(resultSet.getString(NOME));
                shopUser.setEmail(resultSet.getString(EMAIL));
                shopUser.setPassword(resultSet.getString(PASSWORD));
                shopUser.setIndirizzoShop(resultSet.getString(INDIRIZZO));
                shopUser.setCivico(CIVICO);
                shopUser.setCitta(CITTA);
                shopUser.setCap((CAP));
                shopUser.setTipologia(resultSet.getString(TIPOLOGIA));
                shopUser.setRecapitoTelefonico(resultSet.getString(RECAPITO_TEL));
            }

        } catch (SQLException ex) {
            throw new RepositoryException("Error Fetching User", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet
            );
        }
        return shopUser;
    }


    public void updateShopName(ShopUser shopUser, String shopName) {
        String sql = "UPDATE negozi SET NomeNegozio = ? WHERE ID= ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shopName);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                log.info(SUCCESS);
            }else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Name", ex);
        }
    }

    public void updateType(ShopUser shopUser, String type) {
        String sql = "UPDATE negozi SET Tipologia = ? WHERE ID = ?";
        int rowAffected;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Type", ex);
        }
    }

    public void updateEmail(ShopUser shopUser, String email) {
        String sql = "UPDATE negozi SET Email = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Email", ex);
        }
    }

    public void updatePhoneNum(ShopUser shopUser, String phoneNum) {
        String sql = "UPDATE negozi SET RecapitoTel = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNum);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating PhoneNum", ex);
        }
    }

    public void updateAddress(ShopUser shopUser, String address) {
        String sql = "UPDATE negozi SET Indirizzo = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Address", ex);
        }
    }

    public void updateCivico(ShopUser shopUser, String newCivico) {
        String sql = "UPDATE negozi SET Civico = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCivico);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Civic", ex);
        }
    }

    public void updateCitta(ShopUser shopUser, String newCitta) {
        String sql = "UPDATE negozi SET Citta = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCitta);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating City", ex);
        }
    }

    public void updateCap(ShopUser shopUser, String newCap) {
        String sql = "UPDATE negozi SET Cap = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCap);
            stmt.setInt(2, shopUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Address", ex);
        }
    }

    public void deleteCaritas(ShopUser shopUser) {
        String sql = "DELETE FROM negozi where ID=?";
        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shopUser.getID());
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) log.info("Shop ID" + shopUser.getID() +" successfully deleted!");

            else log.info("ID not found!");

        } catch (SQLException ex) {
            throw new RepositoryException("Error Delete", ex);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM negozi";
        int delRecs;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** Shop entries successfully cleaned! *****");
            resetID();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Deleting All", ex);
        }
    }

    private void resetID() {
        String sql = "ALTER TABLE negozi AUTO_INCREMENT = 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            log.info("\t ***** ID Values resetted successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error Resetting ID");
        }
    }

    public void printShopsInTab(List<ShopUser> shopUsers) {
        String s1 = "ID";
        String s2 = "Nome Negozio";
        String s3 = "Email";
        String s4 = "Password";
        String s5 = "Indirizzo";
        String s6 = "Civico";
        String s7 = "Citta";
        String s8 = "CAP";
        String s9 = "Tipologia";
        String s10 = "RecapitoTel";


        System.out.printf("%n %-10s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
        for (ShopUser shopUser : shopUsers) {
            System.out.printf("%n %-10s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", shopUser.getID(), shopUser.getNomeShop(), shopUser.getEmail(),
                    shopUser.getPassword(), shopUser.getIndirizzoShop(), shopUser.getCivico(), shopUser.getCitta(), shopUser.getCap(),
                    shopUser.getTipologia(), shopUser.getRecapitoTelefonico());
        }
    }
}
