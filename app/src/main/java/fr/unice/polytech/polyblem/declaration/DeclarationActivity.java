package fr.unice.polytech.polyblem.declaration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.unice.polytech.polyblem.R;
import fr.unice.polytech.polyblem.bdd.Database;
import fr.unice.polytech.polyblem.model.Category;
import fr.unice.polytech.polyblem.model.Issue;
import fr.unice.polytech.polyblem.model.Photo;

/**
 * Created by Florian on 16/04/2018
 */

public class DeclarationActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_TAKE_PHOTO = 1;

    private List<Photo> photoList = new ArrayList<>();
    private String mCurrentPhotoPath;
    private Spinner categorySpinner;
    private List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);
        Button addPicture = findViewById(R.id.join_image);
        addPicture.setOnClickListener(this);

        findViewById(R.id.title_horizontalScrollView).setVisibility(View.INVISIBLE);
        ImageView noImage = findViewById(R.id.noImageAdded);
        noImage.setImageResource(R.drawable.noimage);

        setSendButton();
        setCategoriesSpinner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_image:
                dispatchTakePictureIntent();
                break;
            default:
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
                photoList.add(new Photo(photoFile.getAbsolutePath()));
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

        super.onActivityResult(requestCode, resultCode, data);

        //Image created !
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setAdapater();
        }
    }

    private void setAdapater() {
        HorizontalScrollView horizontalScrollView = findViewById(R.id.title_horizontalScrollView);
        if (horizontalScrollView.getVisibility() == View.INVISIBLE) {
            findViewById(R.id.title_horizontalScrollView).setVisibility(View.VISIBLE);
            findViewById(R.id.noImageAdded).setVisibility(View.INVISIBLE);
        }

        final ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getApplicationContext(), photoList);
        final GridView gridView = findViewById(R.id.grid_picture);

        int size = photoList.size();
        // Calculated single Item Layout Width for each grid element ....
        int width = 80;

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        int totalWidth = (int) (width * size * density);
        int singleItemWidth = (int) (width * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(totalWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        gridView.setLayoutParams(params);
        gridView.setColumnWidth(singleItemWidth);
        gridView.setHorizontalSpacing(50);
        gridView.setStretchMode(GridView.STRETCH_SPACING);
        gridView.setNumColumns(photoList.size());

        gridView.setAdapter(imageGridAdapter);
    }

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

        String date = new SimpleDateFormat("dd/MM/yy", Locale.FRENCH).format(Calendar.getInstance().getTime());

        return new Issue(issueTitle,
                "Manque",
                issueDescription,
                issueLocation,
                issueLocationDetail,
                "Faible",
                "email",
                date);
    }

    private void setSendButton() {
        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Issue issue = createIssue();
                Database database = new Database(getApplicationContext());
                long id = database.addIssue(issue);
                Log.i("DeclarationActivity", issue.toString());
                for (int i = 0; i < photoList.size(); i++) {
                    database.addPicture(id, photoList.get(i).getUrl());
                }
                finish();
            }
        });
    }

    private void setCategoriesSpinner() {
        categories = new ArrayList<>();
        for (Category category : Category.values()) {
            categories.add(category.getName());
        }

        categorySpinner = findViewById(R.id.category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DeclarationActivity.this,
                android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
