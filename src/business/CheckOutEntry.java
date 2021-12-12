package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutEntry implements Serializable {
	private static final long serialVersionUID = 7995182485401158186L;
	private BookCopy book;
	private LocalDate checkoutdate;
	private LocalDate duedate;
	public LocalDate getCheckoutdate() {
		return checkoutdate;
	}
	public void setCheckoutdate(LocalDate checkoutdate) {
		this.checkoutdate = checkoutdate;
	}
	public LocalDate getDuedate() {
		return duedate;
	}
	 CheckOutEntry(BookCopy book, LocalDate checkoutdate, LocalDate duedate) {
	//	super();
		this.book = book;
		this.checkoutdate = checkoutdate;
		this.duedate = duedate;
		book.getBook().setNumCopies();
	}
	@Override
	public String toString() {
		return "CheckOutEntry [book=" + book.getBook().getTitle() + ", checkoutdate=" + checkoutdate + ", duedate=" + duedate + "]";
	}
	public void setDuedate(LocalDate duedate) {
		this.duedate = duedate;
	}

}
