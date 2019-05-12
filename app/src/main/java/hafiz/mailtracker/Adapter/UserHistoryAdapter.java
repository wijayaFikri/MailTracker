package hafiz.mailtracker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hafiz.mailtracker.Model.Mail;
import hafiz.mailtracker.R;

public class UserHistoryAdapter extends ArrayAdapter<Mail> {
    private int resourceLayout;
    private Context mContext;

    public UserHistoryAdapter(@NonNull Context context, int resource, List<Mail> ListItem) {
        super(context, resource, ListItem);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        final Mail m = getItem(position);

        if (m != null) {
            TextView tv1 = v.findViewById(R.id.user_history_sender);
            TextView tv2 = v.findViewById(R.id.user_history_about);
            TextView tv3 = v.findViewById(R.id.user_history_receiver);
            TextView tv4 = v.findViewById(R.id.user_history_date);

            tv1.setText(m.getSender());
            tv2.setText(m.getAbout());
            tv3.setText(m.getReceiver_name());
            tv4.setText(m.getDate());

        }
        return v;
    }
}
