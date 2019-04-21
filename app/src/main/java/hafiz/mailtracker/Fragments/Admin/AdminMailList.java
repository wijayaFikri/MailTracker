package hafiz.mailtracker.Fragments.Admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


public class AdminMailList extends BaseFragment {

    public AdminMailList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_mail_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference Mymail;
        Mymail = mAuth.getReference(mailref);
        final List<Mail> MailList = new ArrayList<>();
        Mymail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()
                ) {
                    Mail Amail = ds.getValue(Mail.class);
                    if (Amail != null && Integer.parseInt(Amail.getStatus()) == 0) {
                        Log.d("TAG", Amail.getReceiver());
                        MailList.add(Amail);
                    }
                }
                ListView lv = Objects.requireNonNull(getActivity()).findViewById(R.id.admin_listview);
                if( lv != null ) {
                    MailAdapter listadapt = new MailAdapter(getActivity(), R.layout.admin_listview_item, MailList,2);
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
