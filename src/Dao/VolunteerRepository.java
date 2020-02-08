package Dao;

import Dao.RepositoryException;
import connector.Connector;
import entity.VolunteerUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//CRUD create read update delete

public class VolunteerRepository {


    private static final Logger log = Logger.getLogger( VolunteerRepository.class.getName() );
    private final Connector connector;
    private static final String SUCCESS = "Entry successfully modified!";
    private static final String FAILED = "ERR: Operation Failed!";
    private static final String NOME = "Nome";
    private static final String COGNOME = "Cognome";
    private static final String INDIRIZZO = "Indirizzo";
    private static final String CIVICO = "Civico";
    private static final String CITTA = "Citta";
    private static final String CAP = "Cap";
    private static final String EMAIL = "Email";
    private static final String RECAPITO_TEL = "RecapitoTel";
    private static final String PASSWORD = "Password";
    private static final String CARTA_DI_CREDITO = "CartaDiCredito";


    public VolunteerRepository(Connector connector) {
        this.connector = connector;
    }

    public List<VolunteerUser> getAllVolunteers() {

        List<VolunteerUser> vUsers = new ArrayList<>();

        String sql = "SELECT * FROM volontari";

        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {

                String nome = resultSet.getString(NOME);
                String cognome = resultSet.getString(COGNOME);
                String indirizzo = resultSet.getString(INDIRIZZO);
                String civico = resultSet.getString(CIVICO);
                String citta = resultSet.getString(CITTA);
                String cap = resultSet.getString(CAP);
                String email = resultSet.getString(EMAIL);
                String password = resultSet.getString(PASSWORD);
                String recapitoTel = resultSet.getString(RECAPITO_TEL);
                String cartaDiCredito = resultSet.getString(CARTA_DI_CREDITO);

                VolunteerUser vUser = new VolunteerUser();
                vUser.setNome(nome);
                vUser.setCognome(cognome);
                vUser.setPassword(password);
                vUser.setIndirizzoVolontario(indirizzo);
                vUser.setCivico(civico);
                vUser.setCitta(citta);
                vUser.setCap(cap);
                vUser.setRecapitoTel(recapitoTel);
                vUser.setEmail(email);
                vUser.setCartaDiCredito(cartaDiCredito);
                vUser.setId(resultSet.getInt("ID"));

                vUsers.add(vUser);
            }
            return vUsers;
        } catch (SQLException ex){
            throw new RepositoryException("Fetching User Failed", ex);
        }
    }


    public VolunteerUser getVolunteerByID(int id) {

        String sql = "SELECT ID, Nome, Cognome, Password, Indirizzo, Civico, Citta, Cap, RecapitoTel, Email, CartaDiCredito, DataNascita FROM volontari WHERE ID=?";
        VolunteerUser vUser = new VolunteerUser();
        ResultSet res = null;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            res = stmt.executeQuery();

            while (res.next()) {

                vUser.setNome(res.getString(NOME));
                vUser.setCognome(res.getString(COGNOME));
                vUser.setPassword(res.getString(PASSWORD));
                vUser.setIndirizzoVolontario(res.getString(INDIRIZZO));
                vUser.setCivico(CIVICO);
                vUser.setCitta(CITTA);
                vUser.setCap((CAP));
                vUser.setEmail(res.getString(EMAIL));
                vUser.setRecapitoTel(res.getString(RECAPITO_TEL));
                vUser.setCartaDiCredito(res.getString(CARTA_DI_CREDITO));
                vUser.setDataNascita(res.getDate("DataNascita"));
                vUser.setId(id);
            }

        } catch (SQLException ex) {
            throw new RepositoryException("Fetch Error", ex);
        } finally {
            DbUtils.resultSetClosing(res);
        } return vUser;
    }

    public VolunteerUser getVolunteerByEmail(String email) {

        String sql = "SELECT ID, Nome, Cognome, Password, Indirizzo, Civico, Citta, Cap, RecapitoTel, Email, CartaDiCredito FROM volontari WHERE Email=?";
        ResultSet resultSet = null;
        VolunteerUser vUser = new VolunteerUser();

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();


            while (resultSet.next()) {

                vUser.setNome(resultSet.getString(NOME));
                vUser.setCognome(resultSet.getString(COGNOME));
                vUser.setPassword(resultSet.getString(PASSWORD));
                vUser.setIndirizzoVolontario(resultSet.getString(INDIRIZZO));
                vUser.setCivico(CIVICO);
                vUser.setCitta(CITTA);
                vUser.setCap((CAP));
                vUser.setEmail(resultSet.getString(EMAIL));
                vUser.setRecapitoTel(resultSet.getString(RECAPITO_TEL));
                vUser.setCartaDiCredito(resultSet.getString(CARTA_DI_CREDITO));
                vUser.setId(resultSet.getInt(1));
            }

        } catch (SQLException ex) {
            throw new RepositoryException("Error Fetching User", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet
            );
        }
         return vUser;
    }



    public int insertVolunteer(VolunteerUser volunteerUser) {
        Services services = new Services(connector);
        ResultSet resultSet = null;
        int volunteerID = 0;
        if (services.checkIfBanned(volunteerUser.getEmail())){
            log.warning("\t ***** THIS ACCOUNT HAS BEEN BANNED *****");
            return -1;
        }else {

            String sql = "INSERT INTO volontari(Nome,Cognome, Password, Indirizzo, Civico, Citta, Cap, RecapitoTel, Email, CartaDiCredito, DataNascita) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, volunteerUser.getNome());
                pstmt.setString(2, volunteerUser.getCognome());
                pstmt.setString(3, volunteerUser.getPassword());
                pstmt.setString(4, volunteerUser.getIndirizzoVolontario());
                pstmt.setString(5, volunteerUser.getCivico());
                pstmt.setString(6, volunteerUser.getCitta());
                pstmt.setString(7, volunteerUser.getCap());
                pstmt.setString(8, volunteerUser.getRecapitoTel());
                pstmt.setString(9, volunteerUser.getEmail());
                pstmt.setString(10, volunteerUser.getCartaDiCredito());
                pstmt.setDate(11, volunteerUser.getDataNascita());


                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {

                    resultSet = pstmt.getGeneratedKeys();
                    if (resultSet.next())
                        volunteerID = resultSet.getInt(1);
                }
            } catch (SQLException ex) {
                 throw new RepositoryException("Error Inserting User", ex);
            } finally {
                DbUtils.resultSetClosing(resultSet);
            }
            volunteerUser.setId(volunteerID);
            return volunteerID;
        }
    }

    //For TAB Utenti
    public void insertVolunteer2(VolunteerUser volunteerUser) {
        Services services = new Services(connector);

        if (services.checkIfBanned(volunteerUser.getEmail())) {
            log.warning("\t ***** THIS ACCOUNT HAS BEEN BANNED *****");
        } else {

            String sql = "INSERT INTO Utenti(ID, Email, Password, Codice) VALUES(?, ?, ?, ?)";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, volunteerUser.getId());
                pstmt.setString(2, volunteerUser.getEmail());
                pstmt.setString(3, volunteerUser.getPassword());
                pstmt.setString(4, VolunteerUser.getTableName());


                int rowAffected = pstmt.executeUpdate();
                if ((rowAffected != 1)) log.warning(FAILED);

            } catch (SQLException ex) {
                throw new RepositoryException("Error Inserting User", ex);
            }
        }
    }

    private String getPassword(VolunteerUser vUser){
        String sql = "SELECT Password FROM volontari WHERE ID = ?";
        ResultSet res = null;
        String password = "";

        try(Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, vUser.getId());
            res = pstmt.executeQuery();

            while (res.next()){
                password = res.getString(VolunteerRepository.PASSWORD);

            }
        } catch (SQLException ex){
            throw new RepositoryException("Error Fetching User", ex);
        }finally {
            DbUtils.resultSetClosing(res);
        }

        if(password.equals("")) return "";
        else return password;
    }


    public void updatePassword(VolunteerUser vUser, String newPass, String oldPassword){
        String actPassword = getPassword(vUser);

        String sql = "UPDATE volontari SET Password = ? WHERE ID = ?";
        int rowAffected;

        if (actPassword.equals(oldPassword)) {
            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newPass);
                pstmt.setInt(2, vUser.getId());
                rowAffected = pstmt.executeUpdate();

                if (rowAffected == 1) log.info(SUCCESS);
                else log.warning(FAILED);


            } catch (SQLException ex) {
               throw new RepositoryException("Error Updating Password", ex);
            }
        } else log.warning(FAILED);

    }

