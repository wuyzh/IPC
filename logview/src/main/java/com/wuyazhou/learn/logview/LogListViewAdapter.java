package com.wuyazhou.learn.logview;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuyazhou.learn.utils.UIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyzh
 * */
public class LogListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<LogShowView.LogModel> mList = null;
    private List<LogShowView.LogModel> mKeyList = null;

    private String mKey = null;
    public LogListViewAdapter(Context context,List<LogShowView.LogModel> list){
        mContext = context;
        mList = list;
        mKeyList = new ArrayList<LogShowView.LogModel>();
    }
    @Override
    public int getCount() {
        if (mKey == null){
            return mList.size();
        }else {
            return mKeyList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mKey == null){
            return mList.get(position);
        }else {
            return mKeyList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holderView;

        LogShowView.LogModel entity = null;
        if (mKey == null){
            entity = mList.get(position);
        }else {
            entity = mKeyList.get(position);
        }

        if (convertView == null){
            convertView = new TextView(mContext);
            ((TextView) convertView).setTextSize(UIDisplayHelper.sp2px(5));
            holderView = new ViewHolder();

            holderView.textLogView = (TextView) convertView;
            holderView.textLogView.setBackgroundResource(R.drawable.log_item_background);
            convertView.setTag(holderView);
        }else {
            holderView = (ViewHolder) convertView.getTag();
        }

        holderView.textLogView.setText(Html.fromHtml( "<font color=#35A5BB>"+ entity.key+"</font> "+ "<font color=#EDB03A>"+ entity.value+"</font>"));

        holderView.textLogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKey == null){
                    String string = ((TextView)v).getText()+"";
                    int num = string.indexOf(LogShowUtil.INTERVAL);
                    if (num == -1){
                        return;
                    }else {
                        mKey = string.substring(0,num+LogShowUtil.INTERVAL.length());
                    }
                }else {
                    mKey = null;
                }

                showLogByChoseKey();
            }
        });

        return convertView;

    }

    public final class ViewHolder {
        TextView textLogView;
    }

    public void addNewLog(LogShowView.LogModel logModel){
        if (mKey != null && mKey.equals(logModel.key)){
            mKeyList.add(logModel);
        }
        mList.add(logModel);
        notifyDataSetChanged();
    }

    public void clean(){
        mList.clear();
        mKeyList.clear();
        mKey = null;
        notifyDataSetChanged();
    }

    private void showLogByChoseKey(){
        if (mKey == null){
            mKeyList.clear();
        }else {
            for (LogShowView.LogModel logModel: mList){
                if (logModel.key.equals(mKey)){
                    mKeyList.add(logModel);
                }
            }
        }
        notifyDataSetChanged();
    }
}
