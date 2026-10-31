package Biblioteca;

public class Book{

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available = true;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(String isbn, String title){
        this(isbn, title, "Unknown author");
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void lend(){
        if(!isAvailable()){
            throw new IllegalArgumentException("This books is already borrow");
        }
        this.available = false;
    }
    public void giveBack(){
        if(isAvailable()){
            throw new IllegalArgumentException("This book available");
        }
        this.available = true;
    }

    @Override
    public String toString() {
        return isbn + " | " + title + " | " + author + " | " + available;
    }


    
    


}