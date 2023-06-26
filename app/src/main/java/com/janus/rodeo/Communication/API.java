package com.janus.rodeo.Communication;

import com.janus.rodeo.Models.Activity;
import com.janus.rodeo.Models.CoilInformation;
import com.janus.rodeo.Models.CoilInventory;
import com.janus.rodeo.Models.CoilShRelease;
import com.janus.rodeo.Models.CoilUpdateRequest;
import com.janus.rodeo.Models.DrumConsumeRequest;
import com.janus.rodeo.Models.DrumInformation;
import com.janus.rodeo.Models.DrumInventory;
import com.janus.rodeo.Models.DrumUpdateRequest;
import com.janus.rodeo.Models.GeneralResponse;
import com.janus.rodeo.Models.Inventory;
import com.janus.rodeo.Models.InventoryCoilRequest;
import com.janus.rodeo.Models.InventoryDrumRequest;
import com.janus.rodeo.Models.InventoryPost;
import com.janus.rodeo.Models.LoadReleaseRequest;
import com.janus.rodeo.Models.ResponsePost;
import com.janus.rodeo.Models.ShRelease;
import com.janus.rodeo.Models.Tree;
import com.janus.rodeo.Models.User;
import com.janus.rodeo.Models.VersionPost;
import com.janus.rodeo.Models.Warehouse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @FormUrlEncoded
    @POST("api/login/authenticate")
    Call<User> login(@Field("Username") String username, @Field("Password") String password);

    @GET("api/login/echouser")
    Call<User> echouser(@Body User usuario, @Header("Authorization") String token);

    @GET("api/login/echoping")
    Call<ResponsePost> echoping(@Body boolean ping);

    @POST("api/coil/UpdateLocation")
    Call<GeneralResponse> updateCoilLocation(@Body CoilUpdateRequest updateCoil, @Header("Authorization") String token);

    @GET("api/warehouse/getList")
    Call<List<Warehouse>> getWarehouseListCall(@Header("Authorization") String token);

    @GET("api/coil/GetCoilScanned")
    Call<CoilInformation>getCoilDetails(@Query("coilName") String coilName,@Header("Authorization") String token);

    @GET("api/coil/GetCoils")
    Call<List<CoilInformation>>getCoils(@Query("coilName") String coilName,@Header("Authorization") String token);

    @GET("api/coil/CheckCoilShippingRelease")
    Call<GeneralResponse>CheckCoilShippingRelease(@Query("coilName") String coilName,@Header("Authorization") String token);

    @GET("api/coil/GetShippingReleases")
    Call<List<ShRelease>>getReleases(@Query("releaseNumb") String releaseNumb, @Header("Authorization") String token);

    @GET("api/inventory/GetPhysicalInventory")
    Call<List<Inventory>>getPhysicalInventory(@Header("Authorization") String token);

    @GET("api/inventory/GetPhysicalInventoryDrum")
    Call<List<Inventory>>getPhysicalInventoryDrum(@Header("Authorization") String token);

    @GET("api/inventory/GetCoilScanned")
    Call <CoilInventory>getPhysicalCoilInformation(@Query("coilName") String coilName, @Query("physicalInventory") int physicalInventory, @Header("Authorization") String token);

    @GET("api/inventory/GetDrumScanned")
    Call <DrumInventory>getPhysicalDrumInformation(@Query("drumNumber") String drumNumber, @Query("physicalInventory") int physicalInventory, @Header("Authorization") String token);

    @POST("api/inventory/UpdateLocation")
    Call<GeneralResponse> UpdateLocationInventory(@Body InventoryCoilRequest coilInventory, @Header("Authorization") String token);

    @POST("api/inventory/UpdateLocationDrum")
    Call<GeneralResponse> UpdateLocationInventoryDrum(@Body InventoryDrumRequest drumInventory, @Header("Authorization") String token);

    @GET("api/help/GetInfo")
    Call<GeneralResponse>getInfo(@Query("screenTitle") String screenTitle,@Header("Authorization") String token);

    @GET("api/coil/GetCoilsShippingByRelease")
    Call<List<CoilShRelease>>GetShippingReleases(@Query("releaseNumber") String coilName, @Header("Authorization") String token);

    @GET("api/coil/GetCoilsShippingByCoil")
    Call<List<CoilShRelease>>GetCoilsShippingByCoil(@Query("coilName") String coilName, @Header("Authorization") String token);

    @POST("api/coil/LoadRelease")
    Call<GeneralResponse> LoadRelease(@Body LoadReleaseRequest release, @Header("Authorization") String token);

    @GET("api/drum/GetDrumScanned")
    Call<DrumInformation>getDrumDetails(@Query("drumName") String drumName, @Header("Authorization") String token);

    @GET("api/drum/GetDrums")
    Call<List<DrumInformation>>getDrums(@Query("drumName") String drumName,@Header("Authorization") String token);

    @POST("api/drum/UpdateLocation")
    Call<GeneralResponse> updateDrumLocation(@Body DrumUpdateRequest updateDrum, @Header("Authorization") String token);

    @POST("api/drum/ConsumeDrum")
    Call<GeneralResponse> ConsumeDrum(@Body DrumConsumeRequest updateDrum, @Header("Authorization") String token);

    @GET("api/users/GetUser")
    Call<User> getuser(@Query("id") int id_usuario, @Header("Authorization") String token);

    @GET("api/users/GetUserAll")
    Call<List<User>> getUserAll(@Header("Authorization") String token);

    @POST("api/inventory/updateiteminformation")
    Call<ResponsePost> updateiteminfo(@Body InventoryPost inventoryPost, @Header("Authorization") String token);

    @GET("api/inventory/inventorybundlestatus")
    Call<ResponsePost> inventorystatusbundle(@Query("id") int id, @Query("Name") String name, @Query("Description") String des, @Header("Authorization") String token);

    @POST("api/login/echoversion")
    Call<ResponsePost> echoversion(@Body VersionPost post);

    @GET("/api/activity/GetProgramedActivitys")
    Call<List<Activity>> getAsignedActivitys(@Query("iduser") int id_usuario, @Header("Authorization") String token);

    @GET("api/Inventory/GetScrapCode")
    Call<List<Tree>> getTreeNav(@Query("id_Status") int id_cat);

    @POST("api/Inventory/iteminformation")
    Call<ResponsePost> iteminformation(@Body InventoryPost inventoryPost, @Header("Authorization") String token);

}