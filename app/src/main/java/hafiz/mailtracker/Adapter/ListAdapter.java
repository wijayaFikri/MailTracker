package hafiz.mailtracker.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;

public class ListAdapter extends ArrayAdapter<Mail> {
    private int resourceLayout;
    private Context mContext;

    public ListAdapter(@NonNull Context context, int resource, List<Mail> ListItem) {
        super(context, resource, ListItem);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Mail m = getItem(position);

        if (m != null) {
            EditText et1 = v.findViewById(R.id.FromHty);
            EditText et2 = v.findViewById(R.id.ToHty);
            EditText et3 = v.findViewById(R.id.AboutHty);
            TextView tv1 = v.findViewById(R.id.Date_tv);

            if (et1 != null) {
                et1.setText(m.getSender());
                et1.setFocusable(false);
            }

            if (et2 != null) {
                et2.setText(m.getReceiver_name());
                et2.setFocusable(false);
            }

            if (et3 != null) {
                et3.setText(m.getAbout());
                et3.setFocusable(false);
            }
            if (tv1 != null) {
                tv1.setText(m.getDate());
            }
        }

        return v;
    }
}
