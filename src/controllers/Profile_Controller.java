package controllers;

import java.io.IOException;

import entity.VolunteerUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Profile_Controller {
	
	VolunteerUser currentUser;

    public VolunteerUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(VolunteerUser currentUser) {
		this.currentUser = currentUser;
	}

	@FXML
    private ImageView profileImageEdit;

    @FXML
    private Button backHomeButton;

    @FXML
    private Button editButton;

    @FXML
    private ImageView donazioniImage;

    @FXML
    private ImageView eventiImage;

    @FXML
    private Text nomeCognomeEdit;

    @FXML
    private Text emailEdit;

    @FXML
    private Text cittaEdit;

    @FXML
    private Text telefonoEdit;

    @FXML
    void backHomeButtonPressed(ActionEvent event) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserHomePage.fxml"));
			Parent root = loader.load();
			User_Home_Controller userHomeController = loader.getController();
			userHomeController.initData(currentUser);
			Stage home = (Stage) telefonoEdit.getScene().getWindow();
			home.setScene(new Scene(root, 800, 600));
			
			home.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void editButtonPressed(ActionEvent event) {

    }
    
    public void initData(VolunteerUser user) {
    	setCurrentUser(user);
    	emailEdit.setText(user.getEmail());
    	cittaEdit.setText(user.getIndirizzoVolontario());
    	telefonoEdit.setText(user.getRecapitoTel());
    	nomeCognomeEdit.setText(user.getNome() + " " + user.getCognome());
    }

}