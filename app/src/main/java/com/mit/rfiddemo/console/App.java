package com.mit.rfiddemo.console;

import android.app.Application;


/**
 * @author xiny
 * @date 2018/4/27
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }



    public static App getInstance(){
        return instance;
    }


}
