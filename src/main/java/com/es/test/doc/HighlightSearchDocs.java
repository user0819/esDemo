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
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.IOException;

/**
 * 高级查询功能
 *
 * 组合查询
 */
public class HighlightSearchDocs {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("my_user");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //1、模糊查询
            sourceBuilder.query(QueryBuilders.matchQuery("name","wang"));

            HighlightBuilder highlighter = new HighlightBuilder();
            highlighter.field("name");
            highlighter.preTags("<font color = 'red'>");
            highlighter.postTags("</font>");
            sourceBuilder.highlighter(highlighter);

            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse));

            for (SearchHit hit : searchResponse.getHits()) {
                System.out.println(JSON.toJSONString(hit.getSourceAsMap()));
            }
        }
    }
}
