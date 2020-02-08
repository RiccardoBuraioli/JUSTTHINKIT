package controllers;

import java.io.IOException;
import java.util.logging.Logger;

import Dao.Login_Dao;
import Dao.VolunteerRepository;
import connector.Connector;
import entity.Login;
import entity.VolunteerUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login_Controller {
	
	private static final Logger log = Logger.getLogger(Login_Controller.class.getName());
	
	@FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;
    
    @FXML
    void loginPressed(ActionEvent event) {
    	String Email = usernameField.getText();
    	String Passw = passwordField.getText();
    	
    	//Connettore non deve essere qui
    	
    	//Connector connector = new Connector("jdbc:mysql://127.0.0.1:3306/JustthinkIt", "root", "password");
    	Login_Dao login = new Login_Dao();
    	boolean loginResult = login.checkEmail(Email, Passw);
    	String Codice = Login.tableUser;
    	if (loginResult) {
    		
    		//OK MANDA ALLA HOME CORRETTA
    		
    		//Volontario
    		if (Codice.contentEquals("Volontario")) {
    			
    			Connector connectorV = new Connector("jdbc:mysql://127.0.0.1:3306/JustthinkIt", "root", "password");
    			VolunteerRepository vrep = new VolunteerRepository(connectorV);
    			
    			int userID = Login_Dao.returnID(Email,Codice);
    			if (userID == -1) {
    				log.warning("Errore nel ritornare l'ID");
    			}
    			
    			VolunteerUser loggedUser = vrep.getVolunteerByID(userID);
    			
    			//Manda alla home user
    			try {
        			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserHomePage.fxml"));
        			Parent root = loader.load();
        			User_Home_Controller userHomeController = loader.getController();
        			userHomeController.initData(loggedUser);
        			Stage home = (Stage) loginButton.getScene().getWindow();
        			home.setScene(new Scene(root, 800, 600));
        			
        			home.show();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
    		}
    		
    		//Caritas
    		else if (Codice.contentEquals("Caritas")) {
    			
    		}
    		
    		//Negozio
    		else if (Codice.contentEquals("Negozio")) {
    			
    		}
    		else System.out.println("Codice = " + Codice);
    		    		
    	}
    	else {
    		System.out.println("Login Error");
    	}	

    }

    @FXML
    void registrazionePressed(ActionEvent event) {
    	
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/RegistrazioneMenu.fxml"));
			Stage signUp = (Stage) loginButton.getScene().getWindow();
			Scene scene = new Scene(root,600,400);
			signUp.setScene(scene);
			signUp.show();
			signUp.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

}
