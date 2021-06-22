package com.es.test.doc;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 高级查询功能
 *
 * 组合查询
 */
public class FuzzySearchDocs {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("my_user");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //1、模糊查询
            FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("name","ang");
            fuzzyQuery.fuzziness(Fuzziness.TWO); //偏差字段
            sourceBuilder.query(fuzzyQuery);

            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : searchResponse.getHits()) {
                System.out.println(JSON.toJSONString(hit.getSourceAsMap()));
            }
        }
    }
}
