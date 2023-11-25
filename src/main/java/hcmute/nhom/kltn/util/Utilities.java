package hcmute.nhom.kltn.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.common.payload.PagingResponseCustom;

/**
 * Class Utilities.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public final class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

    private Utilities() {
    }

    /**
     * Parse String.
     *
     * @param value Object
     * @return String
     */
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

    /**
     * Parse Integer.
     *
     * @param value Object
     * @return Integer
     */
    public static Object formatOutputListSize(Object value) {
        if (value instanceof List) {
            int size = ((List<Object>) value).size();
            value = size + (size > 1 ? " records" : " record");
        }

        return value;
    }

    /**
     * Parse Integer.
     *
     * @param value Object
     * @return Integer
     */
    public static Object formatOutputMap(Object value) {
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> mapValue = (Map<Object, Object>) value;
        mapValue.entrySet().stream().forEach(e -> {
            Object valTemp = e.getValue();
            if (valTemp instanceof List) {
                valTemp = formatOutputListSize(valTemp);
            }
            map.put(e.getKey(), valTemp);
        });

        return map;
    }

    /**
     * Parse Integer.
     *
     * @param pageNo   Page number
     * @param pageSize Page size
     * @param sortBy   Sort by
     * @param sortDir  Sort direction
     * @return PageRequest
     */
    public static PageRequest getPageRequest(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(pageNo, pageSize, sort);
    }

    /**
     * Get IP Address.
     * @param request HttpServletRequest
     * @return
     * @throws UnknownHostException
     */
    public static String getIpAddress(HttpServletRequest request) throws UnknownHostException {
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr.equals("0:0:0:0:0:0:0:1")) {
            InetAddress localip = InetAddress.getLocalHost();
            remoteAddr = localip.getHostAddress();
        }

        logger.info("After process getRemoteAddr: {}", remoteAddr);
        return remoteAddr;
    }

    /**
     * Get IP Address By Header.
     * @param request HttpServletRequest
     * @return
     * @throws UnknownHostException
     */
    public static String getIpAdressByHeader(HttpServletRequest request) throws UnknownHostException {
        logger.info("getIpAdressByHeader - START");
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip)) {
            logger.info("IP Access from X-Forwarded-For: " + ip);
            logger.info("getIpAdressByHeader - END");
            return ip;
        } else {
            logger.info("Before process getRemoteAddr: {}", request.getRemoteAddr());
            ip = getIpAddress(request);
            logger.info("After process getRemoteAddr: {}", ip);
            logger.info("getIpAdressByHeader - END");
            return ip;
        }
    }

    /**
     * Create Paging Response.
     * @param page Page
     * @param <T> T
     * @return PagingResponseCustom<T>
     */
    public static <T> PagingResponseCustom<T> createPagingResponse(Page<T> page) {
        PagingResponseCustom<T> pagingDataCustom = new PagingResponseCustom<>();
        pagingDataCustom.setContent(page.getContent());
        pagingDataCustom.setFirst(page.isFirst());
        pagingDataCustom.setLast(page.isLast());
        pagingDataCustom.setTotalPages(page.getTotalPages());
        pagingDataCustom.setTotalElements(page.getTotalElements());
        pagingDataCustom.setSize(page.getSize());
        pagingDataCustom.setNumberOfElements(page.getNumberOfElements());
        pagingDataCustom.setEmpty(page.isEmpty());
        return pagingDataCustom;
    }

    /**
     * Create List Response.
     * @param list List
     * @param <T> T
     * @return ListResponse<T>
     */
    public static <T> ListResponse<T> createListResponse(List<T> list) {
        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.setContent(list);
        listResponse.setTotalElements(list.size());
        listResponse.setEmpty(list.isEmpty());
        return listResponse;
    }
}
