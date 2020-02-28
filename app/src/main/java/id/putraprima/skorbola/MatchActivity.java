package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    public static String EXTRA_DATA = "EXTRA_DATA";
    public static String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";

    private static final int REQUEST_CODE_HOME_SCORER = 101;
    private static final int REQUEST_CODE_AWAY_SCORER = 102;

    private List<Score> homeScorers = new ArrayList<>();
    private List<Score> awayScorers = new ArrayList<>();

    private Match match;
    private TextView tvHomeTeam;
    private TextView tvAwayTeam;
    private TextView homeScorer;
    private TextView awayScorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent extras = getIntent();
        match = extras.getParcelableExtra(EXTRA_DATA);

        TextView homeName = findViewById(R.id.txt_home);
        TextView awayName = findViewById(R.id.txt_away);
        ImageView homeLogo = findViewById(R.id.home_logo);
        ImageView awayLogo = findViewById(R.id.away_logo);
        tvHomeTeam = findViewById(R.id.score_home);
        tvAwayTeam = findViewById(R.id.score_away);
        homeScorer = findViewById(R.id.home_scorers);
        awayScorer = findViewById(R.id.away_scorer);

        // 1.Menampilkan detail match sesuai data dari main activity
        homeName.setText(match.getHomeTeam());
        awayName.setText(match.getAwayTeam());
        if (match.getHomeLogo() != null) setImage(homeLogo, match.getHomeLogo());
        if (match.getAwayLogo() != null) setImage(awayLogo, match.getAwayLogo());
    }

    private void setImage(ImageView view, Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            view.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void homeScoreHandler(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_HOME_SCORER);
    }

    public void awayScoreHandler(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_AWAY_SCORER);
    }

    public void CheckResult(View view) {
        String result = (homeScorers.size() == awayScorers.size()) ? " Imbang ":
                (homeScorers.size() > awayScorers.size()) ? match.getHomeTeam()+" Menang" : match.getAwayTeam() + " Menang";

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_RESULT_MATCH, result);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        Score scorer;
        StringBuilder tempScorers = new StringBuilder();
        switch (requestCode) {
            case REQUEST_CODE_HOME_SCORER:
                scorer = data.getParcelableExtra(ScorerActivity.EXTRA_SCORER);

                homeScorers.add(scorer);
                for (Score s : homeScorers) {
                    String temp = s.getName() + " " + s.getMinute() + "\"";
                    tempScorers.append("\n" + temp);
                }

                tvHomeTeam.setText(String.valueOf(homeScorers.size()));
                homeScorer.setText(tempScorers);
                break;

            case REQUEST_CODE_AWAY_SCORER:
                scorer = data.getParcelableExtra(ScorerActivity.EXTRA_SCORER);

                awayScorers.add(scorer);
                for (Score s : awayScorers) {
                    String temp = s.getName() + " " + s.getMinute() + "\"";
                    tempScorers.append("\n" + temp);
                }

                tvAwayTeam.setText(String.valueOf(awayScorers.size()));
                awayScorer.setText(tempScorers);
                break;
        }
    }
}
