package exam.guoqian.me.exam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gq on 2015/5/30.
 */
public class ImageLoader {

    private static final int MSG_SUCCESS = 0;//获取图片成功的标识

    private ImageView imageView;
    private String sUrl;

    private Thread mThread;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //只有当 图片与绑定的URL匹配是才更新
            if (msg.what == MSG_SUCCESS && imageView.getTag().equals(sUrl)){
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    //通过多线程加载图片
    public void showImageByThread(ImageView imageView, final String url){
        this.imageView = imageView;
        sUrl = url;

        mThread = new Thread(runnable);
        mThread.start();

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            HttpURLConnection connection = null;
            final Bitmap bm;
            try {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10 * 1000);
                connection.setReadTimeout(10* 1000);
                InputStream in = connection.getInputStream();
                bm = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
            handler.obtainMessage(MSG_SUCCESS, bm).sendToTarget();
        }
    };

}
