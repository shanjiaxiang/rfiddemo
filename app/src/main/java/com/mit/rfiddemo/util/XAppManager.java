package com.mit.rfiddemo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ConcurrentModificationException;
import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ConcurrentModificationException;
import java.util.Stack;


/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author xiny
 */
public class XAppManager {

    private String PageTag = "XAppManager";
    private static Stack<Activity> activityStack;
    private static XAppManager instance;

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static XAppManager getAppManager() {
        if (instance == null) {
            instance = new XAppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        XLog.d(PageTag, "ActivityList.onCreate(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";") ;

    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }


    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
        XLog.d(PageTag, "ActivityList.onDestroy栈顶(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";") ;

    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            XLog.d(PageTag, "ActivityList.onDestroy(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";") ;
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (ConcurrentModificationException e){}
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        Stack<Activity> delList = new Stack<Activity>();//用来装需要删除的元素
        for (Activity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                delList.add(activity);
            }
        }

        for (Activity activity : delList) {
            finishActivity(activity);
        }
        activityStack.removeAll(delList);//遍历完成后执行删除

        if (delList!=null) {
            delList.clear();
            delList = null;
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                XLog.d(PageTag, "ActivityList.finish(" + activityStack.get(i).getLocalClassName() + ");") ;
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 应用程序退出
     * @param cls 退出后跳转的Activity
     */
    public void AppExit(Context context, Class cls) {
        try {
            finishAllActivity();

            Intent intent = new Intent(context, cls) ;
            context.startActivity(intent) ;

            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
