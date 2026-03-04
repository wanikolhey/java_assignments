import java.time.LocalDate;
import java.util.ArrayList;
public class ArrayListOfBooks {
    public static void main(String[] args){
        /*Book b1 = new Book();
        Book b2 = new Book("To Kill a Mockingbird", "Harper Lee", 399.99);
        Book b3 = new Book("1984", "George Orwell", LocalDate.of(200, 6, 8), 299.99);
        Book b4 = new Book(b3);
        ArrayList<Book> alb = new ArrayList<Book>();
        b1.title = "The Great Gatsby";
        b1.author = "F. Scott Fitzgerald";
        b1.genre = "Fiction";
        b3.genre = "Fiction";
        b2.genre = "Dystopian";
        b4.genre = "Dystopian";
        Book b5 = new Book("The Catcher in the Rye", "J.D. Salinger", LocalDate.of(2006, 7, 16), 349.99);
        b5.genre = "Fiction";
        alb.add(b1);
        alb.add(b2);
        alb.add(b3);
        alb.add(b4);
        alb.add(b5); */
        ArrayList<Book> alb = new ArrayList<Book>();
        try
        { 
        
            Book b1 = new Book();
            Book b2 = new Book("To Kill a Mockingbird", "Harper Lee", 399.99, "Fiction");
            Book b3 = new Book("1984", "George Orwell", LocalDate.of(200, 6, 8), 299.99);
            Book b4 = new Book(b3);
            b1.title = "The Great Gatsby";
            b1.author = "F. Scott Fitzgerald";
            b1.genre = "Fiction";
            b3.genre = "Fiction";
            b2.genre = "Dystopian";
            b4.genre = "Dystopian";
            Book b5 = new Book("ABC123", "J.D. Salinger", LocalDate.of(2006, 7, 16), 349.99);
            b5.genre = "Fiction";
            alb.add(b1);
            alb.add(b2);
            alb.add(b3);
            alb.add(b4);
            alb.add(b5);
        }
        catch (InvalidBookException e)
        {
            System.out.println("Error creating book: " + e.getMessage());
        }
        System.out.println("Books in the ArrayList:");
        for (Book b : alb) {
            System.out.println("Title: " + b.title);
            System.out.println("Author: " + b.author);
            System.out.println("Price: " + b.price);
            System.out.println("Genre: " + b.genre);
            System.out.println("Date of Publication: " + b.dateofPub);
            System.out.println();
        }
        alb.forEach(f->{
            if(f.genre.equalsIgnoreCase("Fiction")){
                System.out.println(f.title);
            }
        });
    }
}
