package nl.jaapsblog.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class KillActivity extends Activity implements Tasks {

    private Requester requester;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Requester requester = new Requester(this);
        requester.execute(getString(R.string.url_logout));
    }

    public void onTaskStarted() { }

    public void onTaskCompleted() {
        Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);

        startActivity(intent);
    }

}