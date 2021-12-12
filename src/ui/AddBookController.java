package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.MySequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.Main;
import business.*;
import dataaccess.DataAccessFacade;
//import model.Book;
//import util.DateUtil;

public class AddBookController {

	@FXML
	private TextField isbn;
	@FXML
	private TextField bookTitle;
	@FXML
	private TextField authors;
	@FXML
	private ComboBox checkoutPeriod;
	@FXML
	private Button addButton;
	
	private Main mainApp;
	private Stage dialogStage;
	private Book book=new Book();
	private boolean okClicked = false;
	private DataAccessFacade dataaccess;

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
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;

		
		// Add observable list data to the table
		
		checkoutPeriod.setItems(FXCollections 
                .observableArrayList(7,21)); 

				
				
			
	}

	/**
	 * Sets the book to be edited in the dialog.
	 *
	 * @param book
	 */


	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	public Main getMainApp() {
		return mainApp;
	}

	

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		
		if (isInputValid()) {
			book.setIsbn(isbn.getText());
			book.setTitle(bookTitle.getText());
			//book.setAuthors(authors.getText());
			book.setMaxCheckoutLength(((int)checkoutPeriod.getValue()));
			
			okClicked = true;
			dataaccess=	new DataAccessFacade();
			dataaccess.saveNewBook(this.book);
			book.addCopy();
			
			
			
			
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleNewAuthor() {
		
		/*boolean addClicked =*/ mainApp.AddAuthorDialog(book);
		//if (addClicked) {
		//	mainApp.getAuthorData();
		//}
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

		
		if (bookTitle.getText() == null || bookTitle.getText().length() == 0) {
			errorMessage += "book title is empty!\n";
		}
//		if (authors.getText() == null || authors.getText().length() == 0) {
//			errorMessage += "No valid street!\n";
//		}

		if (checkoutPeriod.getValue() == null) {
			errorMessage += "please select checkout period!\n";
		
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
