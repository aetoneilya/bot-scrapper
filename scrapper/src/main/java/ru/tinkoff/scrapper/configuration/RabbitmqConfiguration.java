package ru.tinkoff.scrapper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {
    @Value("${rabbit.queue-name}")
    String queueName;
    @Value("${rabbit.exchange-name}")
    String exchangeName;
    @Value("${rabbit.routing-key}")
    String routingKey;

    @Bean
    public Queue directQueue() {
        return new Queue(queueName);
    }

    @Bean
    public DirectExchange messageExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding bindings() {
        return BindingBuilder.bind(directQueue()).to(messageExchange()).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
