package kc.si6b.kampuskita.Api;

import kc.si6b.kampuskita.Model.ModelRespon;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelRespon> ardRetrieve();

    //penggunaan formulirencoded
    @FormUrlEncoded
    @POST("create.php")
    Call<ModelRespon> ardCreate(
            @Field("Nama") String Nama,
            @Field("jenis") String jenis,
            @Field("awal") String awal,
            @Field("pendapatan") String pendapatan,
            @Field("asal") String asal

            );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelRespon> ardUpdate(
            @Field("id") String Id,
            @Field("Nama") String Nama,
            @Field("jenis") String jenis,
            @Field("awal") String awal,
            @Field("pendapatan") String pendapatan,
            @Field("asal") String asal

            );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelRespon> ardDelete(
            @Field("id") String id
    );
}
