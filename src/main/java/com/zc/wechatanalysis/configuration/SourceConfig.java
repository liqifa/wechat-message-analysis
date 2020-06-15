package com.zc.wechatanalysis.configuration;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("wechat.message.datasource")
    public HikariDataSource messageDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("wechat.contact.datasource")
    public HikariDataSource contactDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate messageJdbcTemplate(@Qualifier("messageDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate contactJdbcTemplate(@Qualifier("contactDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                elasticsearchProperties.getClientUsername(), elasticsearchProperties.getClientPassword()));

        return new RestHighLevelClient(RestClient.builder(elasticsearchProperties.hosts())
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .setRequestConfigCallback(
                        config -> config.setConnectTimeout(elasticsearchProperties.getConnectTimeout())
                                .setConnectionRequestTimeout(elasticsearchProperties.getConnectionRequestTimeout())
                                .setSocketTimeout(elasticsearchProperties.getSocketTimeout())));
    }

    
    // private RestHighLevelClient restHighLevelClient;

    // public RestHighLevelClient getRestHighLevelClient() {
    //     if (restHighLevelClient == null) {

    //         CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    //         credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
    //                 elasticsearchProperties.getClientUsername(), elasticsearchProperties.getClientPassword()));

    //         return new RestHighLevelClient(RestClient.builder(elasticsearchProperties.hosts())
    //                 .setHttpClientConfigCallback(
    //                         httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
    //                 .setRequestConfigCallback(
    //                         config -> config.setConnectTimeout(elasticsearchProperties.getConnectTimeout())
    //                                 .setConnectionRequestTimeout(elasticsearchProperties.getConnectionRequestTimeout())
    //                                 .setSocketTimeout(elasticsearchProperties.getSocketTimeout())));
    //     }
    //     return restHighLevelClient;
    // }
}