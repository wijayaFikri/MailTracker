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
        Button btn = getActivity().findViewById(R.id.AdminBtn);
        if (!isMyServiceRunning(MailChecker.class)) {
            Intent intent = new Intent(getActivity(), MailChecker.class);
            getActivity().startService(intent);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value","0");
                nextFragment(new AdminMailList(),1,R.id.fragment_container,bundle);
            }
        });
        Button btn3 = getActivity().findViewById(R.id.admin_hty_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value","1");
                nextFragment(new AdminMailList(),1,R.id.fragment_container,bundle);
            }
        });
        Button btn2 = getActivity().findViewById(R.id.test);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationChannel channel = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    channel = new NotificationChannel(
                            "id",
                            "name",
                            NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Description");
                    final NotificationManager notificationManager = (NotificationManager)
                            getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), "id")
                            .setSmallIcon(R.drawable.ic_account_box_black_24dp)
                            .setContentTitle("Test")
                            .setContentText("Test ugha")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationManager.notify(1, builder.build());
                }
            }
        });
    }

}
