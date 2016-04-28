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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Signup();
        Login();
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                HashMap postData = new HashMap();



                postData.put("txtPriceLimit", 100);




                PostResponseAsyncTask task1 = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.contains("success")) {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                            finish();

                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                task1.execute("http://10.0.2.2/provider/addOffer.php");

                */

                isUser = true ;

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
                                            , json.getString("phone_number"), json.getString("birth_date"), json.getString("address"), json.getString("nationality"));
                                    user.setID(Integer.parseInt(json.getString("userID")));

                                } catch (org.json.JSONException e) {
                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("RegularUser", user);

                                startActivity(intent);

                                finish();

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
                                                            , json.getString("phone_number"), json.getString("birth_date"), json.getLong("nationalID"));
                                                    provider.setID(Integer.parseInt(json.getString("providerID")));

                                                } catch (org.json.JSONException e) {
                                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                }
                                                Intent intent = new Intent(LoginActivity.this, HomeProviderActivity.class);
                                                intent.putExtra("Provider", provider);

                                                startActivity(intent);
                                                finish();

                                            } else {
                                                Toast.makeText(LoginActivity.this, "Wrong phone number or password", Toast.LENGTH_LONG).show();
                                                (findViewById(R.id.txtContactInfo)).requestFocus();
                                                ((EditText) (findViewById(R.id.txtContactInfo))).setError("");
                                                (findViewById(R.id.txtPass)).requestFocus();
                                                ((EditText) (findViewById(R.id.txtPass))).setError("");

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

