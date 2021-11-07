# spring-boot-demo-mq-kafka

> 本 demo 主要演示了 Spring Boot 如何集成 kafka，实现消息的发送和接收。

## 环境准备

> 注意：本 demo 基于 Spring Boot 2.1.0.RELEASE 版本，因此 spring-kafka 的版本为 2.2.0.RELEASE，kafka-clients 的版本为2.0.0，所以 kafka 的版本选用为  kafka_2.11-2.1.0

创建一个名为 `test` 的Topic

```bash
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-mq-kafka</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-mq-kafka</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.xkcoding</groupId>
        <artifactId>spring-boot-demo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-mq-kafka</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      retries: 0
      batch-size: 16384
      buffer-memory: 33554432
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      group-id: spring-boot-demo
      # 手动提交
      enable-auto-commit: false
      auto-offset-reset: latest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      properties:
        session.timeout.ms: 60000
    listener:
      log-container-config: false
      concurrency: 5
      # 手动提交
      ack-mode: manual_immediate
```

## KafkaConfig.java

```java
/**
 * <p>
 * kafka配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-07 14:49
 */
@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
@EnableKafka
@AllArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(KafkaConsts.DEFAULT_PARTITION_NUM);
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean("ackContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> ackContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(KafkaConsts.DEFAULT_PARTITION_NUM);
        return factory;
    }

}
```

## MessageHandler.java

```java
/**
 * <p>
 * 消息处理器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-07 14:58
 */
@Component
@Slf4j
public class MessageHandler {

    @KafkaListener(topics = KafkaConsts.TOPIC_TEST, containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord record, Acknowledgment acknowledgment) {
        try {
            String message = (String) record.value();
            log.info("收到消息: {}", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }
}
```

## SpringBootDemoMqKafkaApplicationTests.java

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoMqKafkaApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 测试发送消息
     */
    @Test
    public void testSend() {
        kafkaTemplate.send(KafkaConsts.TOPIC_TEST, "hello,kafka...");
    }

}
```

## 参考

1. Spring Boot 版本和 Spring-Kafka 的版本对应关系：https://spring.io/projects/spring-kafka

   | Spring for Apache Kafka Version | Spring Integration for Apache Kafka Version | kafka-clients       |
   | ------------------------------- | ------------------------------------------- | ------------------- |
   | 2.2.x                           | 3.1.x                                       | 2.0.0, 2.1.0        |
   | 2.1.x                           | 3.0.x                                       | 1.0.x, 1.1.x, 2.0.0 |
   | 2.0.x                           | 3.0.x                                       | 0.11.0.x, 1.0.x     |
   | 1.3.x                           | 2.3.x                                       | 0.11.0.x, 1.0.x     |
   | 1.2.x                           | 2.2.x                                       | 0.10.2.x            |
   | 1.1.x                           | 2.1.x                                       | 0.10.0.x, 0.10.1.x  |
   | 1.0.x                           | 2.0.x                                       | 0.9.x.x             |
   | N/A*                            | 1.3.x                                       | 0.8.2.2             |

   > **IMPORTANT:** This matrix is client compatibility; in most cases (since 0.10.2.0) newer clients can communicate with older brokers. All users with brokers >= 0.10.x.x **(and all spring boot 1.5.x users)** are recommended to use spring-kafka version 1.3.x or higher due to its simpler threading model thanks to [KIP-62](https://cwiki.apache.org/confluence/display/KAFKA/KIP-62%3A+Allow+consumer+to+send+heartbeats+from+a+background+thread). For a complete discussion about client/broker compatibility, see the Kafka [Compatibility Matrix](https://cwiki.apache.org/confluence/display/KAFKA/Compatibility+Matrix)
   >
   > - Spring Integration Kafka versions prior to 2.0 pre-dated the Spring for Apache Kafka project and therefore were not based on it.
   >
   > These versions will be referenced transitively when using maven or gradle for version management. For the 1.1.x version, the 0.10.1.x is the default.
   >
   > 2.1.x uses the 1.1.x kafka-clients by default. When overriding the kafka-clients for 2.1.x see [the documentation appendix](https://docs.spring.io/spring-kafka/docs/2.1.x/reference/html/deps-for-11x.html).
   >
   > 2.2.x uses the 2.0.x kafka-clients by default. When overriding the kafka-clients for 2.2.x see [the documentation appendix](https://docs.spring.io/spring-kafka/docs/2.2.1.BUILD-SNAPSHOT/reference/html/deps-for-21x.html).
   >
   > - Spring Boot 1.5 users should use 1.3.x (Boot dependency management will use 1.1.x by default so this should be overridden).
   > - Spring Boot 2.0 users should use 2.0.x (Boot dependency management will use the correct version).
   > - Spring Boot 2.1 users should use 2.2.x (Boot dependency management will use the correct version).

2. Spring-Kafka 官方文档：https://docs.spring.io/spring-kafka/docs/2.2.0.RELEASE/reference/html/
