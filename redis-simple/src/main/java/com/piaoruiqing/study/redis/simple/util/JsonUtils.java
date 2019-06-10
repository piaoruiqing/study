package com.piaoruiqing.study.redis.simple.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * @author piaoruiqing
 * @description:
 * @date: 2019-06-09 17:20
 * @since JDK 1.8
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper MAPPER;
    private static final ObjectWriter PRETTY_WRITER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        PRETTY_WRITER = MAPPER.writerWithDefaultPrettyPrinter();
    }

    /**
     * serialize any Java value as a String
     *
     * @param value value
     *
     * @return
     *
     * @author piaoruiqing
     * @date: 2019/06/09 17:20
     */
    public static String writeValueAsString(Object value) {
        return writeValueAsString(value, false);
    }

    /**
     * serialize any Java value as a String
     *
     * @param value  value
     * @param pretty if writer with default pretty printer
     *
     * @return
     *
     * @author piaoruiqing
     * @date: 2019/06/09 17:20
     */
    public static String writeValueAsString(Object value, boolean pretty) {

        try {
            if (pretty) {
                return PRETTY_WRITER.writeValueAsString(value);
            } else {
                return MAPPER.writeValueAsString(value);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("write value {} as json string error", e);
        }

        return StringUtils.EMPTY;
    }
}
