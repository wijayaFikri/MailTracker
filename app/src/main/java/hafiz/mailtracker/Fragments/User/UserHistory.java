package hafiz.mailtracker.Fragments.User;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import hafiz.mailtracker.Adapter.UserHistoryAdapter;
import hafiz.mailtracker.Fragments.BaseFragment;
import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;


public class UserHistory extends BaseFragment {

    public UserHistory() {
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
        return inflater.inflate(R.layout.fragment_user_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv = getActivity().findViewById(R.id.toolbar_imageView);
        iv.setBackgroundResource(R.drawable.ic_home_black_36dp);
        TextView toolbar_text = getActivity().findViewById(R.id.toolbar_textView);
        toolbar_text.setText("HOME");
        LinearLayout user_history_linear = getActivity().findViewById(R.id.history_icon);
        user_history_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment(new UserMain(),0,R.id.fragment_container);
            }
        });
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
                    if(m.getReceived().equals("1")){
                        Mail_data.add(m);
                    }

                }
                final UserHistoryAdapter adapter = new UserHistoryAdapter(getActivity(),R.layout.user_history_item,Mail_data);
                ListView lv = getActivity().findViewById(R.id.user_history_list);
                lv.setAdapter(adapter);
                lv.setDivider(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
