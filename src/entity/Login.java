package entity;

import connector.Connector;

public class Login {

    private Connector connector;
    public static String tableUser;


    public Login(Connector connector) {
        this.setConnector(connector);
    }
    
    public static String getTableUser() {
    	System.out.println(tableUser);
        return tableUser;
    }
 
    public void setTableUser(String codice) {
        this.tableUser = codice;
    }

    public Login() {
    	this.tableUser = tableUser;
    }

	public void setConnector(Connector connector) {
		this.connector = connector;
	}
}


