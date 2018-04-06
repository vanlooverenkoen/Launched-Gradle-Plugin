package be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy

import be.vanlooverenkoen.launched.gradle.plugin.utils.AaptHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException

/**
 * @author Koen Van Looveren
 */
class LaunchIt(apiUrl: String, apiKey: String) {

    private var api: LaunchItApi
    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                        .addHeader(API_KEY_HEADER, apiKey)
                        .build()
                chain.proceed(newRequest)
            }
            .build()

    init {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api = retrofit.create(LaunchItApi::class.java)
    }

    fun uploadApp(apkPath: String, releaseNotes: String, variantName: String, public: Boolean, sendMail: Boolean) {
        val upperVariantName = variantName.toUpperCase()
        val apk = File(apkPath)
        val apkInfo = AaptHelper.getApkInfo(apkPath)
        println("Name: ${apkInfo.name}")
        println("PackageName: ${apkInfo.packageName}")
        println("VersionCode: ${apkInfo.versionCode}")
        println("VersionName: ${apkInfo.versionName}")
        println("ReleaseNotes: $releaseNotes")
        println("VariantName: $upperVariantName")
        println("Public: $public")
        println("SendMail: $sendMail")
        val packageNameRequest = RequestBody.create(MediaType.parse("text/plain"), apkInfo.packageName)
        val versionNameRequest = RequestBody.create(MediaType.parse("text/plain"), apkInfo.versionName)
        val nameRequest = RequestBody.create(MediaType.parse("text/plain"), apkInfo.name)
        val buildNrRequest = RequestBody.create(MediaType.parse("text/plain"), apkInfo.versionCode)
        val releaseNotesRequest = RequestBody.create(MediaType.parse("text/plain"), releaseNotes)
        val variantRequest = RequestBody.create(MediaType.parse("text/plain"), upperVariantName)
        val publicRequest = RequestBody.create(MediaType.parse("text/plain"), public.toString())
        val sendMailRequet = RequestBody.create(MediaType.parse("text/plain"), sendMail.toString())
        val apkBody = RequestBody.create(MediaType.parse("application/vnd.android.package-archive"), apk)
        val apkRequest = MultipartBody.Part.createFormData("apk", apk.getName(), apkBody)

        val request = api.uploadApp(packageNameRequest,
                versionNameRequest,
                nameRequest,
                buildNrRequest,
                releaseNotesRequest,
                variantRequest,
                publicRequest,
                sendMailRequet,
                apkRequest)
                .execute()
        if (request.code() == 201) {
            println("Your app has LAUNCHED!")
            return
        }
        println("Your app could not be LAUNCHED because of a ${request.code()} status code")
        println(request.message())
        throw RuntimeException("Your app could not be LAUNCHED because of a ${request.code()} status code")

    }

    fun shutdown() {
        okHttpClient.dispatcher().executorService().shutdown()
        okHttpClient.connectionPool().evictAll()
        try {
            val cache = okHttpClient.cache()
            cache?.close()
        } catch (ignored: IOException) {
        }
    }

    companion object {
        private const val API_KEY_HEADER = "api-key"
    }
}