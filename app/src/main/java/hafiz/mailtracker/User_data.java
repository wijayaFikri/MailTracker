package hafiz.mailtracker;

public class User_data {
    private String email;
    private String Username;
    public User_data(String email,String Username){
        this.email = email;
        this.Username = Username;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return email;
    }
}
