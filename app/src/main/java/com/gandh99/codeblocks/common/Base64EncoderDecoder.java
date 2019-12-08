package com.gandh99.codeblocks.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.ByteArrayOutputStream;

public class Base64EncoderDecoder {

  public static RoundedBitmapDrawable toRoundedBitmapDrawable(Resources resources, String base64String) {
    byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
    Bitmap profilePicture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    RoundedBitmapDrawable roundedBitmapDrawable =
      RoundedBitmapDrawableFactory.create(resources, profilePicture);
    roundedBitmapDrawable.setCircular(true);
    roundedBitmapDrawable.setAntiAlias(true);

    return roundedBitmapDrawable;
  }

  public static String toBase64String(Bitmap bitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
    byte[] imgByte = byteArrayOutputStream.toByteArray();

    return Base64.encodeToString(imgByte,Base64.DEFAULT);
  }
}
