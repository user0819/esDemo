package com.es.test.doc;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 高级查询功能
 */
public class SearchDocs {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("my_user");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            sourceBuilder.query(QueryBuilders.matchQuery("name", "liu"));
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse));
            System.out.println(JSON.toJSONString(searchResponse.getHits()));
            for (SearchHit hit : searchResponse.getHits()) {
                System.out.println(JSON.toJSONString(hit.getSourceAsMap()));
            }
        }
    }
}
