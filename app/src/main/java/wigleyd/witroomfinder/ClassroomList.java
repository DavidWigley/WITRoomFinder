package wigleyd.witroomfinder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by frascog on 2/22/16.
 */
public class ClassroomList extends ArrayAdapter<Classroom>{

    private LayoutInflater layoutInflater;
    private Context context;

    public ClassroomList(Context context, int rid, List<Classroom> list){
        super(context, rid, list);
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        // Retrieve data
        Classroom classroom = (Classroom)getItem(position);
        // Use layout file to generate View
        View view = layoutInflater.inflate(R.layout.classroom, null);
        // Set classroom
        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(classroom.getName());
        // Set comment
        TextView avaiable = (TextView) view.findViewById(R.id.open);
        String text = "";
        if(classroom.isOpen()){
            text = "Available";
            avaiable.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            text = "Occupied";
            avaiable.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        avaiable.setText(text);
        return view;
    }
}
