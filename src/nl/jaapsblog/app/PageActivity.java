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

public class PageActivity extends ListActivity implements Tasks {

    private ProgressDialog progressDialog;
    private Requester requester;

    private ArrayList<String> values = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private JSONArray data;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<String> (
            this,
            android.R.layout.simple_list_item_1,
            values // empty initially
        );

        setListAdapter(adapter);

        // fetch data
        requester = new Requester(this);
        requester.execute(getString(R.string.url_page_index));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            JSONObject obj = data.getJSONObject(position);

            Intent intent = new Intent(getApplicationContext(), PageEditActivity.class);

            String extra[] = new String[] {
                "id", "title", "label", "url_string", "route", "content", "status",
                "priority", "meta_title", "meta_description", "meta_keywords"
            };

            intent.putExtra("extra", extra);
            for(int i = 0; i < extra.length; ++i) {
                intent.putExtra(extra[i], obj.get(extra[i]).toString());
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
                values.add(data.getJSONObject(i).get("title").toString());
            }

            // update list
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

}