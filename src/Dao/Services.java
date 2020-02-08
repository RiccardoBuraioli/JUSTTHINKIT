package Dao;

import Dao.RepositoryException;
import connector.Connector;

import java.sql.*;

public class Services {

    private static final String EMAIL = "Email";
    private Connector connector;

    public Services(Connector connector) {
        this.connector = connector;
    }

    public boolean checkIfBanned(String email) {
        String sql = "SELECT Email FROM bannedUser WHERE Email = ? ";
        ResultSet resultSet = null;
        boolean res = false;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String mail = resultSet.getString(EMAIL);
                if (mail.equals(email)) {
                    res = true;
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Querying", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet);
        } return res;
    }

    public String checkEmail(String email) {
        String sql = "SELECT Email FROM Utenti WHERE Email = ?";
        ResultSet res = null;
        String returnedEmail = "";


        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                String mail = res.getString(EMAIL);
                if (mail.equals(email)) {
                    returnedEmail = email;
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Querying Email", ex);
        } finally {
            DbUtils.resultSetClosing(res);
        } return returnedEmail;
    }

    public boolean checkEmailBoolean(String email) {
        String sql = "SELECT Email FROM Utenti WHERE Email = ?";
        ResultSet resultSet = null;
        boolean res = false;


        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String mail = resultSet.getString(EMAIL);
                if (mail.equals(email)) res = true;

            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Checking Email", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet);
        } return res;
    }


    public boolean checkPassword(String email, String password) {
        String sql = "SELECT Password FROM Utenti WHERE Email = ?";
        ResultSet resultSet = null;
        boolean check = false;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String psw = resultSet.getString("Password");
                check = psw.equals(password);

            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Checking Password", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet);
        }
        return check;
    }


    public int getUserCode(String email){
        String sql = "SELECT UserCode FROM Utenti WHERE Email = ?";
        ResultSet resultSet = null;
        int userCode = -1;

        try (Connection connection = connector.getConnection();
              PreparedStatement preparedStmt = connection.prepareStatement(sql)){

            preparedStmt.setString(1, email);

            resultSet = preparedStmt.executeQuery();

            while(resultSet.next()){
                userCode = resultSet.getInt("UserCode");
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Querying UserCode", ex);
        } finally {
            DbUtils.resultSetClosing(resultSet);
        }
        return userCode;
    }

    

}
