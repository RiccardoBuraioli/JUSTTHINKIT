package entity;

import java.sql.*;

import controllers.Connector;

public class Login {

    private final Connector connector;
    public int tableUser;


    public Login(Connector connector) {
        this.connector = connector;
    }
    
    public int getTableUser() {
        return this.tableUser;
    }
 
    public void setTableUser(int num) {
        this.tableUser = num;
    }

    public String checkEmail(String email) {
        String sqlUser = "SELECT Email FROM volontari WHERE Email = ?";
        String sqlCaritas = "SELECT Email FROM caritas WHERE Email = ?";
        String sqlShop = "SELECT Email FROM negozi WHERE Email = ?";
        ResultSet res = null;
        String returnedEmail = "";

        //Cerca nei volontari
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlUser)) {

            stmt.setString(1, email);
            res = stmt.executeQuery();

            while (res.next()) {
                String mail = res.getString("Email");
                if (mail.equals(email)) {
                    returnedEmail = email;
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
        if (returnedEmail.equals("")) {
        	
        	//Controlla nelle caritas
        	try (Connection conn = connector.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sqlCaritas)) {

                   stmt.setString(1, email);
                   res = stmt.executeQuery();

                   while (res.next()) {
                       String mail = res.getString("Email");
                       if (mail.equals(email)) {
                           returnedEmail = email;
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
        	
        	if (returnedEmail.equals("")) {
        		
        		//Controlla nei negozi
        		try (Connection conn = connector.getConnection();
        	             PreparedStatement stmt = conn.prepareStatement(sqlShop)) {

        	            stmt.setString(1, email);
        	            res = stmt.executeQuery();

        	            while (res.next()) {
        	                String mail = res.getString("Email");
        	                if (mail.equals(email)) {
        	                    returnedEmail = email;
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
        		if (returnedEmail.equals("")) {
        			return returnedEmail;
        		} else {
        			setTableUser(3);
        			return returnedEmail;
        		}
        		
        	} else {
        		setTableUser(2);
        		return returnedEmail;
        	}
        	
        	
        } else {
        	setTableUser(1);
        	return returnedEmail;
        }
    }


    public boolean checkPassword(String email, String password) {
    	
    	String sql;
    	
    	//Cerca la corrispondenza della password solo nella tabella dove è stata trovata la mail
    	if (getTableUser() == 1) {
    		sql = "SELECT Password FROM volontari WHERE Email = ?";
    	} else if (getTableUser() == 2) {
    		sql = "SELECT Password FROM caritas WHERE Email = ?";
    	} else {
    		sql = "SELECT Password FROM negozi WHERE Email = ?";
    	}
                     
        ResultSet res = null;
        boolean check = false;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            res = stmt.executeQuery();

            while (res.next()) {
                String psw = res.getString("Password");
                if (psw.equals(password)) {
                    check = true;
                 
                } else check = false;

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
        return check;
    }


    public int logger(String email, String password) {
        if (checkEmail(email).equals("")) {
            System.out.println("\t***** ERROR: EMAIL IS NOT CORRECT *****");
            return -1;
        } else {
            if (checkPassword(email, password)) {
                System.out.println("\t***** LOGGED SUCCESSFULLY *****");
                return getTableUser();
            } else {
                System.out.println("\t***** ERROR: PASSWORD IS NOT CORRECT *****");
                return -1;
            }
        }
    }
    
    //Probabilmente sbagliata
    //Serve a ritornare l'ID della mail corrispondente (nel sistema non ci possono essere due mail uguali)
    public int returnID(String email, int table) {
    	
    	String sql;
    	String resID = null;
    	int ID;
    	
    	if (table == 1) {
    		sql = "SELECT ID FROM volontari WHERE Email = ?";
    	}
    	else if (table == 2) {
    		sql = "SELECT ID FROM caritas WHERE Email = ?";
    	}
    	else if (table == 3) {
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


