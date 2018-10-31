package com.mit.rfiddemo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



public class XToast {


    private static String PAGETAG = "XToast" ;

    private static Toast mToast = null ;

    /**Toast 默认布局*/
    private static LinearLayout mToastLayout = null;

    /**Toast 图片控件*/
    private static ImageView mImageCodeProject = null;

    /**Toast 自定义布局*/
    private static View mLayoutView = null;

    /**初始化 Toast 及 布局*/
    private static void initView(String string) {

        if (mToast != null) {
            XLog.d(PAGETAG, "mToast != null;");
            cancelToast();
        }

        mToast = null ;
        mLayoutView = null;
        mImageCodeProject = null;

        mToast = Toast.makeText(getCurrentContext(), string, Toast.LENGTH_SHORT);

        mToastLayout = (LinearLayout)mToast.getView();
//        mToastLayout.setBackgroundResource(x.lib.R.drawable.bg_c88_rounded);


    }

    /**Toast可以输入字符串直接show
     * @param context 该参数不起作用
     * @param string
     */
    public static void showToast(Context context , String string){
        showToast(string);
    }

    /**Toast可以输入字符串直接show*/
    public static void showToast(String string){
        try {

            initView(string);

            show();

        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }

    /**自定义布局Resid的Toast表直接show*/
    public static void showToastView(View view){
        try {

            initView("");

            mToastLayout.removeAllViews();
//			/**设置 toast View */
            mToastLayout.addView(view);

            show();

        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }

    /**自定义布局Resid的Toast表直接show*/
    public static void showToastLayout(int resId){
        try {

            initView("");

            mToastLayout.removeAllViews();
            /**设置 toast layout */
//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mLayoutView = inflater.inflate(resId, null);
            mToastLayout.addView(mLayoutView);

            show();

        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }

    /**带图片的Toast可以输入字符串直接show(不建议使用)*/
    protected static void showToastImage(String string, Drawable drIcon){
        try {

            initView(string);

            if (mImageCodeProject == null) {
                mImageCodeProject = new ImageView(getCurrentContext());
                mImageCodeProject.setPadding(0, 10, 0, 50);
//				mImageCodeProject.setLayoutParams(new LayoutParams(500,400));
            }

            /**设置 imageDrawable 的资源*/
            if (drIcon != null) {
                mImageCodeProject.setImageDrawable(drIcon);
            }

            mToastLayout.addView(mImageCodeProject, 0);

            show();

        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }

    /**带图片的Toast可以输入字符串直接show*/
    public static void showToastImage(String string, int resId){
        try {

            initView(string);

            if (mImageCodeProject == null) {
                mImageCodeProject = new ImageView(getCurrentContext());
                mImageCodeProject.setPadding(0, 10, 0, 50);
            }

            /**设置 imageResId 的资源*/
            try {
                mImageCodeProject.setImageResource(resId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mToastLayout.addView(mImageCodeProject, 0);

            show();

        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }

    /**Toast 显示*/
    protected static void show() {

//		if (MyGetAppState.appIsForeGround(context.getApplicationContext()) == true) {
        if (!XAppUtils.isApplicationInBackground(getCurrentContext())) {
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//			mToastLayout.setGravity(Gravity.CENTER);
            mToast.show();
        }else {
            cancelToast();
        }
    }

    /**清空当前toast的静态Context 释放资源
     * 最好在BaseActivity 的onResume() 中调用 */
    public static void cleanToast() {
        mToast = null;
    }

    /**Toast 取消*/
    public static void cancelToast(){
        if (mToast != null) {
            mToast.cancel() ;
        }
    }

    /**使用getApplicationContext()*/
    private static Context getCurrentContext(){
        return XAppManager.getAppManager().currentActivity().getApplicationContext();
    }
}
