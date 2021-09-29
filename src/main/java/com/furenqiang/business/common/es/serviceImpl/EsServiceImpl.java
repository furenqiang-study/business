package com.furenqiang.business.common.es.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.furenqiang.business.config.ElasticSearchConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("esService")
public class EsServiceImpl {

    @Autowired
    private RestHighLevelClient client;

    public void add(String body) throws IOException {

        IndexRequest indexRequest=new IndexRequest("user");
        indexRequest.id("1");
//        String jsonStr="{\"name\":\"Eric Fu\",\"age\":23}";
        indexRequest.source(body, XContentType.JSON);
        IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(index);
    }


    public List<String> bulkAddBrandEsModel(String body) throws IOException {
        List<JSONObject> objects = JSONArray.parseArray(body,JSONObject.class);
        BulkRequest request = new BulkRequest();
        for(JSONObject object:objects){
            request.add(new IndexRequest("brandesmodel").id(object.get("brandId").toString()+object.get("catelogId").toString())
                    .source(object,XContentType.JSON));
            log.info("存入数据为：{}",object);
        }
        BulkResponse bulk = client.bulk(request, ElasticSearchConfig.COMMON_OPTIONS);
        //TODO 如果批量插入有错误，可以在这里处理
        List<String> collect = Arrays.stream(bulk.getItems()).map((item) -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.error("存es的数据:{},错误原因：{}",collect,bulk.buildFailureMessage());
        return collect;
    }

    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchQuery("name","Eric"));
        //按照年龄聚合
        TermsAggregationBuilder size = AggregationBuilders.terms("aggAgg").field("age").size(3);
        searchSourceBuilder.aggregation(size);
        //按照年龄平均值聚合
        AvgAggregationBuilder field = AggregationBuilders.avg("balanceAvg").field("age");
        searchSourceBuilder.aggregation(field);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(search.toString());
        Aggregations aggregations = search.getAggregations();
        System.out.println(aggregations.toString());
        Aggregation hit = aggregations.get("hit");
        hit.getName();
    }
}
