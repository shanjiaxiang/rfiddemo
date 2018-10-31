package com.mit.rfiddemo.entity;

import android.content.Context;
import android.content.SharedPreferences;

import com.mit.rfiddemo.console.App;


/**
 * @author xiny
 * @date 2017/9/1
 */
public class Preferences {

    private static final String PREFERENCE_NAME = "USER";
    private static SharedPreferences mPreference;
    private static Preferences mUserPreference;
    private static SharedPreferences.Editor mEditor;

    private final String SHARED_KEY_READER_PORT = "shared_key_reader_port";
    private final String SHARED_KEY_READER_BAUD = "shared_key_reader_baud";
    private final String SHARED_KEY_READER_POWER1 = "shared_key_reader_power1";
    private final String SHARED_KEY_READER_POWER2 = "shared_key_reader_power2";
    private final String SHARED_KEY_READER_POWER3 = "shared_key_reader_power3";
    private final String SHARED_KEY_READER_POWER4 = "shared_key_reader_power4";
    private final String SHARED_KEY_READER_COUNT = "shared_key_reader_count";
    private final String SHARED_KEY_READER_TOTAL_COUNT = "shared_key_reader_total_count";

    public Preferences(){
        mPreference = App.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

    }

    public static Preferences getInstance(){
        if (mUserPreference == null){
            mUserPreference = new Preferences();
        }
        mEditor = mPreference.edit();
        return mUserPreference;
    }

    public static void clear(){
        mUserPreference = null;
        mEditor = null;
    }

    public void setReaderPort(String port){
        mEditor.putString(SHARED_KEY_READER_PORT, port);
        mEditor.commit();
    }

    public String getReaderPort(){
        return mPreference.getString(SHARED_KEY_READER_PORT, "");
    }

    public void setReaderBaud(String baud){
        mEditor.putString(SHARED_KEY_READER_BAUD, baud);
        mEditor.commit();
    }

    public String getReaderBaud(){
        return mPreference.getString(SHARED_KEY_READER_BAUD, "");
    }

    public void setReaderPower1(String power){
        mEditor.putString(SHARED_KEY_READER_POWER1, power);
        mEditor.commit();
    }

    public String getReaderPower1(){
        return mPreference.getString(SHARED_KEY_READER_POWER1, "");
    }

    public void setReaderPower2(String power){
        mEditor.putString(SHARED_KEY_READER_POWER2, power);
        mEditor.commit();
    }

    public String getReaderPower2(){
        return mPreference.getString(SHARED_KEY_READER_POWER2, "");
    }

    public void setReaderPower3(String power){
        mEditor.putString(SHARED_KEY_READER_POWER3, power);
        mEditor.commit();
    }

    public String getReaderPower3(){
        return mPreference.getString(SHARED_KEY_READER_POWER3, "");
    }

    public void setReaderPower4(String power){
        mEditor.putString(SHARED_KEY_READER_POWER4, power);
        mEditor.commit();
    }

    public String getReaderPower4(){
        return mPreference.getString(SHARED_KEY_READER_POWER4, "");
    }

    public void setReaderCount(String count){
        mEditor.putString(SHARED_KEY_READER_COUNT, count);
        mEditor.commit();
    }

    public String getReaderCount(){
        return mPreference.getString(SHARED_KEY_READER_COUNT, "");
    }

    public void setReaderTotalCount(String count){
        mEditor.putString(SHARED_KEY_READER_TOTAL_COUNT, count);
        mEditor.commit();
    }

    public String getReaderTotalCount(){
        return mPreference.getString(SHARED_KEY_READER_TOTAL_COUNT, "");
    }

}
