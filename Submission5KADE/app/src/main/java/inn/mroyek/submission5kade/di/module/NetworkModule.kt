package inn.mroyek.submission5kade.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import inn.mroyek.submission5kade.BuildConfig
import inn.mroyek.submission5kade.network.ApiService
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val DEFAULT_TIMEOUT = 30L
    private const val CACHE_CONTROL_HEADER = "Cache-Control"

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(app: Application) : OkHttpClient {
        val context: Context = app.applicationContext

        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.DAYS)
            .build()

        val cache = Cache(File(context.cacheDir, "cache"), 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addNetworkInterceptor{ chain ->
                chain.proceed(chain.request())
                    .newBuilder()
                    .header(CACHE_CONTROL_HEADER, cacheControl.toString())
                    .build()
            }
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}