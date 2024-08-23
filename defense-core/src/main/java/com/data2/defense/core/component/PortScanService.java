package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.IpPortCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PortScanService extends IpPortCheckerService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final int PORT = 49151;

    @Override
    public boolean exists() {
        int port = 1;
        while(port<PORT){
            boolean open = super.isReachable(configuration.getIp(), PORT);
            if (open){
                System.out.println("端口："+PORT+"已开放");
            }
            port += 1;
        }
        return false;
    }

    @Override
    public boolean weakPassword() {
        return false;
    }


}
