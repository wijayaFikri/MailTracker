package hafiz.mailtracker.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hafiz.mailtracker.Activity.Authorization;
import hafiz.mailtracker.R;


public class Login extends BaseFragment {
    public Login() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText email_et = getActivity().findViewById(R.id.email_et);

        final EditText password_et = getActivity().findViewById(R.id.password_et);

        Button bttn = getActivity().findViewById(R.id.Login_bttn);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                ((Authorization)getActivity()).login(email,password);
                String link = "http://basondatabase.000webhostapp.com/Login.php?email=" + email + "&password=" + password;
            }
        });
        TextView registTV = getActivity().findViewById(R.id.regist_tv);
        registTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment(new Register(),1,R.id.frameLayout);
            }
        });

    }
}
