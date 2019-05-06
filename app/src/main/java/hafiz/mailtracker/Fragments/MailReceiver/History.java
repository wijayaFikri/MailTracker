package hafiz.mailtracker.Fragments.MailReceiver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hafiz.mailtracker.Adapter.MailAdapter;
import hafiz.mailtracker.Fragments.BaseFragment;
import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;


public class History extends BaseFragment {
    DatabaseReference Mymail;
    public History() {
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
        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mymail = mAuth.getReference(mailref);
        final List<Mail> MailList = new ArrayList<>();
        Mymail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()
                     ) {
                    Mail Amail = ds.getValue(Mail.class);
                    if (Amail != null) {
                        Log.d("TAG",Amail.getReceiver());
                    }
                    MailList.add(0,Amail);
                }
                ListView lv = Objects.requireNonNull(getActivity()).findViewById(R.id.History_list);
                if( lv != null ) {
                    MailAdapter listadapt = new MailAdapter(getActivity(), R.layout.listviewitem, MailList,1);
                    lv.setDivider(null);
                    lv.setAdapter(listadapt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
