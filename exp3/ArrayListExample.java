import java.lang.reflect.Array;
import java.util.ArrayList;
public class ArrayListExample {
    public static void main(String[] args) {
        // Create an ArrayList of integer objects
        ArrayList<Integer> IntArrList = new ArrayList<Integer>();

        // Add some integer objects to the ArrayList
        IntArrList.add(15);
        IntArrList.add(21);
        IntArrList.add(33);
        for (int i=0;i<=20;i++){
            if (i%2==0)
                IntArrList.add(i);       
        }
        for (Integer i : IntArrList) {
            System.out.println(i);
        }
        ArrayList<Integer> List2 = new ArrayList<Integer>();
        List2.add(100);
        List2.add(1,32);
        List2.add(45);
        List2.add(100);
        IntArrList.addAll(List2);
        IntArrList.forEach(i-> System.out.println(i));
        System.out.println("Size of the ArrayList: " + IntArrList.size());
        System.out.println("Index of 33:"+IntArrList.indexOf(33));
        System.out.println("Index of 100:"+IntArrList.indexOf(100));
        IntArrList.remove(Integer.valueOf(33));
        IntArrList.remove(4);
        IntArrList.forEach(i-> System.out.println(i));

        ArrayList<String> words = new ArrayList<String>();
        words.add("One");
        words.add("fish");
        words.add("Two");
        words.add("fish");
        words.add("Three");
        words.add("fish");
        words.add("Red");
        words.add("fish");
        words.add("Blue");
        boolean c = words.contains("fish");
        System.out.println("Does the list contain 'fish'? " + c);
        words.forEach(w->{
            if(w.equalsIgnoreCase("fish")){
                System.out.println(w);
            }
        });
        ArrayList<String> Fruits = new ArrayList<String>();
        Fruits.add("Apple");
        Fruits.add("Banana");
        Fruits.add("Cherry");
        Fruits.add("Date");
        Fruits.add("Guava");
        Fruits.add("Grapes");
        Fruits.forEach(f-> {
            if(f.startsWith("G")){
                System.out.println(f);
            }
        });
        /* 
        ArrayList<Book> bookList = new ArrayList<Book>();
        Book B1 = new Book("Java Programming", "John Doe", 200.00);
        Book B2 = new Book("Python Programming", "James Salt", 219.99);
        bookList.add(B1);
        bookList.add(B2);
        bookList.forEach(b-> System.out.println(b.title+" by "+b.author));
        Book B3 = new Book(B1);
        B3.title = "C++ Programming";
        B3.genre="Programming";
        B3.price=599.99;
        bookList.add(B3);
        bookList.forEach(b-> System.out.println(b.title+" by "+b.author+" priced at "+b.price));
        */
    }
}
