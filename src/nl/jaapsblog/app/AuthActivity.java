package nl.jaapsblog.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends Activity implements Tasks {

    private ProgressDialog progressDialog;
    private Requester requester;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            this.finish();
        }

        setContentView(R.layout.auth);
    }

    public void login(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        List<NameValuePair> data = new ArrayList<NameValuePair>(2);
        data.add(new BasicNameValuePair("username", username.getText().toString()));
        data.add(new BasicNameValuePair("password", password.getText().toString()));

        requester = new Requester(this);
        requester.add(data);
        requester.execute(getString(R.string.url_login));
    }

    public void onTaskStarted() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void onTaskCompleted() {
        progressDialog.dismiss();

        if (requester.isSuccess()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
        }
    }

}