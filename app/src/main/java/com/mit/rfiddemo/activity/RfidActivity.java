package com.mit.rfiddemo.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mit.rfiddemo.R;
import com.mit.rfiddemo.entity.Preferences;
import com.mit.rfiddemo.include.IncludeEdit;
import com.mit.rfiddemo.include.spiner.AbstractSpinerAdapter;
import com.mit.rfiddemo.include.spiner.SpinerPopWindow;
import com.mit.rfiddemo.serialport.SerialPortFinder;
import com.mit.rfiddemo.util.StringUtils;
import com.mit.rfiddemo.util.XLog;

import java.util.ArrayList;
import java.util.List;

public class RfidActivity extends AppCompatActivity {

    private TextView mTvSerial;
    private LinearLayout mLlSerial;
    private IncludeEdit mIncludePortEdit;
    private IncludeEdit mIncludeBaudEdit;
    private EditText mEditSingle, mEditTotal;
    private EditText mEditPower1, mEditPower2, mEditPower3, mEditPower4;
    private Button mTvSave;
    private TextView mTvText;
    private TextView mTvPower;

    private List<String> mPortList = new ArrayList<>();
    private List<String> mBaudList = new ArrayList<>();
    private List<String> mReaderCountList = new ArrayList<>();
    private List<String> mReaderTotalCountList = new ArrayList<>();
    private List<String> mPowerList = new ArrayList<>();
    private SpinerPopWindow mSpinerPort, mSpinerBaud, mSpinerPower, mSpinerReaderCount, mSpinerReaderTotalCount;
    private String mStrPosPort;
    private boolean isSerial = true;
    private int mPowerPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);

        myFindViewById();
        onMyClick();
        initSerial();


    }

    private void initSerial(){
        XLog.d("initSerial","串口初始化开始....");
        SerialPortFinder mSerialPortFinder = new SerialPortFinder();
        String[] entries = mSerialPortFinder.getAllDevices();
        final String[] entryValues = mSerialPortFinder.getAllDevicesPath();
        XLog.d("initSerial","port num: " + entryValues.length);
        for (int i = 0; i < entryValues.length; i++){
            XLog.d("initSerial","port: " + entryValues[i]);
            if (entryValues[i].equals(Preferences.getInstance().getReaderPort())) {
                mIncludePortEdit.mEditText.setText(entries[i]);
            }
        }

        String[] lists = entries;
        for(int i = 0; i < lists.length; i++){
            mPortList.add(lists[i]);
        }
        //选择串口号
        mSpinerPort = new SpinerPopWindow(this);
        mSpinerPort.refreshData(mPortList, 0);
        mSpinerPort.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                    @Override
                    public void onItemClick(int pos) {
                        if (pos >= 0 && pos < mPortList.size()){
                            String value = mPortList.get(pos);
                            mIncludePortEdit.mEditText.setText(value);
                            mStrPosPort = entryValues[pos];
                            Preferences.getInstance().setReaderPort(mStrPosPort);
                            Log.d("serialport", "serialport" + value);
                        }
                    }
                });

        //波特率
        lists = getResources().getStringArray(R.array.baud_rate);
        for (int i = 0; i < lists.length; i++){
            mBaudList.add(lists[i]);
        }
        mSpinerBaud = new SpinerPopWindow(this);
        mSpinerBaud.refreshData(mBaudList, 0);
        mSpinerBaud.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                if (pos >= 0 && pos < mBaudList.size()){
                    String value = mBaudList.get(pos);
                    mIncludeBaudEdit.mEditText.setText(value);
                    Preferences.getInstance().setReaderBaud(value);
                }
            }
        });

        lists = getResources().getStringArray(R.array.reader_count);
        for (int i = 0; i < lists.length; i++){
            mReaderCountList.add(lists[i]);
        }
        mSpinerReaderCount = new SpinerPopWindow(this);
        mSpinerReaderCount.refreshData(mReaderCountList, 0);
        mSpinerReaderCount.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                                               @Override
                                               public void onItemClick(int pos) {
                                                   if (pos >= 0 && pos < mReaderCountList.size()) {
                                                       String value = mReaderCountList.get(pos);
                                                       mEditSingle.setText(value);
                                                       Preferences.getInstance().setReaderCount(value);
                                                   }
                                               }
                                           });

        lists = getResources().getStringArray(R.array.reader_total_count);
        for (int i = 0; i < lists.length; i++){
            mReaderTotalCountList.add(lists[i]);
        }
        mSpinerReaderTotalCount = new SpinerPopWindow(this);
        mSpinerReaderTotalCount.refreshData(mReaderTotalCountList, 0);
        mSpinerReaderTotalCount.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                if (pos >= 0 && pos < mReaderTotalCountList.size()){
                    String value = mReaderTotalCountList.get(pos);
                    mEditTotal.setText(value);
                    Preferences.getInstance().setReaderTotalCount(value);
                }
            }

        });

        lists = getResources().getStringArray(R.array.power);
        for(int i = 0; i < lists.length; i++){
            mPowerList.add(lists[i]);
        }
        mSpinerPower = new SpinerPopWindow(this);
        mSpinerPower.refreshData(mPowerList, 0);
        mSpinerPower.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                if (pos >= 0 && pos < mPowerList.size()){
                    String value = mPowerList.get(pos);
                    if (mPowerPosition == 1){
                        mEditPower1.setText(value);
                    } else if (mPowerPosition == 2){
                        mEditPower2.setText(value);
                    } else if (mPowerPosition == 3){
                        mEditPower3.setText(value);
                    } else if (mPowerPosition == 4){
                        mEditPower4.setText(value);
                    }

                    if (!StringUtils.isEmpty(getPower1())
                            && !StringUtils.isEmpty(getPower2())
                            && !StringUtils.isEmpty(getPower3())
                            && !StringUtils.isEmpty(getPower4())){

//                    Intent intent = new Intent(ReaderService.EVENT_READER_SET_POWER);
//                    intent.putExtra(Constants.Extra.EXTRA_RFID_SERIAL_POWER1, getPower1());
//                    intent.putExtra(Constants.Extra.EXTRA_RFID_SERIAL_POWER2, getPower2());
//                    intent.putExtra(Constants.Extra.EXTRA_RFID_SERIAL_POWER3, getPower3());
//                    intent.putExtra(Constants.Extra.EXTRA_RFID_SERIAL_POWER4, getPower4());
//                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    }
                }
            }
        });
    }




    private void myFindViewById(){
        mIncludePortEdit = new IncludeEdit(this, R.id.activity_rfid_include_port);
        mIncludeBaudEdit = new IncludeEdit(this, R.id.activity_rfid_include_baud);
        mIncludePortEdit.mTvTitle.setText("串口号");
        mIncludePortEdit.mEditText.setFocusable(false);
        mIncludeBaudEdit.mTvTitle.setText("波特率");
        mIncludeBaudEdit.mEditText.setFocusable(false);
        mEditSingle = (EditText) findViewById(R.id.activity_rfid_edit_single);
        mEditTotal = (EditText) findViewById(R.id.activity_rfid_edit_total);
        mEditSingle.setFocusable(false);
        mEditTotal.setFocusable(false);
        mEditPower1 = (EditText) findViewById(R.id.activity_rfid_edit_no1);
        mEditPower2 = (EditText) findViewById(R.id.activity_rfid_edit_no2);
        mEditPower3 = (EditText) findViewById(R.id.activity_rfid_edit_no3);
        mEditPower4 = (EditText) findViewById(R.id.activity_rfid_edit_no4);
        mEditPower1.setFocusable(false);
        mEditPower2.setFocusable(false);
        mEditPower3.setFocusable(false);
        mEditPower4.setFocusable(false);
        mTvSave = (Button) findViewById(R.id.activity_rfid_tv_save);

        //从preference读取并设置
        mIncludePortEdit.mEditText.setText(Preferences.getInstance().getReaderPort());
        mIncludeBaudEdit.mEditText.setText(Preferences.getInstance().getReaderBaud());
        mEditSingle.setText(Preferences.getInstance().getReaderCount());
        mEditTotal.setText(Preferences.getInstance().getReaderTotalCount());
        mEditPower1.setText(Preferences.getInstance().getReaderPower1());
        mEditPower2.setText(Preferences.getInstance().getReaderPower2());
        mEditPower3.setText(Preferences.getInstance().getReaderPower3());
        mEditPower4.setText(Preferences.getInstance().getReaderPower4());
    }

    public void onMyClick() {
        mIncludePortEdit.mEditText.setOnClickListener(v -> {
            if (isSerial){
                showPortSpinWindow();
            }
        });

        mIncludeBaudEdit.mEditText.setOnClickListener(v -> {
            showBaudSpinWindow();
        });

        mEditSingle.setOnClickListener(v -> {
            showReaderCountSpinWindow();
        });

        mEditTotal.setOnClickListener(v -> {
            showReaderTotalCountSpinWindow();
        });

        mEditPower1.setOnClickListener(v -> {
            showPowerWindow(1, mEditPower1);
        });

        mEditPower2.setOnClickListener(v -> {
            showPowerWindow(2, mEditPower2);
        });

        mEditPower3.setOnClickListener(v -> {
            showPowerWindow(3, mEditPower3);
        });

        mEditPower4.setOnClickListener(v -> {
            showPowerWindow(4, mEditPower4);
        });

        mTvSave.setOnClickListener(v -> {
            Preferences.getInstance().setReaderPower1(getPower1());
            Preferences.getInstance().setReaderPower2(getPower2());
            Preferences.getInstance().setReaderPower3(getPower3());
            Preferences.getInstance().setReaderPower4(getPower4());
            finish();
        });
    }

    private void showPortSpinWindow() {
        mSpinerPort.setWidth(mIncludePortEdit.mEditText.getWidth());
        mSpinerPort.showAsDropDown(mIncludePortEdit.mEditText);
    }

    private void showBaudSpinWindow(){
        mSpinerBaud.setWidth(mIncludeBaudEdit.mEditText.getWidth());
        mSpinerBaud.showAsDropDown(mIncludeBaudEdit.mEditText);
    }

    private void showReaderCountSpinWindow(){
        mSpinerReaderCount.setWidth(mEditSingle.getWidth());
        mSpinerReaderCount.showAsDropDown(mEditSingle);
    }

    private void showReaderTotalCountSpinWindow(){
        mSpinerReaderTotalCount.setWidth(mEditTotal.getWidth());
        mSpinerReaderTotalCount.showAsDropDown(mEditTotal);
    }

    private void showPowerWindow(int position, View view) {
        mPowerPosition = position;
        mSpinerPower.setWidth(view.getWidth());
        mSpinerPower.showAsDropDown(view);
    }
    private String getPower1(){
        return mEditPower1.getText().toString().trim();
    }

    private String getPower2(){
        return mEditPower2.getText().toString().trim();
    }

    private String getPower3(){
        return mEditPower3.getText().toString().trim();
    }

    private String getPower4(){
        return mEditPower4.getText().toString().trim();
    }


}
