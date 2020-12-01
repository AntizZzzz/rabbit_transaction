package com.xjw.config.redis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Description:    redisConfig 1、将key和value全部序列化 防止redis的可以出现乱码情况
 *  							2、支持redis配置刷新
 * @Author:         xyj
 * @CreateDate:     2019/5/25 17:22
 * @Version:        1.0
*/
@Configuration
@RefreshScope
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout:6000}")
	private long timeout;

	@Value("${spring.redis.lettuce.pool.max-idle:5}")
	private int maxIdle;

	@Value("${spring.redis.lettuce.pool.min-idle:0}")
	private int minIdle;

	@Value("${spring.redis.lettuce.pool.max-active:10}")
	private int maxActive;

	@Value("${spring.redis.lettuce.pool.max-wait:2000}")
	private long maxWait;

	private LettuceConnectionFactory lettuceConnFactory() {

		// 单机版配置
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setDatabase(database);
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		if(StringUtils.isNotEmpty(password)) {
			redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		}

		// 集群版配置
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        String[] serverArray = clusterNodes.split(",");
//        Set<RedisNode> nodes = new HashSet<RedisNode>();
//        for (String ipPort : serverArray) {
//            String[] ipAndPort = ipPort.split(":");
//            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
//        }
//        redisClusterConfiguration.setPassword(RedisPassword.of(password));
//        redisClusterConfiguration.setClusterNodes(nodes);
//        redisClusterConfiguration.setMaxRedirects(maxRedirects);

		//连接池配置
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);

		LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				.commandTimeout(Duration.ofMillis(timeout))
				.poolConfig(genericObjectPoolConfig)
				.build();

		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
		lettuceConnectionFactory.afterPropertiesSet();
		return lettuceConnectionFactory;
	}

	/**
	 * 没有此属性就不会装配bean 如果是单个redis 将此注解注释掉
	 * @return
	 */
	@RefreshScope
	@Bean("redisTemplate")
	@ConditionalOnProperty(name = "spring.redis.cluster.nodes", matchIfMissing = false)
	public RedisTemplate<String, Object> getRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnFactory());

		RedisSerializer stringSerializer = new StringRedisSerializer();
		RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		// key的序列化类型
		redisTemplate.setKeySerializer(stringSerializer);
		// value的序列化类型
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(redisObjectSerializer);
		// value的序列化类型
		redisTemplate.setHashValueSerializer(redisObjectSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;

		/*RedisTemplate redisTemplate = new RedisTemplate();
		RedisSerializer stringSerializer = new StringRedisSerializer();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		return redisTemplate;*/
	}

	@Primary
	@Bean("redisTemplate")
	@ConditionalOnProperty(name = "spring.redis.host", matchIfMissing = true)
	@RefreshScope
	public RedisTemplate<String, Object> getSingleRedisTemplate( ) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnFactory());
		// key的序列化类型
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// value的序列化类型
		redisTemplate.setValueSerializer(RedisSerializer.json());
		redisTemplate.setHashValueSerializer(RedisSerializer.json());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
