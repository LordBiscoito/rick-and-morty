package br.sergio.rickandmorty.api

import br.sergio.rickandmorty.APIInterface
import br.sergio.rickandmorty.BuildConfig
import br.sergio.rickandmorty.app.MyApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object RetrofitModule {

    @JvmStatic
    @Provides
    @Singleton
    fun providesGitHubEndpoint(retrofit: Retrofit): APIInterface =
        retrofit.create(APIInterface::class.java)

    @JvmStatic
    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .build()

    @JvmStatic
    @Provides
    @Singleton
    fun providesOkHttpClient(
        okHttpCache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(okHttpCache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


    @JvmStatic
    @Provides
    fun providesOkHttpClientCache(theMovieDatabaseApplication: MyApplication): Cache =
        Cache(theMovieDatabaseApplication.cacheDir, 10 * 1024 * 1024)

    @JvmStatic
    @Provides
    fun providesMoshiConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @JvmStatic
    @Provides
    fun providesRxCallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()
}