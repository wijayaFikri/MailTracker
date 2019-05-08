package hafiz.mailtracker.Fragments.MailReceiver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import hafiz.mailtracker.Fragments.BaseFragment;
import hafiz.mailtracker.R;

public class MailReceiver_main extends BaseFragment {
    public MailReceiver_main() {
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
        return inflater.inflate(R.layout.fragment_mailreceiver_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView inputMail = Objects.requireNonNull(getActivity()).findViewById(R.id.inputMail_cv);
        inputMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment(new InputMail(),1,R.id.fragment_container);
            }
        });
        CardView HistoryCv = getActivity().findViewById(R.id.history_cv);
        HistoryCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment(new History(),1,R.id.fragment_container);
            }
        });
    }
}
