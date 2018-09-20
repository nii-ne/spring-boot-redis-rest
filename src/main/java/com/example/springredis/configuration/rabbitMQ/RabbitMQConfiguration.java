package com.example.springredis.configuration.rabbitMQ;

import com.example.springredis.messages.converters.ByteArrayToStringConverter;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;


@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    @Value("${rabbitmq.uri}")
    String rabbitmqUri;

    @Value("${rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${rabbitmq.password}")
    String rabbitmqPassword;

    @Value("${rabbitmq.virtual-host}")
    String rabbitmqVirtualHost;

    @Value("${rabbitmq.requested-heartbeat}")
    Integer rabbitmqHeartBeat;

    @Value("${rabbitmq.concurrent_consumer}")
    Integer rabbitmqConcurrentConsumer;

    @Value("${rabbitmq.max_concurrent_consumers}")
    Integer rabbitmqMaxConcurrent;

    @Value("${rabbitmq.pre_fetch_count}")
    Integer rabbitmqPrefetchCount;

    @Value("${rabbitmq.auto_startup}")
    boolean rabbitmqAutoStartup;

    @Bean
    public ConnectionFactory connectionFactory() throws URISyntaxException {
        CachingConnectionFactory factory = new CachingConnectionFactory(new URI(this.rabbitmqUri));
        factory.setUsername(this.rabbitmqUsername);
        factory.setPassword(this.rabbitmqPassword);
        factory.setVirtualHost(this.rabbitmqVirtualHost);
        factory.setRequestedHeartBeat(this.rabbitmqHeartBeat);
        return factory;
    }

    @Bean
    public ByteArrayToStringConverter byteArrayToStringConverter() {
        return new ByteArrayToStringConverter();
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ByteArrayToStringConverter
                                                                                       byteArrayToStringConverter) throws URISyntaxException {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(this.rabbitmqConcurrentConsumer);
        factory.setMaxConcurrentConsumers(this.rabbitmqMaxConcurrent);
        factory.setPrefetchCount(this.rabbitmqPrefetchCount);
        factory.setAutoStartup(rabbitmqAutoStartup);
        factory.setChannelTransacted(true);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}

