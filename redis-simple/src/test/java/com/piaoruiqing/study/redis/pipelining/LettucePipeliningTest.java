package com.piaoruiqing.study.redis.pipelining;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piaoruiqing.study.redis.simple.util.JsonUtils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author piaoruiqing
 * @description:
 * @date: 2019-06-23 17:39
 * @since JDK 1.8
 */
public class LettucePipeliningTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** redis client */
    private static final RedisClient CLIENT
        = RedisClient.create("redis://@test-redis-server:6379/0");

    /**
     * test pipelining with Lettuce
     */
    @Test
    public void testPipelining() throws ExecutionException, InterruptedException {

        try (StatefulRedisConnection<String, String> connection = CLIENT.connect()) {

            RedisAsyncCommands<String, String> async = connection.async();
            async.setAutoFlushCommands(false);
            RedisFuture<String> future1 = async.set("mykey1", "myvalue1");
            RedisFuture<String> future2 = async.set("mykey2", "myvalue2");
            RedisFuture<String> future3 = async.set("mykey3", "myvalue3");

            async.flushCommands();

            LOGGER.info("cmd: SET mykey1 myvalue1, result: {}", future1.get());
            LOGGER.info("cmd: SET mykey2 myvalue2, result: {}", future1.get());
            LOGGER.info("cmd: SET mykey3 myvalue3, result: {}", future1.get());
        }
    }

}
