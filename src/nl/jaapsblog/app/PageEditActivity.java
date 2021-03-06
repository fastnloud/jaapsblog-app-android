package nl.jaapsblog.app;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class PageEditActivity extends Form {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_edit);
        setUrl(getString(R.string.url_page_edit));

        if (getIntent().hasExtra("fieldIds")) {
            ArrayList<Integer> fieldIds = getIntent().getIntegerArrayListExtra("fieldIds");

            for (int i = 0; i < fieldIds.size(); ++i) {
                Integer id = fieldIds.get(i);
                String value = getIntent().getStringExtra(fieldIds.get(i).toString());

                if (4 == i) { // route
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("page", "home", "blog"));

                    createSpinner(id, values, value);
                } else if (6 == i) { // status
                    ArrayList values = new ArrayList<String>();
                    values.addAll(Arrays.asList("online", "online, not in menu", "offline"));

                    createSpinner(id, values, value);
                } else if (7 == i) { // priority
                    createSpinner(id, "priority", value);
                } else {
                    createTextField(id, value);
                }
            }
        }
    }

}