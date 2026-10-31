package Biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void add(Book book){
        if(book == null){
            throw new IllegalArgumentException("Books fild's is empty");
        }
        else{
            for(Book isbnBook : books){
                if(isbnBook.getIsbn().equalsIgnoreCase(book.getIsbn())){
                    throw new IllegalArgumentException("The book " + isbnBook.getIsbn() + " | " + isbnBook.getAuthor() + " is already taken");
                }
            }
            books.add(book);
        }
    }

    public void remove(String isbn){
        Book book = findByIsbn(isbn);
        books.remove(book);
    }

    public List<Book> listAll(){
        return new ArrayList<>(books);
    }
    public List<Book> search(String text){
        List<Book> newBooks = new ArrayList<>();
        if(books == null){
            return newBooks;
        }
        for(Book b: books){
            if(b.getTitle().toLowerCase().contains(text.toLowerCase())){
                newBooks.add(b);
            }
        }
        return newBooks;

    }
    public Book findByIsbn(String isbn){
        for(Book b : books){
            if(b.getIsbn().equalsIgnoreCase(isbn));
            return b;
        }
        throw new IllegalArgumentException("ISBN is not found");
    }

}