package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import business.*;
//import model.Author;
//import util.DateUtil;
import dataaccess.DataAccessFacade;

public class AddAuthorController {

	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField telephone;
	@FXML
	private TextField bio;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField stateField;

	private Stage dialogStage;
	private Author author = new Author(new Address());
	private Book book = new Book();
	private boolean okClicked = false;
	private DataAccessFacade dataaccess;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

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
	 * Sets the author to be edited in the dialog.
	 *
	 * @param author
	 */
	public void setAuthor(Author author) {
		this.author = author;

		firstName.setText(author.getFirstName());
		lastName.setText(author.getLastName());
		telephone.setText(author.getTelephone());
		bio.setText(author.getBio());	
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
			author.setFirstName(firstName.getText());
			author.setLastName(lastName.getText());
			author.setBio(bio.getText());
			author.getAddress().setStreet(streetField.getText());
			//	int zip=Integer.parseInt(postalCodeField.getText());
			//	author.getAddress().setPostalCode(Integer.parseInt(postalCodeField.getText()));
			author.getAddress().setZip(postalCodeField.getText());
			author.getAddress().setCity(cityField.getText());
			author.getAddress().setState(stateField.getText());
				this.book.getAuthors().add(author);
			okClicked = true;
			dataaccess=	new DataAccessFacade();
			dataaccess.saveNewAuthor(author);
			System.out.println(author);
			
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

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

//		if (firstName.getText() == null || firstName.getText().length() == 0) {
//			errorMessage += "No valid first name!\n";
//		}
		if (lastName.getText() == null || lastName.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if (telephone.getText() == null || telephone.getText().length() == 0) {
			errorMessage += "No valid street!\n";
		}

		if (bio.getText() == null || bio.getText().length() == 0) {
			errorMessage += "No valid postal code!\n";
		
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
