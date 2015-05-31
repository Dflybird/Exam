package exam.guoqian.me.exam;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private ArrayList<String> titleList = new ArrayList<String>();
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private int versioncode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar myactionbar  = getSupportActionBar();
        myactionbar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        myactionbar.setIcon(R.mipmap.ic_launcher);

        try {
            ViewConfiguration mconfig = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(mconfig, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化控件
        initView();

        //初始化数据
        initData();

        //给ViewPager赋值
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        //用Fragment作为ViewPager数据源
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());

        titleList.add("妹子图");
        titleList.add("段子");
        titleList.add("其他");

        //初始化适配器
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_update:
                sendRequest();

                checkUpdate();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkUpdate() {
        SharedPreferences.Editor editor = getSharedPreferences("versioncode", MODE_PRIVATE).edit();
        editor.putInt("versioncode", 1);
        SharedPreferences pref = getSharedPreferences("versioncode", MODE_PRIVATE);
        int nowVersion = pref.getInt("versioncode", 0);
        if (nowVersion == versioncode){
            Toast.makeText(this, "已经是最新版本", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "需要更新", Toast.LENGTH_SHORT).show();
            editor.putInt("versioncode", versioncode);
        }
    }

    private void sendRequest() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://hongyan.cqupt.edu.cn/app/cyxbsAppUpdate.xml");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10 * 1000);
                    connection.setReadTimeout(10 * 1000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    parseXML(String.valueOf(response));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void parseXML(String response) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));
            int evenType = xmlPullParser.getEventType();
            while (evenType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                Log.d("TAG", nodeName);
                switch (evenType){
                    case XmlPullParser.START_TAG:
                        if ("versionCode".equals(nodeName)){
                            versioncode = Integer.valueOf(xmlPullParser.nextText());
                        }
                        break;
                }
                evenType = xmlPullParser.next();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
