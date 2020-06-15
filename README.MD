comming soon...
大概思路是将iphone微信聊天记录导出后，读取 `WCDB_Contact` 与 `MM` 数据且合并，使用 Elasticsearch 异步 api 批量索引，然后利用 kibana 自己配制统计数据。
`message.md` 中存放的是索引后微信聊天记录的索引表 及 一些简单查询报表

现在是配制好 两个数据源后跑下测试用例就能将数据索引到 Elasticsearch，在这之前先使用`message.md` 的索引脚本创建索引
希望以后有时间做到更简单自动