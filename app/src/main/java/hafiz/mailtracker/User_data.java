package hafiz.mailtracker;

public class User_data {
    private String email;
    private String Username;
    User_data(){
        //empty constructor
    }
    User_data(String email, String Username){
        this.email = email;
        this.Username = Username;
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
