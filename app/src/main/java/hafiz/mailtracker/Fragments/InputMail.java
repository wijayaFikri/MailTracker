package hafiz.mailtracker.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.Model.User_data;
import hafiz.mailtracker.R;


public class InputMail extends BaseFragment {
    DatabaseReference MailRef;
    public InputMail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_mail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MailRef = mAuth.getReference("Mail");
        DatabaseReference myRef = mAuth.getReference(mypref);
        final ArrayList<String> Data_array = new ArrayList<>();
        final ArrayList<String> username_data = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data_array.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User_data user_data = data.getValue(User_data.class);
                    assert user_data != null;
                    Data_array.add(user_data.getEmail());
                    username_data.add(user_data.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final int[] newid = new int[1];
        MailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id= "";
                for (DataSnapshot ds: dataSnapshot.getChildren()
                ) {
                    id =ds.getKey();
                }
                if (id != "") {
                    newid[0] = Integer.parseInt(id) + 1;
                } else {
                    newid[0] = 0;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),android.R.layout.select_dialog_item,username_data);
        final AutoCompleteTextView atv = getActivity().findViewById(R.id.autoCompleteTextView2);
        atv.setThreshold(1);
        atv.setAdapter(adapter);
        Button submitBtn = getActivity().findViewById(R.id.Submitbtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText from_et = Objects.requireNonNull(getActivity()).findViewById(R.id.from_et);
                EditText about_et = getActivity().findViewById(R.id.about_et);
                CheckBox cb = getActivity().findViewById(R.id.checkBox);
                final String from = from_et.getText().toString();
                final String about = about_et.getText().toString();
                final String to = atv.getText().toString();
                final String urgent_status;
                if (! username_data.contains(to)){
                    atv.setError("Username not found");
                    return;
                }
                if (cb.isChecked()){
                    urgent_status = "yes";
                } else {
                    urgent_status = "no";
                }
                Date c = Calendar.getInstance().getTime();
                int idx = username_data.indexOf(to);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                Mail mail = new Mail(String.valueOf(newid[0]),from,Data_array.get(idx),about,urgent_status,formattedDate,to);
                MailRef.child(String.valueOf(newid[0])).setValue(mail, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        nextFragment(new MailReceiver(),1,R.id.fragment_container);
                    }
                });

            }
        });
    }
}
