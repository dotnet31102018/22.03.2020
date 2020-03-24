package hello.itay.com.webapilistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Itay kan on 3/22/2020.
 */

public class MessageCustomAdapter  extends ArrayAdapter<Message> {

    private Context _context;
    private int _resource_layout;
    private ArrayList<Message> _objects;

    public MessageCustomAdapter(@NonNull Context context, int resource_layout, ArrayList<Message> objects) {
        super(context, resource_layout, objects);

        _context = context;
        _resource_layout = resource_layout;
        _objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // take this -- convertView
        View result = convertView;

        // option 1 - view is null
        // option 2 - view is already inflated ( no data guarantee )
        if (result == null)
        {
            result = LayoutInflater.from(_context).inflate(
                    _resource_layout,
                    parent, false);
        }
        TextView idtv = result.findViewById(R.id.idtxt);
        idtv.setText(String.valueOf(_objects.get(position).Id));

        TextView comptv = result.findViewById(R.id.sendertxt);
        comptv.setText(_objects.get(position).Sender);

        TextView ortv = result.findViewById(R.id.bodytxt);
        ortv.setText(_objects.get(position).Body);

        result.setOnClickListener(new MessageDeleteListener(this, position, _context, _objects));

        return result;
    }
}