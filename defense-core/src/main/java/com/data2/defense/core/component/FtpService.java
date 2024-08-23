package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.dto.Pair;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.IpPortCheckerService;
import com.data2.defense.core.service.ParentService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class FtpService extends ParentService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final List<Integer> PORT = List.of(21);

    // 示例用户名和密码列表
    private static final List<String> USERNAMES = Arrays.asList("root", "anonymous", "ftpuser", "ftp", "admin");
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
        List<Pair> collection = super.getNamePwd(USERNAMES, PASSWORDS);
        FTPClient ftpClient = new FTPClient();
        for (Integer port : PORT) {
            try {
                ftpClient.connect(configuration.getIp(), port);
                int replyCode = ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(replyCode)) {
                    System.out.println("FTP server refused connection.");
                    ftpClient.disconnect();
                    return false;
                }

                for (Pair pair : collection) {
                    if (login(ftpClient, pair.getName(), pair.getPwd())) {
                        System.out.println("Success: " + pair.getName() + ":" + pair.getPwd());
                        // 登录成功后，可以选择执行一些操作然后退出，或者立即退出
                        ftpClient.logout();
                        ftpClient.disconnect();
                        return true;
                    }

                }

                System.out.println("No valid credentials found.");
                ftpClient.disconnect();

            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                if (ftpClient.isConnected()) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException ioEx) {
                        // do nothing
                    }
                }
            }

        }

        return false;

    }

    private static boolean login(FTPClient ftpClient, String username, String password) throws IOException {
        boolean success = ftpClient.login(username, password);
        if (!success) {
            ftpClient.logout(); // 尝试登录失败时登出，避免占用资源
        }
        return success;
    }
}
