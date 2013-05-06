package nl.jaapsblog.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        String[] list = new String[] {
            getString(R.string.list_main_1),
            getString(R.string.list_main_2),
            getString(R.string.list_main_3)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            R.layout.list_item, list
        );

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (0 == item.getOrder()) {
            Intent intent = new Intent(this, KillActivity.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (0 == position) {
            Intent intent = new Intent(this, PageActivity.class);
            startActivity(intent);
        } else if (1 == position) {
            Intent intent = new Intent(this, BlogActivity.class);
            startActivity(intent);
        } else if (2 == position) {
            Intent intent = new Intent(this, ReplyActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() { }

}