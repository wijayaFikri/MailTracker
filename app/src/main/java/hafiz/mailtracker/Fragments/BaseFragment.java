package hafiz.mailtracker.Fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import hafiz.mailtracker.R;

public abstract class BaseFragment extends Fragment {
    public FirebaseDatabase mAuth;
    public String mypref = "user_data";
    public String mailref = "Mail";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseDatabase.getInstance();
    }

    public void nextFragment(Fragment fragment, int marker, int fl, Bundle bdl) {
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bdl);
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        if (marker > 0) {
            ft.addToBackStack("back pressed");
        }
        ft.replace(fl, fragment);
        ft.commit();
    }

    public void nextFragment(Fragment fragment, int marker, int fl) {
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        if (marker > 0) {
            ft.addToBackStack("back pressed");
        }
        ft.replace(fl, fragment);
        ft.commit();
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
