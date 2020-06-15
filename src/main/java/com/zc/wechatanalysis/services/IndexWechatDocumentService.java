package com.zc.wechatanalysis.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.wechatanalysis.domains.ESMessage;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;


@Service
public class IndexWechatDocumentService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    

    public void indexMessages(List<ESMessage> esMessages) {
        
        BulkRequest bulkRequest = new BulkRequest();
        Flux.fromIterable(esMessages).map(this::esMessageToIndexRequest).doOnEach(indexRequest -> {
            bulkRequest.add(indexRequest.get());
        }).subscribe();

        restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) {
                //TODO dosomething
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

    }

    private IndexRequest esMessageToIndexRequest(ESMessage esMessage) {
        String json;
        try {
            json = objectMapper.writeValueAsString(esMessage);
            final IndexRequest indexRequest = new IndexRequest("message").source(json, XContentType.JSON);
            return indexRequest;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}