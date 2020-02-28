package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class ScorerActivity extends AppCompatActivity implements Validator.ValidationListener {

    public static final String EXTRA_SCORER = "EXTRA_SCORER";

    private Validator validator;

    @NotEmpty
    private EditText edtScorrer;

    @NotEmpty
    @Pattern(regex = "[0-9]{2}", message = "Input Invalid")
    private EditText edtMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

        edtScorrer = findViewById(R.id.edt_scorer);
        edtMinute = findViewById(R.id.edt_minute);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public void handleSubmit(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String scorer = edtScorrer.getText().toString();
        int minute = Integer.parseInt(edtMinute.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCORER, new Score(scorer, minute));

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
