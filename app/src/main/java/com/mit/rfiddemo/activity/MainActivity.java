package com.mit.rfiddemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mit.rfiddemo.R;

import com.mit.rfiddemo.entity.Preferences;
import com.mit.rfiddemo.serialport.SerialPort;

import com.mit.rfiddemo.util.XLog;
import com.mit.rfiddemo.util.XToast;
import com.ruijie.uhflib.power.manage.PowerManage;
import com.ruijie.uhflib.uhf.LinkInter;
import com.ruijie.uhflib.uhf.manage.LinkManage;
import java.util.HashMap;

import java.util.Timer;
import java.util.TimerTask;

import ruijie.com.uhflib.uhf.InventoryData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button configureRFID;
    private Button connectTest;
    private Button startInventory;
    private Button stopInventory;
    private Button clearInventory;
    private SerialPort mSerialPort;
    private Status status = Status.DISCONNECT;
    HashMap<String, Integer> epcMap = new HashMap <>();
    HashMap<String, Integer> epcMapTotal = new HashMap <>();
    private InventoryData[] inventoryData;
    private TextView epcList;
    LinkInter mLinker;
    String TAG = "RFID_LOG";
    String dispTmp;
    Timer timer;
    private Boolean isDisplayInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XLog.enableLogging();
        //开始计时
        startTiming(2000);
        findMyViewById();
        setStartInventory();
    }

    @SuppressLint("HandlerLeak")
    Handler checkHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what){
                    case 2000:
                        InventoryData[] inventoryData = (InventoryData[]) msg.obj;
                        break;

                    default:
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private void findMyViewById(){
        configureRFID = (Button) findViewById(R.id.configure_rfid);
        connectTest = (Button) findViewById(R.id.connect_test_button);
        startInventory = (Button) findViewById(R.id.start_inventory);
        stopInventory = (Button) findViewById(R.id.stop_inventory);
        epcList = (TextView) findViewById(R.id.epc_list_tv);
        clearInventory = (Button)findViewById(R.id.clear_inventory);

        configureRFID.setOnClickListener(this);
        connectTest.setOnClickListener(this);
        startInventory.setOnClickListener(this);
        stopInventory.setOnClickListener(this);
        clearInventory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.configure_rfid:
                Intent intent = new Intent(MainActivity.this, RfidActivity.class);
                epcMap.clear();
                startActivity(intent);
                break;
            case R.id.connect_test_button:
                String tempPort = Preferences.getInstance().getReaderPort();
                String tempBaud =Preferences.getInstance().getReaderBaud();
                XLog.d(TAG,"串口号: "+ tempPort + ";波特率:" +tempBaud);
                connect(tempPort,  Integer.parseInt(tempBaud));
                break;
            case R.id.start_inventory:
                setStartInventory();
                break;
            case R.id.stop_inventory:
                setStopInventory();
                break;
            case R.id.clear_inventory:
                setClearCountNum();
                break;
            default:
                break;
        }
    }

    private enum Status {
        CONNECT, DISCONNECT, START, STOP
    }

    private void connect(String address, int baud){
        XLog.i("连接设备: " + address + " : " + baud);

        //设备上电
        PowerManage.getInstance().powerUp();
        XLog.d(TAG, "设备上电");
        //获取RFID模组
        mLinker = LinkManage.getInstance(LinkManage.TYPE_HANDSET);
        if (mLinker == null){
            XLog.d(TAG, "获取RFID模组失败，未知错误");
        }
        XLog.d(TAG, "获取RFID模组");
        //RFID模组初始化
        if(mLinker.initInventory(address, baud) != 0){
            XToast.showToast("无效的串口号,连接失败");
            XLog.d(TAG,"连接失败");
        }else {
            status = Status.CONNECT;
            XLog.d(TAG,"连接成功");
        }
    }

    private void setStartInventory(){
        String address = Preferences.getInstance().getReaderPort();
        int baud =  Integer.parseInt(Preferences.getInstance().getReaderBaud());
        XLog.d(TAG, "address: " + address);
        XLog.d(TAG, "baud: " + baud);
        if (mLinker == null){
            connect(address, baud);
        }
        int power1;
        int power2;
        int power3;
        int power4;
//        power1 = Integer.parseInt(Preferences.getInstance().getReaderPower1());
//        power2 = Integer.parseInt(Preferences.getInstance().getReaderPower2());
//        power3 = Integer.parseInt(Preferences.getInstance().getReaderPower3());
//        power4 = Integer.parseInt(Preferences.getInstance().getReaderPower4());
//
//        XLog.d(TAG, "ant0: " + power1);
//        XLog.d(TAG, "ant1: " + power2);
//        XLog.d(TAG, "ant2: " + power3);
//        XLog.d(TAG, "ant3: " + power4);

        power1 = 300;
        power2 = 300;
        power3 = 300;
        power4 = 300;

        mLinker.enableAnt(0, power1, 20);
        mLinker.enableAnt(1, power2, 20);
        mLinker.enableAnt(2, power3, 20);
        mLinker.enableAnt(3, power4, 20);

        mLinker.setCallbackHandler(handler);
        startNewThreadInventory();
    }

    private void startNewThreadInventory(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                XLog.d(TAG, "开始盘点");
                mLinker.startInventory();
            }
        }).start();
    }

    private void setStopInventory(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mLinker.getIsInventoryRunning())
                {
                    if (mLinker.stopInventory()){
                        XLog.d(TAG, "已停止盘存");
                        //timer.cancel();
                    }
                }

            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //XLog.d(TAG, "接收到msg：" + msg.what);
            try{
                switch (msg.what){
                    case 20000:
                        inventoryData = (InventoryData[])msg.obj;
                        if (inventoryData == null || inventoryData.length == 0) {
                            XLog.d(TAG,"盘点空");
                            return;
                        }else {
                            displayEpcInfo();
                        }
                        break;
                    default:
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    private void displayEpcInfo(){

        String stemp = null;
        for (int i = 0; i<inventoryData.length; i++){
            stemp = inventoryData[i].getEpc();
            if (epcMap.containsKey(stemp)){
                epcMap.put(stemp, epcMap.get(stemp) + 1);
            }else {
                epcMap.put(stemp, 1);
            }
            if (epcMapTotal.containsKey(stemp)){
                epcMapTotal.put(stemp, epcMapTotal.get(stemp) + 1);
            }else {
                epcMapTotal.put(stemp, 1);
            }
            XLog.d(TAG, "EPC num: " + i + " " + stemp);
        }
        if (isDisplayInfo){
            dispTmp = "";
            for (String key : epcMapTotal.keySet()){
                if (!epcMap.containsKey(key))
                    epcMap.put(key, 0);
                dispTmp = dispTmp + key + "\t\t\t\t" + epcMap.get(key) + "\t\t\t\t" + epcMapTotal.get(key) + "\n";
            }
            epcList.setText("");
            epcList.setText(dispTmp);
            epcMap.clear();
            isDisplayInfo = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setStopInventory();
    }

    //定时任务两秒显示一次数据
    private void startTiming(int intevalPeriod){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                isDisplayInfo = true;
                XLog.d(TAG,"timer task is execute...");
            }
        };
        timer = new Timer();
        long delay = 0;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
        XLog.d(TAG,"timer is start timing...");
    }

    //清空当前盘存数据
    private void setClearCountNum(){
        setStopInventory();
        epcMap.clear();
        epcMapTotal.clear();
        inventoryData = null;
        XLog.d(TAG, "已停止盘存");
        XLog.d(TAG, "已清空epcMap");
        //setStartInventory();
        epcList.setText("");
        isDisplayInfo = false;
    }
}
