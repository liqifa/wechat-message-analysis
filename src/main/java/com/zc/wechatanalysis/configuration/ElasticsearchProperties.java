
package com.zc.wechatanalysis.configuration;

import org.apache.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {

    private List<String> hosts;
    private int connectTimeout;
    private int connectionRequestTimeout;
    private int socketTimeout;
    private String clientUsername;
    private String clientPassword;

    public HttpHost[] hosts() {
        return hosts.stream().map(HttpHost::create).toArray(HttpHost[]::new);
    }

    public ElasticsearchProperties() {
    }

    public List<String> getHosts() {
        return this.hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getClientUsername() {
        return this.clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientPassword() {
        return this.clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    @Override
    public String toString() {
        return "{" + " hosts='" + getHosts() + "'" + ", connectTimeout='" + getConnectTimeout() + "'"
                + ", connectionRequestTimeout='" + getConnectionRequestTimeout() + "'" + ", socketTimeout='"
                + getSocketTimeout() + "'" + ", clientUsername='" + getClientUsername() + "'" + ", clientPassword='"
                + getClientPassword() + "'" + "}";
    }

}