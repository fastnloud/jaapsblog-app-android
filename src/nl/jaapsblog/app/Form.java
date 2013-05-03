package nl.jaapsblog.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class Form extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* Pre-defined spinners */
    public void createSpinner(int id, String name, String selected) {
        if (name.equals("priority")) {
            ArrayList values = new ArrayList<String>();
            for (Integer priority = 0; priority <= 1000;) {
                values.add(priority.toString());
                priority = priority+10;
            }

            createSpinner(id, values, selected);
        }
    }

    /* Spinner */
    public void createSpinner(int id, ArrayList values, String selected) {
        Spinner spinner = (Spinner) findViewById(id);
        ArrayAdapter adapter = new ArrayAdapter<String> (
                this,
                android.R.layout.simple_spinner_item,
                values
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // select if possible
        int hasSelection = adapter.getPosition(selected);
        if (hasSelection > 0) {
            spinner.setSelection(hasSelection);
        }
    }
}