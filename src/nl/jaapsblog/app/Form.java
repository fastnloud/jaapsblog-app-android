package nl.jaapsblog.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class Form extends Activity implements Tasks {

    private String url = "";
    private ProgressDialog progressDialog;
    private Requester requester;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    /* Pre-defined spinners */
    protected void createSpinner(int id, String name, String selected) {
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
    protected void createSpinner(int id, ArrayList values, String selected) {
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

    /* Default text field */
    protected void createTextField(int id, String value) {
        EditText field = (EditText) findViewById(id);
        field.setText(value);
    }

    /* Submit action */
    public void submitForm(View view) {
        if (this.url.isEmpty()) {
            Toast.makeText(this, "No URL has been given.", Toast.LENGTH_SHORT).show();
        } else {
            try {
                // fetch view group
                ViewGroup viewGroup = (ViewGroup)findViewById(R.id.form);

                // new container for values
                ArrayList<String> values = new ArrayList<String>();

                // fetch all values from childs in view group
                for (int i = 0, count = viewGroup.getChildCount(); i < count; ++i) {
                    View field = viewGroup.getChildAt(i); // field

                    // text fields
                    if (field instanceof EditText) {
                        values.add(((EditText) field).getText().toString());
                    }
                    // spinners
                    else if (field instanceof Spinner) {
                        if (!((Spinner) field).getSelectedItem().toString().isEmpty()) {
                            values.add(((Spinner) field).getSelectedItem().toString());
                        } else {
                            values.add("");
                        }
                    }
                }

                // fields names as defined in activity
                String[] fieldNames = getIntent().getStringArrayExtra("fieldNames");

                // build json string
                JSONStringer jsonStringer = new JSONStringer();
                try {
                    jsonStringer.object();
                    for (int i = 0; i < fieldNames.length; ++i) {
                        jsonStringer.key(fieldNames[i])
                                .value(values.get(i));
                    }
                    jsonStringer.endObject();
                }  catch (JSONException e) {
                    Log.e("JSONException", e.getMessage());
                }

                // add json string to "data¨ param
                List<NameValuePair> data = new ArrayList<NameValuePair>(2);
                data.add(new BasicNameValuePair("data", jsonStringer.toString()));

                requester = new Requester(this);
                requester.add(data);
                requester.execute(this.url); // bye
            } catch(Exception e) {
                Log.e("Submit Form", e.getMessage());
            }
        }
    }

    /* Loader */
    public void onTaskStarted() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /* Dismiss the loader */
    public void onTaskCompleted() {
        progressDialog.dismiss();

        if (requester.isSuccess()) {
            Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed; check your input.", Toast.LENGTH_SHORT).show();
        }
    }

}