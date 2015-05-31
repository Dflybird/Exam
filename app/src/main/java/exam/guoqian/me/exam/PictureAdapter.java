package exam.guoqian.me.exam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gq on 2015/5/30.
 */
public class PictureAdapter extends BaseAdapter {

    private List<PictureBean> mList;
    private LayoutInflater inflater;

    public PictureAdapter(Context context, List<PictureBean> mList) {
        this.mList = mList;
        inflater = LayoutInflater.from(context);
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
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.picture_item, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.author);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.time );
            viewHolder.tvGoodNum = (TextView) convertView.findViewById(R.id.good);
            viewHolder.tvBadNum = (TextView) convertView.findViewById(R.id.bad);
            viewHolder.tvSpeakNum = (TextView) convertView.findViewById(R.id.speak_num);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置默认图片
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        //将图片与URL绑定
        viewHolder.ivIcon.setTag(mList.get(position).pictureIconUrl);
        //通过ImageLoader运用多线程动态加载图片
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.showImageByThread(viewHolder.ivIcon, mList.get(position).pictureIconUrl);

        viewHolder.tvAuthor.setText(mList.get(position).author);
        viewHolder.tvTime.setText(mList.get(position).time);
        viewHolder.tvGoodNum.setText(mList.get(position).goodNum);
        viewHolder.tvBadNum.setText(mList.get(position).badNum);
        viewHolder.tvSpeakNum.setText(mList.get(position).speakNum);
        return convertView;
    }

    class ViewHolder{
        public TextView tvAuthor, tvTime, tvGoodNum, tvBadNum, tvSpeakNum;
        public ImageView ivIcon;

    }
}
