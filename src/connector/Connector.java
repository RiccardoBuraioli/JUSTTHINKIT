package connector;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class Connector {
	    private static Connection connection;

	    public static Connection dbConnect() {
	        if(connection==null) {
	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                return connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JustThinkIt?useLegacyDatetimeCode=false&serverTimezone=Europe/Rome&useSSL=false", "root", "password");
	            } catch (ClassNotFoundException | SQLException e) {
	                e.printStackTrace();
	            }
	        }else
	            return connection;
	        return null;
	    }
	}
