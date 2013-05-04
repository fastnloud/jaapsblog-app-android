package nl.jaapsblog.app;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PageActivity extends ListActivity implements Tasks {

    private ProgressDialog progressDialog;
    private Requester requester;

    private ArrayList<Integer> fieldIds = new ArrayList<Integer>();
    private String[] fieldNames;

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private JSONArray data;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<String> (
            this,
            android.R.layout.simple_list_item_1,
            list // empty initially
        );

        setListAdapter(adapter);

        // fetch data
        requester = new Requester(this);
        requester.execute(getString(R.string.url_page_index));

        // set field ids.
        fieldIds.addAll(
            Arrays.asList(
                R.id.id, R.id.title, R.id.label, R.id.url_string, R.id.route,
                R.id.content, R.id.status, R.id.priority, R.id.meta_title,
                R.id.meta_description, R.id.meta_keywords
            )
        );

        // mapping for field ids.
        fieldNames = new String[] {
            "id", "title", "label", "url_string", "route", "content", "status",
            "priority", "meta_title", "meta_description", "meta_keywords"
        };
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            Intent intent = new Intent(getApplicationContext(), PageEditActivity.class);
            intent.putIntegerArrayListExtra("fieldIds", fieldIds);
            intent.putExtra("fieldNames", fieldNames);

            JSONObject obj = data.getJSONObject(position);
            for(int i = 0; i < fieldNames.length; ++i) {
                intent.putExtra(fieldIds.get(i).toString(), obj.get(fieldNames[i]).toString());
            }

            startActivity(intent);
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

    public void onTaskStarted() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading pages...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void onTaskCompleted() {
        try {
            data = requester.getDataParsed();
            for(int i = 0 ; i < data.length() ; i++){
                list.add(data.getJSONObject(i).get("title").toString());
            }

            // update list
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

}