package com.biubiu.user.thrift;

import com.biubiu.thrift.user.UserService;
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

    @Value("${thrift.user.service.ip}")
    private String serverIp;

    @Value("${thrift.user.service.port}")
    private int serverPort;

    public UserService.Client getUserService() {
        TSocket socket = new TSocket(serverIp, serverPort, 3000);
        TTransport transport = new TFramedTransport(socket);

        try {
            transport.open();
        }catch(TTransportException e) {
            e.printStackTrace();
            return null;
        }

        TProtocol protocol = new TBinaryProtocol(transport);

        UserService.Client client = new UserService.Client(protocol);


        return client;
    }
}
