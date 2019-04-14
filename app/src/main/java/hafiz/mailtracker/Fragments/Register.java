package hafiz.mailtracker.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import hafiz.mailtracker.View.Authorization;
import hafiz.mailtracker.R;

public class Register extends BaseFragment {
    private FirebaseAuth mAuth;
    public Register() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Button Regist = getActivity().findViewById(R.id.regist_bttn);
        Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailET = getActivity().findViewById(R.id.email_ed);
                String email = emailET.getText().toString();
                EditText passwordET = getActivity().findViewById(R.id.password_ed);
                String password = passwordET.getText().toString();
                EditText nameET = getActivity().findViewById(R.id.fullname_ed);
                String name = nameET.getText().toString();
                ((Authorization)getActivity()).register(email,password,name);
                }
        });
    }
}
