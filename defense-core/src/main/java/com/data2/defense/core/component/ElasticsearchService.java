package com.data2.defense.core.component;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.ParentService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticsearchService extends ParentService implements BaseService {

    @Autowired
    private IpConfiguration configuration;
    private static final List<Integer> PORT = List.of(9200);

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
            try {
                // 创建低级客户端
                RestClient restClient = RestClient.builder(
                        new HttpHost(configuration.getIp(), 9200)
                ).build();

                // Create the transport with a Jackson mapper
                ElasticsearchTransport transport = new RestClientTransport(
                        restClient, new JacksonJsonpMapper());

                // And create the API client
                ElasticsearchClient esClient = new ElasticsearchClient(transport);


                GetIndexResponse response = esClient.indices().get(request -> request.index("*"));

                System.out.println(response.result().keySet());

                // Close the transport, freeing the underlying thread
                transport.close();
                restClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean attack() {
        try {
            // 创建低级客户端
            RestClient restClient = RestClient.builder(
                    new HttpHost(configuration.getIp(), 9200)
            ).build();
            // 使用Jackson映射器创建传输层
            ElasticsearchTransport transport = new RestClientTransport(
                    restClient, new JacksonJsonpMapper()
            );
            // 创建API客户端
            ElasticsearchClient client = new ElasticsearchClient(transport);
            // 创建索引
            CreateIndexResponse createIndexResponse = client.indices().create(c -> c.index("user_test"));
            // 响应状态
            Boolean acknowledged = createIndexResponse.acknowledged();
            System.out.println("索引操作 = " + acknowledged);

            // 关闭ES客户端
            transport.close();
            restClient.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}