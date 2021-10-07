package networkconnection;

import android.os.Build;
import android.util.JsonReader;
import android.util.Log;
import android.view.textclassifier.TextLinks;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

import JSONEntity.Credentials;
import JSONEntity.Person;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {

    private OkHttpClient client=null;
    private String results;
    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");
    public NetworkConnection(){
        client=new OkHttpClient();
    }
    private final static String base_url = "http://118.138.90.123:8080/Assignment_FIT5046_307279742/webresources/";
    private final static String credential_url = "restws.credentials/";
    private final static String person_url = "restws.person/";
    private final static String memoir_url = "restws.memoir/";
    private final static String cinema_url = "restws.cinema/";

    private static String getResource(String Resource){
        String resourcePath = null;
        if (Resource.equalsIgnoreCase("credential"))
        {
            resourcePath = credential_url;
        }
        if ( Resource.equalsIgnoreCase("person"))
        {
            resourcePath = person_url;
        }
        if (Resource.equalsIgnoreCase("memoir"))
        {
            resourcePath = memoir_url;
        }
        if (Resource.equalsIgnoreCase("cinema"))
        {
            resourcePath = cinema_url;
        }
        return resourcePath;
    }

    public String findByUsernameAndPassword(String username, String password){
        final String methodPath = "restws.credentials/findByUsernameAndPassword" + username + "/" + password;

        Request.Builder builder = new Request.Builder();
        builder.url(base_url + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                results=response.body().string();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }


    public static JSONObject PostRequestOkHTTP(JSONObject values){

        OkHttpClient client = new OkHttpClient();
        URL url = null;
        Response response = null;
        JSONObject strResponse= null;
        try {
            String methodPath = values.getString("methodPath");
            String resource = getResource(values.getString("resourceValue"));
            url = new URL(base_url + resource+methodPath);
            Log.d("url", base_url+resource+methodPath);
            JSONObject postData = values.getJSONObject("dataValue");
            RequestBody body = RequestBody.create(String.valueOf(postData), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
            String responseString = Objects.requireNonNull(response.body()).string();
            Log.d("response",responseString);
            boolean fail = responseString.contains("Username Already exists");
            if (fail == true)
            {
                strResponse = new JSONObject(responseString);
            }
            else
            {
                strResponse = new JSONObject();
                strResponse.put("status","200");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return strResponse;
    }

    public static JSONObject GetRequestOkHTTP(JSONObject getData){
        OkHttpClient client = new OkHttpClient();
        URL url = null;
        Response response = null;
        JSONObject strResponse= null;
        try {
            String methodPath = getData.getString("methodPath");
            String resource = getResource(getData.getString("resourceValue"));
            url = new URL(base_url + resource+methodPath);
            Log.d("url", base_url+resource+methodPath);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
            String responseString =response.body().string();
            Log.d("response",responseString);
            strResponse = new JSONObject(responseString);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public static JSONArray GetRequestOkHTTPAsArray(JSONObject getData){
        OkHttpClient client = new OkHttpClient();
        URL url = null;
        Response response = null;
        JSONArray strResponse= null;
        try {
            String methodPath = getData.getString("methodPath");
            String resource = getResource(getData.getString("resourceValue"));
            url = new URL(base_url + resource+methodPath);
            Log.d("url", base_url+resource+methodPath);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
            Log.d("response",response.toString());
            Log.d("response","reached");
            strResponse = new JSONArray(response.body().string());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public static String getFirstName(String username){
        String results = null;
        OkHttpClient client = new OkHttpClient();
        final String methodPath = "restws.Person/findUsernamefromCredential" + username;
        Request.Builder builder = new Request.Builder(); builder.url(base_url + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }catch (Exception e){ e.printStackTrace();
        }
        return results;
    }

    public String addCredentials(String[] details) {
        Credentials credentials = new Credentials(details[0], details[1], Date.valueOf(details[3]));
        Gson gson = new Gson();
        String credentialJson = gson.toJson(credentials);
        String strResponse = "";
        Log.i("json", credentialJson);

        final String methodPath = "restws/credentials/userRegistration";

        RequestBody body = RequestBody.create(credentialJson, JSON);
        Request request = new Request.Builder()
                .url(base_url + methodPath)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String addPerson(String[] details){

        Person person = new Person(Integer.parseInt(details[0]), details[1], details[2], details[3], Date.valueOf(details[4]), details[5], details[6],
                Integer.parseInt(details[7]));


        Gson gson = new Gson();
        String personJson = gson.toJson(person);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , personJson);

        final String methodPath = "restws.person/";

        RequestBody body = RequestBody.create(personJson, JSON);
        Request request = new Request.Builder()
                .url(base_url + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute(); strResponse= response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse; }







}
