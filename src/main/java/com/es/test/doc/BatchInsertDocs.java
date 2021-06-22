package com.es.test.doc;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;


/**
 * 批量CRUD文档
 */
public class BatchInsertDocs {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {
            BulkRequest deleteRequest = new BulkRequest();
            deleteRequest.add(new DeleteRequest().index("my_user").id("1005"));
            deleteRequest.add(new DeleteRequest().index("my_user").id("1004"));
            BulkResponse deleteResp = client.bulk(deleteRequest, RequestOptions.DEFAULT);
            System.out.println("批量删除结果：" + JSON.toJSONString(deleteResp));


            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.add(new IndexRequest().index("my_user").id("1003").source(JSON.toJSONString(new User("liu", 23, "234")), XContentType.JSON));
            bulkRequest.add(new IndexRequest().index("my_user").id("1004").source(JSON.toJSONString(new User("xian", 23, "159")), XContentType.JSON));
            bulkRequest.add(new IndexRequest().index("my_user").id("1005").source(JSON.toJSONString(new User("hua", 32, "159516")), XContentType.JSON));
            bulkRequest.add(new IndexRequest().index("my_user").id("1006").source(JSON.toJSONString(new User("ang", 32, "159516")), XContentType.JSON));
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

            System.out.println("批量增加doc结果：" + JSON.toJSONString(bulkResponse));

        }
    }
}
