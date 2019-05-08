package hafiz.mailtracker.Fragments.Admin;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import hafiz.mailtracker.Activity.MainActivity;
import hafiz.mailtracker.Fragments.BaseFragment;
import hafiz.mailtracker.R;
import hafiz.mailtracker.services.MailChecker;


public class AdminMain extends BaseFragment {


    public AdminMain() {
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
        return inflater.inflate(R.layout.fragment_admin_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView MailCv = getActivity().findViewById(R.id.Mail_cv);
        if (!isMyServiceRunning(MailChecker.class)) {
            Intent intent = new Intent(getActivity(), MailChecker.class);
            getActivity().startService(intent);
        }
        MailCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value","0");
                nextFragment(new AdminMailList(),1,R.id.fragment_container,bundle);
            }
        });
        CardView HistoryCv = getActivity().findViewById(R.id.history_cv);
        HistoryCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value","1");
                nextFragment(new AdminMailList(),1,R.id.fragment_container,bundle);
            }
        });
    }

}
