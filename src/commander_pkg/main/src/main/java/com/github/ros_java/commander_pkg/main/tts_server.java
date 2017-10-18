package com.github.ros_java.commander_pkg.main;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import org.ros.node.AbstractNodeMain;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.service.ServiceServer;

import path_finder_pkg.Service_message;
import path_finder_pkg.Service_messageRequest;
import path_finder_pkg.Service_messageResponse;
import speechtotextpoc.demo.HomeActivity;
import speechtotextpoc.translation_engine.TranslatorFactory;
import speechtotextpoc.translation_engine.utils.ConversionCallaback;

/**
 * Created by ailab on 17. 10. 13.
 */

public class tts_server extends AbstractNodeMain {
    public static Activity homeActivity; // static dmfh
    public TextView ttsResult;

    public tts_server(TextView ttsResult) {
        this.ttsResult=ttsResult;
    }
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/ttsServer");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        connectedNode.newServiceServer("tts", Service_message._Type,
                new ServiceResponseBuilder<Service_messageRequest, Service_messageResponse>() {
                    @Override
                    public void build(Service_messageRequest request, Service_messageResponse response) {
                        service_controller.setReq_msg(request.getReqmsg());
                        response.setResultmsg("ok");
                        String result_msg = null;
                        while (result_msg == null) {
                            result_msg = service_controller.getReq_msg();
                        }
                        final String rm = result_msg;

                        TranslatorFactory.getInstance().getTranslator(TranslatorFactory.TRANSLATOR_TYPE.TEXT_TO_SPEECH, (ConversionCallaback)homeActivity).initialize(result_msg, homeActivity);
                        service_controller.resetReq_msg();
                        HomeActivity.CURRENT_MODE = 0;

                        new Thread(new Runnable() {
                            public void run() {
                                homeActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ttsResult.setText(rm);
                                    }
                                });
                            }
                        }).start();
                    }
                });
    }
}
