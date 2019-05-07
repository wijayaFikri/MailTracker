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
        final String status;
        if (this.getArguments() != null) {
            status = this.getArguments().getString("value");
        } else {
            status = "0";
        }
        final List<Mail> NotReceived_List = new ArrayList<>();
        final List<Mail> Received_List = new ArrayList<>();
        final List<Mail> MailList = new ArrayList<>();
        Mymail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NotReceived_List.clear();
                Received_List.clear();
                MailList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    Mail Amail = ds.getValue(Mail.class);
                    if (status.equals("0")) {
                        if (Amail.getReceived().equals("0")) {
                            if (Amail != null && Integer.parseInt(Amail.getStatus()) == 0 && Amail.getUrgent().equals("no")) {
                                Log.d("TAG", Amail.getReceiver());
                                NotReceived_List.add(Amail);
                            } else if (Amail != null && Integer.parseInt(Amail.getStatus()) == 0 && Amail.getUrgent().equals("yes")) {
                                NotReceived_List.add(0, Amail);
                            } else if (Amail != null && Integer.parseInt(Amail.getStatus()) == 1 && Amail.getUrgent().equals("yes")) {
                                Received_List.add(0, Amail);
                            } else if (Amail != null && Integer.parseInt(Amail.getStatus()) == 1 && Amail.getUrgent().equals("no")) {
                                Received_List.add(Amail);
                            }
                        }
                    } else {
                        MailList.add(Amail);
                    }
                }
                MailList.addAll(0, NotReceived_List);
                MailList.addAll(0, Received_List);
                ListView lv = Objects.requireNonNull(getActivity()).findViewById(R.id.admin_listview);
                if (lv != null) {
                    MailAdapter listadapt;
                    if (status.equals("0")) {
                        listadapt = new MailAdapter(getActivity(), R.layout.admin_listview_item, MailList, 2);
                    } else {
                        listadapt = new MailAdapter(getActivity(), R.layout.admin_listview_item, MailList, 3);
                    }
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
