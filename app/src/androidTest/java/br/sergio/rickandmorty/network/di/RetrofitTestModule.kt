package br.sergio.rickandmorty.network.di

import android.util.Log
import br.sergio.rickandmorty.APIInterface
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RestMockUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object RetrofitTestModule {

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
            .baseUrl(RESTMockServer.getUrl())
            .build()

    @JvmStatic
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .sslSocketFactory(
                RESTMockServer.getSSLSocketFactory(),
                RESTMockServer.getTrustManager()
            )
            .build()


    @JvmStatic
    @Provides
    fun providesMoshiConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @JvmStatic
    @Provides
    fun providesRxCallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()
}