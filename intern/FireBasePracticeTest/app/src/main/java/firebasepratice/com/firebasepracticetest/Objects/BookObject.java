package firebasepratice.com.firebasepracticetest.Objects;

public class BookObject {
    private String bookName,author,ISBN,publication,imageUrl,genere,quantity;

    public BookObject() {
    }

    public BookObject(String bookName, String author, String ISBN, String publication,String imageUrl, String genere,String quantity) {

        this.bookName = bookName;
        this.author = author;
        this.ISBN = ISBN;
        this.publication = publication;
        this.imageUrl =imageUrl;
        this.genere = genere;
        this.quantity = quantity;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
