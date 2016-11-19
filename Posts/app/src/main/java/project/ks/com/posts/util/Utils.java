package project.ks.com.posts.util;

import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import project.ks.com.posts.bean.User;

/**
 * Created by Gopal on 03-Oct-16.
 */
public class Utils {

    public static String httpGetRequest(String URL) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseBody = "";
        try {
            HttpGet httpget = new HttpGet(AppConstant.HTTP_BASE_URL + URL);

            System.out.println("Executing request " + httpget.getRequestLine());
            HttpResponse httpResponse = httpclient.execute(httpget);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                responseBody = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(responseBody.toString());
                Log.i("Response 321", "httpGetRequest response : "+responseBody);
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }

        }catch (IOException e){
            Log.e("Exception", "httpGetRequest: IO Exception occure");
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    public static void httpPostRequest() throws IOException {
        String url = "https://selfsolve.apple.com/wcResults.do";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", HTTP.USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
    }


   /**
     *
     * The <code></code> is responsible for post request
     *
     * @param URLString
     * @param requestData
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponse postRequest(final String URLString, final String requestData)
            throws ClientProtocolException, IOException {

        Log.i("Utils", "Method : postRequest --> Enter");

        final String serviceUrl = AppConstant.HOSTNAME + AppConstant.CONTEXTPATH;

        final HttpClient httpClient = HttpClients.createDefault();

        final HttpPost httpPost = new HttpPost(serviceUrl + URLString);

        Map<String, String> requestHeader = generateRequestHeader(AppConstant.APPLICATION_JSON);
        if (requestHeader != null && !requestHeader.isEmpty()) {

            for (final Map.Entry<String, String> entry : requestHeader.entrySet()) {

                httpPost.addHeader(entry.getKey(), entry.getValue());

            }

        }

        if( requestData != null ){
            httpPost.setEntity(new StringEntity(requestData));
        }

        Log.i("Utils", "Method : postRequest --> Exit");
        return httpClient.execute(httpPost);
    }

    /**
     *
     * The <code></code> is responsible for get request
     *
     * @param URLString
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponse getRequest(final String URLString)
            throws ClientProtocolException, IOException {

        final String serviceUrl = AppConstant.HOSTNAME + AppConstant.CONTEXTPATH;
        final HttpClient httpClient = HttpClients.createDefault(); //HttpClientBuilder.create().build();
        final HttpGet httpGet = new HttpGet(serviceUrl + URLString);

        return httpClient.execute(httpGet);
    }

    /**
     * This method responsible to generate JSON from a given object.
     *
     * @param object
     * @return String
     */
    public static String generateJSONFromObject(final Object object) {

        final ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;

        try {

            //objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

            /**
             * Store jSON in to jsonString
             */
            jsonString = objectMapper.writeValueAsString(object);

        } catch (final JsonGenerationException e) {
            e.printStackTrace();
        } catch (final JsonMappingException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * This method responsible to generate object from a from a given given JSON
     * string.
     *
     * @param jSONString
     * @param clazz
     * @return
     */
    public static <T> T generateObjectFromJSON(final String jSONString, final Class<T> clazz) {

        ObjectMapper objectMapper = null;
        T object = null;

        if (!Utils.isEmpty(jSONString)) {

            try {

                object = clazz.newInstance();
                objectMapper = new ObjectMapper();
                object = objectMapper.readValue(jSONString, clazz);

            } catch (final JsonParseException e) {
                e.printStackTrace();
            } catch (final JsonMappingException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final InstantiationException e1) {
                e1.printStackTrace();
            } catch (final IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (final Exception e1) {
                e1.printStackTrace();
            }
        }
        return object;
    }


    /**
     * The <code>generateRequestHeader</code> is responsible for generate the request header with below parameters.
     *
     * @param contentType
     * @return
     */
    public static Map<String, String> generateRequestHeader(final String contentType){
        Map<String, String> requestHeader = null;
        if( !Utils.isEmpty(contentType)){
            requestHeader = new HashMap<String, String>();
            requestHeader.put(AppConstant.CONTENT_TYPE, contentType);
        }
        return requestHeader;
    }

    /**
     * This method responsible to convert java.util.Date to String Date
     *
     * @param date
     * @return String
     */
    public static String convertToStringDate(final Date date) {

        String formattedDate = null;

        if (date != null) {
            formattedDate = AppConstant.DEFAULT_DMY_DATE_FORMAT.format(date);
        }// end of if

        return formattedDate;
    }// end of method - convertToStringDate

    /**
     * This method responsible to check whether the String is empty or not.
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(final String param) {
        final boolean error = false;
        if (param == null || param.trim().length() <= 0) {
            return true;
        }
        return error;
    }

    /**
     * This method responsible to check whether the Digit is valid or not.
     *
     * @param param
     * @return
     */
    public static boolean isValidDigit(final String param) {
        final String regxPattern = "^[0-9]+$";
        final Pattern pattern = Pattern.compile(regxPattern);
        final Matcher matcher = pattern.matcher(param);
        return matcher.matches();
    }

    /**
     * This method responsible to check List is Null or Not.
     *
     * @param list
     * @return
     */
    public static boolean isListNotNull(final List<?> list) {

        boolean flag = false;

        if (list != null && list.size() > 0) {
            flag = true;
        }

        return flag;
    }

    /**
     * This method responsible to return String from Integer.
     *
     * @param paramValue
     * @return
     */
    public static String getStringValue(final Integer paramValue) {
        String value = "";
        try {
            if (paramValue != null) {
                value = String.valueOf(paramValue);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * This method responsible to check alpha numeric character.
     *
     * @param param
     * @return flag
     */
    public static boolean isAlpaNumeric(final String param) {

        final String regxPattern = "[a-zA-Z0-9]+$";
        final Pattern pattern = Pattern.compile(regxPattern);
        final Matcher matcher = pattern.matcher(param);
        return matcher.matches();
    }

    /**
     * This method is responsible to check whether the Integer value passed is
     * empty or not.
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(final Integer param) {

        final boolean error = false;

        if (param == null || param <= 0) {
            return true;
        }
        return error;
    }

    /**
     * This method responsible to check special character for given string
     * value.
     *
     * @param param
     * @return
     */
    public static boolean isSpecialCharacter(final String param) {

        final String regxPattern = "-/@#$%^&_+=()><?*";
        return param.matches("[" + regxPattern + "]+");

    }

    /**
     * This method is responsible to check whether the Double value passed is
     * empty or not.
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(final Double param) {

        final boolean error = false;

        if (param == null || param <= 0.0d) {
            return true;
        }
        return error;
    }

    /**
     * This method converts String into java.util.date
     * @param stringDate
     * @return String
     */
    public static Date getFormattedDate(final String stringDate) {

        Date date = null;

        if (stringDate != null && stringDate.trim().length() > 0) {

            try {
                date = AppConstant.DEFAULT_DMY_DATE_FORMAT.parse(stringDate);
            } catch (final Exception e) {
                e.printStackTrace();
            }

        }//end of if

        return date;
    }//end of method - getFormattedDate

    /**
     * This method is responsible to check whether the Date value is in proper format or not.
     *
     * @param param
     * @return
     */
    public static boolean isValidDate(final String param) {

        final String regxPattern = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
        final Pattern pattern = Pattern.compile(regxPattern);
        final Matcher matcher = pattern.matcher(param);

        if (matcher.matches()) {

            matcher.reset();
            if (matcher.find()) {

                final String day = matcher.group(2);
                final String month = matcher.group(1);
                final int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") && (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11") || month.equals("04")
                        || month.equals("06") || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    // leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static User getUserData(String userJSON){

        User user = null;
        if(!isEmpty(userJSON)){
            user = generateObjectFromJSON(userJSON,User.class);
        }
        return user;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public  static User getQuestionUser(User user){

        User questionUser = user;
        user.setPassword("");
        user.setTags(null);
        return questionUser;
    }
}
