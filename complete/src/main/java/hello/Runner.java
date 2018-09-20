package hello;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import hello.messagebus.base.RabbitConfig;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Receiver receiver;


    @Override
    public void run(String... args) throws Exception {
    	
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitConfig.topicExchangeName, RabbitConfig.topicExchangeName, "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
