package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ScorerActivity extends AppCompatActivity {

    public static final String EXTRA_SCORER = "EXTRA_SCORER";

    private EditText edtScorrer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

        edtScorrer = findViewById(R.id.editText);
    }

    public void handleSubmit(View view) {
        String scorer = edtScorrer.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCORER, scorer);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
