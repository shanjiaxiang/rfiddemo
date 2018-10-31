package com.mit.rfiddemo.include;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mit.rfiddemo.R;
import com.mit.rfiddemo.base.BaseInclude;


/**
 * @author xiny
 * @date 2018/5/29
 */
public class IncludeEdit extends BaseInclude {

    public TextView mTvTitle;
    public EditText mEditText;

    public IncludeEdit(Context context, int R_layout) {
        super(context, R_layout);

        this.myFindView();
    }

    public IncludeEdit(Activity activity, int R_layout) {
        super(activity, R_layout);

        this.myFindView();
    }

    @Override
    public void myFindView() {
        mTvTitle = myFindViewById(R.id.include_edit_tv_title);
        mEditText = myFindViewById(R.id.include_edit_edit);
        mTvTitle.setText("");
        mEditText.setText("");
    }

    public String getEditText(){
        return mEditText.getText().toString();
    }

    @Override
    public void onClick(View v) {

    }
}
