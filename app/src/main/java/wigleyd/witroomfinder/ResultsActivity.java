package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        AssetManager manager;
        manager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = manager.open("fall2015.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String hourString = intent.getStringExtra(MyActivity.HOUR_STRING);
        String minuteString = intent.getStringExtra(MyActivity.MINUTE_STRING);
        String building = intent.getStringExtra(MyActivity.BUILDING_STRING);
        String day = intent.getStringExtra(MyActivity.DAY_STRING);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        MyHandler myHandler = new MyHandler(building, day, hour, minute, inputStream);
        sv.addView(ll);
        for(int i = 0;i< myHandler.search.getNumClassrooms();i++){
            System.out.println("I;m trying to add shit");
            TextView tv = new TextView(this);
            tv.setText(myHandler.search.getClassrooms());
            ll.addView(tv);
            //idea have the taken classrooms go red, open green
        }
        this.setContentView(sv);

    }



}
