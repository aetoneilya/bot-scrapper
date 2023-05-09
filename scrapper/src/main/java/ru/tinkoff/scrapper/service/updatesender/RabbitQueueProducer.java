package ru.tinkoff.scrapper.service.updatesender;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest;

@RequiredArgsConstructor
public class RabbitQueueProducer implements UpdateSender {
    private final RabbitTemplate rabbitTemplate;
    private final Binding binding;

    @Override
    public void send(UpdatesRequest update) {
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), update);
    }
}