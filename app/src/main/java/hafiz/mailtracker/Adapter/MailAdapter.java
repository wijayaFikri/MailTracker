package hafiz.mailtracker.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import hafiz.mailtracker.Fragments.Admin.AdminMailList;
import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;

public class MailAdapter extends ArrayAdapter<Mail> {
    private int resourceLayout;
    private Context mContext;
    private int marker;

    // marker meaning : 1 for MailReceiver, 2 for Admin
    public MailAdapter(@NonNull Context context, int resource, List<Mail> ListItem, int Marker) {
        super(context, resource, ListItem);
        this.resourceLayout = resource;
        this.mContext = context;
        marker = Marker;
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
            EditText et1 = v.findViewById(R.id.FromHty);
            EditText et2 = v.findViewById(R.id.ToHty);
            EditText et3 = v.findViewById(R.id.AboutHty);
            TextView tv1 = v.findViewById(R.id.Date_tv);

            if (et1 != null) {
                et1.setText(m.getSender());
                et1.setFocusable(false);
            }

            if (et2 != null) {
                et2.setText(m.getReceiver_name());
                et2.setFocusable(false);
            }

            if (et3 != null) {
                et3.setText(m.getAbout());
                et3.setFocusable(false);
            }
            if (tv1 != null) {
                tv1.setText(m.getDate());
            }
            if (marker == 2) {
                TextView tv3 = v.findViewById(R.id.Address_tv);
                tv3.setText(m.getReceiver_address());
                TextView tv2 = v.findViewById(R.id.confirm_txt);
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.setStatus("1");
                        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
                        DatabaseReference db = mAuth.getReference("Mail");
                        db.child(m.getID()).setValue(m);
                        Toast.makeText(mContext, m.getSender(),
                                Toast.LENGTH_LONG).show();
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Fragment myFragment = new AdminMailList();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                    }
                });


            }

        }

        return v;
    }
}
