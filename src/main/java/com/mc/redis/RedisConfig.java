package com.mc.redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdl;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdl;
    @Value("${spring.redis.pool.max-wait}")
    private int minwait;

    @Primary
    @Bean(name="jedisConnectionFactory")
    RedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdl);
        poolConfig.setMinIdle(minIdl);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(database);
        return jedisConnectionFactory;
    }
    @Bean(name = "jedisConnectionFactoryDb1")
    RedisConnectionFactory jedisConnectionFactoryDb1(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdl);
        poolConfig.setMinIdle(minIdl);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(1);
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    RedisTemplate<String, Object> objRedisTemplate(Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer, @Qualifier("jedisConnectionFactory" )RedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        //连接
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name="redisTemplateDb1")
    RedisTemplate<String, Object> redisTemplateDb1(Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer,@Qualifier("jedisConnectionFactoryDb1" )RedisConnectionFactory jedisConnectionFactoryDb1) {
        RedisTemplate<String, Object> redisTemplateDb1 = new RedisTemplate<String, Object>();
        //连接
        redisTemplateDb1.setConnectionFactory(jedisConnectionFactoryDb1);
        redisTemplateDb1.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplateDb1.setKeySerializer(new StringRedisSerializer());
        redisTemplateDb1.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplateDb1;
    }

    /**
     * @功能描述 跟JacksonJsonRedisSerializer实际上是一样的
     * 它不仅可以将对象序列化，还可以将对象转换为json字符串并保存到redis中，但需要和jackson配合一起使用。用JacksonJsonRedisSerializer序列化的话，
     * 被序列化的对象不用实现Serializable接口。Jackson是利用反射和getter和setter方法进行读取的，如果不想因为getter和setter方法来影响存储，
     * 就要使用注解来定义被序列化的对象。
     * @param objectMapper
     * @return
     */
    @Bean
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }


    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     */
    @Bean(name="hashOperations")
    public HashOperations<String, String, Object> hashOperations(@Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }
    @Bean(name = "hashOperationsDb1")
    public HashOperations<String, String, Object> hashOperationsDb1(@Qualifier("redisTemplateDb1") RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }


    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     */

    @Bean(name="valueOperations")
    public ValueOperations<String, Object> valueOperations(@Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }
    @Bean(name="valueOperationsDB1")
    public ValueOperations<String, Object> valueOperationsDB1(@Qualifier("redisTemplateDb1")RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(@Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     */
    @Bean
    public SetOperations<String, Object> setOperations(@Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(@Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
