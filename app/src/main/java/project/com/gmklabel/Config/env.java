package project.com.gmklabel.Config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class env {
    Context context;
  private static final String CACHE_CONTROL = "Cache-Control";
  private static  final  String ROOT_URL="http://192.168.0.5:8000/";
  private static final String ROOT_img="http://192.168.0.5:8000/img/";

  // private static final String CACHE_CONTROL = "Cache-Control";
  // private static  final  String ROOT_URL="http://192.168.43.53:8000/";
  // private static final String ROOT_img="http://192.168.43.53:8000/img/";

//  private static final String CACHE_CONTROL = "Cache-Control";
//  private static  final  String ROOT_URL="https://storetulungagung.com/";
//  private static final String ROOT_img="https://storetulungagung.com/img/";

    public static final String url_k =ROOT_URL+"api/";
    public static final String barang_url=ROOT_img+"barang/";
    public static final String kategori_url=ROOT_img+"kategori/";
    public static final String slider_url=ROOT_img+"slider/";
    public static final String user_url=ROOT_img+"user/";

    public static String getRootUrl() {
        return ROOT_URL;
    }

    public Retrofit getRespo(){
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url_k)
                .build();
        return retrofit;
    }

    public Retrofit getRespoSaveCache(Context context){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(offintercept())
                .addNetworkInterceptor(cacheIntercept())
                .cache(proCache(context))
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url_k)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    private Cache proCache(Context context) {
        Cache cache=null;
        try {
            cache=new Cache(new File(context.getCacheDir(),"http-cache-2"),10*1024*1024);
        }catch (Exception e){
            Log.e("Cache","Tidak Bisa Menyimpan");
        }

        return cache;

    }

    private Interceptor cacheIntercept() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response=chain.proceed(chain.request());

                CacheControl cacheControl=new CacheControl.Builder()
                        .maxAge(1, TimeUnit.MINUTES)
                        .build();
                return response.newBuilder()
                        .header(CACHE_CONTROL,cacheControl.toString())
                        .build();
            }
        };
    }

    private Interceptor offintercept() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request=chain.request();
                //if(!isNetworkOnline()){
                CacheControl cacheControl=new CacheControl.Builder()
                        .maxStale(7,TimeUnit.DAYS)
                        .build();
                request=request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();

                //}
                return chain.proceed(request);
            }
        };
    }

    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }



}
