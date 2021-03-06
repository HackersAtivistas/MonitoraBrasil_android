package com.gamfig.monitorabrasil.classes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gamfig.monitorabrasil.application.AppController;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class Imagens {

    public static void getFotoPolitico (Politico p,ImageView img,final boolean cropped){
        if(p==null)
            return;
        ImageLoader imageLoader =  AppController.getInstance().getmImagemLoader();
        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(cropped){
                    ImageView v = (ImageView) view;
                    v.setImageBitmap(Imagens.getCroppedBitmap(((BitmapDrawable) v.getDrawable()).getBitmap()));
                }

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };
        if(p.getTipo().equals("c"))
           imageLoader.displayImage("http://www.camara.gov.br/internet/deputado/bandep/" + p.getIdCadastro() + ".jpg",img,listener);
        else
            imageLoader.displayImage("http://www.senado.gov.br/senadores/img/fotos/bemv" + p.getIdCadastro() + ".jpg", img,listener );


    }

    public static  void carregaImagemFacebook(String idFacebook,ImageView img,String tamanho){
        String url="http://graph.facebook.com/"+idFacebook+"/picture?type="+tamanho;
        AppController.getInstance().getmImagemLoader().displayImage(url,img);
    }

	public static Bitmap getImageBitmap(String id) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(id);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			try {
				URL aURL = new URL("http://www.camara.gov.br/internet/deputado/bandep/" + id + ".jpg");
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
			} catch (IOException e1) {
				try {
					URL aURL = new URL("http://www.senado.gov.br/senadores/img/fotos/bemv" + id + ".jpg");
					URLConnection conn = aURL.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					bm = BitmapFactory.decodeStream(bis);
					bis.close();
					is.close();
				} catch (IOException e2) {
					Log.e("", "Error getting bitmap", e);
				}

			}
		}
		return bm;
	}

	public static Bitmap getCroppedBitmap(Bitmap bitmap) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		// Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
		// return _bmp;
		return output;
	}
}
