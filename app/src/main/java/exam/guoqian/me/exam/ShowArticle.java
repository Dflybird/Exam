package exam.guoqian.me.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by gq on 2015/5/31.
 */
public class ShowArticle extends BaseActivity {

    private String author;
    private String content;
    private TextView tvAuthor;
    private TextView tvContent;

    public static void actionStart(Context context, String author, String content){
        Intent intent = new Intent(context, ShowArticle.class);
        intent.putExtra("author", author);
        intent.putExtra("content", content);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_article);

        tvAuthor = (TextView) findViewById(R.id.show_author);
        tvContent = (TextView) findViewById(R.id.show_content);

        Intent intent = getIntent();
        author = intent.getStringExtra("author");
        content = intent.getStringExtra("content");

        tvAuthor.setText(author);
        tvContent.setText(content);
    }
}
