package icu.whycode.nettyclient.util;

import com.alibaba.fastjson.JSON;
import icu.whycode.nettyclient.model.*;

/**
 * @author xps
 * @Description：传输数据转化包
 */
public class ObjectConvertUtil {
    public static String convertModel(SecureModel secureModel) {
        ReceiveMessage receive = new ReceiveMessage();
        receive.setData(JSON.toJSONString(secureModel));
        receive.setMsgType(Event.MESSAGE_TYPE_SECURE_MODEL);
        return JSON.toJSONString(receive);
    }

    public static String convertModel(ResponseFile response) {
        ReceiveMessage receive = new ReceiveMessage();
        receive.setData(JSON.toJSONString(response));
        receive.setMsgType(Event.MESSAGE_TYPE_RESPONSE_FILE);
        return JSON.toJSONString(receive);
    }

    public static String convertModel(RequestFile requst) {
        ReceiveMessage receive = new ReceiveMessage();
        receive.setData(JSON.toJSONString(requst));
        receive.setMsgType(Event.MESSAGE_TYPE_REQUEST_FILE);
        return JSON.toJSONString(receive);
    }

    public static Object convertModel(String receiveMsg) {
        ReceiveMessage receive = JSON.parseObject(receiveMsg, ReceiveMessage.class);
        Object obj = null;
        switch (receive.getMsgType()) {
            case Event.MESSAGE_TYPE_SECURE_MODEL:
                obj = JSON.parseObject(receive.getData(), SecureModel.class);
                break;
            case Event.MESSAGE_TYPE_REQUEST_FILE:
                obj = JSON.parseObject(receive.getData(), RequestFile.class);
                break;
            case Event.MESSAGE_TYPE_RESPONSE_FILE:
                obj = JSON.parseObject(receive.getData(), ResponseFile.class);
                break;
            default:
                break;
        }
        return obj;
    }

    public static String request(Object obj) {
        if (obj instanceof SecureModel) {
            SecureModel secureModel = (SecureModel) obj;
            return convertModel(secureModel);
        } else if (obj instanceof RequestFile) {
            RequestFile requestFile = (RequestFile) obj;
            return convertModel(requestFile);
        } else if (obj instanceof ResponseFile) {
            ResponseFile responseFile = (ResponseFile) obj;
            return convertModel(responseFile);
        } else {
            return null;
        }

    }
}
