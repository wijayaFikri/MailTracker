package hafiz.mailtracker.Model;

public class User_data {
    private String email;
    private String Username;
    private String token;
    public User_data(){
        //empty constructor
    }
     public User_data(String email, String Username){
        this.email = email;
        this.Username = Username;
        this.token = token;
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
