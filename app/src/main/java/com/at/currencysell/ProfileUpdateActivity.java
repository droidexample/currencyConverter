package com.at.currencysell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.at.currencysell.adapter.PlaceAutocompleteAdapter;
import com.at.currencysell.utils.AlbumStorageDirFactory;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.BaseAlbumDirFactory;
import com.at.currencysell.utils.BaseUrl;
import com.at.currencysell.utils.BitmapUtils;
import com.at.currencysell.utils.BusyDialog;
import com.at.currencysell.utils.FroyoAlbumDirFactory;
import com.at.currencysell.utils.MultipartUtility;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.PersistentUser;
import com.at.currencysell.utils.StorageUtils;
import com.at.currencysell.utils.WebUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private LinearLayout ll_back_sign_up;
    private Context mContext;
    private Activity mActivity;

    private ImageView addPhoto;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PICTURE = 2;
    private String Apath = "1";
    private File file_image = null;
    private static String JPEG_FILE_PREFIX = "";
    private static final String JPEG_FILE_SUFFIX = ".png";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private EditText et_first_name;
    private EditText et_last_name;
    private EditText et_email;
    private AutoCompleteTextView insert_location;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private double latitude = 0;
    private double longitude = 0;
    private String city = "";
    private String address = "";
    private String country = "";

    private String firstname;
    private String lastname;
    private String email;
    private String user_id;

    private LinearLayout ll_update;
    BusyDialog mBusyDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        mContext = this;
        mActivity = this;

        initUI();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        if (!StorageUtils.isSDCardPresent()) {
            AlertMessage.showMessage(mContext, "Low memory size", "Your mobile have low memory.Please increase some .");
            return;
        }
        if (!StorageUtils.isSdCardWrittenable()) {
            AlertMessage.showMessage(mContext, "Low memory size", "Your mobile have low memory.Please increase some .");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    private void initUI(){
        ll_back_sign_up = (LinearLayout)this.findViewById(R.id.ll_back_sign_up);
        ll_back_sign_up.setOnClickListener(listener);
        addPhoto = (ImageView) findViewById(R.id.add_photo);
        addPhoto.setOnClickListener(listener);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_email = (EditText) findViewById(R.id.et_email);
        insert_location = (AutoCompleteTextView) findViewById(R.id.insert_location);
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API)
                .build();

        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, null);
        insert_location.setAdapter(mAdapter);
        insert_location.setOnItemClickListener(mAutocompleteClickListener);

        ll_update = (LinearLayout)this.findViewById(R.id.ll_update);
        ll_update.setOnClickListener(listener);

        user_id = PersistentUser.getUserID(mContext);

        try {
            JSONObject JsonUser = new JSONObject(PersistentUser.getUSERDATA(mContext));
            et_first_name.setText("" + JsonUser.getString("first_name"));
            et_last_name.setText("" + JsonUser.getString("last_name"));
            et_email.setText("" + JsonUser.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.ll_back_sign_up:
                    ProfileUpdateActivity.this.finish();
                    break;
                case R.id.add_photo:
                    alertshow();
                    break;
                case R.id.ll_update:
                    updateProfile();
                    break;

            }

        }
    };

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);


            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(@NonNull PlaceBuffer places) {
                    if(places.getCount()==1){
                        //Do the things here on Click.....
                        latitude = places.get(0).getLatLng().latitude;
                        longitude = places.get(0).getLatLng().longitude;
                        city = (String) places.get(0).getName();
                        try {
                            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            if (addresses != null && addresses.size() > 0) {

                                 address = addresses.get(0).getAddressLine(0);
                                 country = addresses.get(0).getCountryName();
                                Toast.makeText(getApplicationContext(),address+country,Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else {
                        Toast.makeText(getApplicationContext(),"SOMETHING_WENT_WRONG,TRY AGAIN",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    };

    public void updateProfile() {


        firstname = et_first_name.getText().toString();
        lastname = et_last_name.getText().toString();
        email = et_email.getText().toString();


        if (Apath.equalsIgnoreCase("1")) {
            Toast.makeText(mContext, "Please  select an image", Toast.LENGTH_LONG).show();
            return;
        } else if (firstname.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please Enter Your First Name", Toast.LENGTH_LONG).show();
            return;
        } else if (lastname.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please Enter Your Last Name", Toast.LENGTH_LONG).show();
            return;
        }  else {

            new UploadFileToServer().execute();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    private class UploadFileToServer extends AsyncTask<Object, String, Object> {

        String response = "";


        @Override
        protected void onPreExecute() {
            // setting progress bar to zero


            if (!NetInfo.isOnline(mContext)) {
                AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
                return;
            }

            mBusyDialog = new BusyDialog(mContext, true, "Loading");
            mBusyDialog.show();

            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            try {

                String ulr = BaseUrl.HttpUrl + "update-profile";
                MultipartUtility body = new MultipartUtility(ulr);
                body.addFormField("api_key", BaseUrl.Api_key);
                body.addFormField("user_id",user_id);
                body.addFormField("first_name", "" + firstname);
                body.addFormField("last_name", "" + lastname);
                body.addFormField("email", "" + email);
                body.addFormField("lat", "" + latitude);
                body.addFormField("long", "" + longitude);
                body.addFormField("country", "" + country);
                body.addFormField("address", "" + address);
                body.addFilePart("picture", Apath);

                response = body.finish();


                mBusyDialog.dismis();
            } catch (Exception e) {
                Log.w("Exception", e.getMessage());
                // TODO Auto-generated catch block
                mBusyDialog.dismis();

                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            mBusyDialog.dismis();

            Log.w("response", "are" + response);

            try {
                JSONObject JSONresponse = new JSONObject(response);

                int success = JSONresponse.getInt("success");
                if (success == 1) {
                   /* JSONObject userData = JSONresponse.getJSONObject("result");
                    PersistentUser.setUSERNAME(mContext, userData.getString("first_name"));
                    PersistentUser.setUserEmail(mContext, userData.getString("email"));
                    PersistentUser.setUSERPIC(mContext, userData.getString("picture"));
                    PersistentUser.setUserID(mContext, userData.getString("user_id"));*/
                    PersistentUser.setLogin(mContext);

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    ProfileUpdateActivity.this.finish();

                    Toast.makeText(mContext, "" + JSONresponse.getString("message"), Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(mContext, "" + JSONresponse.getString("message"), Toast.LENGTH_LONG).show();

                }

            } catch (JSONException sd) {
                Toast.makeText(mContext, sd.toString(), Toast.LENGTH_LONG).show();
            }


        }

    }



    public void alertshow() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Choose Option").setCancelable(true).setPositiveButton("Take a photo",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Camera();

                    }

                }).setNegativeButton("From Gallery",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        Gallery();

                    }

                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void Gallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void Camera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                file_image = createImageFile();
            } catch (IOException ex) {

            }
            if (file_image != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file_image));
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }

    }

    private File createImageFile() throws IOException {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        final String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        final File albumF = getAlbumDir();
        final File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.e("directory", "failed to create directory");
                        return null;
                    }
                }
            }
        } else {
            Log.e(getString(R.string.app_name),
                    "External storage is not mounted READ/WRITE.");
        }
        return storageDir;
    }

    private String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Apath = file_image.getAbsolutePath();
                    Log.w("ApathA", "" + Apath);
                    File file_image = new File(Apath);
                    final Uri selectedImageUr = Uri.fromFile(file_image);
                    setPrepareImageData(selectedImageUr);
                }
                break;
            case RESULT_LOAD_IMAGE:

                if (resultCode == Activity.RESULT_OK && null != data) {
                    Uri selectedImageUri = data.getData();
                    File file = new File(getRealPathFromURI(selectedImageUri));
                    Apath = file.getAbsolutePath();
                    Log.w("ApathB", "" + Apath);
                    setPrepareImageData(selectedImageUri);
                    break;

                }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    private byte photoFileData[] = new byte[0];
    public Bitmap bit;

    private void setPrepareImageData(Uri selectedImage) {
        // TODO Auto-generated method stub
        try {

            final InputStream is = mActivity.getContentResolver().openInputStream(selectedImage);
            try {
                if (is.available() > 0) {
                    photoFileData = new byte[is.available()];
                    is.read(photoFileData, 0, is.available());
                    is.close();
                    bit = BitmapUtils.getBitmapFromByteArray(photoFileData, 512);
                    addPhoto.setImageBitmap(bit);
                }

            } catch (final IOException e) {


            }

        } catch (final FileNotFoundException e) {

            Log.w("FileNotFoundException", "are" + e.getMessage());

            e.printStackTrace();
        } catch (final Exception e) {
            Log.w("Exception", "are" + e.getMessage());
        }
    }
}
