package ksu.mydelivery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {

    private Provider provider;

    private  String firstN, lastN , phone , email , pass, bDate , address , nation ;
    private  long   nationalID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btnSign = (Button) findViewById(R.id.btnSignup) ;
        btnSign.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                SignupUser();
            }
        });

        TextView signProvider = (TextView) findViewById(R.id.lblSignupProvider) ;
        signProvider.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                SignupProvider();
            }
        });

    }

    public void SignupProvider () {
        //  startActivity(new Intent(SignUpActivity.this, SignUpProviderActivity.class));


            firstN = ((EditText) findViewById(R.id.txtFirstN)).getText().toString();
            lastN = ((EditText) findViewById(R.id.txtLastN)).getText().toString();
            phone = ((EditText) findViewById(R.id.txtContactInfo)).getText().toString();
            email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
            pass = ((EditText) findViewById(R.id.txtPass)).getText().toString();
            bDate = ((EditText) findViewById(R.id.txtBDate)).getText().toString();


            if (pass.equals("") || email.equals("") || firstN.equals("") || lastN.equals("") || phone.equals("") ) {
                (findViewById(R.id.txtPass)).requestFocus();
                ((EditText) (findViewById(R.id.txtPass))).setError("FIELD CANNOT BE EMPTY");
            }

            else {
                            try {
                                provider = new Provider(pass, email, firstN, lastN, phone, bDate, nationalID);
                            } catch (Exception e) {
                                Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                            Intent intent = new Intent(SignUpActivity.this, SignUpProviderActivity.class);
                            intent.putExtra("Provider", provider);

                            startActivity(intent);
                            finish();

                        }
    }

    public void SignupUser () {

          firstN = ((EditText) findViewById(R.id.txtFirstN)).getText().toString();
          lastN = ((EditText) findViewById(R.id.txtLastN)).getText().toString();
          phone = ((EditText) findViewById(R.id.txtContactInfo)).getText().toString();
          email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
          pass = ((EditText) findViewById(R.id.txtPass)).getText().toString();
          bDate = ((EditText) findViewById(R.id.txtBDate)).getText().toString();
          address = ((EditText) findViewById(R.id.txtAddress)).getText().toString();
          nation = ((EditText) findViewById(R.id.txtNation)).getText().toString();

        if (pass.equals("") || email.equals("") || firstN.equals("") || lastN.equals("") || phone.equals("") ) {
             (findViewById(R.id.txtPass)).requestFocus();
            ((EditText) (findViewById(R.id.txtPass))).setError("FIELD CANNOT BE EMPTY");
        }

        else {



            HashMap postData = new HashMap();
            postData.put("txtPassword", pass);
            postData.put("txtEmail", email);
            postData.put("txtFname", firstN);
            postData.put("txtLname", lastN);
            postData.put("txtPhone", phone);
            postData.put("txtAddress", address);
            postData.put("txtNation", nation);
            postData.put("txtBdate", bDate);

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(SignUpActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.contains("success")) {
                        Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task1.execute("http://10.0.2.2/user/insertUser.php");

        }
    }

}

