package business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	@FXML
	private TextField username;
	@FXML   
	private PasswordField password;
	private Stage dialogStage;
	private boolean okClocked;
	private application.Main main;
	
	
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	@FXML
	public void login() throws LoginException {
		main=new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		
		try {
			if(username.getText().isEmpty() || password.getText().isEmpty()) {
				
				throw new LoginException("one or more fields are empty");
			}
			if(!map.containsKey(username.getText())) {
					throw new LoginException("ID " + username.getText() + " not found");
			}
			String passwordFound = map.get(username.getText()).getPassword();
			if(!passwordFound.equals(password.getText())) {
				
				throw new LoginException("Password incorrect");
			}
			
			currentAuth = map.get(username.getText()).getAuthorization();
			if(currentAuth==Auth.ADMIN) {
				main.showMemberOverview();
				
				}
				else if(currentAuth==Auth.LIBRARIAN) {
								
				main.libOverview();
				
				}
				else if(currentAuth==Auth.BOTH)
							
				main.showMemberOverviewBoth();
				
		}
		catch(LoginException ex) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("Error");
			alert.setHeaderText(ex.getMessage());
			alert.setContentText("please insert a correct login credential");

			alert.showAndWait();
			
		}
			
		
		
	//	Collection<User> i=map.values();
		
		
		
	
	}
	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	public boolean isOkClicked() {
		// TODO Auto-generated method stub
		return okClocked;
	}
	
	
}
