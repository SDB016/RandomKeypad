package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

public class BackgroundActivity extends Activity {

    private Button mBackbtn;
    private ImageButton btnBasicOn;
    private ImageButton btnPhotoOn;
    private ImageButton btnBasic01;
    private ImageButton btnBasic02;
    private ImageButton btnBasic03;
    private ImageButton btnBasic04;
    private ImageButton btnBasic05;
    private ImageButton btnPhoto01;
    private ImageButton btnDefaultImg;

    private String bgPhotoPath01;

    private ImageView imgBasic01BG;
    private ImageView imgBasic02BG;
    private ImageView imgBasic03BG;
    private ImageView imgBasic04BG;
    private ImageView imgBasic05BG;
    private ImageView imgPhoto01BG;
    private ImageView imgDefaultBG;

    private boolean isImgCrop01 = false;

    private LinearLayout mlinearLayout;

    private int basicBackground;
    private boolean customBGImg;
    private int backgroundType;
    //private int backgroundSelect;

    private File tempFile;

    private boolean customBgImg;
    private String bgImgFilePath;

    private static final int PICK_FROM_ALBUM = 0;
    private static final int CROP_FROM_ALBUM = 1;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        handler = new Handler(message -> false);

        mBackbtn = (Button)findViewById(R.id.btn_back_bg);

        btnBasicOn = (ImageButton) findViewById(R.id.btn_basic_on);
        btnPhotoOn = (ImageButton) findViewById(R.id.btn_photo_on);
        mlinearLayout = (LinearLayout)findViewById(R.id.linearlayout_title);

        //btnBasic01 = (ImageButton) findViewById(R.id.btn_basic1_bg);
        btnBasic02 = (ImageButton) findViewById(R.id.btn_basic1_bg);
        btnBasic03 = (ImageButton) findViewById(R.id.btn_basic3_bg);
        btnBasic04 = (ImageButton) findViewById(R.id.btn_basic4_bg);
        btnBasic05 = (ImageButton) findViewById(R.id.btn_basic5_bg);


        //imgBasic01BG = (ImageView) findViewById(R.id.img_basic1_bg);
        imgBasic02BG = (ImageView) findViewById(R.id.img_basic1_bg);
        imgBasic03BG = (ImageView) findViewById(R.id.img_basic3_bg);
        imgBasic04BG = (ImageView) findViewById(R.id.img_basic4_bg);
        imgBasic05BG = (ImageView) findViewById(R.id.img_basic5_bg);

        btnBasic01 = (ImageButton) findViewById(R.id.btn_default_bg);
        imgBasic01BG = (ImageView) findViewById(R.id.img_default_bg);

        btnPhoto01 = (ImageButton) findViewById(R.id.btn_photo1_bg);
        imgPhoto01BG = (ImageView) findViewById(R.id.img_photo1_bg);

        {
            customBGImg = PreferenceUtil.getBooleanPref(this, PreferenceUtil.CUSTOM_BG, false);  // false - color // true - photo
            basicBackground = PreferenceUtil.getIntPref(this, PreferenceUtil.BASIC_BACKGROUND, 0);
            //backgroundType = PreferenceUtil.getIntPref(this, PreferenceUtil.BACKGROUND_TYPE, 0);  // 0 - default // 1- photo
            //backgroundSelect = PreferenceUtil.getIntPref(this, PreferenceUtil.BACKGROUND_SELECT, 0); // 0 - default 0 // 1 -  photo 1
            bgImgFilePath = PreferenceUtil.getStringPref(this, PreferenceUtil.CUSTOM_BG_PATH);  // photo path

            if(bgImgFilePath.length() != 0){
                isImgCrop01 = true;
                Glide.with(this).load(bgImgFilePath).override(250, 650).into(btnPhoto01);
            } else {
                isImgCrop01 = false;
            }


            initBtnClick(basicBackground);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            customBgImg = true;

            chk_sideBtn(customBGImg);
            ConfirmSettings();


        }

        mBackbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mlinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnBasicOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                customBGImg = false;
                chk_sideBtn(customBGImg);
                backgroundType = 0;
                customBGImg = false;
                ConfirmSettings();
            }
        });

        btnPhotoOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                customBGImg = true;
                chk_sideBtn(customBGImg);
                backgroundType = 1;
                customBGImg = true;
                ConfirmSettings();
            }
        });


        btnBasic01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //chk_basicbtn(0);
                imgBasic01BG.setBackgroundResource(R.color.colorRed);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                basicBackground = 0;
                ConfirmSettings();
            }
        });

        btnBasic02.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorRed);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                basicBackground = 1;
                ConfirmSettings();
            }
        });

        btnBasic03.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorRed);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                basicBackground = 2;
                ConfirmSettings();
            }
        });

        btnBasic04.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorRed);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                basicBackground = 3;
                ConfirmSettings();
            }
        });

        btnBasic05.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorRed);
                basicBackground = 4;
                ConfirmSettings();
            }
        });

        // photo
/*
        btnDefaultImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                backgroundSelect = 0;
                ConfirmSettings();

                //makeDialog();
            }
        });*/


        btnPhoto01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //backgroundSelect = 1;
                makeDialog();
            }
        });

    }

    private void initBtnClick(int selecBack){

        switch (selecBack) {  // 0 : Basic // 1 : Photo
            case 0:
                imgBasic01BG.setBackgroundResource(R.color.colorRed);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                break;
            case 1:
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorRed);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                break;
            case 2:
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorRed);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                break;
            case 3:
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorRed);
                imgBasic05BG.setBackgroundResource(R.color.colorWhite);
                break;
            case 4:
                imgBasic01BG.setBackgroundResource(R.color.colorWhite);
                imgBasic02BG.setBackgroundResource(R.color.colorWhite);
                imgBasic03BG.setBackgroundResource(R.color.colorWhite);
                imgBasic04BG.setBackgroundResource(R.color.colorWhite);
                imgBasic05BG.setBackgroundResource(R.color.colorRed);
                break;
        }

    }

    private void makeDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle(R.string.setting_dialog_album_title).setMessage(R.string.setting_dialog_album_msg)
                .setIcon(R.drawable.album64)
                .setPositiveButton(R.string.setting_dialog_album_posi_bt,
                        (dialogInterface, id) -> {
                            //Log.v("알림", "다이얼로그 > 앨범선택 선택");
                            //앨범에서 선택
                            selectAlbum(1);
                            //Intent intent = new Intent("com.android.camera.action.CROP");
                            //startService(intent);
                            // 인텐트+뷰 넘기기
                        }).setNegativeButton(R.string.cancel,
                (dialog, id) -> {
                    //Log.v("알림", "다이얼로그 > 취소 선택");
                    // 취소 클릭. dialog 닫기.
                    //btn_bg1.performClick();
                    dialog.cancel();
                });
        AlertDialog alert = alt_bld.create();
        alt_bld.setCancelable(false);
        alert.show();
    }

    //앨범 선택 클릭
    public void selectAlbum(int i){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void chk_sideBtn(boolean index){
        if(!index) {
                btnBasicOn.setBackgroundResource(R.drawable.onoff_on);
                btnPhotoOn.setBackgroundResource(R.drawable.onoff_off);

                enableBasicBtn(true);
                enablePhotoBtn(false);

        }else{
                btnBasicOn.setBackgroundResource(R.drawable.onoff_off);
                btnPhotoOn.setBackgroundResource(R.drawable.onoff_on);
                enableBasicBtn(false);
                enablePhotoBtn(true);

        }
    }

    public void enablePhotoBtn(boolean value){
        btnPhoto01.setEnabled(value);

        if(value) {
            imgPhoto01BG.setBackgroundResource(R.color.colorRed);
            if(!isImgCrop01){
                //backgroundSelect = 1;
                makeDialog();
            }

        } else {
            imgPhoto01BG.setBackgroundResource(R.color.colorWhite);

        }
    }

    public void enableBasicBtn(boolean value){
        btnBasic01.setEnabled(value);
        btnBasic02.setEnabled(value);
        btnBasic03.setEnabled(value);
        btnBasic04.setEnabled(value);
        btnBasic05.setEnabled(value);
        //backgroundSelect = 0;
        if(value){
            initBtnClick(basicBackground);

        } else {
           // imgBasic01BG.setImageResource(R.color.colorWhite);
            imgBasic01BG.setBackgroundResource(R.color.colorWhite);
            imgBasic02BG.setBackgroundResource(R.color.colorWhite);
            imgBasic03BG.setBackgroundResource(R.color.colorWhite);
            imgBasic04BG.setBackgroundResource(R.color.colorWhite);
            imgBasic05BG.setBackgroundResource(R.color.colorWhite);
          //  imgBasic02BG.setImageResource(R.color.colorWhite);
        }
        ConfirmSettings();
    }

    void ConfirmSettings(){

        PreferenceUtil.savePref(this, PreferenceUtil.BASIC_BACKGROUND, basicBackground); // 기본화면 중 선택
        PreferenceUtil.savePref(this, PreferenceUtil.CUSTOM_BG, customBGImg);  /// false - color // true - photo
        //PreferenceUtil.savePref(this, PreferenceUtil.BACKGROUND_SELECT, backgroundSelect); // 0 - default image // 1 - photo
        PreferenceUtil.savePref(this, PreferenceUtil.CUSTOM_BG_PATH, bgImgFilePath); // Image Path

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        //finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            switch (requestCode) {
                case PICK_FROM_ALBUM: {
                    if (data!=null && data.getData() != null) {
                        Uri photoUri = data.getData();

                        if(photoUri!=null){
                            if(backgroundType != 2){
                                cropImageFromAlbum(photoUri);
                            } else{
                                gifImageFilePath(photoUri);
                                cropImageFromAlbum(photoUri);
                            }

                        }
                    }
                    else{
                        //btn_bg1.setChecked(true);
                        //btn_bg2.setChecked(false);
                    }
                    break;
                }
                case Crop.REQUEST_CROP: {
                    //setImage();
                    //Toast.makeText(this, "크롭 리퀘스트", Toast.LENGTH_SHORT).show();
                }
                case CROP_FROM_ALBUM:{
                    setImage();
                }
            }
        }

        else{
            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }
        }

    }


    private void gifImageFilePath(Uri inputUri){

        Cursor cursor = getContentResolver().query(inputUri, null, null, null, null);

        cursor.moveToNext();

        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();

    }

    private void cropImageFromAlbum(Uri inputUri){
        Log.d("Inhyo Test","cropImageFromAlbum()");
        Uri outputUri = null;
        // File 객체의 URI 를 얻는다.
        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            //Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        outputUri = Uri.fromFile(tempFile);

        Intent intent = getCropIntent(inputUri, outputUri);
        bgImgFilePath = tempFile.getAbsolutePath();
        Log.d("Inhyo Test","경로 : " + bgImgFilePath);
        customBgImg = true;
        startActivityForResult(intent, CROP_FROM_ALBUM);
        //setImage();
    }

    private Intent getCropIntent(Uri inputUri, Uri outputUri){
        Log.d("Inhyo Test","getCropIntent()");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "200");
        intent.putExtra("outputY", "200");
        intent.putExtra("scale", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        return intent;
    }

    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap originalBm = BitmapFactory.decodeFile(bgImgFilePath, options);
        customBgImg = true;
        Drawable drawable = new BitmapDrawable(scaleDown(originalBm, 2048, true));
        if(backgroundType != 2) {

        } else {

          //  DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(bg_custom);
          //  Glide.with(this).load(R.drawable.).into(imageViewTarget);

        }

        //bgPhotoPath01 = bgImgFilePath;
        //imgPhoto01BG.setBackgroundResource(R.color.colorRed);
        //btnPhoto01.setBackgroundResource(bgImgFilePath);
        //btnPhoto01.setBackgroundResource(drawable);
        //btnPhoto01.setBackground(drawable);

        Glide.with(this).load(drawable).override(250, 650).into(btnPhoto01);
        isImgCrop01 = true;


        ConfirmSettings();

    }

    public Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                            boolean filter) {

        try{
            float ratio = Math.min(
                    (float) maxImageSize / realImage.getWidth(),
                    (float) maxImageSize / realImage.getHeight());
            int width = Math.round((float) ratio * realImage.getWidth());
            Log.d("Inhyo Test","scaleDown() - " + width);
            int height = Math.round((float) ratio * realImage.getHeight());
            Log.d("Inhyo Test","scaleDown() - " + height);
            return Bitmap.createScaledBitmap(realImage, width, height, filter);

        }catch (Exception e){
            QLog.e(e.toString());

        }

        return null;
    }

    private void cropImage(Uri photoUri) {
        /**
         *  갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }
        //크롭 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);
        Crop.of(photoUri, savingUri).asSquare().start(this);
    }

    public File createImageFile() throws IOException{
        Log.d("Inhyo Test","createImageFile()");
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile= null;

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);

        //mCurrentBtnPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }


}
