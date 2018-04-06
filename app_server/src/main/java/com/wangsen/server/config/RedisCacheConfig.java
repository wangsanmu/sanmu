package com.wangsen.server.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangsen
 * @data 2018/3/28 22:12
 *
 * redis 缓存配置;
 * 注意：RedisCacheConfig这里也可以不用继承 ：CachingConfigurerSupport，也就是直接一个普通的Class就好了；
 * 这里主要我们之后要重新实现 key的生成策略，只要这里修改KeyGenerator，其它位置不用修改就生效了。
 * 普通使用普通类的方式的话，那么在使用@Cacheable的时候还需要指定KeyGenerator的名称;这样编码的时候比较麻烦。
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisCacheConfig extends CachingConfigurerSupport {


    private String host;

    private int port;

    private String timout;

    /**
     * 连接池
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大实例数
        jedisPoolConfig.setMaxTotal(200);
        //设置最大空闲数
        jedisPoolConfig.setMaxIdle(30);
        //设置最小空闲数
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出
        jedisPoolConfig.setMaxWaitMillis(3 * 10000);
        //在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的
        jedisPoolConfig.setTestOnBorrow(true);
        //在还会给pool时，是否提前进行validate操作
        jedisPoolConfig.setTestOnReturn(true);

        return jedisPoolConfig;
    }

    /**
     * 非集群模式
     * @return
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        return new RedisStandaloneConfiguration(host,port);
    }

    @Bean
    /**
     * 连接工厂单个reidis连接
     * @return
     */
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration());
        return jedisConnectionFactory;
    }

    /**
     * 集群配置
     */
//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration(){
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        redisClusterConfiguration.setClusterNodes(clusterNodes());
//        return redisClusterConfiguration;
//    }
//
//    @Bean
//    public Set<RedisNode> clusterNodes(){
//        Set<RedisNode> clusterNodes = new HashSet<RedisNode>();
//        RedisNode redisNode0 = new RedisNode("127.0.0.1",6379);
//        RedisNode redisNode1 = new RedisNode("127.0.0.1",6378);
//        RedisNode redisNode2 = new RedisNode("127.0.0.1",6379);
//        clusterNodes.add(redisNode0);
//        clusterNodes.add(redisNode1);
//        clusterNodes.add(redisNode2);
//        return clusterNodes;
//    }




    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory jedisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String,String> template(RedisConnectionFactory jedisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        return template;
    }



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTimout() {
        return timout;
    }

    public void setTimout(String timout) {
        this.timout = timout;
    }
}
