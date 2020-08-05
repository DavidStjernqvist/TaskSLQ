package Models;

public class Customer {
    private int CustomerId;
    private String FirstName;
    private String LastName;
    private String Company;
    private String Address;
    private String City;
    private String Country;
    private String PostalCode;
    private String Phone;
    private String Fax;
    private String Email;
    private int SupportRepId;

    public Customer(String firstName, String lastName){
        this.FirstName = firstName;
        this.LastName = lastName;
    }
    public Customer(int customerId, String firstName, String lastName, String company, String address, String city, String state, String country, String postalCode, String phone, String fax, String email, int CustomerId){
        this.CustomerId = CustomerId;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Company = company;
        this.Address = address;
        this.City = city;
        this.Country = country;
        this.PostalCode = postalCode;
        this.Phone = phone;
        this.Fax = fax;
        this.Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }
}
