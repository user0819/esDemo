package com.es.test.index;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * 索引增删改查
 */
public class EsCreateIndex {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        try (RestHighLevelClient client = new RestHighLevelClient(builder)) {

            GetIndexRequest getIndexRequest = new GetIndexRequest("my_user");
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            System.out.println("索引是否存在：" + exists);

            if (exists) {
                GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
                System.out.println("查询索引结果：" + JSON.toJSONString(getIndexResponse.getAliases()));
                System.out.println("查询索引结果：" + JSON.toJSONString(getIndexResponse.getDataStreams()));
                System.out.println("查询索引结果：" + JSON.toJSONString(getIndexResponse.getIndices()));
                System.out.println("查询索引结果：" + JSON.toJSONString(getIndexResponse.getDefaultSettings()));
                System.out.println("查询索引结果：" + JSON.toJSONString(getIndexResponse.getMappings()));
                System.out.println("查询索引结果：" + getIndexResponse.getSettings());
                System.out.println();

                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("my_user");
                AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                System.out.println("删除索引结果：" + JSON.toJSONString(delete));
            }

            CreateIndexRequest indexRequest = new CreateIndexRequest("my_user");
            CreateIndexResponse response = client.indices().create(indexRequest, RequestOptions.DEFAULT);
            System.out.println("创建索引结果：" + JSON.toJSONString(response));

        }
    }
}
