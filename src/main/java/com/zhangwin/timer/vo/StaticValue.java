package com.zhangwin.timer.vo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datatime 2019/3/15 15:15
 */
public class StaticValue {
    public static Map<String, Object> staticValueMap = new ConcurrentHashMap<>();
}
