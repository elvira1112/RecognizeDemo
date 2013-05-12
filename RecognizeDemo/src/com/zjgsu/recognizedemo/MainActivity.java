package com.zjgsu.recognizedemo;

import com.zjgsu.ocr.OCRUtils;
import com.zjgsu.utils.Constants;
import com.zjgsu.utils.ImageUtils;
import com.zjgsu.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String  TAG  = "MainActivity";
	
	private TextView tv_result;
	private LinearLayout mainLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		tv_result = (TextView)findViewById(R.id.textView1);
		Button btn_ocr = (Button)findViewById(R.id.button1);
		btn_ocr.setOnClickListener(Recognize);
		mainLayout = (LinearLayout)findViewById(R.id.main_layout);

	}
	
	private OnClickListener Recognize = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			OCRUtils ocr = new OCRUtils();
			ImageUtils imagesUtils = new ImageUtils();
			BitmapFactory.Options opts =  new  BitmapFactory.Options();
	        opts.inSampleSize = 1;
	        
	        Bitmap bitmap = BitmapFactory.decodeFile(Constants.IMAGE_PATH, opts);
			if (bitmap == null){
				System.out.println("获取图片失败");
				return;
			}
			
			Bitmap binaryBitmap = imagesUtils.gray2Binary(imagesUtils.bitmap2Gray(bitmap));
			if (binaryBitmap != null){
				addImage(binaryBitmap);
			}
			Bitmap tiltBitmap = imagesUtils.TiltCorrection(binaryBitmap);
			if (tiltBitmap != null){
				addImage(tiltBitmap);
			}
			
//			String result = ocr.doOcr(tiltBitmap);
//			String str = "word s";
//			result = Utils.readMidWord(result);
//			tv_result.setText("result:" + result);
		}
	};

	public void addImage(Bitmap myBitmap) {
		ImageView iv_image = new ImageView(MainActivity.this);
		iv_image.setImageBitmap(myBitmap);
		iv_image.setMinimumHeight(480);
		iv_image.setMinimumWidth(320);
		mainLayout.addView(iv_image);
	}
}
