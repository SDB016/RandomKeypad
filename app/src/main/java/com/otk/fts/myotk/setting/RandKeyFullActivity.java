package com.otk.fts.myotk.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
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

public class RandKeyFullActivity extends Activity {

    private int numType;

    private int width;
    private int height;
    private float ratio;

    private String imgRandomPath;

    private TextView btnRandomAlbum;
    private ImageView imgRandomAlbum;

    private static final int PICK_FROM_ALBUM = 0;
    private static final int CROP_FROM_ALBUM = 1;
    private File tempFile;

    private LinearLayout mlinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randkeyfull);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        ratio = width / height;

        Log.d("Inhyo Test","width : " + width + "  height : " + height );

        Button mBtnBack = (Button)findViewById(R.id.btn_back_rdkey);
        imgRandomAlbum = (ImageView)findViewById(R.id.img_random_album);

        ImageButton btn_random_line = (ImageButton)findViewById(R.id.btn_random_line);
        ImageButton btn_random_trans = (ImageButton)findViewById(R.id.btn_random_trans);
        ImageButton btn_random_album = (ImageButton)findViewById(R.id.btn_random_album);

        TextView txt_random_line = (TextView)findViewById(R.id.txt_random_line);
        TextView txt_random_trans = (TextView)findViewById(R.id.txt_random_trans);

        btnRandomAlbum = (TextView) findViewById(R.id.btn_random_album_txt);

        mlinearLayout = (LinearLayout)findViewById(R.id.linear_title_randkeyfull);


        if(ratio < 0.5) {
            //imgRandomLine.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.3f));
        }

        numType = PreferenceUtil.getIntPref(this, PreferenceUtil.NUM_TYPE, 2); //sf.getInt("numType", 3);
        imgRandomPath = PreferenceUtil.getStringPref(this, PreferenceUtil.RANDOMICON_PATH); //sf.getInt("numType", 3);

        if(imgRandomPath.length() != 0){
            Glide.with(this).load(imgRandomPath).override(20,20).transform(new CropCircleTransformation()).into(imgRandomAlbum);
        } else {
            //Glide.with(this).load(R.drawable.icon_null_album).override(20,20).transform(new CropCircleTransformation()).into(imgRandomAlbum);
            imgRandomAlbum.setBackgroundResource(R.drawable.icon_null_album);
        }

        switch(numType) {
            case 0:
                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);

                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 1:
                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLUE);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_on);

                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 2:
                txt_random_line.setTextColor(Color.BLUE);
                btn_random_line.setBackgroundResource(R.drawable.onoff_on);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);

                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                break;
            case 3:
                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);

                btnRandomAlbum.setTextColor(Color.BLUE);
                btn_random_album.setBackgroundResource(R.drawable.onoff_on);
                break;
        }


        mlinearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        mBtnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(),"Back 버튼이 눌렸어요", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        btn_random_line.setOnClickListener(new View.OnClickListener(){ //  Line
            @Override
            public void onClick(View view){

                txt_random_line.setTextColor(Color.BLUE);
                btn_random_line.setBackgroundResource(R.drawable.onoff_on);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);


                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                imgRandomPath = "";
                numType = 2;
                ConfirmSettings();
            }
        });

        txt_random_line.setOnClickListener(new View.OnClickListener(){ //  Line
            @Override
            public void onClick(View view){

                txt_random_line.setTextColor(Color.BLUE);
                btn_random_line.setBackgroundResource(R.drawable.onoff_on);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);


                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                imgRandomPath = "";
                numType = 2;
                ConfirmSettings();
            }
        });

        btn_random_trans.setOnClickListener(new View.OnClickListener(){  // 투명
            @Override
            public void onClick(View view){

                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLUE);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_on);
;

                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                imgRandomPath = "";
                numType = 1;
                ConfirmSettings();
            }
        });

        txt_random_trans.setOnClickListener(new View.OnClickListener(){  // 투명
            @Override
            public void onClick(View view){

                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLUE);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_on);

                btnRandomAlbum.setTextColor(Color.BLACK);
                btn_random_album.setBackgroundResource(R.drawable.onoff_off);
                imgRandomPath = "";
                numType = 1;
                ConfirmSettings();
            }
        });



        btn_random_album.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);

                btnRandomAlbum.setTextColor(Color.BLUE);
                btn_random_album.setBackgroundResource(R.drawable.onoff_on);

                numType = 3;
                makeDialog();
                ConfirmSettings();
            }
        });

        btnRandomAlbum.setOnClickListener(new View.OnClickListener(){  // 투명
            @Override
            public void onClick(View view){

                txt_random_line.setTextColor(Color.BLACK);
                btn_random_line.setBackgroundResource(R.drawable.onoff_off);

                txt_random_trans.setTextColor(Color.BLACK);
                btn_random_trans.setBackgroundResource(R.drawable.onoff_off);

                btnRandomAlbum.setTextColor(Color.BLUE);
                btn_random_album.setBackgroundResource(R.drawable.onoff_on);

                numType = 3;
                makeDialog();

                ConfirmSettings();
            }
        });

    }

    void ConfirmSettings(){
        Log.d("Inhyo Test","R2NiconPosition - PreferenceUtil.NUM_TYPE : " + numType);
        PreferenceUtil.savePref(this, PreferenceUtil.NUM_TYPE, numType);
        PreferenceUtil.savePref(this, PreferenceUtil.RANDOMICON_PATH, imgRandomPath);
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

        Intent intent = getCropIntent(inputUri, outputUri);
        imgRandomPath = tempFile.getAbsolutePath();
        Log.d("Inhyo Test","경로 : " + imgRandomPath);
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
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        return intent;
    }

    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap originalBm = BitmapFactory.decodeFile(imgRandomPath, options);
        //customBgImg = true;
        Drawable drawable = new BitmapDrawable(scaleDown(originalBm, 2048, true));


        //  DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(bg_custom);
        //  Glide.with(this).load(R.drawable.).into(imageViewTarget);
        Log.d("Inhyo Test","imgRandomPath - " + imgRandomPath);
        Glide.with(this).load(imgRandomPath).transform(new CropCircleTransformation()).into(imgRandomAlbum);
       // Glide.with(this).load(imgRandomPath).override(214, 214).transform(new CropCircleTransformation()).into(imgBtnView09);
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
