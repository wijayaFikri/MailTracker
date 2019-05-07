package hafiz.mailtracker.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import hafiz.mailtracker.Activity.MainActivity;
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
                final TextView tv2 = v.findViewById(R.id.confirm_txt);
                EditText et4 = v.findViewById(R.id.StatusHty);
                et4.setFocusable(false);
                if (m.getStatus().equals("0")) {
                    et4.setText("NOT RECEIVED");
                    tv2.setText("CONFIRM");
                } else {
                    tv2.setText("VERIFY");
                    et4.setText("RECEIVED");
                }
                TextView tv3 = v.findViewById(R.id.Address_tv);
                tv3.setText(m.getReceiver_address());
                if (m.getUrgent().equals("yes")) {
                    TextView importance = v.findViewById(R.id.important_Tv);
                    importance.setVisibility(View.VISIBLE);
                    LinearLayout ll = v.findViewById(R.id.parent_linear);
                    ll.setBackgroundResource(R.drawable.card_bg);
                } else {
                    TextView importance = v.findViewById(R.id.important_Tv);
                    importance.setVisibility(View.INVISIBLE);
                    LinearLayout ll = v.findViewById(R.id.parent_linear);
                    ll.setBackground(null);
                }
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tv2.getText().equals("CONFIRM")) {
                            m.setStatus("1");
                            FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
                            DatabaseReference db = mAuth.getReference("Mail");
                            DatabaseReference UserSideDb = mAuth.getReference("UserMail");
                            String modifiedEmail = m.getReceiver();
                            modifiedEmail = modifiedEmail.replace("@", "at");
                            modifiedEmail = modifiedEmail.replace(".", "dot");
                            UserSideDb.child(modifiedEmail).child(m.getID()).setValue(m);
                            db.child(m.getID()).setValue(m);
                            Toast.makeText(mContext, m.getSender(),
                                    Toast.LENGTH_LONG).show();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Fragment myFragment = new AdminMailList();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                        } else {
                            // 1. Instantiate an AlertDialog.Builder with its constructor
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                            // 2. Chain together various setter methods to set the dialog characteristics
                            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View dialogView = li.inflate(R.layout.pindialog, null);
                            Button verifyBtn = dialogView.findViewById(R.id.Verify_Btn);
                            final EditText et5 = dialogView.findViewById(R.id.Pin_Et);

                            builder.setMessage("Please input the pin")
                                    .setTitle("VERIFY")
                                    .setView(dialogView);

                            // 3. Get the AlertDialog from create()
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            verifyBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String pin = et5.getText().toString();
                                    if (pin.equals(m.getPIN())) {
                                        m.setReceived("1");
                                        FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
                                        DatabaseReference db = mAuth.getReference("Mail");
                                        DatabaseReference UserSideDb = mAuth.getReference("UserMail");
                                        String modifiedEmail = m.getReceiver();
                                        modifiedEmail = modifiedEmail.replace("@", "at");
                                        modifiedEmail = modifiedEmail.replace(".", "dot");
                                        UserSideDb.child(modifiedEmail).child(m.getID()).setValue(m);
                                        db.child(m.getID()).setValue(m);
                                        dialog.dismiss();
                                        SuccessDialog();
                                    }
                                }
                            });
                        }

                    }
                });


            } else if (marker == 3) {
                TextView tv2 = v.findViewById(R.id.confirm_txt);
                if (m.getStatus().equals("1")) {
                    tv2.setText("Completed");

                } else {
                    tv2.setText("Incomplete");
                    tv2.setTextColor(Color.parseColor("#ea1010"));
                }
                TextView tv3 = v.findViewById(R.id.Address_tv);
                tv3.setText(m.getReceiver_address());
            }

        }

        return v;
    }

    public void SuccessDialog() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        builder2.setMessage("Success")
                .setTitle("VERIFY")
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        AppCompatActivity activity = (AppCompatActivity) mContext;
                        Fragment myFragment = new AdminMailList();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                    }
                });

        // 3. Get the AlertDialog from create()
        final AlertDialog dialog2 = builder2.create();
        dialog2.show();
    }
}
