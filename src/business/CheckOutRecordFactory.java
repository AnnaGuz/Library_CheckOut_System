package business;

public class CheckOutRecordFactory {

	private CheckOutRecordFactory() {}
	
	
	public static CheckOutRecord checkoutBook(LibraryMember m,BookCopy b) {
		
		return new CheckOutRecord(m,b);
	}
	
}
