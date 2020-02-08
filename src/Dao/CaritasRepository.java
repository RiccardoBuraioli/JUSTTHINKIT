package Dao;

import entity.CaritasUser;
import Dao.RepositoryException;
import connector.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CaritasRepository {
    
    private static final Logger log = Logger.getLogger( CaritasRepository.class.getName() );
    private final Connector connector;
    private static final String SUCCESS = "Entry successfully modified!";
    private static final String FAILED = "ERR: Operation Failed!";

    private static final String NOME = "NomeCaritas";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";
    private static final String INDIRIZZO = "Indirizzo";
    private static final String CIVICO = "Civico";
    private static final String CITTA = "Citta";
    private static final String CAP = "Cap";
    private static final String TIPOLOGIA = "Tipologia";
    private static final String RECAPITO_TEL = "RecapitoTel";
    private static final String CODICE_IBAN = "CodiceIban";

    public CaritasRepository(Connector connector) {
        this.connector = connector;
    }

    public int insertCaritas(CaritasUser caritasUser) {
        Services services = new Services(connector);
        ResultSet resultSet = null;
        if (services.checkIfBanned(caritasUser.getEmail())) {
            log.warning("\t ***** THIS ACCOUNT HAS BEEN BANNED *****");
            return -1;
        }else {
            int caritasID = 0;

            String sql = "INSERT INTO caritas(NomeCaritas, Email, Password, Indirizzo, Civico, Citta, Cap, Tipologia, RecapitoTel, CodiceIban) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, caritasUser.getNomeCaritas());
                pstmt.setString(2, caritasUser.getEmail());
                pstmt.setString(3, caritasUser.getPassword());
                pstmt.setString(4, caritasUser.getIndirizzoCaritas());
                pstmt.setString(5, caritasUser.getCivico());
                pstmt.setString(6, caritasUser.getCitta());
                pstmt.setString(7, caritasUser.getCap());
                pstmt.setString(8, caritasUser.getTipologia());
                pstmt.setString(9, caritasUser.getRecapitoTelefonico());
                pstmt.setString(10, caritasUser.getCodiceIban());

                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {

                    resultSet = pstmt.getGeneratedKeys();
                    if (resultSet.next())
                        caritasID = resultSet.getInt(1);
                }
            } catch (SQLException ex) {
                throw new RepositoryException("Error Inserting User", ex);
            } finally {
                DbUtils.resultSetClosing(resultSet);
            }
            return caritasID;
        }
    }

    //FOR TAB UTENTI
    public void insertCaritas2(CaritasUser caritasUser) {
        Services services = new Services(connector);

        if (services.checkIfBanned(caritasUser.getEmail())) log.warning("\t ***** THIS ACCOUNT HAS BEEN BANNED *****");
        else {
            String sql = "INSERT INTO Utenti(ID, Email, Password, UserCode) VALUES(?, ?, ?, ?)";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, caritasUser.getID());
                pstmt.setString(2, caritasUser.getEmail());
                pstmt.setString(3, caritasUser.getPassword());
                pstmt.setInt(4, CaritasUser.getCaritasCode());


                int rowAffected = pstmt.executeUpdate();
                if (rowAffected != 1) log.warning(FAILED);

            } catch (SQLException ex) {
                throw new RepositoryException("Error Inserting User", ex);
            }
        }
    }



    public List<CaritasUser> getAllCaritas() {
        List<CaritasUser> carUsrs = new ArrayList<>();

        String sql = "SELECT * FROM caritas";

        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {

                String nomeCaritas = resultSet.getString(NOME);
                String email = resultSet.getString(EMAIL);
                String password = resultSet.getString(PASSWORD);
                String indirizzoCaritas = resultSet.getString(INDIRIZZO);
                String civico = resultSet.getString(CIVICO);
                String citta = resultSet.getString(CITTA);
                String cap = resultSet.getString(CAP);
                String tipologia = resultSet.getString(TIPOLOGIA);
                String recapitoTel = resultSet.getString(RECAPITO_TEL);
                String codiceIban = resultSet.getString(CODICE_IBAN);

                CaritasUser carUsr = new CaritasUser();
                carUsr.setNomeCaritas(nomeCaritas);
                carUsr.setEmail(email);
                carUsr.setPassword(password);
                carUsr.setIndirizzoCaritas(indirizzoCaritas);
                carUsr.setCivico(civico);
                carUsr.setCitta(citta);
                carUsr.setCap(cap);
                carUsr.setTipologia(tipologia);
                carUsr.setRecapitoTelefonico(recapitoTel);
                carUsr.setCodiceIban(codiceIban);
                carUsr.setId(resultSet.getInt("ID"));

                carUsrs.add(carUsr);
            }
            return carUsrs;
        } catch (SQLException ex) {
            throw new RepositoryException("Error Fetching Users", ex);
        }
    }


    public CaritasUser getCaritasByID(int id) {

        String sql = "SELECT ID, NomeCaritas, Email, Password, Indirizzo, Civico, Citta, Cap, Tipologia, RecapitoTel, CodiceIban  FROM caritas WHERE ID=?";
        ResultSet resultSet = null;
        CaritasUser carUsr = new CaritasUser();

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                carUsr.setNomeCaritas(resultSet.getString(NOME));
                carUsr.setEmail(resultSet.getString(EMAIL));
                carUsr.setPassword(resultSet.getString(PASSWORD));
                carUsr.setIndirizzoCaritas(resultSet.getString(INDIRIZZO));
                carUsr.setCivico(resultSet.getString(CIVICO));
                carUsr.setCitta(resultSet.getString(CITTA));
                carUsr.setCap(CAP);
                carUsr.setTipologia(resultSet.getString(TIPOLOGIA));
                carUsr.setRecapitoTelefonico(resultSet.getString(RECAPITO_TEL));
                carUsr.setCodiceIban(resultSet.getString(CODICE_IBAN));

            }

        } catch (SQLException ex) {
            throw new RepositoryException("Error Fetching User", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet);
            }
        return carUsr;
    }

    public CaritasUser getCaritasByEmail(String email) {

        String sql = "SELECT ID, NomeCaritas, Email, Password, Indirizzo, Civico, Citta, Cap, Tipologia, RecapitoTel, CodiceIban FROM caritas WHERE Email=?";
        ResultSet resultSet = null;
        CaritasUser caritasUser = new CaritasUser();

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();


            while (resultSet.next()) {

                caritasUser.setNomeCaritas(resultSet.getString(NOME));
                caritasUser.setEmail(resultSet.getString(EMAIL));
                caritasUser.setPassword(resultSet.getString(PASSWORD));
                caritasUser.setIndirizzoCaritas(resultSet.getString(INDIRIZZO));
                caritasUser.setCivico(CIVICO);
                caritasUser.setCitta(CITTA);
                caritasUser.setCap((CAP));
                caritasUser.setTipologia(resultSet.getString(TIPOLOGIA));
                caritasUser.setRecapitoTelefonico(resultSet.getString(RECAPITO_TEL));
                caritasUser.setRecapitoTelefonico(resultSet.getString(CODICE_IBAN));
            }

        } catch (SQLException ex) {
            throw new RepositoryException("Error Fetching User", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet
            );
        }
        return caritasUser;
    }

    private String getPassword(CaritasUser carUser){
        String sql = "SELECT Password FROM caritas WHERE ID = ?";
        ResultSet resultSet = null;
        String password = "";

        try(Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, carUser.getID());
            resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                password = resultSet.getString(PASSWORD);
            }
        } catch (SQLException ex){
            throw new RepositoryException("Request Error", ex);
        }finally {
            DbUtils.resultSetClosing(resultSet);
        }
        if (password.equals("")) return "";
        else return password;
    }


    public void updatePassword(CaritasUser carUser, String newPass, String oldPassword){
        String actPassword = getPassword(carUser);
        String sql = "UPDATE caritas SET Password = ? WHERE ID = ?";
        int rowAffected;

        if(actPassword.equals(oldPassword)) {
            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newPass);
                pstmt.setInt(2, carUser.getID());
                rowAffected = pstmt.executeUpdate();

                if (rowAffected == 1) {
                    log.info(SUCCESS);
                } else log.info(FAILED);


            } catch (SQLException ex) {
                throw new RepositoryException("Error Updating", ex);
            }
        } else log.warning(FAILED);
    }



    public void updateCarName(CaritasUser carUser, String carName) {
        String sql = "UPDATE caritas SET NomeCaritas = ? WHERE ID= ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carName);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateType(CaritasUser carUser, String type) {
        String sql = "UPDATE caritas SET Tipologia = ? WHERE ID = ?";
        int rowAffected;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateEmail(CaritasUser carUser, String email) {
        String sql = "UPDATE caritas SET Email = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updatePhoneNum(CaritasUser carUser, String phoneNum) {
        String sql = "UPDATE caritas SET RecapitoTel = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNum);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateAddress(CaritasUser carUser, String address) {
        String sql = "UPDATE caritas SET Indirizzo = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateCivico(CaritasUser carUser, String newCivico) {
        String sql = "UPDATE caritas SET Civico = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCivico);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateCitta(CaritasUser carUser, String newCitta) {
        String sql = "UPDATE caritas SET Citta = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCitta);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateCap(CaritasUser carUser, String newCap) {
        String sql = "UPDATE caritas SET Cap = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCap);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void updateIban(CaritasUser carUser, String newIban) {
        String sql = "UPDATE caritas SET CodiceIban = ? WHERE ID = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newIban);
            stmt.setInt(2, carUser.getID());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating", ex);
        }
    }

    public void deleteCaritas(CaritasUser carUser) {
        String sql = "DELETE FROM caritas where ID=?";
        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carUser.getID());
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) log.info("Caritas ID" + carUser.getID()+ " successfully deleted!");
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Delete", ex);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM caritas";
        int delRecs;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** Caritas entries successfully cleaned! *****");
            resetID();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Deleting All", ex);
        }
    }

    private void resetID() {
        String sql = "ALTER TABLE caritas AUTO_INCREMENT = 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            log.info("\t ***** ID Values resetted successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error Resetting ID", ex);
        }
    }


    public void printCarsInTab(List<CaritasUser> caritasUsers){
        String s1 = "ID";
        String s2 = "Nome Caritas";
        String s3 = "Email";
        String s4 = "Password";
        String s5 = "Indirizzo Caritas";
        String s6 = "Civico";
        String s7 = "Citta";
        String s8 =  "CAP";
        String s9 = "Tipologia";
        String s10 = "RecapitoTel";
        String s11 = "Codice  Iban";


        System.out.printf("%n %-10s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11);
        for(CaritasUser car:caritasUsers){
            System.out.printf("%n %-10s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", car.getID(), car.getNomeCaritas(), car.getEmail(),
                    car.getPassword(), car.getIndirizzoCaritas(), car.getCivico(), car.getCitta(), car.getCap(),
                    car.getTipologia(), car.getRecapitoTelefonico(), car.getCodiceIban());
        }
    }

}
