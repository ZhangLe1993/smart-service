package com.biubiu.user.thrift;

import com.biubiu.thrift.user.UserService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author yule.zhang
 * @date 2019/5/9 20:16
 * @email zhangyule1993@sina.com
 * @description Thrift启动
 */

@Configuration
public class ThriftServer {

    @Value("${service.port}")
    private int servicePort;

    @Autowired
    private UserService.Iface userService;


    @PostConstruct
    public void startThriftServer() {

        TProcessor processor = new UserService.Processor<>(userService);

        TNonblockingServerSocket socket = null;
        try {
            socket = new TNonblockingServerSocket(servicePort);
        } catch (Exception e) {

            e.printStackTrace();
        }
        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);

        args.processor(processor);
        args.transportFactory(new TFramedTransport.Factory());

        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(args);

        server.serve();

    }
}
