package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.IpPortCheckerService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class FtpService extends IpPortCheckerService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final int FTP_PORT = 21;

    // 示例用户名和密码列表
    private static final List<String> USERNAMES = Arrays.asList("root", "anonymous", "ftpuser","admin");
    private static final List<String> PASSWORDS = Arrays.asList("admin", "password", "root","123456",
            "12345678", "123456789", "12345","ftpuser","ftpuser123","anonymous","");

    @Override
    public boolean exists() {
        return super.isReachable(configuration.getIp(), FTP_PORT);
    }

    @Override
    public boolean weakPassword() {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(configuration.getIp(), FTP_PORT);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("FTP server refused connection.");
                ftpClient.disconnect();
                return false;
            }

            for (String username : USERNAMES) {
                for (String password : PASSWORDS) {
                    if (login(ftpClient, username, password)) {
                        System.out.println("Success: " + username + ":" + password);
                        // 登录成功后，可以选择执行一些操作然后退出，或者立即退出
                        ftpClient.logout();
                        ftpClient.disconnect();
                        return true;
                    }
                }
            }

            System.out.println("No valid credentials found.");
            ftpClient.disconnect();

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioEx) {
                    // do nothing
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
