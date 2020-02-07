package com.example.bua1sautet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonchonanh ,mButtonupload;
    private TextView mTextviewshowupload;
    private EditText mEditextFilename;
    private ImageView mImage;
    private ProgressBar mprogressbar;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        onclick();
    }

    private void onclick() {
        mButtonchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooseimage();

            }
        });

        mButtonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTextviewshowupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void openfilechooseimage() {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImage);
        }
    }

    private void Anhxa() {
        mButtonchonanh =findViewById(R.id.chonfileBtn);
        mButtonupload =findViewById(R.id.uploadBtn);
        mTextviewshowupload =findViewById(R.id.showimageTv);
        mEditextFilename =findViewById(R.id.textEdt);
        mImage =findViewById(R.id.imageImv);
        mprogressbar =findViewById(R.id.progress);
    }
}
