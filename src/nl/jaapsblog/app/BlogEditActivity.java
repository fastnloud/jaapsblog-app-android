package nl.jaapsblog.app;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class BlogEditActivity extends Form {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.blog_edit);
        setUrl(getString(R.string.url_blog_edit));

        if (getIntent().hasExtra("fieldIds")) {
            ArrayList<Integer> fieldIds = getIntent().getIntegerArrayListExtra("fieldIds");

            for (int i = 0; i < fieldIds.size(); ++i) {
                Integer id = fieldIds.get(i);
                String value = getIntent().getStringExtra(fieldIds.get(i).toString());

                if (6 == i) { // category
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("code snippet", "media", "review", "social"));

                    createSpinner(id, values, value);
                } else if (7 == i) { // status
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("online", "offline"));

                    createSpinner(id, values, value);
                } else if (8 == i) { // rating
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));

                    createSpinner(id, values, value);
                } else {
                    createTextField(id, value);
                }
            }
        }
    }

}