package hafiz.mailtracker.Model;

public class User_data {
    private String email;
    private String Username;
    private String token;
    private String Address;
    public User_data(){
        //empty constructor
    }
     public User_data(String email, String Username, String address){
        this.email = email;
        this.Username = Username;
        this.token = token;
        this.Address = address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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
