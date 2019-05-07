package hafiz.mailtracker.Fragments.User;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;

public class UserTrack extends Fragment {


    public UserTrack() {
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
        return inflater.inflate(R.layout.fragment_user_track, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String gson = bundle.getString("Data");
        Mail data = new Gson().fromJson(gson,Mail.class);
        TextView sender = getActivity().findViewById(R.id.sender_textView);
        sender.setText(data.getSender());
        TextView about = getActivity().findViewById(R.id.about_textview);
        about.setText(data.getAbout());
        TextView date = getActivity().findViewById(R.id.date_tv);
        date.setText(data.getDate());
        TextView status = getActivity().findViewById(R.id.mail_status);
        if (data.getStatus().equals("1")){
            status.setText("YES");
        }
        if (data.getReceived().equals("1")){
            TextView receiver_name = getActivity().findViewById(R.id.mail_receiver_tv);
            receiver_name.setText(data.getReceiver_name());
        }
        TextView PinTv = getActivity().findViewById(R.id.PIN_Tv);
        PinTv.setText(data.getPIN());
    }
}
