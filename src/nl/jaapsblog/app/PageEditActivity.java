package nl.jaapsblog.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class PageEditActivity extends Form implements Tasks {

    private ArrayList<Integer> fields = new ArrayList<Integer>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_edit);

        if (getIntent().hasExtra("extra")) {
            fields.addAll(
                Arrays.asList(
                    R.id.id,R.id.title, R.id.label, R.id.url_string, R.id.route,
                    R.id.content, R.id.status, R.id.priority, R.id.meta_title,
                    R.id.meta_description, R.id.meta_keywords
                )
            );

            String extra[] = getIntent().getStringArrayExtra("extra");
            for (int i = 0; i < extra.length; ++i) {
                if (extra[i].equals("priority")) {
                    createSpinner(fields.get(i), "priority", getIntent().getStringExtra(extra[i]));
                } else if (extra[i].equals("status")) {
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("online", "online-not-in-menu", "offline"));

                    createSpinner(fields.get(i), values, getIntent().getStringExtra(extra[i]));
                } else {
                    EditText field = (EditText) findViewById(fields.get(i));
                    field.setText(getIntent().getStringExtra(extra[i]));
                }
            }
        }
    }

    public void onTaskStarted() { }

    public void onTaskCompleted() { }

}