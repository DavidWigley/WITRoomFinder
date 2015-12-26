package wigleyd.witroomfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

import java.util.Calendar;


public class MyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public final static String HOUR_STRING = "HOUR_STRING";
    public final static String MINUTE_STRING = "MINUTE_STRING";
    public final static String BUILDING_STRING = "BUILDING_STRING";
    public final static String DAY_STRING = "DAY_STRING";

    private Spinner buildingSpinner, daySpinner;
    private static final String[]buildings = {"Annex Central", "Annex North", "Annex South", "Beatty", "Dobbs Hall",
    "Ira Allen", "Kingman Hall", "Rubenstein Hall", "Watson Hall", "Wentworth Hall", "Willison Hall", "Williston Hall"};
    private static final String[]days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private EditText hourInput;
    private Button submit;
    String buildingChosen, dayChosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        hourInput = (EditText) findViewById(R.id.hour_message);
        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), ResultsActivity.class);
                String hour, minute;
                //deals with input box left blank. I leave it as an input so people can plan ahead
                if(hourInput.getText().toString() == "" || hourInput.getText().toString() == null) {
                    Calendar calendar = new Calendar() {
                        @Override
                        public void add(int field, int value) {

                        }

                        @Override
                        protected void computeFields() {

                        }

                        @Override
                        protected void computeTime() {

                        }

                        @Override
                        public int getGreatestMinimum(int field) {
                            return 0;
                        }

                        @Override
                        public int getLeastMaximum(int field) {
                            return 0;
                        }

                        @Override
                        public int getMaximum(int field) {
                            return 0;
                        }

                        @Override
                        public int getMinimum(int field) {
                            return 0;
                        }

                        @Override
                        public void roll(int field, boolean increment) {

                        }
                    };
                    hour = Integer.toString(calendar.get(Calendar.HOUR));
                    minute = Integer.toString(calendar.get(Calendar.MINUTE));
                }else{
                    hour = hourInput.getText().toString();
                    minute = "0";
                }
                myIntent.putExtra(HOUR_STRING, hour);
                myIntent.putExtra(MINUTE_STRING, minute);
                myIntent.putExtra(BUILDING_STRING, buildingChosen);
                myIntent.putExtra(DAY_STRING, dayChosen);
                startActivity(myIntent);
            }
        });

        buildingSpinner = (Spinner)findViewById(R.id.building_spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,buildings);

        daySpinner = (Spinner)findViewById(R.id.day_spinner);
        ArrayAdapter<String>dayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter);
        buildingSpinner.setOnItemSelectedListener(this);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (parent.getId()) {
            case R.id.building_spinner:
                buildingChosen = buildings[position];
                break;
            case R.id.day_spinner:
                dayChosen = days[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
