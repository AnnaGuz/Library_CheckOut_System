package application;
	
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccessFacade;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.AddAuthorController;
import ui.AddBookController;
import ui.AddMemberDialogController;
import ui.AdminViewBookController;
import ui.BothViewBookController;
import ui.LibrarianViewBookController;
import ui.CheckOutController;
import ui.MemberEditDialogController;
import ui.MemberOverViewLibrarianController;
import ui.MemberoverViewController;
import ui.MemberoverViewControllerBoth;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	//private Parent rootLayout;
	private BorderPane rootLayout;
	private ObservableList<LibraryMember> memberData =setMemberData();
	private ObservableList<Book> bookData =setBookData();
	private ObservableList<Author> authorData =setAuthorData();


	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public ObservableList<Book> getBookData() {
		// TODO Auto-generated method stub
		
		
		return bookData;
	}
	public ObservableList<Author> getAuthorData() {
		// TODO Auto-generated method stub
		
		
		return authorData;
	}
	public ObservableList<Book> setBookData() {
		DataAccessFacade daf=new DataAccessFacade();
		HashMap<String, Book> temp= daf.readBooksMap();
		ObservableList<Book> memberData = FXCollections.observableArrayList();
		memberData.addAll(temp.values());
		return memberData;
		
	}
	
	public ObservableList<Author> setAuthorData() {
		DataAccessFacade daf=new DataAccessFacade();
		HashMap<String, Author> temp= daf.readAuthorMap();
		ObservableList<Author> memberData = FXCollections.observableArrayList();
		memberData.addAll(temp.values());
		return memberData;
		
	}

	public ObservableList<LibraryMember> getMemberData() {
		// TODO Auto-generated method stub
		
		
		return memberData;
	}
	public ObservableList<LibraryMember> setMemberData() {
		DataAccessFacade daf=new DataAccessFacade();
		HashMap<String, LibraryMember> temp= daf.readMemberMap();
		ObservableList<LibraryMember> memberData = FXCollections.observableArrayList();
		memberData.addAll(temp.values());
		return memberData;
		
	}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Library");
	//	this.primaryStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));

			//initRootLayout();

		//showMemberOverview();
		//AddMemberDialog();
	//	showMemberOverviewLib();
	LoginDialog();
//	showCheckOutview();
	//libOverview();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../application/myScene.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean LoginDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/Login.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Login");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			SystemController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			//controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.show();
			

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean AddMemberDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/AddMember.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Member");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			AddMemberDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			//controller.setPerson(person);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean EditMemberDialog(LibraryMember member) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/AdminEditMember.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Member");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			MemberEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMember(member);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void showMemberOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/MemberView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Member Detail");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
						
			MemberoverViewController controller = loader.getController();
			controller.setMainApp(this);
			controller.setStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showMemberOverviewBoth() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/bothMemberOverview.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Member Detail Both");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
						
			MemberoverViewControllerBoth controller = loader.getController();
			controller.setMainApp(this);
			controller.setStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showCheckOutview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/CheckOutView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Checkout ");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
			CheckOutController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showMemberOverviewLib() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/MemberView2.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Member Detail");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
						MemberOverViewLibrarianController controller = loader.getController();
			controller.setMainApp(this);
			controller.setStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public boolean AddBookDialog(Book book) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/AddBook.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Book");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			AddBookController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			//controller.setPerson(person);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void showABout() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/About.fxml"));
			AnchorPane about = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			//rootLayout.setCenter(about);
			Stage dialogStage = new Stage();
			dialogStage.setTitle("About Detail");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(about);
			dialogStage.setScene(scene);
			dialogStage.show();	
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void AddAuthorDialog(Book book) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/AddAuthor.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Author");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			AddAuthorController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setBook(book);
			//controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		//	return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			//return false;
		}
	}
	
	public void libOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/AdminViewBooks.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Book Detail");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
						AdminViewBookController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void BothBookOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/BothViewBooks.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Both Book  Detail");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
			BothViewBookController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void AdminBookOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../ui/LibrarianViewBooks.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
				
			//rootLayout.setCenter(page);
			// Create the dialog Stage.
						Stage dialogStage = new Stage();
						dialogStage.setTitle("Book Detail");
						dialogStage.initModality(Modality.WINDOW_MODAL);
						dialogStage.initOwner(primaryStage);
						Scene scene = new Scene(page);
						dialogStage.setScene(scene);
						dialogStage.show();
						LibrarianViewBookController		 controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
