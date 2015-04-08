package demoapp;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

@Component
public class ESClientFactory implements FactoryBean<Client> {
    @Value("${elasticsearch.clustername}")
    private String clusterName;

    private Client client;

    @PostConstruct
    private void initialize() {
        Node node = nodeBuilder().clusterName(clusterName).client(true).node();
        client = node.client();
    }

    @Override
    public Client getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
