package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.IpPortCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Component
public class PortScanService extends IpPortCheckerService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final int PORT = 49151;

    @Override
    public boolean exists() {
        ArrayList<Integer> openPorts = new ArrayList<>();
        int port = 1024;
        while(port<PORT){
            boolean open = super.isReachable(configuration.getIp(), port);
            if (open){
                openPorts.add(port);
                System.out.println("端口："+port+"已开放");
            }
            port += 1;
        }

        openPorts.forEach(integer -> System.out.println("端口："+integer+"已开放"));
        return false;
    }

    @Override
    public boolean weakPassword() {
        return false;
    }


}
