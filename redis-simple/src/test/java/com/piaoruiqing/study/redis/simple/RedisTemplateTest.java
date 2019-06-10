package com.piaoruiqing.study.redis.simple;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.piaoruiqing.study.redis.simple.util.JsonUtils;

/**
 * redis template test
 *
 * @author piaoruiqing
 * @description:
 * @date: 2019-06-10 23:05
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisSimpleApplication.class)
public class RedisTemplateTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * test Binary-safe strings with RedisTemplate
     */
    @Test
    public void testStringRedisTemplateSimple() {
        {   // SET mykey myvalue
            stringRedisTemplate.opsForValue().set("mykey", "myvalue");
        }
        {   // GET mykey
            String result = stringRedisTemplate.opsForValue().get("mykey");
            LOGGER.info("cmd: GET mykey, result: {}", result);
        }
        {   // KEYS my*
            Set<String> keys = stringRedisTemplate.keys("my*");
            LOGGER.info("cmd: KEYS my*, result: {}", JsonUtils.writeValueAsString(keys, true));
        }
        {   // EXISTS mykey
            Boolean result = stringRedisTemplate.hasKey("mykey");
            LOGGER.info("cmd: EXISTS mykey, result: {}", result);
        }
        {   // DEL mykey
            Boolean result = stringRedisTemplate.delete("mykey");
            LOGGER.info("cmd: DEL mykey, result: {}", result);
        }
        {   // GET mykey
            String result = stringRedisTemplate.opsForValue().get("mykey");
            LOGGER.info("cmd: GET mykey, result: {}", result);
        }
    }

}
