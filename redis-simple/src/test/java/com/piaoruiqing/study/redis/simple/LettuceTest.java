package com.piaoruiqing.study.redis.simple;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piaoruiqing.study.redis.simple.util.JsonUtils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * LettuceTest Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 9, 2019</pre>
 */
public class LettuceTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** redis client */
    private static final RedisClient CLIENT = RedisClient.create("redis://@test-redis-server:6379/0");

    /**
     * test Binary-safe strings with Lettuce
     */
    @Test
    public void testString() {
        try (StatefulRedisConnection<String, String> connection = CLIENT.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            {   // SET mykey myvalue
                String result = commands.set("mykey", "myvalue");
                LOGGER.info("cmd: SET mykey myvalue, result: {}", result);
            }
            {   // GET mykey
                String result = commands.get("mykey");
                LOGGER.info("cmd: GET mykey, result: {}", result);
            }
            {   // KEYS my*
                List<String> keys = commands.keys("my*");
                LOGGER.info("cmd: KEYS my*, result: {}", JsonUtils.writeValueAsString(keys, true));
            }
            {   // EXISTS mykey
                Long result = commands.exists("mykey");
                LOGGER.info("cmd: EXISTS mykey, result: {}", result);
            }
            {   // DEL mykey
                Long result = commands.del("mykey");
                LOGGER.info("cmd: DEL mykey, result: {}", result);
            }
            {   // GET mykey
                String result = commands.get("mykey");
                LOGGER.info("cmd: GET mykey, result: {}", result);
            }
        }
    }

}
