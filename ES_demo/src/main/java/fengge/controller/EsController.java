package fengge.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import fengge.entity.B;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchResult;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Feng, Ge 2020-10-13 20:32
 */
@Slf4j
@RequestMapping("/es")
@RestController
public class EsController {

    @Autowired
//    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @ApiOperation("创建索引")
    @PostMapping("/createIndex")
    public void createIndex() throws IOException {
        // 1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("index-2");
        // 2.客户端执行请求
        CreateIndexResponse response = client
                .indices()
                .create(request, RequestOptions.DEFAULT);
        Console.log(response);
    }

    @ApiOperation("获取索引")
    @PostMapping("/getIndex")
    public boolean getIndex() throws IOException {
        // 1.创建索引请求
        GetIndexRequest request = new GetIndexRequest("index-2");
        // 2.客户端执行请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        System.out.println("[getIndexResponse:]" + getIndexResponse);
        return exists;
    }

    @ApiOperation("删除索引")
    @PostMapping("/delIndex")
    public AcknowledgedResponse delIndex() throws IOException {
        // 1.创建索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("index-2");
        // 2.客户端执行请求
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        return delete;
    }

    @ApiOperation("添加文档")
    @PostMapping("/addDoc")
    public IndexResponse addDoc() throws IOException {
        // 1.创建文档信息
        B b = new B("鲁智深", 18);
        // 2.创建请求
        IndexRequest request = new IndexRequest("index-2");
        request.id("1");
        request.timeout("1s");
        request.source(JSONUtil.toJsonStr(b), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        return indexResponse;
    }

    @ApiOperation("获取文档")
    @PostMapping("/getDoc")
    public String getDoc() throws IOException {
        // 1.创建请求
        GetRequest request = new GetRequest("index-2", "1");
        // 2.客户端执行请求
        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);
        return documentFields.getSourceAsString();
    }

    @ApiOperation("更新文档")
    @PostMapping("/updateDoc")
    public RestStatus updateDoc() throws IOException {
        B b = new B("鲁智深", 19);
        UpdateRequest request = new UpdateRequest("index-2", "1");
        request.doc(JSONUtil.toJsonStr(b), XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        return updateResponse.status();
    }

    @ApiOperation("删除文档")
    @PostMapping("/delDoc")
    public RestStatus delDoc() throws IOException {
        DeleteRequest request = new DeleteRequest("index-2", "1");
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        return deleteResponse.status();
    }

    @ApiOperation("批量添加文档")
    @PostMapping("/batchAddDoc")
    public String batchAddDoc() throws IOException {
        List<B> bList = new ArrayList<B>();
        bList.add(new B("林冲", 20));
        bList.add(new B("武松", 21));
        bList.add(new B("扈三娘", 18));
        bList.add(new B("张青", 22));
        bList.add(new B("孙二娘", 18));
        bList.add(new B("王英", 30));
        bList.add(new B("花荣", 20));

        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < bList.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("index-2").id("" + (i+1)).source(JSONUtil.toJsonStr(bList.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.buildFailureMessage();
    }

    @ApiOperation("按条件查询文档")
    @PostMapping("/searchDoc")
    public String searchDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("index-2");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("", 20);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout();
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        log.info(JSONUtil.toJsonStr(searchResponse.getHits()));
        for (SearchHit hit : searchResponse.getHits()){
            System.out.println(hit.getSourceAsMap());
            System.out.println("============================");
            System.out.println(hit.getSourceAsString());
        }
        return JSONUtil.toJsonStr(searchResponse.getHits());
    }

}
