package ru.tinkoff.scrapper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {
    @Value("${rabbit.queue-name}")
    private String queueName;
    @Value("${rabbit.exchange-name}")
    private String exchangeName;
    @Value("${rabbit.routing-key}")
    private String routingKey;
    private final String deadQueueExchangeName = exchangeName + ".dlx";

    @Bean
    public Queue messageQueue() {
        return QueueBuilder
            .durable(queueName)
            .withArgument("x-dead-letter-exchange", deadQueueExchangeName)
            .build();
    }

    @Bean
    public DirectExchange messageExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(deadQueueExchangeName);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(queueName + ".dlq");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(messageQueue()).to(messageExchange()).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
