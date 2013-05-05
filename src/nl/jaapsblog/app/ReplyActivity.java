package nl.jaapsblog.app;

import android.os.Bundle;

public class ReplyActivity extends ListCreator {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setChildForm(BlogEditActivity.class);
        setUrlIndex(getString(R.string.url_blog_reply_index));
        setUrlDelete(getString(R.string.url_blog_reply_delete));
        setFieldIndex("name");
        hasMenu(false);
        isEditable(false);

        load();
    }

}