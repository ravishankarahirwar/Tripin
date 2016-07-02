package com.casetable.casetable.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonMethods {

    public static ProgressHUD mProgressHUD;

    public static void isProgressShow(Context mContext) {
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.show(mContext,
                    CommonMessages.msgPleaseWait, false, false, null);
        } else {
            if (!mProgressHUD.isShowing()) {
                mProgressHUD = ProgressHUD.show(mContext,
                        CommonMessages.msgPleaseWait, false, false, null);
            }
        }
    }



    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void isProgressShowNoMessage(Context mContext) {
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.show(mContext, null, false, false, null);
        } else {
            if (!mProgressHUD.isShowing()) {
                mProgressHUD = ProgressHUD.show(mContext, null, false, false,
                        null);
            }
        }
    }

    public static void isProgressHide() {
        if (mProgressHUD != null) {

            if (mProgressHUD.isShowing()) {
                mProgressHUD.dismiss();
            }
        }
    }


   /* public static String UploadImageVideo(Context context, String path, String type1, HashMap<String, String> map, String key, String url) {

        final HttpResponse localHttpResponse;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            FileBody localFileBody = null;
            File file = new File(path);
            if (type1.equalsIgnoreCase("img")) {
                localFileBody = new FileBody(file, "image/jpg");
            } else if (type1.equalsIgnoreCase("VIDEO")) {
                localFileBody = new FileBody(file, "video*//*");
            }

            HttpPost localHttpPost = new HttpPost(url);
            MultipartEntity localMultipartEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            try {

                Set<String> keys = map.keySet();  //get all keys
                for (String i : keys) {
                    localMultipartEntity.addPart(i, new StringBody(map.get(i)));
                }

                localMultipartEntity.addPart(key, localFileBody);


                localHttpResponse = httpClient.execute(localHttpPost);
                localHttpPost.setEntity(localMultipartEntity);
                HttpResponse response = httpClient.execute(localHttpPost);
                HttpEntity resEntity = response.getEntity();
                // InputStream is=resEntity.getContent();
                String response_str = EntityUtils.toString(resEntity);


                return response_str;

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("exception", e.toString());
                return e.getMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("exception", e.toString());
            return e.getMessage();
        }


    }*/

    /**
     * return date as per your formate
     *
     * @param milliSeconds pass timestamp
     * @param formate      date formate
     * @return date
     */
    public static String getDate(long milliSeconds, String formate) {
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds * 1000);
        return formatter.format(calendar.getTime());
    }

    public static Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File("/sdcard/ikyden/");

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        File file = new File("/sdcard/ikyden/" + System.currentTimeMillis() + ".jpg");

        try {
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (IOException e) {

            e.printStackTrace();
        }


        return rotatedBitmap;
    }


    //user these function for chat and date display in header
    public static String timestamptotime_date(long timestamp) {

        Date date = new Date(timestamp * 1000L); // *1000 is to convert seconds to milliseconds
        // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    //user in chat with display sending and from time with am/pm formate in 12 hr
    public static String timestamptotime_to_AM_PM(long timestamp) {

        Date date = new Date(timestamp * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // the format of your date


        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    public static String getFormattedDate(Context context, long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis * 1000);

        Calendar now = Calendar.getInstance();


        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday";
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return timestamptotime_date(smsTimeInMilis);
        } else {
            return timestamptotime_date(smsTimeInMilis);
        }

    }

    public static String numberFormat(double number) {

        String[] suffix = new String[]{"", "k", "m", "b", "t"};
        int MAX_LENGTH = 4;

        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }

    /**
     * for open dialog
     *
     * @param context pass context
     * @param title   title of dialog
     * @param message message in dialog
     */
   /* public static void opendialog(Context context, String title, String message) {

        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog1.getWindow().setBackgroundDrawable(
                new ColorDrawable(
                        Color.TRANSPARENT));
        dialog1.setContentView(R.layout.info_dialog);


        TextView tv_title = (TextView) dialog1
                .findViewById(R.id.tv_title);

        tv_title.setText(title);
        TextView tv_ok = (TextView) dialog1
                .findViewById(R.id.tv_ok);
        TextView tv_mess = (TextView) dialog1
                .findViewById(R.id.tv_mess);
        tv_mess.setText(message);


        tv_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                dialog1.dismiss();
            }
        });

        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);


    }
*/

    /*public static String numberFormat(double value) {
        int power;
        String suffix = " kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(value);
        value = value/(Math.pow(10,(power/3)*3));
        formattedNumber=formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3);
        return formattedNumber.length()>4 ?  formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }*/
}
