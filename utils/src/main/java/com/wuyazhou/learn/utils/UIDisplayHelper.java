
package com.wuyazhou.learn.utils;

import android.content.res.Resources;


/**
 * @author 吴亚洲
 * @version 1.0
 * @date Created at 2018/8/4
 * @since 1.0
 */
public class UIDisplayHelper {

    /**
     * 屏幕密度,系统源码注释不推荐使用
     */
    public static final float DENSITY = Resources.getSystem()
            .getDisplayMetrics().density;
    private static final String TAG = "Devices";
    /**
     * 屏幕密度
     */
    public static float sDensity = 0f;
    /**
     * 是否有摄像头
     */


    /**
     * 把以 dp 为单位的值，转化为以 px 为单位的值
     *
     * @param dpValue 以 dp 为单位的值
     * @return px value
     */
    public static int dpToPx(int dpValue) {
        return (int) (dpValue * DENSITY + 0.5f);
    }

    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(float spValue) {
        return (int) (spValue * DENSITY + 0.5f);
    }

}
