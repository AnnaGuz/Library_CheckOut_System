package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord implements Serializable {
	private static final long serialVersionUID = -315801007755647287L;
	List<CheckOutEntry> books=new ArrayList<>();
		
	public List<CheckOutEntry> getBooks() {
		return books;
	}

	public void setBooks(List<CheckOutEntry> books) {
		this.books = books;
	}

	CheckOutRecord(LibraryMember m,BookCopy b){
	int duedate=	b.getBook().getMaxCheckoutLength();
		books.add(new CheckOutEntry(b,LocalDate.now(),LocalDate.now().plusDays(duedate)));
		m.setCr(this);
		
		
	}

	@Override
	public String toString() {
		return "CheckOutRecord [books=" + books + "]";
	}
	
}
