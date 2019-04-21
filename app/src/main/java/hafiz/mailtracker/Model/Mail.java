package hafiz.mailtracker.Model;

public class Mail {
    private String ID;
    private String Sender;
    private String Receiver;
    private String about;
    private String Urgent;
    private String date;
    private String Receiver_name;
    private String Status;
    private String Received;
    public Mail() {
        //empty constructora
    }

    public Mail(String id,String Sender, String Receiver, String about, String Urgent,String date,String Receiver_name) {
        this.ID = id;
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.about = about;
        this.Urgent = Urgent;
        this.date = date;
        this.Receiver_name = Receiver_name;
        this.Status = "0";
        this.Received = "0";
    }

    public void setReceived(String received) {
        Received = received;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReceived() {
        return Received;
    }

    public String getStatus() {
        return Status;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getReceiver_name() {
        return Receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        Receiver_name = receiver_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setUrgent(String urgent) {
        this.Urgent = urgent;
    }

    public String getUrgent() {
        return Urgent;
    }

    public void setReceiver(String receiver) {
        this.Receiver = receiver;
    }

    public void setSender(String sender) {
        this.Sender = sender;
    }

    public String getAbout() {
        return about;
    }

    public String getReceiver() {
        return Receiver;
    }

    public String getSender() {
        return Sender;
    }
}
