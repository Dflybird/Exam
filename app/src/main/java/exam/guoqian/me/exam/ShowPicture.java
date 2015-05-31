package exam.guoqian.me.exam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by gq on 2015/5/31.
 */
public class ShowPicture extends BaseActivity {

    private String url;
    private ImageView imageView;

    public static void actionStart(Context context, String url){
        Intent intent = new Intent(context, ShowPicture.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_picture);
        imageView = (ImageView) findViewById(R.id.show_picture);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        Log.d("TAG", url);

        imageView.setTag(url);
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.showImageByThread(imageView, url);
    }
}
