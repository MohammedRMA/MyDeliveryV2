package ksu.mydelivery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignupProvider();
    }

    public void SignupProvider () {
        TextView TsignP = (TextView) findViewById(R.id.Tview_signupP);
        TsignP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignUpProviderActivity.class));
            }
        });
    }

}

