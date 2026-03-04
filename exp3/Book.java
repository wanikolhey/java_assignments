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
    public Book(String t, double p) throws InvalidBookException
    {
        if ("".equals(t))
            throw new InvalidBookException("Title cannot be blank");
        title=t;
        if (p<0)
            throw new InvalidBookException("Price cannot be negative");
        price=p;
    }
    //parameterized constructor 2
    public Book(String t, String a, double p, String g) throws InvalidBookException{
        {
        if ("".equals(t))
            throw new InvalidBookException("Title cannot be blank");
        title = t;
        if ("".equals(a))
            throw new InvalidBookException("Author cannot be blank");
        author = a;
        if (p<0)
            throw new InvalidBookException("Price cannot be negative");
        price=p;
        }
        String[] genres = {"Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Mystery", "Biography", "History", "Children"};
        boolean validGenre = false;
        for (String gen : genres) {
            if (gen.equalsIgnoreCase(g)) {
                validGenre = true;
                break;
            }
        }
        if (!validGenre)
            throw new InvalidBookException("Invalid genre");
        genre = g;
    }
    //parameterized constructor 3
    public Book(String t, String a, LocalDate d, double p) throws InvalidBookException{
        {
        if ("".equals(t))
            throw new InvalidBookException("Title cannot be blank");
        title = t;
        author = a;
        if (p<0)
            throw new InvalidBookException("Price cannot be negative");
        price=p;
        if (d.isAfter(LocalDate.now()))
            throw new InvalidBookException("Date of publication cannot be in the future");
        }
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