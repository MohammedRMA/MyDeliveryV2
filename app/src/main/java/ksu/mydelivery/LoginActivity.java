package ksu.mydelivery;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Signup();
        Login();
    }

    public void Signup () {
        TextView Tsign = (TextView) findViewById(R.id.Tview_signup);
        Tsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void Login () {
      /*  final String email = findViewById(R.id.txt_email).toString();
        final String pass =  findViewById(R.id.txt_pass).toString();*/
        Button login = (Button) findViewById(R.id.btn_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (email == "m@m.com" && pass == "123")
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                 //   Toast.makeText("Failed", Toast.LENGTH_LONG,);
            }
        });
    }

}

