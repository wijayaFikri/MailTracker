package hafiz.mailtracker.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import hafiz.mailtracker.R;

public class main extends BaseFragment {
    public main() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button inputMail = Objects.requireNonNull(getActivity()).findViewById(R.id.inputMail);
        inputMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment(new InputMail(),1,R.id.fragment_container);
            }
        });
    }
}
