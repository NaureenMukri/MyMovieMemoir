package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigInteger;

import networkconnection.NetworkConnection;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private String email;

    final static String base_url = "http://118.138.90.123:8080/Assignment_FIT5046_307279742/webresources/";
    NetworkConnection networkConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        networkConnection = new NetworkConnection();

        final TextView email_input = findViewById(R.id.it_email);
        final TextView password_input = findViewById(R.id.it_password);
        Button login_btn = findViewById(R.id.login_button);
        TextView register = findViewById(R.id.link_signup);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_input.getText().toString();
                String password_text = password_input.getText().toString();
                String password_hash = passwordHashToMD5(password_text);
                  //Bundle bundle = new Bundle();
//                bundle.putString("username", email_input.getText().toString());
//                HomeFragment getusername = new HomeFragment();
//                getusername.setArguments(bundle);
                login Login = new login();
                Login.execute(email, password_hash);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public String passwordHashToMD5(String password_text) {

        byte[] md5Input = password_text.getBytes();
        BigInteger md5data = null;

        try {
            md5data = new BigInteger(1, MD5Hash.encryptMD5(md5Input));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String md5str = md5data.toString(16);
        return md5str;
    }

    public String getUsername(){
        return email;
    }

    public class getPersonDetails extends AsyncTask<Void, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String methodPath = "findUsernamefromCredential/" + email;
            String resource = "person";
            JSONObject response = null;
            try {
                JSONObject personObject = new JSONObject();
                personObject.put("methodPath", methodPath);
                personObject.put("resourceValue", resource);
                response = NetworkConnection.GetRequestOkHTTP(personObject);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                String personid = jsonObject.getString("personid");
                String firstname = jsonObject.getString("firstname");
                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                intent.putExtra("username",email);
                intent.putExtra("firstname", firstname);
                intent.putExtra("personid", personid);
                startActivity(intent);
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class login extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            String message= "";
            MediaType JSON =
                    MediaType.parse("application/json; charset=utf-8");
            OkHttpClient okHttpClient = new OkHttpClient();
            final String methodPath = "restws.credentials/findByUsername/" + username + "/" + password;
            Request.Builder builder = new Request.Builder();
            builder.url(base_url + methodPath);
            Request request = builder.build();
            Log.d("url",base_url+methodPath);
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
                Log.d("response",response.toString());
                if (response.isSuccessful()) {
                    Log.d("success","reached");
                    String result = response.body().string();
                    message = "success";
                    }
                else showToast("Email or Password Incorrect!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equalsIgnoreCase("success")) {
                Log.d("getting","Person details");
                getPersonDetails g = new getPersonDetails();
                g.execute();
            }
        }
    }


    private void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
