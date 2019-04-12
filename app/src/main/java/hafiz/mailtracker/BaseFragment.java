package hafiz.mailtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public abstract class BaseFragment extends Fragment {

    public void nextFragment(Fragment fragment, int marker){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        if(marker >0){ ft.addToBackStack("back pressed"); }
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }
}
