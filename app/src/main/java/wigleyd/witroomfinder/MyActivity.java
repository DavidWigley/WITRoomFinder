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


public class MyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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
                Intent myIntent = new Intent(getApplicationContext(), ResultsActivity.class);
                myIntent.putExtra("hourString", hourInput.getText().toString());
                myIntent.putExtra("buildingString", buildingChosen);
                myIntent.putExtra("dayString", dayChosen);
                startActivity(myIntent);
                System.out.println("The button was pushed");
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
