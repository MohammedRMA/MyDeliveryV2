package ksu.mydelivery;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    //ProgressDialog pd;

    final String LOG = "LoginActivity";
    private RegularUser user;
    private Provider provider;
    private String phone, pass;
    private boolean isUser;
    private Admin admin;

    private ArrayList<String> jsonList;
    private ArrayList<Request> requestList;
    private  Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestList = new ArrayList<>();

        Signup();
        Login();
        forgetPasswors();
    }



    public void forgetPasswors() {
        TextView Tsign = (TextView) findViewById(R.id.lblForgetPass);
        Tsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MailSenderActivity.class));
            }
        });
    }


    public void Signup() {
        TextView Tsign = (TextView) findViewById(R.id.lblSignup);
        Tsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void Login() {

        Button login = (Button) findViewById(R.id.btnLogin);
        if (login !=  null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    isUser = true;

                    phone = ((EditText) findViewById(R.id.txtContactInfo)).getText().toString();
                    pass = ((EditText) findViewById(R.id.txtPass)).getText().toString();


                    if (phone.equals("") || pass.equals("")) {

                        if (phone.equals("")) {
                            (findViewById(R.id.txtContactInfo)).requestFocus();
                            ((EditText) (findViewById(R.id.txtContactInfo))).setError("FIELD CANNOT BE EMPTY");
                        }

                        if (pass.equals("")) {
                            (findViewById(R.id.txtPass)).requestFocus();
                            ((EditText) (findViewById(R.id.txtPass))).setError("FIELD CANNOT BE EMPTY");
                        }
                    } else {


                        HashMap postUserData = new HashMap();

                        postUserData.put("txtPhone", phone);
                        postUserData.put("txtPassword", pass);

                        PostResponseAsyncTask loginUser = new PostResponseAsyncTask(LoginActivity.this, postUserData, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                if (s.contains("success")) {

                                    s = s.replace("success ", "");

                                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                                    //   user = new ArrayList<String>();
                                    try {
                                        JSONObject json = new JSONObject(s);

                                        user = new RegularUser(json.getString("password"), json.getString("email"), json.getString("first_name"), json.getString("last_name")
                                                , json.getString("phone_number"), json.getString("birth_date"), json.getString("address"), json.getString("nationality")  , json.getString("is_suspended") , json.getDouble("rate_app") );
                                        user.setID(Integer.parseInt(json.getString("userID")));

                                        /*
                                         user = new RegularUser(json.getString("password"), json.getString("email"), json.getString("first_name"), json.getString("last_name")
                                                , json.getString("phone_number"), json.getString("birth_date"), json.getString("address"), json.getString("nationality"), json.getString("rate_app"));
                                        user.setID(Integer.parseInt(json.getString("userID")));
                                         */

                                    } catch (org.json.JSONException e) {
                                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                    }



                                        PostResponseAsyncTask getRequests = new PostResponseAsyncTask(LoginActivity.this, new AsyncResponse() {
                                            @Override
                                            public void processFinish(String s) {

                                                jsonList = new ArrayList<>();
                                                requestList = new ArrayList<>();

                                                try {
                                                    JSONArray jsonArray = new JSONArray(s);
                                                    JSONObject jsonObject;

                                                    if (jsonArray != null) {
                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                            jsonList.add(jsonArray.get(i).toString());
                                                        }
                                                    }

                                                    for (int i = 0; i < jsonList.size(); i++) {
                                                        jsonObject = new JSONObject(jsonList.get(i));
                                                        request = new Request(jsonObject.getInt("requestID"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("type")
                                                                , jsonObject.getString("source_address"), jsonObject.getString("destination_address"), jsonObject.getInt("price")
                                                                , jsonObject.getString("due_time"), jsonObject.getString("contact_info"));
                                                        requestList.add(request);

                                                    }

                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    intent.putExtra("RegularUser", user);
                                                    intent.putExtra("Requests", requestList);

                                                    startActivity(intent);
                                                    finish();

                                                } catch (org.json.JSONException e) {
                                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                }



                                            }
                                        });

                                    getRequests.execute("http://10.0.2.2/user/requests.php");



                                } else { // Info doesn't exist on user table we should search for provider table

                                    HashMap postProviderData = new HashMap();
                                    postProviderData.put("txtPhone", phone);
                                    postProviderData.put("txtPassword", pass);

                                    PostResponseAsyncTask loginProvider = new PostResponseAsyncTask(LoginActivity.this, postProviderData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                            if (s.contains("success")) {

                                                s = s.replace("success ", "");

                                                Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                                                //   user = new ArrayList<String>();
                                                try {
                                                    JSONObject json = new JSONObject(s);

                                                    provider = new Provider(json.getString("password"), json.getString("email"), json.getString("first_name"), json.getString("last_name")
                                                            , json.getString("phone_number"), json.getString("birth_date"), json.getLong("nationalID") , json.getString("is_verified") , json.getDouble("rate_app"));
                                                    provider.setID(Integer.parseInt(json.getString("providerID")));

                                                } catch (org.json.JSONException e) {
                                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                }



                                                PostResponseAsyncTask getRequests = new PostResponseAsyncTask(LoginActivity.this, new AsyncResponse() {
                                                    @Override
                                                    public void processFinish(String s) {

                                                        jsonList = new ArrayList<>();
                                                        requestList = new ArrayList<>();

                                                        try {
                                                            JSONArray jsonArray = new JSONArray(s);
                                                            JSONObject jsonObject;

                                                            if (jsonArray != null) {
                                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                                    jsonList.add(jsonArray.get(i).toString());
                                                                }
                                                            }

                                                            for (int i = 0; i < jsonList.size(); i++) {
                                                                jsonObject = new JSONObject(jsonList.get(i));
                                                                request = new Request(jsonObject.getInt("requestID"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("type")
                                                                        , jsonObject.getString("source_address"), jsonObject.getString("destination_address"), jsonObject.getInt("price")
                                                                        , jsonObject.getString("due_time"), jsonObject.getString("contact_info"));
                                                                requestList.add(request);

                                                            }

                                                            Intent intent = new Intent(LoginActivity.this, HomeProviderActivity.class);
                                                            intent.putExtra("Provider", provider);
                                                            intent.putExtra("Requests", requestList);

                                                            startActivity(intent);
                                                            finish();

                                                        } catch (org.json.JSONException e) {
                                                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                        }



                                                    }
                                                });

                                                getRequests.execute("http://10.0.2.2/user/requests.php");




                                            } else { // Info doesn't exist on user table we should search for provider table

                                                HashMap postAdminData = new HashMap();
                                                postAdminData.put("txtPhone", phone);
                                                postAdminData.put("txtPassword", pass);

                                                PostResponseAsyncTask loginAdmin = new PostResponseAsyncTask(LoginActivity.this, postAdminData, new AsyncResponse() {
                                                    @Override
                                                    public void processFinish(String s) {
                                                        if (s.contains("success")) {

                                                            s = s.replace("success ", "");

                                                            Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                                                            //   user = new ArrayList<String>();
                                                            try {
                                                                JSONObject json = new JSONObject(s);

                                                                admin = new Admin(json.getString("password"), json.getString("email"), json.getString("first_name"), json.getString("last_name")
                                                                        , json.getString("phone_number"), json.getString("birth_date"));
                                                                admin.setID(Integer.parseInt(json.getString("adminID")));

                                                            } catch (org.json.JSONException e) {
                                                                Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                            }

                                                            PostResponseAsyncTask getRequests = new PostResponseAsyncTask(LoginActivity.this, new AsyncResponse() {
                                                                @Override
                                                                public void processFinish(String s) {

                                                                    jsonList = new ArrayList<>();
                                                                    requestList = new ArrayList<>();

                                                                    try {
                                                                        JSONArray jsonArray = new JSONArray(s);
                                                                        JSONObject jsonObject;

                                                                        if (jsonArray != null) {
                                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                                jsonList.add(jsonArray.get(i).toString());
                                                                            }
                                                                        }

                                                                        for (int i = 0; i < jsonList.size(); i++) {
                                                                            jsonObject = new JSONObject(jsonList.get(i));
                                                                            request = new Request(jsonObject.getInt("requestID"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("type")
                                                                                    , jsonObject.getString("source_address"), jsonObject.getString("destination_address"), jsonObject.getInt("price")
                                                                                    , jsonObject.getString("due_time"), jsonObject.getString("contact_info"));
                                                                            requestList.add(request);

                                                                        }

                                                                        Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                                                        intent.putExtra("Admin", admin);
                                                                        intent.putExtra("Requests", requestList);

                                                                        startActivity(intent);
                                                                        finish();

                                                                    } catch (org.json.JSONException e) {
                                                                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                                    }



                                                                }
                                                            });

                                                            getRequests.execute("http://10.0.2.2/user/requests.php");

                                                        }

                                                        else {
                                                            Toast.makeText(LoginActivity.this, "Wrong phone number or password", Toast.LENGTH_LONG).show();
                                                            (findViewById(R.id.txtContactInfo)).requestFocus();
                                                            ((EditText) (findViewById(R.id.txtContactInfo))).setError("");
                                                            (findViewById(R.id.txtPass)).requestFocus();
                                                            ((EditText) (findViewById(R.id.txtPass))).setError("");

                                                        }
                                                    }
                                                });

                                                loginAdmin.execute("http://10.0.2.2/admin/login.php");

                                            }

                                        }
                                    });

                                    loginProvider.execute("http://10.0.2.2/provider/login.php");


                                }
                            }
                        });

                        loginUser.execute("http://10.0.2.2/user/login.php");

                        //BindDictionary<String> dict = new BindDictionary<String>();


                    }


                }
            });
        }
    }

}

