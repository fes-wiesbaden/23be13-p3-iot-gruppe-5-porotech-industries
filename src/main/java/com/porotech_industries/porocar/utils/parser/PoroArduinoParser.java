package com.porotech_industries.porocar.utils.parser;

import com.porotech_industries.porocar.utils.logger.PoroLogger;

import java.util.HashMap;
import java.util.Objects;

public class PoroArduinoParser {
    static HashMap<String, String> topicByKey = new HashMap<>();

    static {
        topicByKey.put("t",   "porocar/arduino/sensors/dht11/temperature");
        topicByKey.put("h",   "porocar/arduino/sensors/dht11/humidity");
        topicByKey.put("ufl", "porocar/arduino/sensors/hc-sr04/front-left");
        topicByKey.put("ufr", "porocar/arduino/sensors/hc-sr04/front-right");
        topicByKey.put("ub",  "porocar/arduino/sensors/hc-sr04/back");
        topicByKey.put("a",   "porocar/arduino/sensors/gy-271/azimuth");
        topicByKey.put("b",   "porocar/arduino/sensors/gy-271/bearing");
        topicByKey.put("d",   "porocar/arduino/sensors/gy-271/direction");
        topicByKey.put("p",   "porocar/arduino/sensors/gy-271/position");
        topicByKey.put("ga",  "porocar/arduino/sensors/mpu6050/acceleration");
        topicByKey.put("ac",  "porocar/arduino/sensors/mpu6050/acceleration/angle");
        topicByKey.put("an",  "porocar/arduino/sensors/mpu6050/gyro");
        topicByKey.put("aa",  "porocar/arduino/sensors/mpu6050/angle");
        topicByKey.put("fl",  "porocar/arduino/sensors/f249/fl");
        topicByKey.put("fr",  "porocar/arduino/sensors/f249/fr");
        topicByKey.put("bl",  "porocar/arduino/sensors/f249/bl");
        topicByKey.put("br",  "porocar/arduino/sensors/f249/br");
    }

    private static String convertKeyToTopic(String key) {
        for (String keyValue : topicByKey.keySet()) {
            if (Objects.equals(keyValue, key)) {
                return topicByKey.get(keyValue);
            }
        }
        return key;
    }

    public static HashMap<String, String> parseArduinoData(String data) {
        HashMap<String, String> parsedData = new HashMap<>();


        int start = data.indexOf("DATASTART");
        int end = data.indexOf("DATAEND");
        if (start == -1 || end == -1) {
            //PoroLogger.warn("PoroARduinoParser", "Failed to retrieve Start or end of data");

            return parsedData;
        }

        String payload = data.substring(start + "DATASTART".length(), end);
        String[] pairs = payload.split("\\|");

        for (String pair : pairs) {
            if (!pair.contains(":")) continue;

            String[] keyValue = pair.split(":", 2);
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            String topic = convertKeyToTopic(key);

            parsedData.put(topic, value);
        }

        return parsedData;
    }

}
