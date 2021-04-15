package learning.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: learning-kafka
 * @description: Application entry
 * @author: c0l0121
 * @modified:
 * @create: 2021-03-29 11:18
 */
@SpringBootApplication
@RestController
public class Application {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final String topic = "import";

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        this.template.send(topic, input);
    }

    @KafkaListener(id = "import-test1", topics = topic)
    public void listen(String message, Acknowledgment ack) {
        try {
            logger.info("receive message from [{}] : {}", topic, message);
            ack.acknowledge();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
