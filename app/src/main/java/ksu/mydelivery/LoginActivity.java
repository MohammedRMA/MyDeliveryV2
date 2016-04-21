package ksu.mydelivery;


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

    final String LOG = "LoginActivity";
    private RegularUser user;

    private String phone, pass;

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


                    HashMap postData = new HashMap();

                    postData.put("txtPhone", phone);
                    postData.put("txtPassword", pass);

                    PostResponseAsyncTask task1 = new PostResponseAsyncTask(LoginActivity.this, postData, new AsyncResponse() {
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

                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong phone number or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    task1.execute("http://10.0.2.2/user/login.php");

                    BindDictionary<String> dict = new BindDictionary<String>();


                }
            }
        });
    }

}

