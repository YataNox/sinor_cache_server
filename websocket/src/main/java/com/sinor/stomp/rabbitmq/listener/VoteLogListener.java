package com.sinor.stomp.rabbitmq.listener;

import com.sinor.stomp.vote.model.dto.response.VoteLogResponseDto;
import com.sinor.stomp.vote.model.entity.VoteLog;
import com.sinor.stomp.vote.service.VoteLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class VoteLogListener {
    private final RabbitTemplate template;
    private final VoteLogService voteLogService;

    @Autowired
    public VoteLogListener(RabbitTemplate template, VoteLogService voteLogService) {
        this.template = template;
        this.voteLogService = voteLogService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ""),
            exchange = @Exchange(value = "vote.server", type = "topic"),
            key = "*"
    ))
    public void onVote(
            @Header(value = "method") String method,
            @Header(value = "amqp_receivedRoutingKey") String routingKey,
            VoteLogResponseDto message
    ) {
        if ("post".equals(method)) {
            VoteLog entity = VoteLog.builder().voteItemId(message.voteItemId()).memberId(message.memberId()).build();
            VoteLogResponseDto logDidSaved = voteLogService.createObject(entity);
            template.convertAndSend("vote.client", routingKey, logDidSaved, m -> {
                m.getMessageProperties().setHeader("method", "post");
                return m;
            });
        } else if ("delete".equals(method)) {
            voteLogService.deleteObject(message.id());
            template.convertAndSend("vote.client", routingKey, message, m -> {
                m.getMessageProperties().setHeader("method", "delete");
                return m;
            });
        } else {
            log.info(method);
        }
    }
}
