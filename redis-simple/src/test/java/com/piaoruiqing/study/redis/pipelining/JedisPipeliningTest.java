package com.piaoruiqing.study.redis.pipelining;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piaoruiqing.study.redis.simple.util.JsonUtils;

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
public class JedisPipeliningTest {


    /** jedis pool */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JedisPool POOL =
        new JedisPool(new JedisPoolConfig(), "test-redis-server", 6379);

    /**
     * test pipelining with Jedis
     */
    @Test
    public void testPipelining() {

        try (Jedis jedis = POOL.getResource()) {

            Pipeline pipelined = jedis.pipelined();
            Response<String> response1 = pipelined.set("mykey1", "myvalue1");
            Response<String> response2 = pipelined.set("mykey2", "myvalue2");
            Response<String> response3 = pipelined.set("mykey3", "myvalue3");

            pipelined.sync();

            LOGGER.info("cmd: SET mykey1 myvalue1, result: {}", response1.get());
            LOGGER.info("cmd: SET mykey2 myvalue2, result: {}", response2.get());
            LOGGER.info("cmd: SET mykey3 myvalue3, result: {}", response3.get());
        }
    }

    /**
     * pipeline vs direct
     */
    @Test
    public void compared() {

        try (Jedis jedis = POOL.getResource()) {
            jedis.set("mykey", "myvalue");
        }

        try (Jedis jedis = POOL.getResource()) {
            long start = System.nanoTime();
            Pipeline pipelined = jedis.pipelined();
            for (int index = 0; index < 500; index++) {
                pipelined.set("mykey" + index, "myvalue" + index);
            }
            pipelined.sync();
            long end = System.nanoTime();
            LOGGER.info("pipeline cost: {} ns", end - start);
        }

        try (Jedis jedis = POOL.getResource()) {
            long start = System.nanoTime();
            for (int index = 0; index < 500; index++) {
                jedis.set("mykey" + index, "myvalue" + index);
            }
            long end = System.nanoTime();
            LOGGER.info("direct cost: {} ns", end - start);
        }
    }


}
