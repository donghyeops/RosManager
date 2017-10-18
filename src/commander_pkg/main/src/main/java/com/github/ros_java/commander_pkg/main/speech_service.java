package com.github.ros_java.commander_pkg.main;

import android.app.Activity;

import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;
import path_finder_pkg.*;


public class speech_service extends AbstractNodeMain {


    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("srv_client");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        ServiceClient<Service_messageRequest, Service_messageResponse> serviceClient;

        try {
            serviceClient = connectedNode.newServiceClient("/service", Service_message._Type);
        } catch (ServiceNotFoundException e) {
            throw new RosRuntimeException(e);
        }

        Service_messageRequest request = serviceClient.newMessage();
        String msg = service_controller.getReq_msg();
        request.setReqmsg(msg);


        serviceClient.call(request, new listener() {
            @Override
            public void onSuccess(Service_messageResponse response) {
                connectedNode.getLog().info(response.getResultmsg());
                System.out.println(response.getResultmsg());
                //service_controller.setResult_msg(response.getResultmsg());
            }

            @Override
            public void onFailure(RemoteException e) {
                throw new RosRuntimeException(e);
            }
        });
    }
    public class listener implements ServiceResponseListener<Service_messageResponse>{
        public void onSuccess(Service_messageResponse response){

        };

        public void onFailure(RemoteException e){

        };
    }
}