package be.vanlooverenkoen.launched.gradle.plugin.tasks.deploy

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @author Koen Van Looveren
 */
interface LaunchItApi {

    @Multipart
    @POST("upload")
    fun uploadApp(@Part("packageName") packageName: RequestBody,
                  @Part("versionName") versionName: RequestBody,
                  @Part("name") name: RequestBody,
                  @Part("buildNr") buildNr: RequestBody,
                  @Part("releaseNotes") releaseNotes: RequestBody,
                  @Part("variant") variant: RequestBody,
                  @Part("public") public: RequestBody,
                  @Part("sendMail") sendMail: RequestBody,
                  @Part apk: MultipartBody.Part): Call<ResponseBody>
}