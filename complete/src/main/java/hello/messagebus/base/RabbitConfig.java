package hello.messagebus.base;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

import hello.Application;

@Configuration
public class RabbitConfig {
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	public static final String topicExchangeName = "spring-boot-exchange";
	public static final String queueName = "spring-boot";

	@Bean // subscription
	RabbitConfig config() {
		 RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
		
		TopicExchange exchange = new TopicExchange(topicExchangeName);
		Queue queue = new Queue(queueName, false);
		
		rabbitAdmin.declareExchange(exchange);
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(topicExchangeName+ ".#"));
    	
        return this;
    }
}