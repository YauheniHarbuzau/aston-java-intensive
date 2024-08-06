package com.aston.util;

import com.aston.constant.Constant;
import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static com.aston.constant.Constant.APPLICATION_YML_FILE_NAME;

/**
 * Утилитарный класс для чтения конфигурационного yml-файла
 *
 * @see Constant
 */
@UtilityClass
public class YamlUtil {

    private static final Map<String, Object> PROPERTIES;
    private static final Map<String, String> DATASOURCE;

    static {
        var yaml = new Yaml();
        var inputStream = YamlUtil.class.getClassLoader().getResourceAsStream(APPLICATION_YML_FILE_NAME);
        PROPERTIES = yaml.load(inputStream);
        DATASOURCE = (Map<String, String>) PROPERTIES.get("datasource");
    }

    public Map<String, String> getDatasource() {
        return DATASOURCE;
    }
}
