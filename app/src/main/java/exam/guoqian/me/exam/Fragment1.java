package exam.guoqian.me.exam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gq on 2015/5/30.
 */
public class Fragment1 extends BaseFragment {

    private ListView mListView;
    private List<PictureBean> pictureBeanList = new ArrayList<>();
    private static String URL = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments&page=2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.list_fr1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureBean pictureBean = pictureBeanList.get(position);
                String url = pictureBean.pictureIconUrl;
                ShowPicture.actionStart(getActivity(), url);
            }
        });
        new ContentAsyncTask().execute(URL);
    }

    private List<PictureBean> getJsonData(String URL) {

        HttpURLConnection connection = null;
        try {
            java.net.URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();

            //读取Json数据
            String response = readStream(in);

            //解析JSON格式数据
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("comments");
                for (int i = 0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    PictureBean pictureBean = new PictureBean();
                    pictureBean.author = jsonObject.getString("comment_author");
                    pictureBean.time = jsonObject.getString("comment_date");
                    pictureBean.goodNum = jsonObject.getString("vote_positive");
                    pictureBean.badNum = jsonObject.getString("vote_negative");
                    pictureBean.speakNum = jsonObject.getString("comment_ID");
                    String iconUrl = jsonObject.getString("pics");
                    pictureBean.pictureIconUrl = iconUrl.subSequence(2, iconUrl.length()-2).toString().replaceAll("\\\\", "");
                    pictureBeanList.add(pictureBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pictureBeanList;
    }

    //解析网页返回的数据
    private String readStream(InputStream in){

        StringBuilder response = new StringBuilder();
        InputStreamReader isReader;
        try {
            String line;

            //将字节流转化为字符流
            isReader = new InputStreamReader(in, "UTF-8");
            BufferedReader reader = new BufferedReader(isReader);
            while ((line = reader.readLine()) != null){

                //若读取不为空，就加到StringBuilder中
                response.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(response);
    }

    //继承一个异步实现网络异步访问
    class ContentAsyncTask extends AsyncTask<String, Void, List<PictureBean>>{

        @Override
        protected List<PictureBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<PictureBean> pictureBean) {
            super.onPostExecute(pictureBean);
            PictureAdapter adapter = new PictureAdapter(getActivity(), pictureBean);
            mListView.setAdapter(adapter);
        }
    }

}
