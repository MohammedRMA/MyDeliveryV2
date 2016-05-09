package ksu.mydelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class MailSenderActivity extends Activity {

        private String email;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mail_sender);


            Button unVerifyProvider = (Button) findViewById(R.id.btnNext);
            unVerifyProvider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendPassword();

                }
            });

        }




        public void sendPassword(){

            email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();

            if (email.equals("")) {
                (findViewById(R.id.txtEmail)).requestFocus();
                ((EditText) (findViewById(R.id.txtEmail))).setError("FIELD CANNOT BE EMPTY");


            } else { // Info doesn't exist on user table we should search for provider table

                HashMap emailData = new HashMap();
                emailData.put("txtEmail", email);

                PostResponseAsyncTask checkEmail = new PostResponseAsyncTask(MailSenderActivity.this, emailData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.contains("success")) {

                            s = s.replace("success ", "");

                            try {
                                GMailSender sender = new GMailSender("isproject2016@gmail.com", "is112233");
                                sender.sendMail("This is Subject",
                                        "This is Body",
                                        "isproject2016@gmail.com",
                                        "Mohammed.RMA@hotmail.com");
                                Toast.makeText(MailSenderActivity.this, "MohammedRMA Sent", Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(MailSenderActivity.this, "Failed", Toast.LENGTH_LONG).show();

                                Log.e("SendMail", e.getMessage(), e);
                            }


                            Toast.makeText(MailSenderActivity.this, "Email Sent", Toast.LENGTH_LONG).show();
                            //   user = new ArrayList<String>();

                            Intent intent = new Intent(MailSenderActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                    }

                }
            });

            checkEmail.execute("http://10.0.2.2/provider/forgotPass.php");



        }




}

}