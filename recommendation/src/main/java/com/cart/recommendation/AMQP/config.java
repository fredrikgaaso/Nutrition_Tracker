package com.cart.recommendation.AMQP;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class config {
    @Bean
    public TopicExchange productTopicExhange(
            @Value("${amqp.exchange.name}") final String exchangeName
    ){
        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Binding productBinding(
            final Queue productQueue,
            final TopicExchange productTopicExhange,
            @Value("${amqp.routing.key}") final String routingKey
    ){
        return BindingBuilder
                .bind(productQueue)
                .to(productTopicExhange)
                .with(routingKey);
    }
    @Bean
    public Queue productQueue(
            @Value("${amqp.queue.name}") final String queueName
    ){
        return QueueBuilder
                .durable(queueName)
                .build();
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();

        final MappingJackson2MessageConverter jackson2MessageConverter = new MappingJackson2MessageConverter();
        jackson2MessageConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jackson2MessageConverter);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
