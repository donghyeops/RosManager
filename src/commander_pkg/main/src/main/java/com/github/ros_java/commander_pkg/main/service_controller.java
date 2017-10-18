package com.github.ros_java.commander_pkg.main;

import android.util.Log;
import android.widget.TextView;

import java.net.Socket;
import java.net.URI;

import org.ros.address.InetAddressFactory;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

import speechtotextpoc.demo.HomeActivity;

/**
 * Created by ailab on 17. 10. 12.
 */

public class service_controller {
    URI masterUri;
    String host;
    NodeMainExecutor severExecutor, publisherExecutor;
    NodeMain serverNode, publisherNode;
    NodeConfiguration serverNode_config, publisherNode_config;


    static String publisher_msg = null;
    static String request_msg = null;
    public static void resetPub_msg() {
        service_controller.publisher_msg = null;
    }

    public static void resetReq_msg() {
        service_controller.request_msg = null;
    }

    public static void setReq_msg(String request_msg) {
        service_controller.request_msg = request_msg;
    }
    public static void setPub_msg(String publisher_msg) {
        service_controller.publisher_msg = publisher_msg;
    }
    public static String getReq_msg() {
        return request_msg;
    }
    public static String getPub_msg() {
        return publisher_msg;
    }


    public void init(TextView ttsResult) {
        masterUri = URI.create(HomeActivity.RosIP);
        Log.v("OWL", "HomeActivity.RosIP : " + HomeActivity.RosIP);
        //host = IP.getLocalIpAddress(IP.INET4ADDRESS);
        host="203.249.22.11";

        host = "localhost";
        IP2 ip2 = new IP2(masterUri);
        ip2.start();

        while (host == null || host.equals("localhost")) {
            host = ip2.getHost();
        }
        //host="203.249.22.11";
        // server setting
        serverNode = new tts_server(ttsResult);
        //NodeConfiguration Task_srvDemoNodeConfiguration = NodeConfiguration.newPrivate(masterUri);
        serverNode_config = NodeConfiguration.newPublic(host, masterUri);
        severExecutor = DefaultNodeMainExecutor.newDefault();

        // client setting
        publisherNode = new speech_publisher();
        publisherNode_config = NodeConfiguration.newPublic(host, masterUri);
        //publisherNode_config = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        //publisherNode_config.setMasterUri(masterUri);
        publisherExecutor = DefaultNodeMainExecutor.newDefault();
    }

    public void run_server() {
        severExecutor.execute(serverNode, serverNode_config);
        //Task_srvExecutor.shutdown();
        System.out.println("server running");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run_pulisher() {
        publisherExecutor.execute(publisherNode, publisherNode_config);
        //Task_srvExecutor.shutdown();
        System.out.println("pulisher running");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop_nodes() {
        severExecutor.shutdown();
        System.out.println("sever shutdowned");
        publisherExecutor.shutdown();
        System.out.println("pulisher shutdowned");
    }

}
