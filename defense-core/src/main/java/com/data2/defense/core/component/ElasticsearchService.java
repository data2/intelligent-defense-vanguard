package com.data2.defense.core.component;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.ParentService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class ElasticsearchService extends ParentService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final List<Integer> PORT = List.of(9200);

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
        for (Integer port : PORT) {
            // 创建客户端
            try (RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(configuration.getIp(), port, "http")))) {

                // 列出所有索引
                String[] indices = client.indices().getAlias(RequestOptions.DEFAULT).getAliases().keySet().toArray(new String[0]);
                for (String index : indices) {
                    System.out.println("Index: " + index);

                    // 读取索引数据（这里以查询所有文档为例）
                    SearchRequest searchRequest = new SearchRequest(index);
                    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
                    searchRequest.source(searchSourceBuilder);

                    SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                    // 处理搜索结果...
                    // 注意：这里只是简单地打印了响应的字符串表示，实际使用中你可能需要解析SearchResponse对象
                    System.out.println(searchResponse.toString());
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean attack() {
        return false;
    }
}