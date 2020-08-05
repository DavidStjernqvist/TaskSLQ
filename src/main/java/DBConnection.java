import Models.*;

import java.util.*;
import java.sql.*;

public class DBConnection {

    private static String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private static Connection conn = null;
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    private static ArrayList<Genre> genres = new ArrayList<Genre>();

    //Checks conn and opens the connection in the constructor
    public DBConnection(){
        checkConn();
    }
    public void checkConn(){
        try{
            conn = DriverManager.getConnection(URL);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    //Recieves the preparedstatement and executes the query
    public void executeCustomerQuery(PreparedStatement preparedStatement){
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            //Adds the values to the Arraylist
            while (resultSet.next()){
                customers.add(new Customer(
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName")
                ));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            try{
                //Closes the connection
                conn.close();
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    //Recieves the preparedstatement and executes the query
    public void executeGenreQuery(PreparedStatement preparedStatement) {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            //Gets the biggestOccurance, query sort by desc order, therefore it starts with the biggest value first
            //If the value change and become smaller than the biggest occurance, break the while loop
            int biggestOccurance = 0;
            while (resultSet.next()) {
                int temp = resultSet.getInt("value_ocurrance");
                if(temp >= biggestOccurance)
                {
                    biggestOccurance = temp;
                    genres.add(new Genre(
                            resultSet.getInt("GenreId"),
                            resultSet.getString("Name")
                    ));
                }
                else
                    break;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                //Closes the connection
                conn.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    //Gets the Query and executes it
    public Integer getRandomCharacterId(PreparedStatement preparedStatement) {
        int randomId = 0;
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randomId = resultSet.getInt("CustomerId");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                //Close the connection
                conn.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return randomId;
    }
    //Returns the connection
    public Connection getConn(){
        return conn;
    }
    public ArrayList<Customer> getCustomers(){
        return customers;
    }
    public ArrayList<Genre> getGenres(){
        return genres;
    }
}
