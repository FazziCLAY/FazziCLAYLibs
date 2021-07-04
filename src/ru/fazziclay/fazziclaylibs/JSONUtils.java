package ru.fazziclay.fazziclaylibs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static Object get(final JSONObject source, String key, Object defaultValue) {
        if (!source.has(key)) {
            source.put(key, defaultValue);
        }
        return source.get(key);
    }

    public static Object get(final JSONArray source, int index, Object defaultValue) {
        if (!(source.length() > index)) {
            source.put(index, defaultValue);
        }
        return source.get(index);
    }

    public static JSONObject readJSONObjectFile(String path) {
        String content = FileUtils.read(path);
        JSONObject jsonObject = new JSONObject();
        try {
            if (content != null) {
                jsonObject = new JSONObject(content);
            }
        } catch (JSONException exception) {
            FileUtils.write(path, jsonObject.toString());
        }
        return jsonObject;
    }

    public static JSONArray readJSONArrayFile(String path) {
        String content = FileUtils.read(path);
        JSONArray jsonArray = new JSONArray();
        try {
            if (content != null) {
                jsonArray = new JSONArray(content);
            }
        } catch (JSONException exception) {
            FileUtils.write(path, jsonArray.toString());
        }
        return jsonArray;
    }
}
