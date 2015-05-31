package exam.guoqian.me.exam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Interpolator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;

/**
 * Created by gq on 2015/5/30.
 */
public class Fragment3 extends BaseFragment {

//    public static final int TAKE_PHOTO = 1;
//    public static final int CROP_PHOTO = 2;
//
//    private Button takePhoto;
//    private ImageView picture;
//
//    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        return view;

    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        takePhoto = (Button) view.findViewById(R.id.take_photo);
//        picture = (ImageView) view.findViewById(R.id.picture);
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                File outPutImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
//                try {
//                    if (outPutImage.exists()){
//                        outPutImage.delete();
//                    }
//                    outPutImage.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                imageUri = Uri.fromFile(outPutImage);
//                Intent intent = new Intent("android.media,action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, TAKE_PHOTO);
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case TAKE_PHOTO:
//                if (requestCode == RES){
//                    Intent intent = new Intent("come.android.camera.action.CROP");
//                    intent.setDataAndType(imageUri, "image/*");
//                    intent.putExtra("scale", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                    startActivityForResult(intent, CROP_PHOTO);
//                }
//                break;
//            case CROP_PHOTO:
//                if (resultCode == RES){
//                    Bitmap bitmap = BitmapFactory.decodeStream(getCo)
//                }
//        }
//    }
}
