import java.util.*;
import java.sql.*;
import Models.*;

public class Main {
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    private static ArrayList<Genre> genres = new ArrayList<Genre>();

    public static void main(String[] args) {
        int number = getCheckedNumber();
        queryCustomer(number);
        queryGenre(number);
        printCustomersMostPopularGenre();
    }
    private static Integer getCheckedNumber() {
        int number = 0;
        //Checks if the user value is valid, else the number will get randomized by a valid character id
        try {
            number = inputNumber();
        } catch (Exception ex) {
            System.out.println("Not a valid input, id is randomized: ");
            number = queryRandomNumber();
        }
        return number;
    }
    private static Integer inputNumber() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter id: ");
        int number = scan.nextInt();
        return number;
    }
    //Randomizes a character and gets the id(number)
    private static Integer queryRandomNumber(){
        int number = 0;
        try{
            DBConnection db = new DBConnection();
            Connection conn = db.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT CustomerId FROM Customer ORDER BY RANDOM() LIMIT 1"
            );
            number = db.getRandomCharacterId(preparedStatement);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return number;
    }

    private static void queryGenre(int number){
        try {
            //Opens the connection
            DBConnection db = new DBConnection();
            Connection conn = db.getConn();

            //Creates a query thats calculates all of the ocurrences of GenreId in Genre
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT Genre.GenreId, Genre.Name, COUNT(Genre.GenreId) AS `value_ocurrance` FROM Genre " +
                            "INNER JOIN Track T on Genre.GenreId = T.GenreId " +
                            "INNER JOIN InvoiceLine IL on T.TrackId = IL.TrackId " +
                            "INNER JOIN Invoice I on IL.InvoiceId = I.InvoiceId " +
                            "INNER JOIN Customer C on I.CustomerId = C.CustomerId " +
                            "WHERE C.CustomerId =? GROUP BY Genre.GenreId, Genre.Name " +
                            "ORDER BY `value_ocurrance` DESC");
            //Injects the query on ?, searches for the user inputs CustomerId
            preparedStatement.setInt(1, number);
            db.executeGenreQuery(preparedStatement);
            //recieves the genres and puts in Arraylist
            genres = db.getGenres();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    private static void queryCustomer(int number){
        try{
            //Opens connection
            DBConnection db = new DBConnection();
            Connection conn = db.getConn();
            //Sends a query to find Customer by id
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT FirstName, LastName FROM Customer WHERE CustomerId =?"
            );
            //Replaces ? with the user input number
            preparedStatement.setInt(1, number);
            db.executeCustomerQuery(preparedStatement);
            //Gets the customers in a ArrayList
            customers = db.getCustomers();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    //Prints out every Customer and their belonging favourite genres
    private static void printCustomersMostPopularGenre(){
        for (Customer customer:customers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
            System.out.println("---Favourite genres---");
            for (Genre genre: genres) {
                System.out.println(genre.getName());
            }
            System.out.println("---------------------");
        }
    }
}
