package exam.guoqian.me.exam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by gq on 2015/5/30.
 */
public class Fragment2 extends BaseFragment {

    private ListView mListView;
    List<ArticleBean> articleBeanList = new ArrayList<>();

    private static String URL = "http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_duan_comments&page=1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.list_fr2);

        new ContentAsyncTask().execute(URL);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleBean articleBean = articleBeanList.get(position);
                String author = articleBean.arAuthor;
                String content = articleBean.arContent;
                ShowArticle.actionStart(getActivity(), author, content);
            }
        });
    }

    private class ContentAsyncTask extends AsyncTask<String, Void, List<ArticleBean>>{

        @Override
        protected List<ArticleBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<ArticleBean> articleBeans) {
            super.onPostExecute(articleBeans);
            ArticleAdapter adapter = new ArticleAdapter(getActivity(), articleBeans);
            mListView.setAdapter(adapter);
        }
    }

    private List<ArticleBean> getJsonData(String sUrl) {
        HttpURLConnection connection = null;
        try {
            java.net.URL url = new URL(sUrl);
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
                    ArticleBean articleBean= new ArticleBean();
                    articleBean.arAuthor = jsonObject.getString("comment_author");
                    articleBean.arTime = jsonObject.getString("comment_date");
                    articleBean.arGoodNum = jsonObject.getString("vote_positive");
                    articleBean.arBadNum = jsonObject.getString("vote_negative");
                    articleBean.arSpeakNum = jsonObject.getString("comment_post_ID");
                    articleBean.arContent = jsonObject.getString("comment_content");
                    articleBeanList.add(articleBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return articleBeanList;
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
}
