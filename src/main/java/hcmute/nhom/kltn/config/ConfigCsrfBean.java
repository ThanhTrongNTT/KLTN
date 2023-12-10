package hcmute.nhom.kltn.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Class ConfigCsrfBean.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Component
public class ConfigCsrfBean {
    @Autowired
    private Environment env;

    public List<String> getAllowedOrigins() {
        return Arrays.asList(env.getProperty("web.cors.allowed-origins").split(","));
    }

    public List<String> getAllowedMethod() {
        return Arrays.asList(env.getProperty("web.cors.allowed-methods").split(","));
    }

    public List<String> getAllowedHeader() {
        return Arrays.asList("*", "x-csrf-token");
    }
}
