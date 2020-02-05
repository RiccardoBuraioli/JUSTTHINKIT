package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.VolunteerUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Registrazione_Volontario_Controller implements Initializable {
	
	TextField[] textFields;

    @FXML
    private TextField nomeVol;

    @FXML
    private TextField emailVol;

    @FXML
    private TextField cittaVol;

    @FXML
    private TextField codiceFiscVol;

    @FXML
    private TextField cognomeVol;

    @FXML
    private TextField provinciaVol;

    @FXML
    private TextField viaVol;

    @FXML
    private TextField civicoVol;

    @FXML
    private TextField telVol;

    @FXML
    private Button registraVolontarioButton;

    @FXML
    private Button backButtonVol;

    @FXML
    private PasswordField passwordVol;

    @FXML
    private PasswordField confermaPassVol;
    
    @FXML
    private Text passwordMatch;


    @FXML
    void backButtonVolPressed(ActionEvent event) {
    	
	    try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/RegistrazioneMenu.fxml"));
			Stage signUp = (Stage) backButtonVol.getScene().getWindow();
			Scene scene = new Scene(root,600,400);
			signUp.setScene(scene);
			signUp.show();
			signUp.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void registraVolontarioButtonPressed(ActionEvent event) {
    	
    	if (checker() == 0) {
    		Connector connector = new Connector("jdbc:mysql://127.0.0.1:3306/justhinkit", "root", "password");
    		//Crea nuova istanza VolunteerUser?? 	
        	VolunteerUser newUser = new VolunteerUser(nomeVol.getText(), cognomeVol.getText(), passwordVol.getText(), viaVol.getText(), telVol.getText(), emailVol.getText(), "CartaDiCredito");
        	VolunteerRepository vrep = new VolunteerRepository(connector);
        	int id = vrep.insertVolunteer(newUser);
        	newUser.setID(id);
        	
        	//Manda alla home dopo la registrazione
        	try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserHomePage.fxml"));
    			Parent root = loader.load();
    			User_Home_Controller userHomeController = loader.getController();
    			userHomeController.initData(newUser);
    			Stage home = (Stage) registraVolontarioButton.getScene().getWindow();
    			home.setScene(new Scene(root, 800, 600));
    			
    			home.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	

    }
    
    public int checker() {
    	
    	//Controlla che non ci siano campi lasciati vuoti
    	for (int i = 0; i < textFields.length; i++) {
			if (textFields[i].getText().isEmpty()) {
				passwordMatch.setText("Alcuni campi sono vuoti");
				passwordMatch.setVisible(true);
				return -1;
			}
		}
    	
    	//Valida che i campi password e conferma password siano uguali
    	
    	if (passwordVol.getText().equals(confermaPassVol.getText())) {
    		passwordMatch.setVisible(false);
    		System.out.println("Password confirmed");
    		return 0;
    	}
    	else {
    		passwordMatch.setText("Le password non corrispondono");
    		passwordMatch.setVisible(true);
    		return -1;
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		passwordMatch.setVisible(false);
		textFields = new TextField[] {nomeVol,emailVol,cittaVol,codiceFiscVol,cognomeVol,provinciaVol,civicoVol,viaVol,telVol,passwordVol,confermaPassVol};
		//Per rendere opzionale un campo basta rimuoverlo da questa lista
	}

}