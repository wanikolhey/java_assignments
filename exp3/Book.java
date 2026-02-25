package exp3;
import java.time.LocalDate;

public class Book {
    public String title;
    public String author;
    public double price;
    private String ISBN;
    public String genre;
    public LocalDate dateofPub;

    //default constructor
    public Book(){
        title = "The Great Gatsby";
        author = "F. Scott Fitzgerald";
        price = 499.99;
        ISBN = "ISB978316100";
        genre = "Fiction";
        dateofPub = LocalDate.of(1925, 4, 10);
    }
    // Parameterized constructors 1
    public Book(String t, double p){
        title = t;
        price = p;
    }
    //parameterized constructor 2
    public Book(String t, String a, double p){
        title = t;
        author = a;
        price = p;
    }
    //parameterized constructor 3
    public Book(String t, String a, LocalDate d, double p){
        title = t;
        author = a;
        dateofPub = d;
        price = p;
    }
    // Copy constructor
    public Book(Book b){
        title = b.title;
        author = b.author;
        price = b.price;
        ISBN = b.ISBN;
        genre = b.genre;
        dateofPub = b.dateofPub;
    }
    
}