package com.kimson.kcframeofandroid.ui.util;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Window;

import com.kimson.kcframeofandroid.model.Dimension;

/**
 * Created by zhujianheng on 8/30/16.
 */
public class DensityUtils extends com.kimson.library.util.DensityUtils {

    /**
     * 获取屏幕高度
     * @param activity
     * @return
     */
    public static Dimension getAreaOne(Activity activity){
        Dimension dimen = new Dimension();
        Display disp = activity.getWindowManager().getDefaultDisplay();
        Point outP = new Point();
        disp.getSize(outP);
        dimen.mWidth = outP.x ;
        dimen.mHeight = outP.y;
        return dimen;
    }

    /**
     * 获取
     * @param activity
     * @return
     */
    public static Dimension getAreaThree(Activity activity){
        Dimension dimen = new Dimension();
        // 用户绘制区域
        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        dimen.mWidth = outRect.width() ;
        dimen.mHeight = outRect.height();
        // end
        return dimen;
    }

}
