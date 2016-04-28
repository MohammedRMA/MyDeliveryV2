package ksu.mydelivery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;


public class SignUpProviderActivity extends AppCompatActivity {
    private String firstN, lastN, phone, email, pass, bDate, nationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_provider);

        Button signup = (Button) findViewById(R.id.btnSignupProvider);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Provider provider = (Provider) getIntent().getSerializableExtra("Provider");
                Toast.makeText(SignUpProviderActivity.this, provider.getPhoneNumber(), Toast.LENGTH_LONG).show();

                firstN = provider.getfName();
                lastN = provider.getlName();
                phone = provider.getPhoneNumber();
                email = provider.getEmail();
                pass = provider.getPassword();
                bDate = provider.getBirthDate();
                nationID = ((EditText) findViewById(R.id.txtNationID)).getText().toString();

                if (nationID.equals("")){
                    (findViewById(R.id.txtNationID)).requestFocus();
                    ((EditText) (findViewById(R.id.txtNationID))).setError("FIELD CANNOT BE EMPTY");
                }

                else {
                    HashMap postData = new HashMap();
                    postData.put("txtPassword", pass);
                    postData.put("txtEmail", email);
                    postData.put("txtFname", firstN);
                    postData.put("txtLname", lastN);
                    postData.put("txtPhone", phone);
                    postData.put("txtBdate", bDate);
                    postData.put("txtNationalID", nationID);


                    PostResponseAsyncTask task1 = new PostResponseAsyncTask(SignUpProviderActivity.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (s.contains("success")) {
                                Toast.makeText(SignUpProviderActivity.this, "Success", Toast.LENGTH_LONG).show();
                              //  startActivity(new Intent(SignUpProviderActivity.this, LoginActivity.class));
                                finish();

                            } else {
                                Toast.makeText(SignUpProviderActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    task1.execute("http://10.0.2.2/provider/insertProvider.php");

                }
            }
        });


    }
}
