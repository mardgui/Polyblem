package fr.unice.polytech.polyblem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.declaration.DeclarationActivity;
import fr.unice.polytech.polyblem.help.HelpFragment;
import fr.unice.polytech.polyblem.issueList.IssueGridFragment;

public class MainActivity extends AppCompatActivity {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private static final String BACK_STACK_HELP_TAG = "help_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        if(fab.getVisibility() == View.GONE){
            fab.setVisibility(View.VISIBLE);
        }
        fab.setBackgroundColor(0);
        fab.setImageResource(R.drawable.folder);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        DeclarationActivity.class);
                startActivity(myIntent);
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction()
               .replace(R.id.container, new IssueGridFragment())
               .commit();

        Database db = new Database(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            fragmentManager.popBackStack(BACK_STACK_HELP_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, new HelpFragment())
                    .addToBackStack(BACK_STACK_HELP_TAG)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
