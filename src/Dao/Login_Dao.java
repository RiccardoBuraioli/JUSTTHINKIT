package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connector.Connector;
import entity.Login;




public class Login_Dao {

	Connector connector = new Connector("jdbc:mysql://127.0.0.1:3306/JustthinkIt", "root", "password");
	
	public Login_Dao() {
		
	}
	
	 public boolean checkEmail(String email, String Passw) {
	      	String Codice = null;

		 	boolean check = false;
		 	String sqlUser = "SELECT Email, Password, Codice FROM utenti WHERE Email = ? AND Password = ?";
	        ResultSet res = null;

	        //Cerca nei volontari
	        try (Connection conn = connector.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sqlUser)) {
	        	

	            stmt.setString(1, email);
	            stmt.setString(2, Passw);
	            res = stmt.executeQuery();

	            while (res.next()) {
	                String mail = res.getString("Email");
	                String pas = res.getString("Password");
	                Codice = res.getString("Codice");
	                
	                if (mail.equals(email) ) {
	                	if(pas.equals(pas)) {
	                    
	                    check = true;
	                }
	                }
	                	
	            }
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        } finally {
	            try {
	                if (res != null) res.close();
	            } catch (SQLException e) {
	                System.out.println(e.getMessage());
	            }
	        }
	        	Login login = new Login();
	        
	        	login.setTableUser(Codice);
	        	return check;
				
	        	
	        } 
	    

	  public static int returnID(String email, String table) {
	    	Connector connector = new Connector("jdbc:mysql://127.0.0.1:3306/JustthinkIt", "root", "password");

	    	String sql;
	    	String resID = null;
	    	int ID;
	    	
	    	if (table.contentEquals("Volontario")) {
	    		sql = "SELECT ID FROM volontari WHERE Email = ?";
	    	}
	    	else if (table.contentEquals("Caritas")) {
	    		sql = "SELECT ID FROM caritas WHERE Email = ?";
	    	}
	    	else if (table.contentEquals("Negozio")) {
	    		sql = "SELECT ID FROM negozi WHERE Email = ?";
	    	}
	    	else {
	    		System.out.println("Errore");
	    		return -1;
	    	}
	    	
	    	ResultSet res = null;
	    	try (Connection conn = connector.getConnection();
	                PreparedStatement stmt = conn.prepareStatement(sql)) {

	               stmt.setString(1, email);
	               res = stmt.executeQuery();

	               while (res.next()) {
	            	   resID = res.getString("ID");
	               }
	           } catch (SQLException ex) {
	               System.out.println(ex.getMessage());
	           } finally {
	               try {
	                   if (res != null) res.close();
	               } catch (SQLException e) {
	                   System.out.println(e.getMessage());
	               }
	           }
	    	
	    	ID = Integer.parseInt(resID);
	    	return ID;
	    	
	    }

	  
}
