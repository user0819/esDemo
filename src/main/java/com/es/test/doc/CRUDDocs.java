package com.es.test.doc;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class CRUDDocs {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        try(RestHighLevelClient client = new RestHighLevelClient(builder)){

            //删
            DeleteRequest deleteRequest = new DeleteRequest();
            deleteRequest.index("my_user").id("1003");
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(deleteResponse));

            //增
            User user = new User("xiang",18, "123456789");
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index("my_user").id("1003");
            indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(indexResponse));

            //改
            user.setAge(23);
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index("my_user").id("1003");
            updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(updateResponse));

            //查
            GetRequest getRequest = new GetRequest();
            getRequest.index("my_user").id("1003");
            GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(documentFields));
        }
    }
}
