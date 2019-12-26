package mg.recharge;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import mg.recharge.helpers.CreditCodeHelper;
import mg.recharge.helpers.ErrorDialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends rootActivity {
    private final int PERMISSION_CODE = 1000;
    private final int IMAGE_GALLERY_CODE = 1001;
    private final int IMAGE_CAMERA_CODE = 1002;
    private final int SEND_CREDIT_CODE = 1003;
    private Toolbar toolbar;
    private CardView galleryCard;
    private CardView cameraCard;
    private CardView historyLocal;
    private ImageView imageView;
    private Uri imageUri;
    private CreditCodeHelper creditCodeHelper;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.requestPermission();
        this.imageView = new ImageView(this);
        this.galleryCard = findViewById(R.id.galleryCard);
        this.galleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        this.creditCodeHelper = new CreditCodeHelper();
        this.gson = new Gson();
        this.cameraCard = findViewById(R.id.cameraCard);
        this.cameraCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        this.historyLocal = findViewById(R.id.HistoryCard);
        this.historyLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HistoryActivity.class));
            }
        });
        this.toolbar = findViewById(R.id.app_main_toolbar);
        setSupportActionBar(this.toolbar);
    }

    private void openCamera() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());

        ContentValues newImage = new ContentValues();
        newImage.put(MediaStore.Images.Media.TITLE, "recharge-" + date);
        newImage.put(MediaStore.Images.Media.DESCRIPTION, "rechargeCard");
        this.imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, newImage);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri);
        startActivityForResult(cameraIntent, IMAGE_CAMERA_CODE);
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, IMAGE_GALLERY_CODE);
    }

    private void requestPermission() {
        int cameraCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int writeStorageCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int readStorageCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int callCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        int readPhoneStatCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int coarseLocationCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (
                (cameraCheck != PackageManager.PERMISSION_GRANTED) ||
                        (writeStorageCheck != PackageManager.PERMISSION_GRANTED) ||
                        (readStorageCheck != PackageManager.PERMISSION_GRANTED) ||
                        (callCheck != PackageManager.PERMISSION_GRANTED) ||
                        (readPhoneStatCheck != PackageManager.PERMISSION_GRANTED) ||
                        (coarseLocationCheck != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recharge_main_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_GALLERY_CODE:
                    CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this);
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    getCreditCode(resultCode, data);
                    break;
                case IMAGE_CAMERA_CODE:
                    CropImage.activity(this.imageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);
                    break;
                case SEND_CREDIT_CODE:
                    Toast.makeText(this,data.toString(),Toast.LENGTH_LONG).show();
                default:
                    // show global error
                    break;

            }
        }
    }

    private void getCreditCode(int resultCode, @Nullable Intent data) {
        if (data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                this.imageView.setImageURI(resultUri);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) this.imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (textRecognizer.isOperational()) {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock textBlock = items.get(i);
                        stringBuilder.append(textBlock.getValue());
                    }
                    this.creditCodeHelper.setCreditCode(stringBuilder.toString());
                    this.creditCodeHelper.formatCode();
                    if (this.creditCodeHelper.isValidCode()){
                        Log.e("code",this.creditCodeHelper.getCreditCode());
                        Intent intent = new Intent(MainActivity.this,CodeInfoActivity.class);
                        intent.putExtra("code", gson.toJson(this.creditCodeHelper));
                        startActivity(intent);
                    } else {
                        Log.e("code",this.creditCodeHelper.getCreditCode());
                    }
                } else {
                    ErrorDialog errorDialog = new ErrorDialog(this);
                    errorDialog.setMessage(R.string.globalError);
                    errorDialog.show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                ErrorDialog errorDialog = new ErrorDialog(this);
                errorDialog.setMessage(R.string.globalError);
                errorDialog.show();
            }
        } else {
            ErrorDialog errorDialog = new ErrorDialog(this);
            errorDialog.setMessage(R.string.globalError);
            errorDialog.show();
        }
    }
}
