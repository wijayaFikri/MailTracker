package hafiz.mailtracker.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import hafiz.mailtracker.Model.User_data;
import hafiz.mailtracker.R;


public class InputMail extends BaseFragment {
    public InputMail() {
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
        return inflater.inflate(R.layout.fragment_input_mail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference myRef = mAuth.getReference(mypref);
        final ArrayList<String> Data_array = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data_array.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User_data user_data = data.getValue(User_data.class);
                    assert user_data != null;
                    Data_array.add(user_data.getEmail());
                }
                for (String cek: Data_array) {
                    Log.d("cek", cek);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),android.R.layout.select_dialog_item,Data_array);
        AutoCompleteTextView atv = getActivity().findViewById(R.id.autoCompleteTextView2);
        atv.setThreshold(1);
        atv.setAdapter(adapter);
    }
}
