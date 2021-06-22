package com.es.test.doc;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * 高级查询功能
 *
 * 组合查询
 */
public class RangeSearchDocs {
    public static void main(String[] args) throws IOException {
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        try (RestHighLevelClient client = new RestHighLevelClient(clientBuilder)) {

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("my_user");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //1、范围查询
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
            rangeQueryBuilder.gte(23);
            rangeQueryBuilder.lte(32);
            sourceBuilder.query(rangeQueryBuilder);

            //2、排序
            sourceBuilder.sort("age", SortOrder.ASC);

            //3、分页
            sourceBuilder.from(0);
            sourceBuilder.size(5);

            //4、筛选字段
            String[] includes = new String[]{"age","name"};
            String[] excludes = new String[]{};
            sourceBuilder.fetchSource(includes, excludes);

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
