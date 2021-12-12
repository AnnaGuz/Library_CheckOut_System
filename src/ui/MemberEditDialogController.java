package ui;





import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import util.DateUtil;

public class MemberEditDialogController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField birthdayField;

	private Stage dialogStage;
	private LibraryMember member;
	private boolean okClicked = false;
	@FXML
	private TextField stateField;
	@FXML
	private TextField pnumberField;
	@FXML
	private Button edit;
	@FXML
	private Button cancel;
	

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 *
	 * @param person
	 */
	public void setMember(LibraryMember member) {
		this.member = member;

		firstNameField.setText(member.getFirstName());
		lastNameField.setText(member.getLastName());
		streetField.setText(member.getAddress().getStreet());
		postalCodeField.setText(member.getAddress().getZip());
		cityField.setText((member.getAddress().getCity()));
		pnumberField.setText(member.getTelephone());
		stateField.setText((member.getAddress().getState()));
	
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			member.setFirstName(firstNameField.getText());
			member.setLastName(lastNameField.getText());
			member.getAddress().setStreet(streetField.getText());
		//	int zip=Integer.parseInt(postalCodeField.getText());
		//	member.getAddress().setPostalCode(Integer.parseInt(postalCodeField.getText()));
			member.getAddress().setZip(postalCodeField.getText());
			member.getAddress().setCity(cityField.getText());
			member.getAddress().setState(stateField.getText());
			member.setTelephone(pnumberField.getText());
		
			okClicked = true;
		DataAccess	dataaccess=	new DataAccessFacade();
			dataaccess.saveNewMember(member);
			
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	@FXML
	private void handleClear() {
		firstNameField.setText("");
		lastNameField.setText("");
		streetField.setText("");
		postalCodeField.setText("");
		cityField.setText("");
		pnumberField.setText("");
		stateField.setText("");
	
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if (streetField.getText() == null || streetField.getText().length() == 0) {
			errorMessage += "No valid street!\n";
		}

		if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
			errorMessage += "No valid postal code!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(postalCodeField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid postal code (must be an integer)!\n";
			}
		}

		if (cityField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "No valid city!\n";
		}

		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "No valid birthday!\n";
		} else {
			if (!DateUtil.validDate(birthdayField.getText())) {
				errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
