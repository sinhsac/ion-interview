package codes.opencms.ioninterview.components.calllogs.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/callLogs/")
public class CallAnalys {

    @PostMapping("/analyse")
    @SuppressWarnings("unchecked")
    public Map<String, Object> analyse(@RequestBody List<String> body) {
        if (CollectionUtils.isEmpty(body)) {
            return new LinkedHashMap<>();
        }
        Map<String, Object> response = new LinkedHashMap<>();
        List<List> lists = body.stream()
                .filter(line -> StringUtils.isNotBlank(line))
                //.distinct()
                .map(line -> Arrays.stream(line.split("/"))
                                    .collect(Collectors.toList()))
                .collect(Collectors.toList());
        lists.forEach(items -> processForItems(response, items, 0));
        return response;
    }

    @SuppressWarnings("unchecked")
    private void processForItems(Map<String, Object> response, List<String> items, int indx) {
        if (CollectionUtils.isEmpty(items) || indx >= items.size()) {
            return;
        }

        String curVal = items.get(indx);
        if (StringUtils.isBlank(curVal)) {
            return;
        }

        if (!response.containsKey(curVal)) {
            addNewItem(response, items, indx);
            return;
        }

        Object obj = response.get(curVal);
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) response.get(curVal);
            Integer curCount = (Integer) map.get("_count") + 1;
            map.put("_count", curCount);
            processForItems(map, items, indx + 1);
            return;
        }
        Integer count = (Integer) obj + 1;
        response.put(curVal, count);
    }

    private void addNewItem(Map<String, Object> response, List<String> items, int indx) {
        if (indx >= items.size()) {
            return;
        }

        Map<String, Object> map = new LinkedHashMap<>();
        if (indx < (items.size() - 1)) {
            map.put("_count", 1);
            response.put(items.get(indx), map);
        } else {
            response.put(items.get(indx), 1);
        }
        addNewItem(map, items, indx + 1);
    }
}
