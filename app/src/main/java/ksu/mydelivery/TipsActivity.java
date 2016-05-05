package ksu.mydelivery;

    import android.support.v7.app.ActionBarActivity;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.VideoView;
    import android.widget.MediaController;
    import android.util.Log;
    import android.media.MediaPlayer;

    public class TipsActivity extends ActionBarActivity {

        String TAG = "com.ebookfrenzy.videoplayer";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tips);

            final VideoView videoView =
                    (VideoView) findViewById(R.id.vvTips);

            videoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG, "Duration = " + videoView.getDuration());
                }
            });

            videoView.start();

        }

    }
