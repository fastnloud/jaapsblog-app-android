package nl.jaapsblog.app;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListCreator extends ListActivity implements Tasks  {

    private ProgressDialog progressDialog;
    private Requester requester;

    private ArrayList<Integer> fieldIds = new ArrayList<Integer>();
    private String[] fieldNames = new String[]{};

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private JSONArray data;

    /* configurable */
    private String urlIndex = "";
    private String urlDelete = "";
    private String fieldIndex = "";
    private Class form;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerForContextMenu(getListView());
        setAdapter();
    }

    /**
     * Creates an empty simple list adapter.
     */
    private void setAdapter() {
        adapter = new ArrayAdapter<String> (
                this,
                android.R.layout.simple_list_item_1,
                list // empty initially
        );

        setListAdapter(adapter);
    }

    /**
     * Set child "form" Activity.
     * @param activity Class
     */
    protected void setChildForm(Class activity) {
        form = activity;
    }

    /**
     * Set index URL (list)
     * @param url String
     */
    protected void setUrlIndex(String url) {
        urlIndex = url;
    }

    /**
     * Set delete URL (on long click)
     * @param url String
     */
    protected void setUrlDelete(String url) {
        urlDelete = url;
    }

    /**
     * Set field (resource) ID's.
     * @param ids Integers
     */
    protected void setFieldIds(Integer... ids) {
        for (int i = 0; i < ids.length; ++i) {
            fieldIds.add(i,ids[i]);
        }
    }

    /**
     * Set field names as defined in the JSON string.
     * @param names String[]
     */
    protected void setFieldNames(String[] names) {
        fieldNames = names;
    }

    /**
     * Set field index (displayed in list).
     * @param field String
     */
    protected void setFieldIndex(String field) {
        fieldIndex = field;
    }

    /**
     * Loader.
     */
    protected void load() {
        // fetch data
        requester = new Requester(this);
        requester.execute(urlIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (0 == item.getOrder()) {
            Intent intent = new Intent(getApplicationContext(), form);
            intent.putIntegerArrayListExtra("fieldIds", fieldIds);
            intent.putExtra("fieldNames", fieldNames);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        try {
            java.util.List<NameValuePair> dataToDelete = new ArrayList<NameValuePair>(2);
            dataToDelete.add(new BasicNameValuePair("data", data.get(info.position).toString()));

            adapter.remove(adapter.getItem(info.position));

            requester = new Requester(this);
            requester.add(dataToDelete);
            requester.setSilent(true);
            requester.execute(urlDelete);

            Toast.makeText(this, "Item deleted.", Toast.LENGTH_SHORT).show();
        } catch(JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        return false;
    }

    @Override
    public void onRestart() {
        super.onRestart();

        // clear data
        adapter.clear();

        // reload
        requester = new Requester(this);
        requester.execute(urlIndex); // reload
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        try {
            Intent intent = new Intent(getApplicationContext(), form);
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
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void onTaskCompleted() {
        try {
            data = requester.getDataParsed();
            if (data.length() > 0) {
                for(int i = 0 ; i < data.length() ; i++){
                    list.add(data.getJSONObject(i).get(fieldIndex).toString());
                }
            }

            // update list_context
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

}