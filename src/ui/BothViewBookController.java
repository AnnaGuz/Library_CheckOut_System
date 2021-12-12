package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.Main;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * We must put it in the same package as the BookOverview.fxml, otherwise the
 * SceneBuilder won't find it.
 *
 * @author rXing
 *
 */
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.util.Callback;
//import model.Book;
import business.Book;
import business.BookCopy;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class BothViewBookController {
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> titleColumn;
	@FXML
	private TableColumn<Book, String> copyColumn;
	@FXML
	private ComboBox<String> membercombo = new ComboBox<String>();
	@FXML
	private Label isbn;
	@FXML
	private Label authors;
	@FXML
	private Label checkoutPeriod;
	@FXML
	private Label availability;
	@FXML
	private MenuBar menubar;
	// Reference to the main application.
	private Main mainApp;
	private Stage dialogStage;

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public BothViewBookController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
//		titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
//		copyColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		preJava8();
		// Clear person details.
		showBookDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		bookTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showBookDetails(newValue));

	}

	private void preJava8() {
		titleColumn.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				return new ReadOnlyObjectWrapper(param.getValue().getTitle());
			}
		});

		copyColumn.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				return new ReadOnlyObjectWrapper(param.getValue().getNumCopies());
			}
		});
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		ObservableList<LibraryMember> temp = this.mainApp.getMemberData();
		// HashMap<String, Book> book = da.readBooksMap();

		List<String> temp1 = new ArrayList<>();
		for (LibraryMember x : temp) {
			temp1.add(x.getMemberId());

		}

		// memberTable.setItems(mainApp.getMemberData());
		membercombo.setItems(FXCollections.observableArrayList(temp1));
		bookTable.setItems(mainApp.getBookData());
	}

	private void showBookDetails(Book book) {
		if (book != null) {
			// Fill the labels with info from the person object.
			isbn.setText(book.getIsbn());
			authors.setText(book.getAuthors1());
			checkoutPeriod.setText(Integer.toString(book.getMaxCheckoutLength()));

			if (book.isAvailable()) {
				availability.setText("Available");
			} else {
				availability.setText("Not Available");
			}
			// TODO: We need a way to convert the birthday into a String!
		} else {
			// Book is null, remove all the text.
			isbn.setText("");
			authors.setText("");
			checkoutPeriod.setText("");
			availability.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleNewBook() {
		Book tempBook = new Book();
		boolean okClicked = mainApp.AddBookDialog(tempBook);
		if (okClicked) {
			bookTable.setItems(mainApp.setBookData());
			
		}
	}

	@FXML
	private void handleDeleteBook() {
		int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
		Book selectedbook = bookTable.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			bookTable.getItems().remove(selectedIndex);
			DataAccess dataaccess = new DataAccessFacade();
			dataaccess.RemoveBook(selectedbook);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Member Selected");
			alert.setContentText("Please select a Book in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	public void addCopy() {

		int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
		Book selectedbook = bookTable.getSelectionModel().getSelectedItem();
		String idbook = selectedbook.getIsbn();
		mainApp = new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> book = da.readBooksMap();

		Book checkoutBook = book.get(idbook);// add bookid
		checkoutBook.addCopy();
		book.put(checkoutBook.getIsbn(), checkoutBook);
		da.saveNewBook1(book);
		book.put(checkoutBook.getIsbn(), checkoutBook);
		da.saveNewBook1(book);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(dialogStage);
		alert.setTitle("copy Added");
		alert.setHeaderText("copy added successfully");
		alert.setContentText("copy added successfully" + checkoutBook.getCopies().size());
		alert.showAndWait();

	}
	public void handleCheckout() {
		
		mainApp = new application.Main();
		mainApp.showCheckOutview();
	}
	@FXML
	public void handleCheckout1() {

		String id = membercombo.getValue();
		mainApp = new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> member = da.readMemberMap();
		LibraryMember borrower = member.get(id);

		HashMap<String, Book> book = da.readBooksMap();

		Book selectedbook = bookTable.getSelectionModel().getSelectedItem();

		String idbook = selectedbook.getIsbn();
		Book checkoutBook = book.get(idbook);// add bookid
		// int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
		try {
			BookCopy booktemp = checkoutBook.getNextAvailableCopy();

			booktemp.changeAvailability();
			business.CheckOutRecordFactory.checkoutBook(borrower, booktemp);
			book.put(checkoutBook.getIsbn(), checkoutBook);
			da.saveNewBook1(book);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("Check out Successfull");
			alert.setHeaderText("Check out Successfull");
			alert.setContentText("book checkedout" + "due date is"
					+ LocalDate.now().plusDays(booktemp.getBook().getMaxCheckoutLength()));

			alert.showAndWait();
			dialogStage.close();
		} catch (NullPointerException e) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("no available copies");
			alert.setHeaderText("no available copies");
			alert.setContentText("no available copies");

			alert.showAndWait();
		}

	}
	@FXML
	private void handleMemberMenuBar() {
		//	LibraryMember tempMember =new  LibraryMember( new Address());
			mainApp.showMemberOverviewBoth();
			dialogStage.close();
			
		}
	@FXML
	private void handleExit() {
		Platform.exit();
	}
	@FXML
	private void handleLogout() {
		//	LibraryMember tempMember =new  LibraryMember( new Address());
		mainApp.LoginDialog();
		dialogStage.close();
	
			
		}
	@FXML
	private void about() {
		//	LibraryMember tempMember =new  LibraryMember( new Address());
		
			mainApp.showABout();	
			
		}


	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */

	/*
	 * private void handleEditBook() { Book selectedBook =
	 * bookTable.getSelectionModel().getSelectedItem(); if (selectedBook != null) {
	 * boolean okClicked = mainApp.EditBookDialog(selectedBook); if (okClicked) {
	 * showBookDetails(selectedBook); }
	 * 
	 * } else { // Nothing selected. Alert alert = new Alert(AlertType.WARNING);
	 * alert.initOwner(mainApp.getPrimaryStage()); alert.setTitle("No Selection");
	 * alert.setHeaderText("No Book Selected");
	 * alert.setContentText("Please select a person in the table.");
	 * 
	 * alert.showAndWait(); } }
	 */
}

