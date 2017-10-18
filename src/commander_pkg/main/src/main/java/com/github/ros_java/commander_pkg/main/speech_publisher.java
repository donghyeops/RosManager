package com.github.ros_java.commander_pkg.main;

import android.provider.Settings;
import android.widget.Toast;

import org.ros.node.AbstractNodeMain;
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

/**
 * Created by ailab on 17. 10. 13.
 */

public class speech_publisher extends AbstractNodeMain {
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/sttTalker123");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        final Publisher<std_msgs.String> publisher =
                connectedNode.newPublisher("talker123", std_msgs.String._TYPE);
        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;

            @Override
            protected void setup() {
                sequenceNumber = 0;
            }

            @Override
            protected void loop() throws InterruptedException {
                String sttmsg = service_controller.getPub_msg();
                if (sttmsg != null) {

                    std_msgs.String str = publisher.newMessage();
                    str.setData(sttmsg);

                    publisher.publish(str);
                    service_controller.resetPub_msg();
                    Thread.sleep(500);
                }
                //sequenceNumber++;
                //Thread.sleep(500);
            }
        });
    }
}
