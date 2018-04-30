package fr.unice.polytech.polyblem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Issue;

/**
 * Created by user on 17/04/2018.
 */

public class IssueActivity extends Activity{

    String idIssue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_container);
        Intent seeActivity = getIntent();
        idIssue = seeActivity.getStringExtra("IdIncident");
        Database db = new Database(this.getApplicationContext());
        Issue issue = db.getIssue(idIssue);
    }


}
