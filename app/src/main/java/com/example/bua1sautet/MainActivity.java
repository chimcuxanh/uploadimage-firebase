package com.example.bua1sautet;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonchonanh ,mButtonupload;
    private TextView mTextviewshowupload;
    private EditText mEditextFilename;
    private ImageView mImage;
    private ProgressBar mprogressbar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDataRef;

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
                uploadfile();

            }
        });
        mTextviewshowupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private String getfileExtention(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadfile() {
        if(mImageUri != null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+
                    getfileExtention(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler =  new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mprogressbar.setProgress(0);

                        }
                    },5000);

                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/
                            taskSnapshot.getTotalByteCount());
                    mprogressbar.setProgress((int)progress);

                }
            });

        }
        else 
            {
                Toast.makeText(this, "NO FILE SELECTED", Toast.LENGTH_SHORT).show();
            }


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
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDataRef = FirebaseDatabase.getInstance().getReference("uploads");
    }
}
