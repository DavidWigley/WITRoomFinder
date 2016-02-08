package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Handler;

public class ClassDetailsActivity extends Activity {

    //this is just a garbage hour so we return all the results. Sorta shitty but it works.
    private static final int HOUR = 99;
    private static final int MINUTE = 0;
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
        String Classroom = intent.getStringExtra(ResultsActivity.CLASSROOM_STRING);
        String day = intent.getStringExtra(MyActivity.DAY_STRING);
        System.out.println("Room searching for is " + Classroom);
        MyHandler myHandler = new MyHandler(Classroom,day,HOUR,MINUTE,inputStream);
        garbage = myHandler.getResults();
        System.out.println("This many entries: " + garbage.size());
        for (int i =0; i < garbage.size(); i++){
            System.out.println("Got this: " + garbage.get(i).toString());
        }
    }

}
