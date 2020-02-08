package controllers;

import entity.CaritasUser;
import entity.ShopUser;
import entity.VolunteerUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connector.Connector;

public class PowerUserRepository {

    private Connector connector;
    private static final String EMAIL = "Email";
    private static final String UNBAN_STAT = "DELETE FROM bannedUser WHERE Email = ?";
    private static final String USR_NOT_FOUND = "\n\t ***** USER NOT FOUND *****\n";
    private static final String BANNED = "\n\t ***** USER HAS BEEN BANNED ***** ";
    private static final String SUCCESS = "Entry successfully modified! ";
    private static final String FAILED = "ERR: Operation Failed!";

    public PowerUserRepository(Connector connector) {
        this.connector = connector;
    }

    private void updateBanned(String email){
        String sql = "INSERT INTO bannedUser (Email) VALUES (?)";
        int rowAffected;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            rowAffected = stmt.executeUpdate();

            if (rowAffected == 1) System.out.println(SUCCESS);

            else System.out.println(USR_NOT_FOUND);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void kickUser(VolunteerUser volunteerUser){
        String sql = "DELETE FROM volontari WHERE Email = ?";

        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, volunteerUser.getEmail());
            deletedRec = stmt.executeUpdate();

            if (deletedRec != 1){
                System.out.println(FAILED);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void kickUser(CaritasUser caritasUser){
        String sql = "DELETE FROM caritas WHERE Email = ?";

        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, caritasUser.getEmail());
            deletedRec = stmt.executeUpdate();

            if (deletedRec != 1){
                System.out.println(FAILED);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void kickUser(ShopUser shopUser){
        String sql = "DELETE FROM negozi WHERE Email = ?";

        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shopUser.getEmail());
            deletedRec = stmt.executeUpdate();

            if (deletedRec != 1){
                System.out.println(FAILED);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void banUser(VolunteerUser volunteerUser){
        String sql = "DELETE FROM volontari WHERE Email = ?";

        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, volunteerUser.getEmail());
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) {
                System.out.println(BANNED);
                updateBanned(volunteerUser.getEmail());
            }

            else System.out.println(USR_NOT_FOUND);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void banUser(CaritasUser caritasUser){
        String sql = "DELETE FROM caritas WHERE Email = ?";

        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, caritasUser.getEmail());
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) {
                System.out.println(BANNED);
                updateBanned(caritasUser.getEmail());
            }

            else System.out.println(USR_NOT_FOUND);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void banUser(ShopUser shopUser){
        String sql = "DELETE FROM negozi WHERE Email = ?";

        int deletedRec;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shopUser.getEmail());
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) {
                System.out.println(BANNED);
                updateBanned(shopUser.getEmail());
            }

            else System.out.println(USR_NOT_FOUND);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void unbanUser(VolunteerUser vUser){

        int deletedRec;

        try(Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(UNBAN_STAT)){

            pstmt.setString(1, vUser.getEmail());
            deletedRec = pstmt.executeUpdate();

            if (deletedRec == 1){
                System.out.println(SUCCESS);

            }
            else System.out.println(USR_NOT_FOUND);
        } catch (SQLException ex){
            System.out.println((ex.getMessage()));
        }
    }
    public void unbanUser(CaritasUser caritasUser){
        int deletedRec;

        try(Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(UNBAN_STAT)){

            pstmt.setString(1, caritasUser.getEmail());
            deletedRec = pstmt.executeUpdate();

            if (deletedRec == 1){
                System.out.println(SUCCESS);

            }
            else System.out.println(USR_NOT_FOUND);
        } catch (SQLException ex){
            System.out.println((ex.getMessage()));
        }
    }
    public void unbanUser(ShopUser shopUser){
        int deletedRec;

        try(Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(UNBAN_STAT)){

            pstmt.setString(1, shopUser.getEmail());
            deletedRec = pstmt.executeUpdate();

            if (deletedRec == 1){
                System.out.println(SUCCESS);

            }
            else System.out.println(USR_NOT_FOUND);
        } catch (SQLException ex){
            System.out.println((ex.getMessage()));
        }
    }

    public List<String> getAllBanned() {
        List<String> banned = new ArrayList<>();

        String sql = "SELECT * FROM bannedUser";

        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {

                String email = resultSet.getString(EMAIL);
                banned.add(email);
            }
            return banned;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error Fetching Users", ex);
        }
    }

    public void printBannedInTab(List<String> banned){
        if (banned.isEmpty()){
            System.out.println("\n\t ***** NO BANNED USERS *****\n");
        }
        else {
            System.out.println("\n" + "Banned Users Email:");
            for (String str : banned) {
                System.out.println(str + "\n");
            }
        }
    }


}
