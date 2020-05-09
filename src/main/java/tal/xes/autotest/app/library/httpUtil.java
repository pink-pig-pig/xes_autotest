package tal.xes.autotest.app.library;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author zyz
 * @date 2020.05.09
 * @descirption http工具类
 *
 * */

public class httpUtil {

    private static CloseableHttpResponse response;
    private static CloseableHttpClient httpClient;

    //get请求基础方法
    private static CloseableHttpResponse get(String url, HashMap<String, String> map, HashMap<String, String> headers) {
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            StringBuffer params = new StringBuffer();
            String final_url = null;
            if (map.size()!=0) {
                int i = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (i == 0) {
                        params.append("?");
                    } else {
                        params.append("&");
                    }
                    String key = entry.getKey();
                    String value = entry.getValue();
                    params.append(key).append("=").append(value);
                    i++;
                }
                final_url = url + params.toString();
            } else {
                final_url = url;
            }
            HttpGet httpGet = new HttpGet(final_url);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {//get请求中的Headers
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.setHeader(key, value);
                }
            }
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            return response;
        } catch (IOException ex) {
            System.out.println("Get请求失败");
            return null;
        }
    }

    //get请求获取status code
    public static String getStatusCode(String url, HashMap<String, String> map) throws IOException {
        String statuscode = null;
        CloseableHttpResponse rp = get(url, map, null);
        if (rp != null) {
            int sc = rp.getStatusLine().getStatusCode();
            statuscode = String.valueOf(sc);
            response.close();
            httpClient.close();
        }
        return statuscode;
    }

    public static String getStatusCode(String url, HashMap<String, String> map, HashMap<String, String> headers) throws IOException {
        String statuscode = null;
        CloseableHttpResponse rp = get(url, map, headers);
        if (rp != null) {
            int sc = rp.getStatusLine().getStatusCode();
            statuscode = String.valueOf(sc);
            response.close();
            httpClient.close();
        }
        return statuscode;
    }

    //get请求获取接口响应数据
    public static String getResponse(String url, HashMap<String, String> datamap) throws IOException {
        String responsedata = null;
        CloseableHttpResponse rp = get(url, datamap, null);
        if (rp != null) {
            responsedata = EntityUtils.toString(rp.getEntity(), "utf-8");
            response.close();
            httpClient.close();
        }
        return setFilter(responsedata);
    }
    public static String getResponse(String url, HashMap<String, String> datamap, HashMap<String, String> headers) throws IOException {
        String responsedata = null;
        CloseableHttpResponse rp = get(url, datamap, headers);
        if (rp != null) {
            responsedata = EntityUtils.toString(rp.getEntity(), "utf-8");
            response.close();
            httpClient.close();
        }
        return setFilter(responsedata);
    }

    //post请求基础方法
    private static CloseableHttpResponse post(String url, HashMap<String, String> data, HashMap<String, String> headers, HashMap<String, String> cookies) throws IOException {
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            HttpPost httpPost = new HttpPost(url);
            DataUtil dataUtil = new DataUtil();
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(dataUtil.mapToList(data), Consts.UTF_8);//post请求中的data数据
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(entity);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {//post请求中的Headers
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpPost.setHeader(key, value);
                }
            }
            if (cookies != null) {
                String c = dataUtil.mapToCookie(cookies);
                httpPost.setHeader("Cookie", c);
            }
            response = httpClient.execute(httpPost);
            return response;
        } catch (IOException ex) {
            System.out.println("Post请求失败");
            return null;
        }
    }

    //Post请求得到Status code
    public static String postStatusCode(String url, HashMap<String, String> data, HashMap<String, String> headers) throws IOException {
        String statuscode = null;
        CloseableHttpResponse rp = post(url, data, headers, null);
        if (rp != null) {
            int sc = rp.getStatusLine().getStatusCode();
            statuscode = String.valueOf(sc);
            response.close();
            httpClient.close();
        }
        return statuscode;
    }

    public static String postStatusCode(String url, HashMap<String, String> data) throws IOException {
        String statuscode = null;
        CloseableHttpResponse rp = post(url, data, null, null);
        if (rp != null) {
            int sc = rp.getStatusLine().getStatusCode();
            statuscode = String.valueOf(sc);
            response.close();
            httpClient.close();
        }
        return statuscode;
    }

    //Post请求得到Status code
    public static String postStatusCode(String url, HashMap<String, String> data, HashMap<String, String> headers, HashMap<String, String> cookies) throws IOException {
        String statuscode = null;
        CloseableHttpResponse rp = post(url, data, headers, cookies);
        if (rp != null) {
            int sc = rp.getStatusLine().getStatusCode();
            statuscode = String.valueOf(sc);
            response.close();
            httpClient.close();
        }
        return statuscode;
    }


    //POST请求获取接口响应数据
    public static String postResponse(String url, HashMap<String, String> data, HashMap<String, String> headers) throws IOException {
        String responsedata = null;
        CloseableHttpResponse rp = post(url, data, headers, null);
        if (rp != null) {
            responsedata = EntityUtils.toString(rp.getEntity(), "utf-8");
            response.close();
            httpClient.close();
        }
        return setFilter(responsedata);
    }

    //get请求获取接口响应数据
    public static String postResponse(String url, HashMap<String, String> data, HashMap<String, String> headers, HashMap<String, String> cookies) throws IOException {
        String responsedata = null;
        CloseableHttpResponse rp = post(url, data, headers, cookies);
        if (rp != null) {
            responsedata = EntityUtils.toString(rp.getEntity(), "utf-8");
            response.close();
            httpClient.close();
        }
        return setFilter(responsedata);
    }

    //POST请求获取接口响应数据
    public static String postResponse(String url, HashMap<String, String> data) throws IOException {
        String responsedata = null;
        CloseableHttpResponse rp = post(url, data, null, null);
        if (rp != null) {
            responsedata = EntityUtils.toString(rp.getEntity(), "utf-8");
            response.close();
            httpClient.close();
        }
        return setFilter(responsedata);
    }

    //POST请求获取Headers
    public static Header[] postHeaders(String url, HashMap<String, String> data) throws IOException {
        Header[] responseHeaders = new Header[0];
        CloseableHttpResponse rp = post(url, data, null, null);
        if (rp != null) {
            responseHeaders = rp.getAllHeaders();
            response.close();
            httpClient.close();
        }
        System.out.println(responseHeaders);
        return responseHeaders;
    }


    private static String setFilter(String responsedata) {
        JSONObject jsonObject = JSON.parseObject(responsedata);
        return JSON.toJSONString(jsonObject, httpUtil.filter);
    }

    private static PropertyFilter filter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {

            if("traceId".equals(name)){
                return false;
            }
            if("version".equals(name)){
                return false;
            }
            return true;
        }
    };

}
