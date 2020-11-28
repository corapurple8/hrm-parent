package cn.itsource.hrm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    public static final String EXCHANGE_NAME_DIRECT="hrm-exchange-direct";
    public static final String QUEUE_NAME_SMS="hrm-queue-sms";
    public static final String QUEUE_NAME_EMAIL="hrm-queue-email";
    public static final String QUEUE_NAME_SYSTEM_MESSAGE="hrm-queue-system-message";


    @Bean(EXCHANGE_NAME_DIRECT)
    public Exchange getExchange(){
        return ExchangeBuilder.directExchange(EXCHANGE_NAME_DIRECT).durable(true).build();
    }

    @Bean(QUEUE_NAME_SYSTEM_MESSAGE)
    public Queue getQueueSystem(){
        return QueueBuilder.durable(QUEUE_NAME_SYSTEM_MESSAGE).build();
    }

    @Bean(QUEUE_NAME_SMS)
    public Queue getQueueSMS(){
        return QueueBuilder.durable(QUEUE_NAME_SMS).build();
    }

    @Bean(QUEUE_NAME_EMAIL)
    public Queue getQueueEmail(){
        return QueueBuilder.durable(QUEUE_NAME_EMAIL).build();
    }

    @Bean
    public Binding bindSystem(@Qualifier(QUEUE_NAME_SYSTEM_MESSAGE)Queue queue,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("system.message").noargs();
    }

    @Bean
    public Binding bindSMS(@Qualifier(QUEUE_NAME_SMS)Queue queue,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("sms").noargs();
    }

    @Bean
    public Binding bindEmail(@Qualifier(QUEUE_NAME_EMAIL)Queue queue,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("email").noargs();
    }

}
