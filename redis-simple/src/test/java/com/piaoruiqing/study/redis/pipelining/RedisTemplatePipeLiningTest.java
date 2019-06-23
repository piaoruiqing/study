package com.piaoruiqing.study.redis.pipelining;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.piaoruiqing.study.redis.simple.RedisSimpleApplication;

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
public class RedisTemplatePipeLiningTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * test pipelining with RedisTemplate
     */
    @Test
    public void testPipelining() {

        List<Object> objects = stringRedisTemplate.executePipelined((RedisCallback<Object>)connection -> {

            connection.set("mykey1".getBytes(), "myvalue1".getBytes());
            connection.set("mykey2".getBytes(), "myvalue2".getBytes());
            connection.set("mykey3".getBytes(), "myvalue3".getBytes());
            return null;
        });

        LOGGER.info("cmd: SET mykey myvalue, result: {}", objects);

    }


}
