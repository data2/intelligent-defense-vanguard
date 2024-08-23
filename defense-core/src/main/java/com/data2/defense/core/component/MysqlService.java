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
import org.springframework.validation.method.ParameterErrors;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

@Component
public class MysqlService extends ParentService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final List<Integer> PORT = List.of(3306);
    private static final String url = "jdbc:mysql://localhost:3306/information_schema";

    // 示例用户名和密码列表
    private static final List<String> USERNAMES = Arrays.asList("root", "anonymous", "ftpuser","admin");
    private static final List<String> PASSWORDS = Arrays.asList("admin", "password", "root","123456",
            "12345678", "123456789", "12345","ftpuser","ftpuser123","anonymous","");

    @Override
    public boolean exists() {
        List<Integer> res = super.isReachable(configuration.getIp(), PORT);
        System.out.println(res);
        return !res.isEmpty();
    }

    @Override
    public boolean weakPassword() {
        List<Pair> collection = super.getNamePwd(USERNAMES, PASSWORDS);
        for (Integer port : PORT) {
            for (Pair pair : collection){
                try {
                    // 加载MySQL JDBC驱动
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // 建立数据库连接
                    Connection conn = DriverManager.getConnection(url.replace("localhost", configuration.getIp()), pair.getName(), pair.getPwd());

                    // 创建Statement对象
                    Statement stmt = conn.createStatement();

                    // 执行SQL查询
                    // 这里我们查询information_schema数据库中的SCHEMATA表，以获取所有数据库的名称
                    String sql = "SELECT SCHEMA_NAME FROM SCHEMATA";
                    ResultSet rs = stmt.executeQuery(sql);

                    // 处理查询结果
                    while (rs.next()) {
                        // 获取数据库名称
                        String databaseName = rs.getString("SCHEMA_NAME");
                        System.out.println(databaseName);
                    }

                    // 关闭资源
                    rs.close();
                    stmt.close();
                    conn.close();

                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return false;

        }

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
