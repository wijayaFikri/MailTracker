package hafiz.mailtracker.Model;

public class User_data {
    private String email;
    private String Username;
    private String Address;
    public User_data(){
        //empty constructor
    }
     public User_data(String email, String Username, String address){
        this.email = email;
        this.Username = Username;
        this.Address = address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return email;
    }
}
