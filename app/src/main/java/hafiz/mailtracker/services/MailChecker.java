package hafiz.mailtracker.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;

public class MailChecker extends Service {
    int flag;

    @Override

    public void onCreate() {
        flag = 0;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Mail");
        ref.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Mail data = dataSnapshot.getValue(Mail.class);
                String Sender = data.getSender();
                String about = data.getAbout();
                if ( flag == 0 ){
                    flag +=1;
                }  else{
                    NotificationChannel channel = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        channel = new NotificationChannel(
                                "id",
                                "name",
                                NotificationManager.IMPORTANCE_HIGH);
                        channel.setDescription("Description");
                        final NotificationManager notificationManager = (NotificationManager)
                                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        if (notificationManager != null) {
                            notificationManager.createNotificationChannel(channel);
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(Objects.requireNonNull(getApplicationContext()), "id")
                                .setSmallIcon(R.drawable.ic_account_box_black_24dp)
                                .setContentTitle(Sender)
                                .setContentText(about)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        notificationManager.notify(1, builder.build());
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return START_STICKY;
        // For each start request, send a message to start a job and deliver the
    }
        @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
