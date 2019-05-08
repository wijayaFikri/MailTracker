package hafiz.mailtracker.Fragments.MailReceiver;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Random;

import hafiz.mailtracker.Fragments.Admin.AdminMailList;
import hafiz.mailtracker.Fragments.BaseFragment;
import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.Model.User_data;
import hafiz.mailtracker.R;


public class InputMail extends BaseFragment {
    DatabaseReference MailRef;
    DatabaseReference UserSideMail;

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
        UserSideMail = mAuth.getReference("UserMail");
        DatabaseReference myRef = mAuth.getReference(mypref);
        final ArrayList<User_data> Data_array = new ArrayList<>();
        final ArrayList<String> username_data = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data_array.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User_data user_data = data.getValue(User_data.class);
                    assert user_data != null;
                    Data_array.add(user_data);
                    username_data.add(user_data.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.select_dialog_item, username_data);
        final AutoCompleteTextView atv = getActivity().findViewById(R.id.autoCompleteTextView2);
        atv.setThreshold(1);
        atv.setAdapter(adapter);
        Button submitBtn = getActivity().findViewById(R.id.Submitbtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText from_et = Objects.requireNonNull(getActivity()).findViewById(R.id.from_et);
                EditText about_et = getActivity().findViewById(R.id.about_et);
                EditText receiver_name_et = getActivity().findViewById(R.id.Mail_Receiver_et);
                CheckBox cb = getActivity().findViewById(R.id.checkBox);
                final String from = from_et.getText().toString();
                if (from.equals("")){
                    from_et.setError("Mail sender must not empty");
                    return;
                }
                final String about = about_et.getText().toString();
                if (about.equals("")){
                    about_et.setError("About mail must not be empty");
                    return;
                }
                final String to = atv.getText().toString();
                final String urgent_status;
                if (!username_data.contains(to)) {
                    atv.setError("Username not found");
                    return;
                }
                if (cb.isChecked()) {
                    urgent_status = "yes";
                } else {
                    urgent_status = "no";
                }
                String Receiver = receiver_name_et.getText().toString();
                if (Receiver.equals("")){
                    receiver_name_et.setError("Receiver must not be empty!");
                }
                Date c = Calendar.getInstance().getTime();
                int idx = username_data.indexOf(to);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);

                String modifiedEmail = Data_array.get(idx).getEmail().replace("@", "at");
                modifiedEmail = modifiedEmail.replace(".", "dot");
                DatabaseReference newref = UserSideMail.child(modifiedEmail).push();
                String key = newref.getKey();
                System.out.println(key);

                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                int number = rd.nextInt(1000);
                Character[] Alphabet = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
                sb.append(number);
                sb.append(Alphabet[rd.nextInt(25)]);
                sb.append(Alphabet[rd.nextInt(25)]);
                sb.append(rd.nextInt(100));
                sb.append(Alphabet[rd.nextInt(25)]);
                Mail mail = new Mail(key, from, Data_array.get(idx).getEmail(), about, urgent_status, formattedDate, Receiver, Data_array.get(idx).getAddress(), sb.toString());
                newref.setValue(mail);

                MailRef.child(key).setValue(mail, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Success")
                                .setTitle("Input Mail")
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        nextFragment(new MailReceiver_main(), 1, R.id.fragment_container);
                                    }
                                });

                        // 3. Get the AlertDialog from create()
                        final AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

            }
        });
    }
}
