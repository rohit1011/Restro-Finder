package firebasepratice.com.firebasepracticetest.Objects;

public class BookEntryObject {
    public String isbn, name, cardNumber, phoneNumber,borrowedDate,returnDate;

    public BookEntryObject(String ISBN, String name, String cardNumber, String phoneNumber, String borrowedDate, String returnDate) {
        this.isbn = ISBN;
        this.name = name;
        this.cardNumber = cardNumber;
        this.phoneNumber = phoneNumber;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}