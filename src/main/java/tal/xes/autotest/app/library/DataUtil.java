package tal.xes.autotest.app.library;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import org.omg.DynamicAny.NameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataUtil {

    //String转Json格式
    public static JSONObject strtojson(String str) throws IOException {

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(str);
            return jsonObject;
        } catch (Exception e) {
//            System.out.println("Json解析发生异常");
            return null;
        }

    }

    public static JSONArray strtojsonArray(String str) throws IOException {
        JSONArray jsonArray;
        try {
            jsonArray = JSONArray.parseArray(str);
            return jsonArray;
        } catch (Exception e) {
//            System.out.println("JsonArray解析发生异常");
            return null;
        }

    }

    public static String getJsonType(String jsonfile) throws IOException {
        JSONObject jsonObject = strtojson(jsonfile);
        JSONArray jsonArray = strtojsonArray(jsonfile);
        if (jsonArray!=null) {
            return "JSONArray";
        }
        else if (jsonObject!=null) {
            return "JSONObject";
        }
        else {
            return "String";
        }
    }

    //传入JsonArray遍历需要找到的key值的对象
    private static Object get(JSONArray jsonfile, String key) throws IOException {
        int array_size = jsonfile.size();
        Object final_object = null;
        if (array_size>0) {
            for (int i=0; i<array_size; i++) {
                JSONObject jsonObject = jsonfile.getJSONObject(i);
                Object object = get(jsonObject, key);
                if (object!=null) {
                    final_object = object;
                }
            }
            return final_object;
        } else {
            System.out.println("Error: ----传入的JsonArray为空");
            return null;
        }
    }

    //返回Json文件的Value对象
    private static Object get(JSONObject jsonfile, String key) throws IOException {
        Object final_value = null;
        try {
            Set<Map.Entry<String, Object>> item_key = jsonfile.entrySet();
            for (Map.Entry<String, Object> entry : item_key) {//遍历Key list
                String n_key = entry.getKey();
                Object value = entry.getValue();
                if (n_key.equals(key)) {//满足条件则返回value
                    final_value = value.toString();
                    break;
                } else {
                    if (value instanceof JSONObject) {//校验Value值是否为Json对象
                        //带入get
                        Object s = get((JSONObject) value, key);
                        if (s != null) {
                            final_value = s;
                            break;
                        }
                    }
                    else if (value instanceof JSONArray) {//校验Value值是否为JsonArray
                        if (((JSONArray) value).size() > 0) {
                            for (int i = 0; i < ((JSONArray) value).size(); i++) {
                                Object vo = ((JSONArray) value).get(i);
                                if (vo instanceof JSONObject) {
                                    JSONObject jsonObject = ((JSONArray) value).getJSONObject(i);
                                    //带入get
                                    Object s = get(jsonObject, key);
                                    if (s != null) {
                                        final_value = s;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: ----Json解析失败,获取数据发生异常");
        }
        return final_value;
    }

    //遍历Json数据,通过Key获取Value
    public static String getValue(JSONObject jsonfile, String key) throws IOException {
        String final_value = null;
        Object value = get(jsonfile, key);
        if (value != null) {
            final_value = value.toString();
        }
        return final_value;
    }

    //遍历Json数据,通过Key获取Value(传入string类型,支持自动校验json类型)
    public static String getValue(String jsonfile, String key) throws IOException {
        String final_value = null;
        Object value = null;
        String jsonType = getJsonType(jsonfile);
        if (jsonType.equals("JSONArray")) {
            value = get(strtojsonArray(jsonfile), key);
        }
        else if (jsonType.equals("JSONObject")) {
            value = get(strtojson(jsonfile), key);
        }
        if (value != null) {
            final_value = value.toString();
        }
        return final_value;
    }

    //校验Json对象某个Key对应的Value是否为Number类型
    public static boolean isValueNumber(String jsonfile, String key) throws IOException {
        boolean final_value = false;
        String jsonType = getJsonType(jsonfile);
        if (jsonType.equals("JSONObject")) {
            JSONObject jsonObject = strtojson(jsonfile);
            final_value = isValueNumber(jsonObject, key);
        }
        else if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = strtojsonArray(jsonfile);
            final_value = isValueNumber(jsonArray, key);
        }
        return final_value;
    }

    public static boolean isValueNumber(JSONObject jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            if (value instanceof Number) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Number");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    public static boolean isValueNumber(JSONArray jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            if (value instanceof Number) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Number");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    //校验Json对象某个Key对应的Value是否为String类型
    public static boolean isValueString(String jsonfile, String key) throws IOException {
        boolean final_value = false;
        String jsonType = getJsonType(jsonfile);
        if (jsonType.equals("JSONObject")) {
            JSONObject jsonObject = strtojson(jsonfile);
            final_value = isValueString(jsonObject, key);
        }
        else if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = strtojsonArray(jsonfile);
            final_value = isValueString(jsonArray, key);
        }
        return final_value;
    }

    public static boolean isValueString(JSONObject jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            if (value instanceof String) {
                final_value = true;
            } else {
                System.out.println("数据类型不为String");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    public static boolean isValueString(JSONArray jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            if (value instanceof String) {
                final_value = true;
            } else {
                System.out.println("数据类型不为String");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    //校验Json对象某个Key对应的Value是否为Boolean类型
    public static boolean isValueBoolean(String jsonfile, String key) throws IOException {
        boolean final_value = false;
        String jsonType = getJsonType(jsonfile);
        if (jsonType.equals("JSONObject")) {
            JSONObject jsonObject = strtojson(jsonfile);
            final_value = isValueBoolean(jsonObject, key);
        }
        else if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = strtojsonArray(jsonfile);
            final_value = isValueBoolean(jsonArray, key);
        }
        return final_value;
    }

    public static boolean isValueBoolean(JSONObject jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            if (value instanceof Boolean) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Boolean");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    public static boolean isValueBoolean(JSONArray jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            if (value instanceof Boolean) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Boolean");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    //校验Json对象某个Key对应的Value是否为Array类型
    public static boolean isValueArray(String jsonfile, String key) throws IOException {
        boolean final_value = false;
        String jsonType = getJsonType(jsonfile);
        if (jsonType.equals("JSONObject")) {
            JSONObject jsonObject = strtojson(jsonfile);
            final_value = isValueArray(jsonObject, key);
        }
        else if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = strtojsonArray(jsonfile);
            final_value = isValueArray(jsonArray, key);
        }
        return final_value;
    }

    public static boolean isValueArray(JSONObject jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            System.out.println(value.getClass().toString());
            if (value instanceof JSONArray) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Array");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    public static boolean isValueArray(JSONArray jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            System.out.println(value.getClass().toString());
            if (value instanceof JSONArray) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Array");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    //校验Json对象某个Key对应的Value是否为Object类型
    public static boolean isValueObject(String jsonfile, String key) throws IOException {
        boolean final_value = false;
        String jsonType = getJsonType(jsonfile);
        if (jsonType.equals("JSONObject")) {
            JSONObject jsonObject = strtojson(jsonfile);
            final_value = isValueObject(jsonObject, key);
        }
        else if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = strtojsonArray(jsonfile);
            final_value = isValueObject(jsonArray, key);
        }
        return final_value;
    }

    public static boolean isValueObject(JSONObject jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            System.out.println(value.getClass().toString());
            if (value instanceof JSONObject) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Object");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    public static boolean isValueObject(JSONArray jsonfile, String key) throws IOException {
        boolean final_value = false;
        Object value = get(jsonfile, key);
        if (value != null) {
            System.out.println(value.getClass().toString());
            if (value instanceof JSONObject) {
                final_value = true;
            } else {
                System.out.println("数据类型不为Object");
            }
        } else {
            System.out.println("数据为空,无法校验数据");
        }
        return final_value;
    }

    //从map中获取数据重新定义为Headers中的Cookie
    public String mapToCookie(HashMap<String, String> map) throws IOException {
        try {
            StringBuffer stringBuffer = new StringBuffer();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    stringBuffer.append(key).append("=").append(value).append(";");
                }
            return stringBuffer.toString();

        } catch (Exception e) {
            System.out.println("Error : Map to Cookie");
            return null;
        }

    }


    //map转list
    public List<NameValuePair> mapToList(HashMap<String, String> map) throws IOException {
        try {
            List<NameValuePair> data = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                data.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            return data;
        } catch (Exception e) {
            System.out.println("Error : Map to List");
            return null;
        }
    }

    //处理form-data传参中带有对象或列表的参数
    private void paramsUtil(String key, String value, List<NameValuePair> data) throws IOException {
        String jsonType = getJsonType(value);
        String final_value = null;
        StringBuffer start_key = new StringBuffer();
        start_key.append(key);
        if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = strtojsonArray(value);
            int jsonArray_lenth = jsonArray.size();
            for (int i=0; i<jsonArray_lenth; i++) {
                StringBuffer final_key = new StringBuffer();
                final_key.append(start_key.toString());
                final_key.append("[").append(i).append("]");
                paramsUtil(final_key.toString(), jsonArray.get(i).toString(), data);
            }
        } else if (jsonType.equals("JSONObject")) {
            JSONObject jsonObject = strtojson(value);
            Set<Map.Entry<String, Object>> item_key = jsonObject.entrySet();
            for (Map.Entry<String, Object> entry : item_key) {//遍历Key list
                StringBuffer final_key = new StringBuffer();
                String n_key = entry.getKey();
                final_key.append(start_key.toString());
                final_key.append(".");
                final_key.append(n_key);
                String n_value = entry.getValue().toString();
                paramsUtil(final_key.toString(), n_value, data);
            }
        } else if (jsonType.equals("String")) {
            final_value = value;
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(start_key.toString(), final_value);
            data.add(basicNameValuePair);
        }
    }

    //根据传入字符串的格式,校验数据类型
    public static String getTypeByStr(String data) throws IOException {
        String flag;
        if (DataUtil.strtojson(data)!=null) {
            flag = "JSONObject";
        } else if (DataUtil.strtojsonArray(data) != null) {
            flag = "JSONArray";
        } else {
            flag = "String";
        }
        return flag;
    }

    //根据传入字符串的格式,校验数据类型
    public static String getListSize(String data) throws IOException {
        String jsonType = getJsonType(data);
        if (jsonType.equals("JSONArray")) {
            JSONArray jsonArray = DataUtil.strtojsonArray(data);
            if (jsonArray!=null) {
                return String.valueOf(jsonArray.size());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //获取当前时间戳
    public static String getRandom() {
        DateFormat dateTimeFormat = new SimpleDateFormat("yyMMddHHmmssSS");
        String id = dateTimeFormat.format(new Date());
        return id;
    }
}
