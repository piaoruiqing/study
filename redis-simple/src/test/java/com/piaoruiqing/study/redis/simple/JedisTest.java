package com.piaoruiqing.study.redis.simple;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piaoruiqing.study.redis.simple.util.JsonUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisTest Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 9, 2019</pre>
 */
public class JedisTest {

    /** jedis pool */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final JedisPool POOL =
        new JedisPool(new JedisPoolConfig(), "test-redis-server", 6379);

    /**
     * test Binary-safe strings with Jedis
     */
    @Test
    public void testString() {

        try (Jedis jedis = POOL.getResource()){
            {   // SET mykey myvalue
                String result = jedis.set("mykey", "myvalue");
                LOGGER.info("cmd: SET mykey myvalue, result: {}", result);
            }
            {   // GET mykey
                String result = jedis.get("mykey");
                LOGGER.info("cmd: GET mykey, result: {}", result);
            }
            {   // KEYS my*
                Set<String> keys = jedis.keys("my*");
                LOGGER.info("cmd: KEYS my*, result: {}", JsonUtils.writeValueAsString(keys, true));
            }
            {   // EXISTS mykey
                Boolean result = jedis.exists("mykey");
                LOGGER.info("cmd: EXISTS mykey, result: {}", result);
            }
            {   // DEL mykey
                Long result = jedis.del("mykey");
                LOGGER.info("cmd: DEL mykey, result: {}", result);
            }
            {   // GET mykey
                String result = jedis.get("mykey");
                LOGGER.info("cmd: GET mykey, result: {}", result);
            }
        }
    }
} 
