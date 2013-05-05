package nl.jaapsblog.app;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class Requester extends AsyncTask<String, Void, Void> {

    private Tasks listener;
    private List data = null;
    private boolean success = false;
    private boolean silent = false;

    private static DefaultHttpClient httpClient;

    private HttpPost httpPost;
    private InputStream inputStream = null;
    private JSONObject jsonObject = null;
    private String dataString = "";

    /* Parsed data */
    private JSONArray dataParsed = null;

    public Requester(Tasks listener) {
        this.listener = listener;
    }

    public void add(List list) {
        data = list;
    }

    @Override
    protected void onPreExecute() {
        if (!silent) {
            listener.onTaskStarted();
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        if (!silent) {
            listener.onTaskCompleted();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        return request(params[0]);
    }

    private Void request(String url) {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();

            ClientConnectionManager mgr = httpClient.getConnectionManager();
            HttpParams params = httpClient.getParams();

            httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(
                    params,
                    mgr.getSchemeRegistry()
            ), params);
        }

        try {
            httpPost = new HttpPost(url);
            if (null != data) {
                httpPost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
            }

            this.reader(httpClient.execute(httpPost));
        } catch (IOException e) {
            Log.i("IOException", e.getMessage());
        } catch (Exception e) {
            Log.i("Exception", e.getMessage());
        }

        return null;
    }

    private void reader(HttpResponse response) {
        HttpEntity httpEntity = response.getEntity();

        try {
            inputStream = httpEntity.getContent();

            BufferedReader reader       = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            // close stream
            inputStream.close();

            // data fetched
            dataString = stringBuilder.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(dataString);

            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();

                if (key.equals("success")) {
                    setSuccess((Boolean) jsonObject.get(key));
                    break;
                }

                if (key.equals("data")) {
                    setDataParsed(jsonObject.getJSONArray(key));
                    break;
                }
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    private void setSuccess(Boolean success) {
        this.success = success;
    }

    private void setDataParsed(JSONArray data) {
        this.dataParsed = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public JSONArray getDataParsed() {
        return dataParsed;
    }

}
