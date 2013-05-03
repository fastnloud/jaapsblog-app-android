package nl.jaapsblog.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class PageEditActivity extends Activity implements Tasks {

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
                EditText field = (EditText) findViewById(fields.get(i));
                field.setText(getIntent().getStringExtra(extra[i]));
            }
        }
    }

    public void onTaskStarted() { }

    public void onTaskCompleted() { }

}