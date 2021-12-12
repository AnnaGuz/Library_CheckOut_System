package ui;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.Main;
import business.Address;
import business.Book;
import business.BookCopy;
import business.CheckOutEntry;
import business.CheckOutRecord;
import business.LibraryMember;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;


public class CheckOutController {
	@FXML
	private ComboBox<String> memberTable=new ComboBox<String>();
	@FXML
	private ComboBox<String> bookTable=new ComboBox<String>();
	@FXML
	private Label ISBN;
	@FXML
	private Label Title;
	@FXML
	private Label CheckOutPeriod;
	@FXML
	private TextArea Authors;
	@FXML
	private Label numofCopy;
	@FXML
	private Label availability;
	
	
	private Stage dialogStage;
	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	// Reference to the main application.
	private Main mainApp=null;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public CheckOutController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
//		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		preJava8();
		// Clear person details.
	//	showMemberDetails(null);

		// Listen for selection changes and show the person details when
	//	showMemberDetails(getBook());
		
	}

	private void preJava8() {
		
		
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 * @return 
	 */
	public void setbookCombo(Main mainApp) {
		ObservableList<Book> temp=mainApp.getBookData();
		//HashMap<String, Book> book = da.readBooksMap();
		
			List<String> temp1=new ArrayList<>();
			for(Book x:temp) {
				temp1.add(x.getIsbn());
				
			}
            
	//memberTable.setItems(mainApp.getMemberData());
			bookTable.setItems(FXCollections 
                    .observableArrayList(temp1)); 
	}
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;

		
		// Add observable list data to the table
		
				ObservableList<LibraryMember> temp=this.mainApp.getMemberData();
			//HashMap<String, Book> book = da.readBooksMap();
			
				List<String> temp1=new ArrayList<>();
				for(LibraryMember x:temp) {
					temp1.add(x.getMemberId());
					
				}
                
		//memberTable.setItems(mainApp.getMemberData());
				memberTable.setItems(FXCollections 
                        .observableArrayList(temp1)); 
				setbookCombo( this.mainApp);
				
			
	}
	public void getBook() {
		
		String id=bookTable.getValue();
		mainApp=new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> book = da.readBooksMap();
		Book  b=book.get(bookTable.getValue());// add bookid
		ISBN.setText(b.getIsbn());
		Title.setText(b.getTitle());
		numofCopy.setText(Integer.toString(b.getNumCopies()));
		CheckOutPeriod.setText(Integer.toString(b.getMaxCheckoutLength()));
		Authors.setText(b.getAuthors1());
		
		System.out.println(b.getIsbn()+" "+ b.getNumCopies());
			
	}
	public void setId() {
		System.out.println(bookTable.getValue());
		ISBN.setText(bookTable.getValue());
		
		
	}
	public void setIdMember() {
		String id=memberTable.getValue();
		mainApp=new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> member = da.readMemberMap();
		LibraryMember borrower=member.get(id);
		System.out.println(borrower.getMemberId());
	
		
	}
	private void showMemberDetails(Book b) {
		
		
		if (b != null) {
			// Fill the labels with info from the person object.
			ISBN.setText(b.getIsbn());
			Title.setText(b.getTitle());
			CheckOutPeriod.setText(Integer.toString(b.getMaxCheckoutLength()));
			Authors.setText(b.getAuthors1());
			
			// TODO: We need a way to convert the birthday into a String!
			// birthdayLabel.setText(...);
		} else {
			// Person is null, remove all the text.
			ISBN.setText("");
			Title.setText("");
			CheckOutPeriod.setText("");
			Authors.setText("");
			
			
		}
	}
	
	public void handleOk() {
		
		String id=memberTable.getValue();
		mainApp=new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> member = da.readMemberMap();
		HashMap<String, Book> book = da.readBooksMap();
			LibraryMember borrower=member.get(id);
			
			Book  checkoutBook=book.get(bookTable.getValue());// add bookid
			try {
			BookCopy booktemp=checkoutBook.getNextAvailableCopy();
		
			booktemp.changeAvailability();
			business.CheckOutRecordFactory.checkoutBook(borrower,booktemp);
			book.put(checkoutBook.getIsbn(),checkoutBook);
			da.saveNewBook1(book);
			da.saveNewMember(borrower);
			
			CheckOutRecord c=borrower.getCr();
			List<CheckOutEntry> entry=c.getBooks();
				System.out.println(entry.size()+"member id" + id);
			for(CheckOutEntry temp: entry) {
				
				System.out.println(temp);
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("Check out Successfull");
			alert.setHeaderText("Check out Successfull");
			alert.setContentText("book checkedout" + "due date is"+LocalDate.now().plusDays(booktemp.getBook().getMaxCheckoutLength()));

			alert.showAndWait();
			dialogStage.close();
			}
			catch(NullPointerException e) {
			
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("no available copies");
				alert.setHeaderText("no available copies");
				alert.setContentText("no available copies");

				alert.showAndWait();
			}}
			
		
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	}

	
	/**
	 * Called when the user clicks on the delete button.
	 */

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	

