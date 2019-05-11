package com.biubiu.user.thrift;

import com.biubiu.thrift.message.MessageService;
import com.biubiu.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yule.zhang
 * @date 2019/5/9 21:10
 * @email zhangyule1993@sina.com
 * @description 服务提供者
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.service.host}")
    private String serverHost;

    @Value("${thrift.user.service.port}")
    private int serverPort;


    @Value("${thrift.message.service.host}")
    private String messageServiceHost;

    @Value("${thrift.message.service.port}")
    private int messageServicePort;

    private enum ServiceType {
        User,
        Message
    }

    public UserService.Client getUserService() {
        return getService(serverHost, serverPort, ServiceType.User);
    }

    public MessageService.Client getMessageService() {
        return getService(messageServiceHost, messageServicePort, ServiceType.User);
    }

    public <T> T getService(String serviceHost, int servicePort, ServiceType serviceType) {
        TSocket socket = new TSocket(serviceHost, servicePort, 3000);
        TTransport transport = new TFramedTransport(socket);

        try {
            transport.open();
        }catch(TTransportException e) {
            e.printStackTrace();
            return null;
        }

        TProtocol protocol = new TBinaryProtocol(transport);

        TServiceClient client = null;
        switch (serviceType) {
            case User:
                client = new UserService.Client(protocol);
                break;
            case Message:
                client = new UserService.Client(protocol);
                break;
        }
        return (T) client;
    }
}
