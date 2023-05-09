package ru.tinkoff.bot.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.bot.controller.dto.request.UpdatesRequest;

@Configuration
public class RabbitmqConfiguration {
    @Value("${rabbit.queue-name}")
    String queueName;
    @Value("${rabbit.exchange-name}")
    String exchangeName;
    @Value("${rabbit.routing-key}")
    private String routingKey;

    @Bean
    public Queue messageQueue() {
        return QueueBuilder
            .durable(queueName)
            .withArgument("x-dead-letter-exchange", exchangeName + ".dlx")
            .build();
    }

    @Bean
    public DirectExchange messageExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(exchangeName + ".dlx");
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
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest", UpdatesRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.scrapper.client.tgbot.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
