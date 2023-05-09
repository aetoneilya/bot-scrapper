package ru.tinkoff.bot.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.bot.controller.dto.request.UpdatesRequest;

@Service
@RequiredArgsConstructor
@RabbitListener(queues = "${rabbit.queue-name}")
public class QueueListener {

    private final Bot bot;

    @RabbitHandler
    public void receiver(UpdatesRequest request) {
        bot.updateRequest(request.tgChatIds(), request.url(), request.description());
    }
}
