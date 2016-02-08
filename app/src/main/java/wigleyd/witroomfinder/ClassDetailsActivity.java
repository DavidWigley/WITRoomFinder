package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ClassDetailsActivity extends Activity {
    
    private static final int PADDING = 15;
    ArrayList garbage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        AssetManager manager;
        manager = getAssets();
        InputStream inputStream = null;
        try {
            //inputStream = manager.open("fall2015.txt");
            inputStream = manager.open("spring2016.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String classroom = intent.getStringExtra(ResultsActivity.CLASSROOM_STRING);
        String day = intent.getStringExtra(MyActivity.DAY_STRING);
        System.out.println("Room searching for is " + classroom);
        MyHandler myHandler = new MyHandler(classroom,day,inputStream);
        myHandler.skipTimeSearch();
        garbage = myHandler.getDetailedRooms();
        System.out.println("This many entries: " + garbage.size());

        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        TextView firstBox = new TextView(this);
        firstBox.setText("The classroom: " + classroom + " on " + day + " has these classes");
        ll.addView(firstBox);
        for (int i =0; i < garbage.size(); i++){
            System.out.println("Got this: " + garbage.get(i).toString());
            TextView tv = new TextView(this);
            //tv.setBackgroundResource(color);
            tv.setText(garbage.get(i).toString());
            tv.setPadding(0, PADDING, 0, PADDING);
            tv.setTag(i);
            ll.addView(tv);
        }
        this.setContentView(sv);
    }

}
