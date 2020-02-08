package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import entity.VolunteerUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class User_Home_Controller implements Initializable{
	
	VolunteerUser currentUser;
	
	//Images slideshow
	private Image img1 = new Image("file:/C:/Users/Admin/Documents/Java/JustThinkIt/src/resources/images/caritas1.jpg");
	private Image img2 = new Image("file:/C:/Users/Admin/Documents/Java/JustThinkIt/src/resources/images/caritas2.jpg");
	private Image img3 = new Image("file:/C:/Users/Admin/Documents/Java/JustThinkIt/src/resources/images/caritas3.jpg");
	private Image[] images = {img1, img2, img3};
	private int currentImage = 0;
	
	public VolunteerUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(VolunteerUser currentUser) {
		this.currentUser = currentUser;
	}
	

	@FXML
    private Text nomeCognome;

	@FXML
    private Button profileButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private ImageView imagePresentation;
    
    @FXML
    private ImageView profileImage;
    
    @FXML
    private Button searchCaritasButton;

    @FXML
    private Button logoutButton;
    
    @FXML
    private Button leftArrowButton;

    @FXML
    private Button rightArrowButton;

    @FXML
    void deleteAccountButtonPressed(ActionEvent event) {

    }

    @FXML
    void profileButtonPressed(ActionEvent event) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserProfilePage.fxml"));
			Parent root = loader.load();
			Profile_Controller profileController = loader.getController();
			profileController.initData(getCurrentUser());
			Stage home = (Stage) logoutButton.getScene().getWindow();
			home.setScene(new Scene(root, 800, 600));
			
			home.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void helpButtonPressed(ActionEvent event) {

    }

    @FXML
    void leftArrowPressed(ActionEvent event) {
    	
    	//Se è la prima riparti dall'ultima
    	if (currentImage == 0) {
    		currentImage = 2;
    		imagePresentation.setImage(images[currentImage]);
    	} else {
    		currentImage--;
    		imagePresentation.setImage(images[currentImage]);
    	}
    }

    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Logout");
    	alert.setHeaderText("Dovrai accedere di nuovo se vuoi tornare alla home");
    	alert.setContentText("Sei sicuro di voler eseguire il logout?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login_boundary.fxml"));
    			Parent root = loader.load();
    			Stage home = (Stage) logoutButton.getScene().getWindow();
    			home.setScene(new Scene(root, 600, 385));
    			home.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
    	} else {
    	    //nothing
    	}
    	
	}

    @FXML
    void rightArrowPressed(ActionEvent event) {
    	
    	//Se è l'ultima riparti dalla prima
    	if (currentImage == 2) {
    		currentImage = 0;
    		imagePresentation.setImage(images[currentImage]);
    	} else {
    		currentImage++;
    		imagePresentation.setImage(images[currentImage]);
    	}
    }

    @FXML
    void searchCaritasButtonPressed(ActionEvent event) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GMaps.fxml"));
			Parent root = loader.load();
			Stage home = (Stage) logoutButton.getScene().getWindow();
			home.setScene(new Scene(root, 600, 385));
			home.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    public void initData(VolunteerUser user) {
    	setCurrentUser(user);
    	nomeCognome.setText(user.getNome() + " "+ user.getCognome());
    	final Circle clip = new Circle();
    	clip.setCenterX(25);
    	clip.setCenterY(58);
    	clip.setRadius(200);
        profileImage.setClip(clip);
    	long delay = 3000; //update once per 3 seconds.
    	new Timer().schedule(new TimerTask() {

    	    @Override
    	    public void run() {
    	        imagePresentation.setImage(images[currentImage++]);
    	        if (currentImage >= images.length) {
    	            currentImage = 0;
    	        }
    	    }
    	}, 0, delay);
    }
    
    
    


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
