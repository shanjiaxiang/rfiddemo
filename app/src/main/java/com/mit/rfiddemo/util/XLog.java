package com.mit.rfiddemo.util;

import android.util.Log;


/**
 * This class can replace android.util.Log.
 *
 * @author xiny
 */
public final class XLog {

    private static boolean isLogShow = false ;

    public static void disableLogging(){
        isLogShow = false;
    }

    public static void enableLogging(){
        isLogShow = true;
    }

    public static boolean isLogShow(){
        return isLogShow;
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private XLog() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param obj
     */
    public static void v(Object obj) {
        if (isLogShow) {
            String tag = getClassName();
            String msg = obj != null ? obj.toString() : "obj == null";
            Log.v(tag, msg);
        }
    }

    /**
     * Send a {DEBUG_LEVEL} log message.
     *
     * @param obj
     */
    public static void d(Object obj) {
        if (isLogShow) {
            String tag = getClassName();
            String msg = obj != null ? obj.toString() : "obj == null";
            Log.d(tag, msg);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param obj
     */
    public static void i(Object obj) {
        if (isLogShow) {
            String tag = getClassName();
            String msg = obj != null ? obj.toString() : "obj == null";
            Log.i(tag, msg);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param obj
     */
    public static void w(Object obj) {
        if (isLogShow) {
            String tag = getClassName();
            String msg = obj != null ? obj.toString() : "obj == null";
            Log.w(tag, msg);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param obj
     */
    public static void e(Object obj) {
        if (isLogShow) {
            String tag = getClassName();
            String msg = obj != null ? obj.toString() : "obj == null";
            Log.e(tag, msg);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param obj
     */
    public static void wtf(Object obj) {
        if (isLogShow) {
            String tag = getClassName();
            String msg = obj != null ? obj.toString() : "obj == null";
            Log.wtf(tag, msg);
        }
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (isLogShow) {
            Log.v(tag, msg);
        }
    }

    /**
     * Send a {DEBUG_LEVEL} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (isLogShow) {
            Log.d(tag, msg);
        }
    }

    public static void d(Object obj, String msg){
        if (isLogShow){
            Log.d(obj.getClass().getName(), msg);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (isLogShow) {
            Log.i(tag, msg);
        }
    }

    public static void i(Object obj, String msg) {
        if (isLogShow) {
            Log.i(obj.getClass().getName(), msg);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (isLogShow) {
            Log.w(tag, msg);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (isLogShow) {
            Log.e(tag, msg);
        }
    }

    public static void e(Object obj, String msg){
        if (isLogShow){
            Log.e(obj.getClass().getName(), msg);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void wtf(String tag, String msg) {
        if (isLogShow) {
            Log.wtf(tag, msg);
        }
    }

    /**
     * Send a {@link Log#VERBOSE} log message. And just print method name and
     * position in black.
     */
    public static void print() {
        if (isLogShow) {
            String tag = getClassName();
            String method = callMethodAndLine();
            Log.v(tag, method);
        }
    }

    /**
     * Send a {DEBUG_LEVEL} log message.
     *
     * @param object The object to print.
     */
    public static void print(Object object) {
        if (isLogShow) {
            String tag = getClassName();
            String method = callMethodAndLine();
            String content = "";
            if (object != null) {
                content = object.toString() + "                    ----    "
                        + method;
            } else {
                content = " ## " + "                ----    " + method;
            }
            Log.d(tag, content);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param object The object to print.
     */
    public static void printError(Object object) {
        if (isLogShow) {
            String tag = getClassName();
            String method = callMethodAndLine();
            String content = "";
            if (object != null) {
                content = object.toString() + "                    ----    "
                        + method;
            } else {
                content = " ## " + "                    ----    " + method;
            }
            Log.e(tag, content);
        }
    }

    /**
     * Print the array of stack trace elements of this method in black.
     *
     * @return
     */
    public static void printCallHierarchy() {
        if (isLogShow) {
            String tag = getClassName();
            String method = callMethodAndLine();
            String hierarchy = getCallHierarchy();
            Log.v(tag, method + hierarchy);
        }
    }

    /**
     * Print debug log in blue.
     *
     * @param object The object to print.
     */
    public static void printMyLog(Object object) {
        if (isLogShow) {
            String tag = "MYLOG";
            String method = callMethodAndLine();
            String content = "";
            if (object != null) {
                content = object.toString() + "                    ----    "
                        + method;
            } else {
                content = " ## " + "                ----    " + method;
            }
            Log.d(tag, content);
        }
    }

    private static String getCallHierarchy() {
        String result = "";
        StackTraceElement[] trace = (new Exception()).getStackTrace();
        for (int i = 2; i < trace.length; i++) {
            result += "\r\t" + trace[i].getClassName() + ""
                    + trace[i].getMethodName() + "():"
                    + trace[i].getLineNumber();
        }
        return result;
    }

    private static String getClassName() {
        String result = "";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        return result;
    }

    /**
     * Realization of double click jump events.
     *
     * @return
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName() + "";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
}
