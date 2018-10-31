package com.mit.rfiddemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mit.rfiddemo.R;
import com.mit.rfiddemo.constants.Constants;
import com.mit.rfiddemo.entity.Preferences;
import com.mit.rfiddemo.serialport.SerialPort;
import com.mit.rfiddemo.service.ReaderService;
import com.mit.rfiddemo.util.StringUtils;
import com.mit.rfiddemo.util.XAppUtils;
import com.mit.rfiddemo.util.XLog;
import com.mit.rfiddemo.util.XToast;
import com.ruijie.uhflib.power.manage.PowerManage;
import com.ruijie.uhflib.uhf.LinkInter;
import com.ruijie.uhflib.uhf.manage.LinkManage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import ruijie.com.uhflib.uhf.InventoryData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button configureRFID;
    private Button connectTest;
    private Button startInventory;
    private Button stopInventory;
    private SerialPort mSerialPort;
    private Status status = Status.DISCONNECT;
    private ArrayList<String> epcList = new ArrayList <>();
    private InventoryData[] inventoryData;
    private ListView listView;
    LinkInter mLinker;
    String TAG = "RFID_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XLog.enableLogging();
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
        listView = (ListView)findViewById(R.id.epc_list);

        configureRFID.setOnClickListener(this);
        connectTest.setOnClickListener(this);
        startInventory.setOnClickListener(this);
        stopInventory.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.configure_rfid:
                Intent intent = new Intent(MainActivity.this, RfidActivity.class);
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
                displayEpcInfo();
                break;
            case R.id.stop_inventory:
                setStopInventory();
                break;
        }
    }

    private void startService(){
        if (!StringUtils.isEmpty(Preferences.getInstance().getReaderPort())
                && !StringUtils.isEmpty(Preferences.getInstance().getReaderBaud())){
            if (!XAppUtils.isServiceRunning(this, ReaderService.class.getName())){
                Intent intent = new Intent(this, ReaderService.class);
                intent.putExtra(Constants.Extra.EXTRA_READER_TO_START, true);
                startService(intent);
            }
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
            XToast.showToast("获取RFID模组失败，未知错误");
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
        int power1 = Integer.parseInt(Preferences.getInstance().getReaderPower1());
        int power2 = Integer.parseInt(Preferences.getInstance().getReaderPower2());
        int power3 = Integer.parseInt(Preferences.getInstance().getReaderPower3());
        int power4 = Integer.parseInt(Preferences.getInstance().getReaderPower4());

        XLog.d(TAG, "ant0: " + power1);
        XLog.d(TAG, "ant1: " + power2);
        XLog.d(TAG, "ant2: " + power3);
        XLog.d(TAG, "ant3: " + power4);


        power1 = 300;
        power2 = 300;
        power3 = 300;
        power4 = 300;


        mLinker.enableAnt(0, power1, 2000);
        mLinker.enableAnt(1, power2, 2000);
        mLinker.enableAnt(2, power3, 2000);
        mLinker.enableAnt(3, power4, 2000);


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
                        XToast.showToast("已停止盘存");

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

//                        inventoryData = (InventoryData[]) msg.obj;
////                        XLog.d(TAG, "data length: " + inventoryData.length);
//                        for (int i = 0; i<inventoryData.length; i++){
//                            epcList.add(new String(inventoryData[i].epc));
//                            XLog.d(TAG, "EPC num: " + i + " " + new String(inventoryData[i].epc));
//                        }
//                        displayEpcInfo();
                        //startNewThreadInventory();
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
        if (epcList.size() > 0){
            epcList.clear();
        }
        String stemp = null;
        for (int i = 0; i<inventoryData.length; i++){
            stemp = inventoryData[i].getEpc();
            epcList.add(stemp);
            XLog.d(TAG, "EPC num: " + i + " " + stemp);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter <String>(MainActivity.this, android.R.layout.simple_list_item_1, epcList);
        listView.setAdapter(adapter);
    }
}
