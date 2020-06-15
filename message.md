```
PUT message
{
  "mappings": {
    "properties": {
      "createTime": {
        "type": "date"
      },
      "dbContactRemark": {
        "type": "text",
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 256
          }
        }
      },
      "des": {
        "type": "byte"
      },
      "message": {
        "type": "text",
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 256
          },
          
          "ngram2": {
            "type": "text",
            "analyzer": "my_ngram2_analyzer",
            "fielddata": true
          },
          "ngram3": {
            "type": "text",
            "analyzer": "my_ngram3_analyzer",
            "fielddata": true
          }
        }
      },
      "status": {
        "type": "byte"
      },
      "type": {
        "type": "byte"
      },
      "friendType": {
        "type": "byte"
      },
      "userName": {
        "type": "keyword",
        "ignore_above": 256
      }
    }
  },
  "settings": {
    "analysis": {
      "analyzer": {
        "my_ngram2_analyzer": {
          "tokenizer": "my_ngram2"
        },
        "my_ngram3_analyzer": {
          "tokenizer": "my_ngram3"
        }
      },
      "tokenizer": {
        "my_ngram2": {
          "type": "ngram",
          "min_gram": 2,
          "max_gram": 2
        },
        "my_ngram3": {
          "type": "ngram",
          "min_gram": 3,
          "max_gram": 3
        }
      }
    }
  }
}
```

```
### 查看自己频率最高的一句话
GET message/_search?size=0
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "type": "1" ##1为自己发的消息，0为接收对方消息
          }
        }
      ],
      "must_not": [
        {
          "match": {
            "friendType": "2" ##2为群聊天
          }
        }
      ]
    }
  }, 
  "aggs": {
    "ofenword": {
      "terms": {
        "field": "message.keyword",
        "size": 10
      }
    }
  }
}
```