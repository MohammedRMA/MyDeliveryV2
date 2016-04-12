package ksu.mydelivery;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Signup();
        Login();
    }

    public void Signup () {
        TextView Tsign = (TextView) findViewById(R.id.lblSignup);
        Tsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void Login () {

        Button login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                EditText phone = (EditText) findViewById(R.id.txtPhone);
                EditText  pass =  (EditText) findViewById(R.id.txtPass);
                if (phone.getText().toString().equals("123") && pass.getText().toString().equals("123")) {
                    Toast.makeText(getApplicationContext(), "Login Success User", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
                else if (phone.getText().toString().equals("321") && pass.getText().toString().equals("321")){
                    Toast.makeText(getApplicationContext(), "Login Success Provider", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeProviderActivity.class));
                }
                else
                   Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

}

