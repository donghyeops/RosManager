package com.github.ros_java.commander_pkg.main;

import android.util.Log;

import org.ros.node.NodeConfiguration;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;

/**
 * Created by ailab on 17. 10. 13.
 */

public class IP2 extends Thread {
    String host = null;
    URI masterUri;

    public IP2(URI masterUri) {
        this.masterUri = masterUri;
    }
    public String getHost() {
        return host;
    }

    public void run() {
        /*
        Socket socket;
        try {
            socket = new Socket("www.google.com", 80);
            host = socket.getLocalAddress().toString();
            System.out.println("nnononn");
        } catch (Exception e) {
            System.out.println("nnononn");
        }
        */
        try {
            java.net.Socket socket = new java.net.Socket(masterUri.getHost(), masterUri.getPort());
            java.net.InetAddress local_network_address = socket.getLocalAddress();
            socket.close();
            host = local_network_address.getHostAddress();
        } catch (IOException e) {
            // Socket problem
            Log.e("Camera Tutorial", "socket error trying to get networking information from the master uri");
        }
    }
}
