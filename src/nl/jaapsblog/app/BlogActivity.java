package nl.jaapsblog.app;

import android.os.Bundle;

public class BlogActivity extends ListCreator {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setChildForm(BlogEditActivity.class);
        setUrlIndex(getString(R.string.url_blog_index));
        setUrlDelete(getString(R.string.url_blog_delete));
        setFieldIndex("title");

        setFieldIds(
            R.id.id, R.id.title, R.id.subtitle, R.id.lead, R.id.content,
            R.id.date, R.id.category, R.id.status, R.id.rating,
            R.id.amazon_item_id, R.id.meta_title, R.id.meta_description, R.id.meta_keywords
        );

        setFieldNames(new String[] {
            "id", "title", "subtitle", "lead", "content", "date", "category",
            "status", "rating", "amazon_item_id", "meta_title", "meta_description", "meta_keywords"}
        );

        load();
    }

}