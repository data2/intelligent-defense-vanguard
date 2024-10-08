package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class KibanaService extends ParentService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final List<Integer> PORT = List.of(5601);

    // 示例用户名和密码列表
    private static final List<String> USERNAMES = Arrays.asList("root", "anonymous", "ftpuser", "admin");
    private static final List<String> PASSWORDS = Arrays.asList("admin", "password", "root", "123456",
            "12345678", "123456789", "12345", "ftpuser", "ftpuser123", "anonymous", "");

    @Override
    public boolean exists() {
        List<Integer> res = super.isReachable(configuration.getIp(), PORT);
        System.out.println(res);
        return !res.isEmpty();
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

}
