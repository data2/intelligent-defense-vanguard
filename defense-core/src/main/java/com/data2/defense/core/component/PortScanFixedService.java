package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.IpPortCheckerService;
import com.data2.defense.core.utils.ReadExcelXlsx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PortScanFixedService extends IpPortCheckerService implements BaseService, CommandLineRunner {

    @Autowired
    private IpConfiguration configuration;

    @Override
    public boolean exists() {
        Map<Integer, String> portMap = ReadExcelXlsx.config();
        Map<Integer, String> openPortMap = new HashMap<>();
        for (Integer port : portMap.keySet()) {
            if (super.isReachable(configuration.getIp(), port)) {
                openPortMap.put(port, portMap.get(port));
                log.info("端口：{}, 开放，描述：{} ", port, openPortMap.get(port));
            }
        }

        return false;
    }

    @Override
    public boolean weakPassword() {
        return false;
    }

    @Override
    public boolean unauthorizedAccess() {
        return false;
    }

    @Override
    public boolean attack() {
        return false;
    }

    @Override
    public void run(String... args) throws Exception {
        exists();
    }
}
