package nl.jaapsblog.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class PageEditActivity extends Form implements Tasks {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_edit);

        if (getIntent().hasExtra("fieldIds")) {
            ArrayList<Integer> fieldIds = getIntent().getIntegerArrayListExtra("fieldIds");

            for (int i = 0; i < fieldIds.size(); ++i) {
                Integer id = fieldIds.get(i);
                String value = getIntent().getStringExtra(fieldIds.get(i).toString());

                if (6 == i) { // status
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("online", "online-not-in-menu", "offline"));

                    createSpinner(id, values, value);
                } else if (7 == i) { // priority
                    createSpinner(id, "priority", value);
                } else {
                    createTextField(id, value);
                }
            }
        }
    }

    public void onTaskStarted() { }

    public void onTaskCompleted() { }

}