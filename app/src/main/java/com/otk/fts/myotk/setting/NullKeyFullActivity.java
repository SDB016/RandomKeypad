package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.otk.fts.myotk.R;
import com.otk.fts.myotk.utils.PreferenceUtil;
import com.otk.fts.myotk.utils.QLog;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class NullKeyFullActivity extends Activity {

    private int btnType;
    private TextView btnNullAlbum;
    private ImageView imgNullAlbum;

    private String imgNullPath;

    private LinearLayout mlinearLayout;

    private static final int PICK_FROM_ALBUM = 0;
    private static final int CROP_FROM_ALBUM = 1;
    private File tempFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nullkeyfull);

        Button mBtnBack = (Button)findViewById(R.id.btn_back_nullkey);

        btnNullAlbum = (TextView)findViewById(R.id.txt_null_album);
        imgNullAlbum = (ImageView)findViewById(R.id.img_null_custom);
        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_nullkey);

        ImageButton btn_null_line = (ImageButton)findViewById(R.id.btn_null_line);
        ImageButton btn_null_trans = (ImageButton)findViewById(R.id.btn_null_trans);
        ImageButton btn_null_album = (ImageButton)findViewById(R.id.btn_null_album);


        TextView txt_null_line = (TextView)findViewById(R.id.txt_null_line);
        TextView txt_null_trans = (TextView)findViewById(R.id.txt_null_trans);

        btnType = PreferenceUtil.getIntPref(this, PreferenceUtil.BTN_TYPE, 2);
        imgNullPath = PreferenceUtil.getStringPref(this, PreferenceUtil.NULLICON_PATH);

        if(imgNullPath.length() != 0){
            Glide.with(this).load(imgNullPath).override(20,20).transform(new CropCircleTransformation()).into(imgNullAlbum);
        } else {
            //Glide.with(this).load(R.drawable.icon_null_album).override(20,20).transform(new CropCircleTransformation()).into(imgNullAlbum);
            imgNullAlbum.setBackgroundResource(R.drawable.icon_null_album);
        }

        switch(btnType){  // 0 -- gray // 1- trans // 2 - line // 3 - album // 4 - point
            case 0:
                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);

                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

                break;
            case 1:
                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLUE);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_on);


                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

                break;
            case 2:
                txt_null_line.setTextColor(Color.BLUE);
                btn_null_line.setBackgroundResource(R.drawable.onoff_on);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);


                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

                break;
            case 3:
                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);


                btnNullAlbum.setTextColor(Color.BLUE);
                btn_null_album.setBackgroundResource(R.drawable.onoff_on);

                break;
            case 4:
                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);


                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

        }


        mBtnBack.setOnClickListener(new View.OnClickListener(){
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

        btnNullAlbum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);

                btnNullAlbum.setTextColor(Color.BLUE);
                btn_null_album.setBackgroundResource(R.drawable.onoff_on);


                btnType = 3;

                makeDialog();

                ConfirmSettings();
            }
        });

        btn_null_line.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_null_line.setTextColor(Color.BLUE);
                btn_null_line.setBackgroundResource(R.drawable.onoff_on);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);

                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);
                imgNullPath = "";

                btnType = 2;
                ConfirmSettings();
            }
        });

        txt_null_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_null_line.setTextColor(Color.BLUE);
                btn_null_line.setBackgroundResource(R.drawable.onoff_on);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);

                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

                imgNullPath = "";
                btnType = 2;
                ConfirmSettings();
            }
        });

        btn_null_trans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLUE);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_on);

                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

                imgNullPath = "";
                btnType = 1;
                ConfirmSettings();
            }
        });

        txt_null_trans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLUE);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_on);

                btnNullAlbum.setTextColor(Color.BLACK);
                btn_null_album.setBackgroundResource(R.drawable.onoff_off);

                imgNullPath = "";
                btnType = 1;
                ConfirmSettings();
            }
        });


        btn_null_album.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_null_line.setTextColor(Color.BLACK);
                btn_null_line.setBackgroundResource(R.drawable.onoff_off);

                txt_null_trans.setTextColor(Color.BLACK);
                btn_null_trans.setBackgroundResource(R.drawable.onoff_off);

                btnNullAlbum.setTextColor(Color.BLUE);
                btn_null_album.setBackgroundResource(R.drawable.onoff_on);

                btnType = 3;

                makeDialog();
                ConfirmSettings();
            }
        });


    }

    void ConfirmSettings(){
        Log.d("Inhyo Test","ScreenLock - PreferenceUtil.BTN_TYPE : " + btnType);
        PreferenceUtil.savePref(this, PreferenceUtil.BTN_TYPE, btnType);
        PreferenceUtil.savePref(this, PreferenceUtil.NULLICON_PATH, imgNullPath);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            switch (requestCode) {
                case PICK_FROM_ALBUM: {
                    if (data!=null && data.getData() != null) {
                        Uri photoUri = data.getData();

                        if(photoUri!=null){

                            cropImageFromAlbum(photoUri);

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

            //btn_bg1.setChecked(true);
            //btn_bg2.setChecked(false);
        }

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
        //outputUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, inputUri);
        Intent intent = getCropIntent(inputUri, outputUri);
        imgNullPath = tempFile.getAbsolutePath();
        Log.d("Inhyo Test","경로 : " + imgNullPath);
        //customBgImg = true;
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
        Log.d("Inhyo Test","getCropIntent() : outputUri -" + outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        return intent;
    }

    private void setImage() {
        Log.d("Inhyo Test","setImage()");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap originalBm = BitmapFactory.decodeFile(imgNullPath, options);
        //customBgImg = true;
        Drawable drawable = new BitmapDrawable(scaleDown(originalBm, 2048, true));


        //  DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(bg_custom);
        //  Glide.with(this).load(R.drawable.).into(imageViewTarget);

        Glide.with(this).load(imgNullPath).transform(new CropCircleTransformation()).into(imgNullAlbum);

        ConfirmSettings();
        //img2.setImageBitmap(originalBm);

        //btn_bg1.setChecked(false);
        //btn_bg2.setChecked(true);
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
