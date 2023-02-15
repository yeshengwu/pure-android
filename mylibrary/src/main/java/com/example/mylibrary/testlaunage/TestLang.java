package com.example.mylibrary.testlaunage;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class TestLang {

    public static void main(String[] args) {
        testBoolWithMethod(false);

        //  unchecked exception: RuntimeException 发生逻辑问题后开发者自己责任选择处理或者不处理， 编译器不强制
        testThrow();

        // checked exception: Java编译器强制要求处理
        try {
            testThrow3();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            testThrow4();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            testAndroidSocket();
        } catch (IOException exception) {
            System.out.println("adb forward test exception=" + exception);
        }

    }

    /**
     * https://www.jianshu.com/p/63c4d5c31909 PC与手机 --有图
     * https://blog.csdn.net/zhouqilong970/article/details/78960499 端口映射原理
     * evan： 一句话总结： pc端一个 socket 端口 通过 adb(adb.exe 包含 adb client 和 adb server) forward命令
     * 能够使得 pc socket 能和 设备端 端口 serversocket 进行通信。
     * 流程： pc 端socket 连接到 adb server，adb server 转发到 adbd. adbd转发到 设备端 serversocket.
     * <p>
     * adb forward test.
     * 1,pc端
     *
     * @throws IOException
     */
    public static void testAndroidSocket() throws IOException {
        //转发，
        Runtime.getRuntime().exec("adb forward tcp:8888 tcp:9999");
// 可以再执行adb forward --list解析一下结果判断是否转发成功
        Process process = Runtime.getRuntime().exec("adb forward --list");
        DataInputStream dis = new DataInputStream(process.getInputStream());
        byte[] buf = new byte[8];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        while ((len = dis.read(buf)) != -1) {
            String str = new String(buf, 0, len);
            sb.append(str);
        }
        String adbList = sb.toString().toString();
        System.out.println("adb forward list=" + adbList);
        String[] forwardArr = adbList.split("\n");
        boolean mForwardSuccess = false;
        for (String forward : forwardArr) {
            System.out.println("forward=" + forward);
            if (forward.contains("8888") && forward.contains("9999")) {
                mForwardSuccess = true;
            }
        }
        if (!mForwardSuccess) return;
//转发成功则建立socket
        Socket mSocket = new Socket("127.0.0.1", 8888);
        byte[] buffer = new byte[256];
        DataOutputStream dos = new DataOutputStream(mSocket.getOutputStream());
// 发送数据
        String msg = "I am from pc 127.0.0.1: 8080 msg";
        System.out.println("Client:" + msg);
        dos.write(msg.getBytes("UTF-8"));
        dos.flush();
    }

    public static void testBoolWithMethod(boolean result) {
        if (result && onTouchEvent()) {  // 如果 result 为 false. 那么 onTouchEvent() 这个方法不会执行。
            System.out.println("IN &&");
        } else {
            System.out.println("else &&");
        }
    }

    private static boolean onTouchEvent() {
        System.out.println("IN onTouchEvent");
        return false;
    }

    private static void testThrow() throws ArithmeticException {
    }

    private static void testThrow3() throws IOException {
    }

    private static void testThrow4() throws SQLException {
    }

    public void testOverload(String xxx) {

    }

//    public int testOverload(String xxx) {
//        return 1;
//    }
}
