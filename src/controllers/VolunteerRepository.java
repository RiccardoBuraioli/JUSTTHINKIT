package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.VolunteerUser;

//CRUD create read update delete

public class VolunteerRepository {

    private final Connector connector;
    private static final String SUCCESS = "Voce modificata con successo!";
    private static final String FAILED = "Operazione non riuscita.";



    public VolunteerRepository(Connector connector) {
        this.connector = connector;
    }

    public List<VolunteerUser> getAllVolunteers() {
        List<VolunteerUser> vUsers = new ArrayList<>();

        String sql = "SELECT * FROM volontari";

        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String password = rs.getString("password");
                String indirizzo = rs.getString("indirizzo");
                String recapitoTel = rs.getString("recapitoTel");
                String email = rs.getString("email");
                String cartaDiCredito = rs.getString("cartaDiCredito");

                VolunteerUser vUser = new VolunteerUser(nome, cognome, password, indirizzo, recapitoTel, email, cartaDiCredito);
                vUser.setID(rs.getInt("ID"));
                vUsers.add(vUser);
            }
            return vUsers;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new IllegalStateException("error fetching users", ex);
        }
    }


    public VolunteerUser getVolunteerByID(int id) {

        String sql = "SELECT ID, Nome, Cognome, Password, Indirizzo, RecapitoTel, Email, CartaDiCredito FROM volontari WHERE ID=?";
        ResultSet rs = null;
        VolunteerUser vUser = new VolunteerUser();

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {

                vUser.setNome(rs.getString("Nome"));
                vUser.setCognome(rs.getString("Cognome"));
                vUser.setPassword(rs.getString("Password"));
                vUser.setIndirizzo(rs.getString("Indirizzo"));
                vUser.setEmail(rs.getString("Email"));
                vUser.setRecapitoTel(rs.getString("RecapitoTel"));
                vUser.setCartaDiCredito(rs.getString("CartaDiCredito"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } return vUser;
    }
    
//    public boolean login(String actEmail, String actPassword){
//        String sql = "SELECT Email AND Password FROM volontari WHERE Email = ? AND Password = ?";
//        ResultSet res = null;
//
//
//        try (Connection conn = connector.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, actEmail);
//            stmt.setString(2, actPassword);
//            res = stmt.executeQuery();
//            
//            //Modificato il 22/01
//            //res.first() ritorna true se c'è una riga nel risultato, false altrimenti
//            
//            if (res.first()){
//                return true;
//            }else return false;
//
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//         finally {
//            try {
//                if (res != null) res.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }return false;
//    }



    public int insertVolunteer(VolunteerUser volunteerUser) {

        ResultSet rs = null;
        int volunteerID = 0;

        String sql = "INSERT INTO volontari(Nome,Cognome, Password, Indirizzo, RecapitoTel, Email, CartaDiCredito) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, volunteerUser.getNome());
            pstmt.setString(2, volunteerUser.getCognome());
            pstmt.setString(3, volunteerUser.getPassword());
            pstmt.setString(4, volunteerUser.getIndirizzo());
            pstmt.setString(5, volunteerUser.getRecapitoTel());
            pstmt.setString(6, volunteerUser.getEmail());
            pstmt.setString(7, volunteerUser.getCartaDiCredito());


            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {

                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    volunteerID = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return volunteerID;
    }

    private String getPassword(VolunteerUser vUser){
        String sql = "SELECT Password FROM volontari WHERE ID = ?";
        ResultSet res = null;
        String password = "";

        try(Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, vUser.getID());
            res = pstmt.executeQuery();

            while (res.next()){
                password = res.getString("Password");
                System.out.println("QUESTA E' LA PASSWORD: " + password);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                if (res != null) res.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if(password.equals("")) {
            System.out.println("FALLISCE QUI? " + FAILED);
            return "";
        }
        else{
            System.out.println("LA GETPASSWORD FUNZIONA");
            return password;
        }

    }


    public void updatePassword(VolunteerUser vUser, String newPass, String oldPassword){
        String actPassword = getPassword(vUser);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAA " + actPassword);
        String sql = "UPDATE volontari SET Password = ? WHERE ID = ?";
        int rowAffected;

        if(actPassword.equals(oldPassword)) {
            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newPass);
                pstmt.setInt(2, vUser.getID());
                rowAffected = pstmt.executeUpdate();

                if (rowAffected == 1) {
                    System.out.println(SUCCESS);
                } else System.out.println(FAILED);


            } catch (SQLException ex) {
                System.out.println((ex.getMessage()));
            }
        }else System.out.println(FAILED);

    }

//    hai dimenticato la password?
//    public void resetPassword(VolunteerUser vUser){
//    API google per mandare email automatizzate?. . .
//    }


    public void updateFirstName(int id, String firstName) {
        String sql = "UPDATE volontari SET Nome = ? WHERE ID= ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setInt(2, id);
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                System.out.println(SUCCESS);
            }else System.out.println(FAILED);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateLastName(int id, String lastName) {
        String sql = "UPDATE volontari SET Cognome = ? WHERE ID = ?";
        int rowAffected;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lastName);
            stmt.setInt(2, id);
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                System.out.println(SUCCESS);
            }else System.out.println(FAILED);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateEmail(int id, String email) {
        String sql = "UPDATE volontari SET Email = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, id);
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                System.out.println(SUCCESS);
            }else System.out.println(FAILED);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updatePhoneNum(int id, String phoneNum) {
        String sql = "UPDATE volontari SET RecapitoTel = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNum);
            stmt.setInt(2, id);
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                System.out.println(SUCCESS);
            }else System.out.println(FAILED);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateAddress(int id, String address) {
        String sql = "UPDATE volontari SET Indirizzo = ? WHERE id = ?";
        int rowAffected;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.setInt(2, id);
            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) {
                System.out.println(SUCCESS);
            } else System.out.println(FAILED);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void deleteVolunteer(int id) {
        String sql = "DELETE FROM volontari where ID=?";
        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) System.out.println("Volontario ID " + id + ", rimosso con successo!");

            else System.out.println("ID non trovato.");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM volontari";
        int delRecs;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) System.out.println("\t ***** Volontari rimossi con successo! *****");
            resetID();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void resetID() {
        String sql = "ALTER TABLE volontari AUTO_INCREMENT = 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            System.out.println("\t ***** ID Values resetted successfully! *****");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//carta di credito? 


    public void printVolInTab(List<VolunteerUser> vUsers){
        String s1 = "ID";
        String s2 = "Nome";
        String s3 = "Cognome";
        String s4 = "Password";
        String s5 = "Indirizzo";
        String s6 = "RecapitoTel";
        String s7 =  "Email";
        String s8 = "CartaDiCredito";


        System.out.printf("%n %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", s1, s2, s3, s4, s5, s6, s7, s8);
        for(VolunteerUser vUsr:vUsers){
            System.out.printf("%-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %n", vUsr.getID(), vUsr.getNome(), vUsr.getCognome(),
                    vUsr.getPassword(), vUsr.getIndirizzo(), vUsr.getRecapitoTel(), vUsr.getEmail(), vUsr.getCartaDiCredito());
        }
    }



}
