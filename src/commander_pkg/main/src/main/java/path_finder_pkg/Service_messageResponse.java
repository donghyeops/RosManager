package path_finder_pkg;

import org.ros.internal.message.Message;

/**
 * Created by ailab on 17. 10. 12.
 */

public interface Service_messageResponse extends Message {
    String _Type="path_finder_pkg/Service_message";
    String _DEFINITION="string resultmsg\n";

    void setResultmsg(String request);
    String getResultmsg();
}

