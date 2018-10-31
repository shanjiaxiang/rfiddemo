package com.mit.rfiddemo.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;


/**
 * activity  include
 *
 */
public abstract class BaseInclude implements View.OnClickListener {

    protected View view = null;
    protected Context context = null;
    protected Activity activity = null;

    public abstract void myFindView();

    public BaseInclude(Activity activity, int R_layout) {
        if (activity != null) {
            this.activity = activity;
            view = (View)activity.findViewById(R_layout);
        }
    }

    public BaseInclude(View convertView, int R_layout) {
        if (convertView != null) {
            this.view = convertView.findViewById(R_layout);
        }
    }

    public BaseInclude(Context context, int R_layout) {
        if (context != null) {
            this.context = context;
            this.view = LayoutInflater.from(context).inflate(R_layout, null) ;
        }
    }

    public View getView() {
        return view;
    }

    protected <T extends View>T myFindViewById(int id){
        return (T) view.findViewById(id);
    }

}
