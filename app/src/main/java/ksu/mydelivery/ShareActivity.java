package ksu.mydelivery;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ShareActivity extends AppCompatActivity {

    private Context context;
    private static Fb_Twitter instance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        ImageButton twitter = (ImageButton) findViewById(R.id.btnTwitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fb_Twitter fb = new Fb_Twitter();

            }
        });


    }


    public class Fb_Twitter {


        private  Fb_Twitter instance;
        private  Fb_Twitter instance() {
            if(instance == null)
            {
                instance = new Fb_Twitter();
            }
            return instance;
        }
        public Fb_Twitter()
        {
            this.instance = this;
        }
        public void setContext(Context context1)
        {
            context = context1;
        }
        private boolean appInstalledOrNot(String uri) {
            PackageManager pm = context.getPackageManager();
            boolean app_installed = false;
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                app_installed = true;
            }
            catch (PackageManager.NameNotFoundException e) {
                app_installed = false;
            }
            return app_installed;
        }
        public void showMessage(String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        public void ShareTextOnTwitter(String msg)
        {
            List<Intent> targetShareIntents=new ArrayList<Intent>();
            if(appInstalledOrNot("com.twitter.android"))// Check android app is installed or not
            {
                PackageManager packageManager= context.getPackageManager();// Create instance of PackageManager
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
//Get List of all activities
                List<ResolveInfo>resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);
                for (int j = 0; j <resolveInfoList.size(); j++)
                {
                    ResolveInfo resInfo = resolveInfoList.get(j);
                    String packageName = resInfo.activityInfo.packageName;
//Find twitter app from list
                    if(packageName.contains("com.twitter.android")){
                        Intent intent=new Intent();
                        intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));//Create Intent with twitter app package
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, msg);// Set message
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Text Sharing");//set subject
                        intent.setPackage(packageName);
                        targetShareIntents.add(intent);
                    }
                    if(!targetShareIntents.isEmpty()){
                        Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                        context.startActivity(chooserIntent);
                    }else{
                        System.out.println("Do not Have Intent");
                    }
                }
            }
            else
                showMessage("App Not Installed");
        }
        public void PostImageOnTwitter(String imageUri, String msg)
        {
            System.out.println("Cick on FB");
            List<Intent>targetShareIntents=new ArrayList<Intent>();
            if(appInstalledOrNot("com.twitter.android"))
            {
                System.out.println("App installed");
                PackageManager packageManager= context.getPackageManager();
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("image/*");
                List<ResolveInfo>resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);
                for (int j = 0; j <resolveInfoList.size(); j++)
                {
                    ResolveInfo resInfo = resolveInfoList.get(j);
                    String packageName = resInfo.activityInfo.packageName;
                    Log.i("Package Name", packageName);
                    if(packageName.contains("com.twitter.android")){
                        Intent intent=new Intent();
                        intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("image/*");//Set MIME Type
                        intent.putExtra(Intent.EXTRA_TEXT, msg);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Post Image");
                        Uri screenshotUri = Uri.parse(imageUri);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);// Pur Image to intent
                        intent.setPackage(packageName);
                        targetShareIntents.add(intent);
                    }
                    if(!targetShareIntents.isEmpty()){
                        System.out.println("Have Intent");
                        Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                        context.startActivity(chooserIntent);
                    }else{
                        System.out.println("Do not Have Intent");
                    }
                }
            }
            else
                showMessage("App Not Installed");
        }
    }


    }