package hafiz.mailtracker.Fragments.User;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import hafiz.mailtracker.Adapter.UserMailAdapter;
import hafiz.mailtracker.Fragments.BaseFragment;
import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;
import hafiz.mailtracker.services.MailChecker;
import hafiz.mailtracker.services.UserMailChecker;


public class UserMain extends BaseFragment {

    public UserMain() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!isMyServiceRunning(UserMailChecker.class)) {
            Intent intent = new Intent(getActivity(), UserMailChecker.class);
            getActivity().startService(intent);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference UserMail = mAuth.getReference("UserMail");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        email = email.replace("@", "at");
        email = email.replace(".", "dot");
        final ArrayList<Mail> Mail_data = new ArrayList<>();
        UserMail.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    Mail m = ds.getValue(Mail.class);
                    Mail_data.add(m);
                }
                UserMailAdapter adapter = new UserMailAdapter(getActivity(),R.layout.user_list_item,Mail_data);
                ListView lv = getActivity().findViewById(R.id.user_mail_list);
                lv.setAdapter(adapter);
                lv.setDivider(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
