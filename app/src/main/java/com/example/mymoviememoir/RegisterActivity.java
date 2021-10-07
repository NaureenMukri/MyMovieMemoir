package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import networkconnection.NetworkConnection;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    String emailid_value;
    String password_value;
    String FirstName_value;
    String LastName_value;
    String gender_value;
    String dateofbirth;
    String address_value;
    String state;
    int postcode_int_value;

    EditText username;
    EditText password;
    EditText firstname;
    EditText surname;
    RadioGroup Gender;
    DatePicker datePicker;
    EditText address;
    Spinner spinner_state;
    EditText postcode;
    Button register_button;


    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean checkDataEntered() {
        boolean error = false;
        if (isEmpty(firstname)) {
            //Toast t = Toast.makeText(getApplicationContext(), "You must enter first name to register!", Toast.LENGTH_SHORT);
            //t.show();
            firstname.setError("Please enter your First Name");
            firstname.requestFocus();
            error = true;
        }
        if (isEmpty(surname)) {
            surname.setError("Please enter your Surname");
            surname.requestFocus();
            error = true;
        }
        if (!isEmail(username)) {
            username.setError("Enter valid email!");
            username.requestFocus();
            error = true;
        }
        if (isEmpty(password)) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            error = true;
        } else if (password.getText().length() < 6) {
            password.setError("Password too short, at least 6");
            password.requestFocus();
            error = true;
        } else if (!(isValidPassword(password.getText().toString().trim()))) {
            password.setError("Password Must Contain a lowercase and uppercase alphabet, a numeric digit and a special character");
            password.requestFocus();
            error = true;
        }
        if (Gender.getCheckedRadioButtonId() == -1) {
            // if no radio buttons are checked
            Toast.makeText(getApplicationContext(), "Please select a Gender", Toast.LENGTH_SHORT).show();
            Gender.requestFocus();
            error = true;
        }
        if (isEmpty(address)) {
            address.setError("Please provide an address");
            address.requestFocus();
            error = true;
        }
        if (isEmpty(postcode)) {
            postcode.setError("Postcode is Required");
            postcode.requestFocus();
            error = true;
        }
        return error;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_it_email);
        password = findViewById(R.id.register_it_password);
        firstname = findViewById(R.id.register_it_firstName);
        surname = findViewById(R.id.register_it_lastName);
        Gender = findViewById(R.id.genderRadioGroup);
        datePicker = findViewById(R.id.date_picker_dob);
        address = findViewById(R.id.register_it_address);
        spinner_state = findViewById(R.id.spinner_state);
        postcode = findViewById(R.id.register_it_postcode);
        register_button = findViewById(R.id.btn_register);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resource = "credential";
                String methodPath = "userRegistration";
                JSONObject postdataObject = new JSONObject();
                JSONObject postdata = new JSONObject();
                emailid_value = username.getText().toString();
                password_value = password.getText().toString();
                password_value = passwordHashToMD5(password_value);
                FirstName_value = firstname.getText().toString();
                LastName_value = surname.getText().toString();
                gender_value = ((RadioButton) findViewById(Gender.getCheckedRadioButtonId()))
                        .getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                dateofbirth = "" + day + "-" + month + "-" + year + "";
                address_value = address.getText().toString();
                state = spinner_state.getSelectedItem().toString();
                try {
                    postcode_int_value = Integer.parseInt(postcode.getText().toString());
                }
                catch(NumberFormatException ex){

                }
                boolean validations = checkDataEntered();
                try {
                    postdata.put("username", emailid_value);
                    postdata.put("password", password_value);
                    postdataObject.put("methodPath", methodPath);
                    postdataObject.put("resourceValue", resource);
                    postdataObject.put("dataValue",postdata);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (validations == false) {
                    RegisterUserCredentials registerUserCredentials = new RegisterUserCredentials();
                    registerUserCredentials.execute(postdataObject);
                }
            }
        });


    }

    private class RegisterUserCredentials extends AsyncTask<JSONObject, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(JSONObject... jsonObjects) {
            return NetworkConnection.PostRequestOkHTTP(jsonObjects[0]);
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            try {
                String responseStatus = response.getString("status");
                if (Integer.parseInt(responseStatus) == 200) {
                    String resource = "person";
                    String methodPath = "personRegistration";
                    JSONObject postdataObject = new JSONObject();
                    JSONObject postdata = new JSONObject();
                    postdata.put("username", emailid_value);
                    postdata.put("firstname", FirstName_value);
                    postdata.put("surname", LastName_value);
                    postdata.put("dateofbirth", dateofbirth);
                    postdata.put("gender", gender_value);
                    postdata.put("address", address_value);
                    postdata.put("State", state);
                    postdata.put("postcode", postcode_int_value);
                    postdataObject.put("methodPath", methodPath);
                    postdataObject.put("resourceValue", resource);
                    postdataObject.put("dataValue", postdata);
                    RegisterPerson registerPerson = new RegisterPerson();
                    registerPerson.execute(postdataObject);
                    Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_LONG).show();

                } else {
                    String errorMessage = response.getString("message");
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private class RegisterPerson extends AsyncTask<JSONObject, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(JSONObject... jsonObjects) {
            return NetworkConnection.PostRequestOkHTTP(jsonObjects[0]);
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }


    private void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}