//    hai dimenticato la password?
//    public void resetPassword(VolunteerUser vUser)
//    API  per mandare email automatizzate?. . .


    public void updateFirstName(VolunteerUser volunteerUser, String firstName) {
        String sql = "UPDATE volontari SET Nome = ? WHERE ID= ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                log.info(SUCCESS);
            }else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating FirstName", ex);
        }
    }

    public void updateLastName(VolunteerUser volunteerUser, String lastName) {
        String sql = "UPDATE volontari SET Cognome = ? WHERE ID = ?";
        int rowAffected;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lastName);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating LastName", ex);
        }
    }

    public void updateEmail(VolunteerUser volunteerUser, String email) {
        String sql = "UPDATE volontari SET Email = ? WHERE id = ?";
        Services util = new Services(connector);

        if (util.checkEmailBoolean(email)) log.info("\t ***** GIVEN EMAIL IS ALREADY USED *****");
        else {
            int rowAffected;
            try (Connection conn = connector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, email);
                stmt.setInt(2, volunteerUser.getId());
                rowAffected = stmt.executeUpdate();

                if (rowAffected == 1) log.info(SUCCESS);
                else log.warning(FAILED);


            } catch (SQLException ex) {
                throw new RepositoryException("Error Updating Email", ex);
            }
        }
    }

    public void updatePhoneNum(VolunteerUser volunteerUser, String phoneNum) {
        String sql = "UPDATE volontari SET RecapitoTel = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNum);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Phone Num", ex);
        }
    }

    public void updateCity(VolunteerUser volunteerUser, String newCity) {
        String sql = "UPDATE volontari SET Citta = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCity);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating City", ex);
        }
    }

    public void updateCivico(VolunteerUser volunteerUser, String newCiv) {
        String sql = "UPDATE volontari SET Civico = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCiv);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Civ", ex);
        }
    }

    public void updateCap(VolunteerUser volunteerUser, String newCap) {
        String sql = "UPDATE volontari SET Cap = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCap);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);


        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating CAP", ex);
        }
    }

    public void updateAddress(VolunteerUser volunteerUser, String address) {
        String sql = "UPDATE volontari SET Indirizzo = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.setInt(2, volunteerUser.getId());
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) log.info(SUCCESS);
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Address", ex);
        }
    }


    public void deleteVolunteer(VolunteerUser volunteerUser) {
        String sql = "DELETE FROM volontari where ID=?";
        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, volunteerUser.getId());
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) log.info("Volunteer ID" + volunteerUser.getId() + "successfully deleted!");
            else log.warning(FAILED);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Deleting User", ex);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM volontari";
        int delRecs;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** Volunteer entries successfully cleaned! *****");
            resetID();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Delete All", ex);
        }
    }

    private void resetID() {
        String sql = "ALTER TABLE volontari AUTO_INCREMENT = 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            log.info("\t ***** ID Values resetted successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error ResetID", ex);
        }
    }

//carta di credito?


    public void printVolInTab(List<VolunteerUser> vUsers){
        String s1 = "ID";
        String s2 = "Nome";
        String s3 = "Cognome";
        String s4 = "Indirizzo";
        String s5 = "Civico";
        String s6 = "Citta";
        String s7 = "CAP";
        String s8 =  "Email";
        String s9 = "Password";
        String s10 = "Recapito Tel";
        String s11 = "Carta Di Credito";


        System.out.printf("%n %-10s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11);
        for(VolunteerUser vUsr:vUsers){
            System.out.printf("%n %-10s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s", vUsr.getId(), vUsr.getNome(), vUsr.getCognome(),
                    vUsr.getIndirizzoVolontario(), vUsr.getCivico(), vUsr.getCitta(), vUsr.getCap(), vUsr.getEmail(),
                    vUsr.getPassword(), vUsr.getRecapitoTel(), vUsr.getCartaDiCredito());
        }
    }



}
