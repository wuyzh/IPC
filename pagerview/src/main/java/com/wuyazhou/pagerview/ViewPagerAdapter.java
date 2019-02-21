package com.wuyazhou.pagerview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function 功能主要是作为pager适配器
 * */

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private List<String> mViewTitles;
    private Context mContext;

    /** 有参构造*/
    public ViewPagerAdapter(List<View> views, List<String> viewTitles,Context context) {
        super();
        this.mViews = views;
        this.mViewTitles = viewTitles;
        this.mContext = context;
    }
    /** 获得长度*/
    @Override
    public int getCount() {

        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    /**展示的view*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /** 获得展示的view*/
        View view= mViews.get(position);
        /** 添加到容器*/
        container.addView(view);
        /** 返回显示的view*/
        return view;
    }
    /** 销毁view*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        /** 从容器中移除view*/
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mViewTitles.get(position);
    }
}
