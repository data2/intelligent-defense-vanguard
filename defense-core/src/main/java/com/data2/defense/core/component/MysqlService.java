package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.IpPortCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MysqlService extends IpPortCheckerService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final int PORT = 3306;

    // 示例用户名和密码列表
    private static final List<String> USERNAMES = Arrays.asList("root", "anonymous", "ftpuser","admin");
    private static final List<String> PASSWORDS = Arrays.asList("admin", "password", "root","123456",
            "12345678", "123456789", "12345","ftpuser","ftpuser123","anonymous","");

    @Override
    public boolean exists() {
        return super.isReachable(configuration.getIp(), PORT);
    }

    @Override
    public boolean weakPassword() {
        return false;
    }


}
