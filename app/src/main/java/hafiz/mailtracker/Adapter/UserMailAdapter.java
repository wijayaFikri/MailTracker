package hafiz.mailtracker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import hafiz.mailtracker.Activity.MainActivity;
import hafiz.mailtracker.Fragments.Admin.AdminMailList;
import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;

public class UserMailAdapter extends ArrayAdapter<Mail> {
    private int resourceLayout;
    private Context mContext;
    public UserMailAdapter(@NonNull Context context, int resource, List<Mail> ListItem) {
        super(context, resource, ListItem);
        this.resourceLayout = resource;
        this.mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        final Mail m = getItem(position);
        if (m != null) {
            TextView tv1 = v.findViewById(R.id.mail_sender_tv);
            TextView tv2 = v.findViewById(R.id.mail_about_tv);
            TextView tv3 = v.findViewById(R.id.mail_receiver_tv);
            TextView tv4 = v.findViewById(R.id.mail_receiver_address_tv);
            LinearLayout linearLayout = v.findViewById(R.id.user_linearLayout);

            if (tv1 != null) {
                String sender = "From : " + m.getSender();
                tv1.setText(sender);
                tv1.setFocusable(false);
            }

            if (tv2 != null) {
                String about = "About : " + m.getAbout();
                tv2.setText(about);
                tv2.setFocusable(false);
            }

            if (tv3 != null) {
                tv3.setText(m.getReceiver_name());
                tv3.setFocusable(false);
            }
            if (tv4 != null){
                tv4.setText(m.getReceiver_address());
                tv4.setFocusable(false);
            }

        }

        return v;
    }
}
