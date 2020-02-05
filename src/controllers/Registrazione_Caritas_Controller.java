package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Registrazione_Caritas_Controller implements Initializable{
	
	TextField[] textFields;
	
	@FXML
    private TextField cittadiResidenza;

    @FXML
    private TextField Provincia;

    @FXML
    private TextField via;

    @FXML
    private TextField civico;

    @FXML
    private TextField telefono;

    @FXML
    private Button backButton;

    @FXML
    private TextField nomeCaritas;

    @FXML
    private TextField email;

    @FXML
    private CheckBox type;
    
    @FXML
    private CheckBox type2;

    @FXML
    private TextField nomeResp;

    @FXML
    private TextField cognomeResp;

    @FXML
    private Button completaButton;

    @FXML
    private TextField codiceFiscaleResp;

    @FXML
    private PasswordField passwordCaritas;
    
    @FXML
    private Text passwordMatch;

    @FXML
    private PasswordField confermaPassCaritas;

    @FXML
    void backButtonPressed(ActionEvent event) {
    	
	    try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/RegistrazioneMenu.fxml"));
			Stage signUp = (Stage) backButton.getScene().getWindow();
			Scene scene = new Scene(root,600,400);
			signUp.setScene(scene);
			signUp.show();
			signUp.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    public void completaButtonPressed(ActionEvent event) {
    	int resCheck = checker();
    	if (resCheck == 0) {
    		//Registra Caritas
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
			else if (type.isSelected() || type2.isSelected()) {
				passwordMatch.setText("Alcuni campi sono vuoti");
				passwordMatch.setVisible(true);
				return -1; //Almeno uno dei tipi deve essere selezionato
			}
		}
    	
    	//Valida che i campi password e conferma password siano uguali
    	
    	if (passwordCaritas.getText().equals(confermaPassCaritas.getText())) {
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
		textFields = new TextField[] {cittadiResidenza,Provincia,via,civico,telefono,nomeCaritas,email,nomeResp,cognomeResp,codiceFiscaleResp,passwordCaritas};
		
		
	}

}
