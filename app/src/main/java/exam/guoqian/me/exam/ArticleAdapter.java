package exam.guoqian.me.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gq on 2015/5/31.
 */
public class ArticleAdapter extends BaseAdapter {

    private List<ArticleBean> mList;
    private LayoutInflater mInflater;

    public ArticleAdapter(Context context, List<ArticleBean> mList) {
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ArtViewHolder();
            convertView = mInflater.inflate(R.layout.article_item, null);
            viewHolder.tvArtAuthor = (TextView) convertView.findViewById(R.id.author_article);
            viewHolder.tvArtTime = (TextView) convertView.findViewById(R.id.time_article);
            viewHolder.tvArtGood = (TextView) convertView.findViewById(R.id.good_article);
            viewHolder.tvArtBad = (TextView) convertView.findViewById(R.id.bad_article);
            viewHolder.tvArtSpeak = (TextView) convertView.findViewById(R.id.speak_num_article);
            viewHolder.tvArtContent = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ArtViewHolder) convertView.getTag();
        }

        viewHolder.tvArtContent.setText(mList.get(position).arAuthor);
        viewHolder.tvArtTime.setText(mList.get(position).arTime);
        viewHolder.tvArtGood.setText(mList.get(position).arGoodNum);
        viewHolder.tvArtBad.setText(mList.get(position).arBadNum);
        viewHolder.tvArtSpeak.setText(mList.get(position).arSpeakNum);
        viewHolder.tvArtContent.setText(mList.get(position).arContent);
        return convertView;
    }

    class ArtViewHolder{
        public TextView tvArtAuthor, tvArtTime, tvArtGood, tvArtBad, tvArtSpeak, tvArtContent;
    }
}
