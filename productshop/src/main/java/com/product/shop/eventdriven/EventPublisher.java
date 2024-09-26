package com.product.shop.eventdriven;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    public EventPublisher(
            final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.name}") final String exchangeName
    ) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

  /*  public String buildEventString(RoundR round, OutcomeR outcome){
        StringBuffer eventBuffer = new StringBuffer();

        eventBuffer.append("{")
                .append("\"playedCardId\":" + round.getPlayedCardId() + ",")
                .append("\"roundId\":" + round.getRoundId() + ",")
                .append("\"outcomeId\":" + outcome.getOutcomeId() + ",")
                .append("\"outcomeText\":" + outcome.getOutcomeText() + ",")
                .append("\"winningPlayerId\":" + outcome.getWinningPlayerId())
                .append("}");
        return eventBuffer.toString();
    }*/

 /*   public ProductEvent buildEvent(RoundR round, OutcomeR outcome){
        RoundEvent event = new RoundEvent(
                round.getPlayedCardId(),
                round.getRoundId(),
                outcome.getOutcomeId(),
                outcome.getOutcomeText(),
                outcome.getWinningPlayerId()
        );
        return event;
    }*/
}