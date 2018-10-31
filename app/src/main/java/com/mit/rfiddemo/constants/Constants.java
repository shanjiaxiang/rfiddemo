package com.mit.rfiddemo.constants;
/**
 * @author xiny
 * @date 2018/4/27
 */
public class Constants {

    /**
     * 屏保延时
     */
    public static int saver = 0;
    /**
     * 是否因读写器导致重启
     */
    public static boolean isRfidRestart;

    public static String qiniuToken;
    public static String qiniuBaseUrl;
    public static String qiniuBucket;

    public static String accesskeyId;
    public static String accesskeySecret;
    public static String logEndpoint;
    public static String logProject;
    public static String logStore;
    /**
     * 红外延时无人关闭
     */
    public static boolean delayedStop = false;

    public final static class EventType {
        public final static String EVENT_NET_CHANGE = "event_net_change";
        public final static String EVENT_TIME = "event_time";
        public final static String EVENT_SOCKET_STATUS = "event_socket_status";
        public final static String EVENT_READ_TEXT = "event_read_text";
        public final static String EVENT_HAS_PEOPLE = "event_has_people";
        public final static String EVENT_EPC_LIST = "event_epc_list";
        public final static String EVENT_OPEN_DOOR = "event_open_door";
        public final static String EVENT_OPEN_DOOR_SUCCESS = "event_open_door_success";
        public final static String EVENT_OPEN_PAY_DOOR = "event_open_pay_door";
        public final static String EVENT_PAY_NOTIFY = "event_pay_notify";
        public final static String EVENT_PAY_SUCCESS = "event_pay_success";
        public final static String EVENT_REFRESH_PRODUCT = "event_refresh_product";
        public final static String EVENT_SHOP_STATUS = "event_shop_status";
        public final static String EVENT_CHECK_COMMODITY = "event_check_commodity";
        public final static String EVENT_UPDATE = "event_update";
        public final static String EVENT_ACTIVITY = "event_activity";
        public final static String EVENT_SYNC_SKU = "event_sync_sku";
        public final static String EVENT_SYNC_EPC = "event_sync_epc";
        public final static String EVENT_ACTIVITY_SUCCESS = "event_activity_success";
        public final static String EVENT_SYNC_SKU_SUCCESS = "event_sync_sku_success";
        public final static String EVENT_RFID_ERROR = "event_rfid_error";
        public final static String EVENT_RFID_POWER = "event_rfid_power";
        public final static String EVENT_RESTART_APP = "event_restart_app";
        public final static String EVENT_UPLOAD_LOG = "event_upload_log";
    }

    public final static class Extra {
        public static final String EXTRA_READER_TO_START = "extra_reader_test";
        public static final String EXTRA_OUT_DOOR_STATUS = "extra_out_door_status";
        public static final String EXTRA_PAY_DOOR_STATUS = "extra_pay_door_status";
        public static final String EXTRA_DOWN_URL = "extra_down_url";
        public static final String EXTRA_DOWN_TYPE = "extra_down_type";
        public static final String EXTRA_HARDWARE = "extra_hardware";
        public static final String EXTRA_RFID_MODEL = "extra_rfid_model";
        public static final String EXTRA_RFID_SERIAL_PORT = "extra_rfid_serial_port";
        public static final String EXTRA_RFID_SERIAL_BAUD = "extra_rfid_serial_baud";
        public static final String EXTRA_RFID_SERIAL_POWER1 = "extra_rfid_serial_power1";
        public static final String EXTRA_RFID_SERIAL_POWER2 = "extra_rfid_serial_power2";
        public static final String EXTRA_RFID_SERIAL_POWER3 = "extra_rfid_serial_power3";
        public static final String EXTRA_RFID_SERIAL_POWER4 = "extra_rfid_serial_power4";
        public static final String EXTRA_INFRARED_PID = "extra_infrared_pid";
        public static final String EXTRA_INFRARED_VID = "extra_infrared_vid";
        public static final String EXTRA_INFRARED_SINGLE = "extra_infrared_single";
    }
}
