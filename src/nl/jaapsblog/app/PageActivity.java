package nl.jaapsblog.app;

import android.os.Bundle;

public class PageActivity extends ListCreator {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setChildForm(PageEditActivity.class);
        setUrlIndex(getString(R.string.url_page_index));
        setUrlDelete(getString(R.string.url_page_delete));
        setFieldIds(
            R.id.id, R.id.title, R.id.label, R.id.url_string, R.id.route,
            R.id.content, R.id.status, R.id.priority, R.id.meta_title,
            R.id.meta_description, R.id.meta_keywords
        );
        setFieldNames(new String[] {
            "id", "title", "label", "url_string", "route", "content", "status",
            "priority", "meta_title", "meta_description", "meta_keywords"}
        );
        setFieldIndex("title");
        load();
    }

}