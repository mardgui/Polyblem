package fr.unice.polytech.polyblem.declaration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Issue;

/**
 * Created by Florian on 16/04/2018
 */

public class DeclarationActivity extends Activity implements View.OnClickListener  {

    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);
        Button send = findViewById(R.id.send);
        Button addPicture = findViewById(R.id.joinImage);
        addPicture.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joinImage:
                dispatchTakePictureIntent();
                break;
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i("DeclarationActivity", "Cannot create file");
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("in activity","a");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.i("ActivityResult", "hello");
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
