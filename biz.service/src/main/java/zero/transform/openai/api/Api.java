package zero.transform.openai.api;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.*;
import zero.transform.openai.model.ChatCompletion;
import zero.transform.openai.model.ChatCompletionResponse;
import zero.transform.openai.model.Transcriptions;
import zero.transform.openai.model.images.Edits;
import zero.transform.openai.model.images.Generations;
import zero.transform.openai.model.images.ImagesRensponse;
import zero.transform.openai.model.images.Variations;
import zero.transform.response.AudioResponse;
import zero.transform.response.billing.CreditGrantsResponse;
import zero.transform.response.billing.SubscriptionData;
import zero.transform.response.billing.UseageResponse;


/**
 * opeiApi
 */
public interface Api {

    String DEFAULT_API_HOST = "https://api.openai.com/";


    /**
     * chat
     */
    @POST("v1/chat/completions")
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletion chatCompletion);

    /**
     * image_generations
     */
    @POST("v1/images/generations")
    Single<ImagesRensponse> imageGenerations(@Body Generations generations);

    /**
     * image_edits
     */
    @Multipart
    @POST("v1/images/edits")
    Single<ImagesRensponse> imageEdits(@Part() MultipartBody.Part image,
                                       @Part() MultipartBody.Part mask,
                                       @PartMap Edits edits);


    /**
     * image_variations
     */
    @Multipart
    @POST("v1/images/variations")
    Single<ImagesRensponse> imageVariations(@Part() MultipartBody.Part image,
                                            @PartMap Variations variations);

    /**
     * audio_transcriptions
     */
    @Multipart
    @POST("v1/audio/transcriptions")
    Single<AudioResponse> audioTranscriptions(@Part() MultipartBody.Part audio,
                                              @PartMap Transcriptions transcriptions);

    /**
     * audio_translations
     */
    @Multipart
    @POST("v1/audio/translations")
    Single<AudioResponse> audioTranslations(@Part() MultipartBody.Part audio,
                                            @PartMap Transcriptions transcriptions);


    /**
     * 余额查询
     */
    @GET("dashboard/billing/credit_grants")
    Single<CreditGrantsResponse> creditGrants();

    /**
     * 余额查询
     */
    @GET("v1/dashboard/billing/subscription")
    Single<SubscriptionData> subscription();

    /**
     * 余额查询
     */
    @GET("v1/dashboard/billing/usage")
    Single<UseageResponse> usage(@Query("start_date") String startDate,
                                 @Query("end_date") String endDate);


}
