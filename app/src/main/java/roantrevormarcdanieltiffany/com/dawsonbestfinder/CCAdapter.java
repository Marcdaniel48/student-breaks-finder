package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Roan on 11/27/2017.
 */

public class CCAdapter extends BaseAdapter {
    //CancelledClassActivity.DownloadXMLRSSThread xcontext;
    Context context;
    List<CancelledClass> classes;
    static LayoutInflater inflater = null;

    public CCAdapter(CancelledClassActivity.DownloadXMLRSSThread context, List<CancelledClass> classes){
        //this.context = context;
        this.classes = classes;
        //inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.cancelled_class_list_item, null);
        holder.lv = rowView.findViewById(R.id.CancelledClassLV);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked a thing", Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

    public class Holder{
        ListView lv;
    }
}
