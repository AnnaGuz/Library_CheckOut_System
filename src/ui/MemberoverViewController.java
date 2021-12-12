package ui;

import application.Main;
import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;


public class MemberoverViewController {
	@FXML
	private TableView<LibraryMember> memberTable=new TableView<LibraryMember>();
	@FXML
	private TableColumn<LibraryMember, String> firstNameColumn;
	@FXML
	private TableColumn<LibraryMember, String> lastNameColumn;
	@FXML
	private TableColumn<LibraryMember, String> idColumn;
	@FXML
	private MenuBar  menubar;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Label memberId;
	@FXML
	private Label phoneNumberLabel;
	@FXML
	private Label pnumber;
	// Reference to the main application.
	private Main mainApp;
	private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public MemberoverViewController() {
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
		showMemberDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		memberTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMemberDetails(newValue));
		memberTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> DeleteMemberDetails(newValue));

	}

	private void preJava8() {
		
		lastNameColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> param) {
				 return new ReadOnlyObjectWrapper(param.getValue().getLastName());
			}
		});
		firstNameColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> param) {
				 return new ReadOnlyObjectWrapper(param.getValue().getFirstName());
			}
		});
		idColumn.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<LibraryMember, String> param) {
				 return new ReadOnlyObjectWrapper(param.getValue().getMemberId());
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
		memberTable.setItems(mainApp.getMemberData());
	}

	private void showMemberDetails(LibraryMember member) {
	
		if (member != null) {
			// Fill the labels with info from the person object.
			
			memberId.setText(member.getMemberId());
			firstNameLabel.setText(member.getFirstName());
			lastNameLabel.setText(member.getLastName());
			streetLabel.setText(member.getAddress().getStreet());
			postalCodeLabel.setText((member.getAddress().getZip()));
			cityLabel.setText((member.getAddress().getCity()));
			phoneNumberLabel.setText(member.getTelephone());
			stateLabel.setText((member.getAddress().getState()));

			// TODO: We need a way to convert the birthday into a String!
			// birthdayLabel.setText(...);
		} else {
			// Person is null, remove all the text.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			
		}
	}
	private void DeleteMemberDetails(LibraryMember member) {
		
		if (member != null) {
			// Fill the labels with info from the person object.
			
			
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteMember() {
		int selectedIndex = memberTable.getSelectionModel().getSelectedIndex();
		 LibraryMember selectedmember = memberTable.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			memberTable.getItems().remove(selectedIndex);
			DataAccess	dataaccess=	new DataAccessFacade();
			dataaccess.RemoveMember(selectedmember);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Member Selected");
			alert.setContentText("Please select a Member in the table.");
			
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewMember() {
	//	LibraryMember tempMember =new  LibraryMember( new Address());
		boolean okClicked =	mainApp.AddMemberDialog();
	if(okClicked) {
		memberTable.setItems(mainApp.setMemberData());
		
		
	}
	}
	@FXML
	private void handleBookMenuBar() {
		//	LibraryMember tempMember =new  LibraryMember( new Address());
			mainApp.AdminBookOverview();
			stage.close();
		}
	@FXML
	private void handleExit() {
		Platform.exit();
	}
	@FXML
	private void handleLogout() {
		//	LibraryMember tempMember =new  LibraryMember( new Address());
		mainApp.LoginDialog();
		stage.close();
	
			
		}
	@FXML
	private void about() {
		//	LibraryMember tempMember =new  LibraryMember( new Address());
		
			mainApp.showABout();	
			
		}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditMember() {
		LibraryMember selectedMember = memberTable.getSelectionModel().getSelectedItem();
		if (selectedMember != null) {
			boolean okClicked = mainApp.EditMemberDialog(selectedMember);
			if (okClicked) {
				showMemberDetails(selectedMember);
				memberTable.setItems(mainApp.setMemberData());
				
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}
}
