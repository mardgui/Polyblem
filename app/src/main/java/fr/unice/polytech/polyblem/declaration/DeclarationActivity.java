package fr.unice.polytech.polyblem.declaration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Issue;

/**
 * Created by Florian on 16/04/2018
 */

public class DeclarationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);
        Button send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Issue issue = createIssue();
                new Database(getApplicationContext()).addIssue(issue);
                Log.i("DeclarationActivity", issue.toString());
                finish();
            }
        });
    }

    private Issue createIssue() {
        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        Spinner category = findViewById(R.id.category);
        Spinner location = findViewById(R.id.location);
        EditText locationDetail = findViewById(R.id.locationDetail);
        SeekBar urgencyValue = findViewById(R.id.urgencyValue);

        String issueTitle = "";
        String issueDescription = "";
        String issueCategory = "";
        String issueLocation = "";
        String issueLocationDetail = "";
        String issueUrgencyValue;

        if (title.getText() != null) {
            issueTitle = title.getText().toString();
        }

        if (description.getText() != null) {
            issueDescription = description.getText().toString();
        }

        if (category.getSelectedItem() != null) {
            issueCategory = category.getSelectedItem().toString();
        }

        if (location.getSelectedItem() != null) {
            issueLocation = location.getSelectedItem().toString();
        }

        if (locationDetail.getText() != null) {
            issueLocationDetail = locationDetail.getText().toString();
        }

        issueUrgencyValue = Integer.toString(urgencyValue.getProgress());

        return new Issue("Manque",
                issueTitle,
                issueDescription,
                "",
                issueLocation,
                issueLocationDetail,
                "Faible",
                "email",
                "date");
    }
}
