package hcmute.nhom.kltn.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Utilities.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

    private Utilities() {}

    public static String parseString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static Object formatOutputListSize(Object value) {
        if (value instanceof List) {
            int size = ((List<Object>) value).size();
            value = size + (size > 1 ? " records" : " record");
        }

        return value;
    }

    public static Object formatOutputMap(Object value) {
        Map<Object, Object> mTemp = new HashMap<>();
        Map<Object, Object> mValueTemp = (Map<Object, Object>) value;
        mValueTemp.entrySet().stream().forEach(e -> {
            Object valTemp = e.getValue();
            if (valTemp instanceof List) {
                valTemp = formatOutputListSize(valTemp);
            }
            mTemp.put(e.getKey(), valTemp);
        });

        return mTemp;
    }
}
