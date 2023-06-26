package com.janus.rodeo.Communication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.janus.rodeo.R;
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
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServices {
    private static RestServices instance = new RestServices();
    static Context context;
    private ConnectivityManager connectivityManager;
    private NetworkInfo wifiInfo, mobileInfo;
    private boolean connected = false;
    private User userLogged;
    private API service;
    private String URLRest;
    public  static Retrofit retrofit;

    public static RestServices getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
        return connected;
    }

    public User getUserLogged() {
        return userLogged;
    }

    public String refreshToken() {
        User temp = loginRequest(userLogged.getUserName(),userLogged.getPassword());
        return temp.getToken();
    }

    public void setToken(String token) {
        this.userLogged.setToken(token);
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

    public User loginRequest(String username, String password) {
        if(isOnline()) {
            User userResponse;
            Call<User> UserCall = service.login(username, password);
            try {
                User userList = UserCall.execute().body();
                userResponse = userList;
            } catch (Exception ex) {
                Log.e("Login","Login Sync: --> " + ex.toString());
                userResponse = new User();
                userResponse.setFullName("N/C");
            }
            return userResponse;
        } else {
            return null;
        }
    }

    public GeneralResponse postUpdateCoilLocation(String coilName, String currentLocation, String coilType, int userId) {
        if(isOnline()) {
            CoilUpdateRequest post = new CoilUpdateRequest();
            post.setCoilName(coilName);
            post.setCoilLocation(currentLocation);
            post.setCoilType(coilType);
            post.setUserId(userId);

            Call<GeneralResponse> responseCall = service.updateCoilLocation(post, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = postUpdateCoilLocation(coilName, currentLocation, coilType, userId);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public GeneralResponse checkCoilShippingRelease(String coilName) {
        if(isOnline()) {
            Call<GeneralResponse> responseCall = service.CheckCoilShippingRelease(coilName, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost = response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = checkCoilShippingRelease(coilName);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public GeneralResponse getHelpInfo(String fragmentName) {
        if(isOnline()) {
            Call<GeneralResponse> responseCall = service.getInfo(fragmentName, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = getHelpInfo(fragmentName);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public List<Warehouse> getWarehouseList() {
        if(isOnline()) {
            List<Warehouse> listWarehouseResponse = new ArrayList<>();
            List<Warehouse> listWarehouseBody;
            Call<List<Warehouse>> warehouseCall = service.getWarehouseListCall(userLogged.getToken());
            try {
                Response<List<Warehouse>> response = warehouseCall.execute();
                if(response.isSuccessful()) {
                    listWarehouseBody = response.body();
                    if((listWarehouseBody != null ? listWarehouseBody.size() : 0) >0) {
                        listWarehouseResponse.addAll(listWarehouseBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listWarehouseResponse = getWarehouseList();
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listWarehouseResponse;
        } else {
            return null;
        }
    }

    public List<CoilInformation> getCoilsList(String query) {
        if(isOnline()) {
            List<CoilInformation> listCoilsResponse = new ArrayList<>();
            List<CoilInformation> listCoilsBody;
            Call<List<CoilInformation>> ListCoilsCall = service.getCoils(query,userLogged.getToken());
            try {
                Response<List<CoilInformation>> response = ListCoilsCall.execute();
                if(response.isSuccessful()) {
                    listCoilsBody = response.body();
                    if((listCoilsBody != null ? listCoilsBody.size() : 0) >0) {
                        listCoilsResponse.addAll(listCoilsBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listCoilsResponse = getCoilsList(query);
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listCoilsResponse;
        } else {
            return null;
        }
    }

    public CoilInformation getCoilInformation(String query) {
        if(isOnline()) {
            CoilInformation  CoilResponse = new CoilInformation();
            CoilInformation  CoilBody;
            Call<CoilInformation>  CoilCall = service.getCoilDetails(query, userLogged.getToken());
            try {
                Response<CoilInformation> response = CoilCall.execute();
                if(response.isSuccessful()) {
                    CoilBody = response.body();
                    CoilResponse=CoilBody;
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            CoilResponse = getCoilInformation(query);
                        }
                    }
                }
            } catch (Exception ex) {
                return  null;
            }

            if(CoilResponse.getCoilId()==0) {
                return  null;
            } else {
                return CoilResponse;
            }
        } else {
            return null;
        }
    }

    public List<DrumInformation> getDrumsList(String query) {
        if(isOnline()) {
            List<DrumInformation> listDrumsResponse = new ArrayList<>();
            List<DrumInformation> listDrumsBody;
            Call<List<DrumInformation>> ListDrumCall = service.getDrums(query,userLogged.getToken());

            try {
                Response<List<DrumInformation>> response = ListDrumCall.execute();
                if(response.isSuccessful()) {
                    listDrumsBody = response.body();
                    if((listDrumsBody != null ? listDrumsBody.size() : 0) >0) {
                        listDrumsResponse.addAll(listDrumsBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listDrumsResponse = getDrumsList(query);
                        }
                    }
                }
            } catch (Exception ex) {
                return  null;
            }
            return listDrumsResponse;
        } else {
            return null;
        }
    }

    public DrumInformation getDrumInformation(String query) {
        if(isOnline()) {
            DrumInformation  DrumResponse = new DrumInformation();
            DrumInformation  DrumBody;
            Call<DrumInformation>  DrumCall = service.getDrumDetails(query, userLogged.getToken());
            try {
                Response<DrumInformation> response = DrumCall.execute();
                if(response.isSuccessful()) {
                    DrumBody = response.body();
                    DrumResponse=DrumBody;
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            DrumResponse = getDrumInformation(query);
                        }
                    }
                }
            } catch (Exception ex) {
                return  null;
            }

            if(DrumResponse.getDrumName().isEmpty()) {
                return  null;
            } else {
                return DrumResponse;
            }
        } else {
            return null;
        }
    }

    public GeneralResponse postUpdateDrumLocation(String DrumName, String currentLocation, int userId) {
        if(isOnline()) {
            DrumUpdateRequest post = new DrumUpdateRequest();
            post.setDrumName(DrumName);
            post.setDrumLocation(currentLocation);
            post.setUserId(userId);
            Call<GeneralResponse> responseCall = service.updateDrumLocation(post, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = postUpdateDrumLocation(DrumName, currentLocation, userId);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public GeneralResponse postConsumeDrum(String DrumName, int userId) {
        if(isOnline()) {
            DrumConsumeRequest post = new DrumConsumeRequest();
            post.setDrumName(DrumName);
            post.setUserId(userId);
            Call<GeneralResponse> responseCall = service.ConsumeDrum(post, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = postConsumeDrum(DrumName, userId);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public List<ShRelease> getReleasesList(String query) {
        if(isOnline()) {
            List<ShRelease> listReleasesResponse = new ArrayList<>();
            List<ShRelease> listReleasesBody;
            Call<List<ShRelease>> ListReleasesCall = service.getReleases(query,userLogged.getToken());
            try {
                Response<List<ShRelease>> response = ListReleasesCall.execute();
                if(response.isSuccessful()) {
                    listReleasesBody = response.body();
                    if((listReleasesBody != null ? listReleasesBody.size() : 0) >0) {
                        listReleasesResponse.addAll(listReleasesBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listReleasesResponse = getReleasesList(query);
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listReleasesResponse;
        } else {
            return null;
        }
    }

    public List<CoilShRelease> getCoilsByRelease(String query) {
        if(isOnline()) {
            List<CoilShRelease> listCoilsResponse = new ArrayList<>();
            List<CoilShRelease> listCoilsBody;
            Call<List<CoilShRelease>> ListCoilsCall = service.GetShippingReleases(query,userLogged.getToken());
            try {
                Response<List<CoilShRelease>> response = ListCoilsCall.execute();
                if(response.isSuccessful()) {
                    listCoilsBody = response.body();
                    if((listCoilsBody != null ? listCoilsBody.size() : 0) >0) {
                        listCoilsResponse.addAll(listCoilsBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listCoilsResponse = getCoilsByRelease(query);
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listCoilsResponse;
        } else {
            return null;
        }
    }

    public List<CoilShRelease> getCoilsReleaseByCoil(String query) {
        if(isOnline()) {
            List<CoilShRelease> listCoilsResponse = new ArrayList<>();
            List<CoilShRelease> listCoilsBody;
            Call<List<CoilShRelease>> ListCoilsCall = service.GetCoilsShippingByCoil(query,userLogged.getToken());
            try {
                Response<List<CoilShRelease>> response = ListCoilsCall.execute();
                if(response.isSuccessful()) {
                    listCoilsBody = response.body();
                    if((listCoilsBody != null ? listCoilsBody.size() : 0) >0) {
                        listCoilsResponse.addAll(listCoilsBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listCoilsResponse = getCoilsReleaseByCoil(query);
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listCoilsResponse;
        } else {
            return null;
        }
    }

    public GeneralResponse postLoadRelease(LoadReleaseRequest release) {
        if(isOnline()) {
            Call<GeneralResponse> responseCall = service.LoadRelease(release, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = postLoadRelease(release);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public List<Inventory> getPhysicalInventory() {
        if(isOnline()) {
            List<Inventory> listInventoryResponse = new ArrayList<>();
            List<Inventory> listInventoryBody;
            Call<List<Inventory>> ListInventoryCall = service.getPhysicalInventory(userLogged.getToken());
            try {
                Response<List<Inventory>> response = ListInventoryCall.execute();
                if(response.isSuccessful()) {
                    listInventoryBody = response.body();
                    if((listInventoryBody != null ? listInventoryBody.size() : 0) >0) {
                        listInventoryResponse.addAll(listInventoryBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listInventoryResponse = getPhysicalInventory();
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listInventoryResponse;
        } else {
            return null;
        }
    }

    public List<Inventory> getPhysicalInventoryDrum() {
        if(isOnline()) {
            List<Inventory> listInventoryResponse = new ArrayList<>();
            List<Inventory> listInventoryBody;
            Call<List<Inventory>> ListInventoryCall = service.getPhysicalInventoryDrum(userLogged.getToken());
            try {
                Response<List<Inventory>> response = ListInventoryCall.execute();
                if(response.isSuccessful()) {
                    listInventoryBody = response.body();
                    if((listInventoryBody != null ? listInventoryBody.size() : 0) >0) {
                        listInventoryResponse.addAll(listInventoryBody);
                    }
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            listInventoryResponse = getPhysicalInventoryDrum();
                        }
                    }
                }
            } catch (Exception ex) {
                String Error=ex.getMessage();
            }
            return listInventoryResponse;
        } else {
            return null;
        }
    }

    public CoilInventory getPhysicalCoilInformation(String query, int physicalInventory) {
        if(isOnline()) {
            CoilInventory  CoilInventoryResponse = new CoilInventory();
            CoilInventory  CoilInventoryBody;
            Call<CoilInventory>  CoilInventoryCall = service.getPhysicalCoilInformation(query,physicalInventory, userLogged.getToken());
            try {
                Response<CoilInventory> response = CoilInventoryCall.execute();
                if(response.isSuccessful()) {
                    CoilInventoryBody = response.body();
                    CoilInventoryResponse=CoilInventoryBody;
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            CoilInventoryResponse = getPhysicalCoilInformation(query, physicalInventory);
                        }
                    }
                }
            } catch (Exception ex) {
                return  null;
            }

            if(CoilInventoryResponse.getCoilNumber()==null) {
                return  null;
            } else {
                return CoilInventoryResponse;
            }
        } else {
            return null;
        }
    }

    public DrumInventory getPhysicalDrumInformation(String query, int physicalInventory) {
        if(isOnline()) {
            DrumInventory DrumInventoryResponse = new DrumInventory();
            DrumInventory  DrumInventoryBody;
            Call<DrumInventory>  DrumInventoryCall = service.getPhysicalDrumInformation(query,physicalInventory, userLogged.getToken());
            try {
                Response<DrumInventory> response = DrumInventoryCall.execute();
                if(response.isSuccessful()) {
                    DrumInventoryBody = response.body();
                    DrumInventoryResponse=DrumInventoryBody;
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            DrumInventoryResponse = getPhysicalDrumInformation(query, physicalInventory);
                        }
                    }
                }
            } catch (Exception ex) {
                return  null;
            }
            if(DrumInventoryResponse.getDrumNumber()==null) {
                return  null;
            } else {
                return DrumInventoryResponse;
            }
        } else {
            return null;
        }
    }
    
    public GeneralResponse UpdateCoilPhysicalInventory(InventoryCoilRequest coil) {
        if(isOnline()) {
            Call<GeneralResponse> responseCall = service.UpdateLocationInventory(coil, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = UpdateCoilPhysicalInventory(coil);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }
    
    public GeneralResponse UpdateDrumPhysicalInventory(InventoryDrumRequest drum) {
        if(isOnline()) {
            Call<GeneralResponse> responseCall = service.UpdateLocationInventoryDrum(drum, userLogged.getToken());
            GeneralResponse responsePost = new GeneralResponse();

            try {
                Response<GeneralResponse> response = responseCall.execute();
                if(response.isSuccessful()) {
                    responsePost=response.body();
                } else {
                    if(response.code() == 401) {
                        User tempUser = loginRequest(userLogged.getUserName(),userLogged.getPassword());
                        if(tempUser!=null) {
                            userLogged = tempUser;
                            responsePost = UpdateDrumPhysicalInventory(drum);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Item", "Item Information Request: -->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public ResponsePost ping(boolean code) {
        if(isOnline()) {
            ResponsePost response = new ResponsePost();
            Call<ResponsePost> isping = service.echoping(code);
            try {
                response = isping.execute().body();
            } catch(Exception ex) {
                Log.e("Code","Code Sync: --> " + ex.toString());
            }
            return response;
        } else {
            return null;
        }
    }

    public User echoUser() {
        if (isOnline()) {
            User response = new User();
            Call<User> isecho = service.echouser(userLogged, userLogged.getToken());
            try {
                User User = isecho.execute().body();
                response = User;
            } catch (Exception ex) {
                Log.e("Username", " Username Sync: --> " + ex.toString());
            }
        } else {
            return null;
        }
        return null;
    }

    public User GetUserID() {
        if (isOnline()) {
            User response = new User();
            Call<User> getUser = service.getuser(userLogged.getUserCode(), userLogged.getToken());
            try {
                User UserList = getUser.execute().body();
                response = UserList;
            } catch (Exception ex) {
                Log.e("User", "User Sync: --> " + ex.toString());
            }
            return response;
        }
        return null;
    }

    public List<User> getAllUsers() {
        if(isOnline()) {
            List<User> UserListResponse = new ArrayList<>();
            List<User> UserlistBody;
            Call<List<User>> UserCall = service.getUserAll(userLogged.getToken());
            try {
                Response<List<User>> response = UserCall.execute();
                if(response.isSuccessful()) {
                    UserlistBody = response.body();
                    if((UserlistBody != null ? UserlistBody.size() : 0) >0) {
                        UserListResponse.addAll(UserlistBody);
                        Log.d("Response User","Response Firts User:-->"+UserListResponse.get(0).toString());
                    }
                } else {
                    Log.d("Error Request","Error code Request User --->"+response.code());
                    if(response.code() == 401) {
                        Log.d("Response User","Bad Token or expired token, refresh:-->");
                    }
                }
            } catch(Exception ex) {
                Log.e("Response User","User Asigned Request: --> " + ex.toString());
            }
            return  UserListResponse;
        } else {
            return null;
        }
    }

    public ResponsePost GetInventoryBundleStatus(int id) {
        if(isOnline()) {
            InventoryPost response = new InventoryPost();
            response.setId_actividad(id);
            Call<ResponsePost> responsePostCall = service.iteminformation(response, userLogged.getToken());
            ResponsePost rrr = new ResponsePost();
            try {
                rrr = responsePostCall.execute().body();
            } catch(Exception ex) {
                Log.e("Exception", "Exception Response: --> " + ex.toString());
            }
            return rrr;
        } else {
            return null;
        }
    }

    public ResponsePost UpdateItemInfo(String itemnumber, int itemstatus, int station_id, int user_id, String item_type) {
        if(isOnline()) {
            InventoryPost post = new InventoryPost();
            post.setDescripcion_locacion(itemnumber);
            post.setStatus(true);
            post.setCodigo_locacion(station_id);
            post.setUsuario_asignado(userLogged.getUserCode());
            post.setDescripcion(item_type);
            Log.d("Post Object","Post Body--->"+post.toString());
            Call<ResponsePost> postUpdateInfoInventory = service.updateiteminfo(post,userLogged.getToken());
            ResponsePost responsePost = new ResponsePost();

            try {
                responsePost = postUpdateInfoInventory.execute().body();
                if(responsePost!=null) {
                    Log.d("Response Post","Response:-->"+responsePost.toString());
                } else {
                    responsePost.setStatus_code("500");
                    responsePost.setMessage("Error--->");
                }
            } catch(Exception ex) {
                Log.d("Response Post","Error Response:-->"+ex.toString());
                responsePost.setStatus_code("500");
                responsePost.setMessage("Error--->" + ex.toString());
            }
            return responsePost;
        } else {
            return null;
        }
    }

    public ResponsePost versionApk(int version,String aplicationID) {
        if(isOnline()) {
            ResponsePost response = new ResponsePost();
            VersionPost versionPost = new VersionPost();
            versionPost.setVersionCode(version);
            versionPost.setAplicationID(aplicationID);
            Call<ResponsePost> isNew = service.echoversion(versionPost);
            try {
                response = isNew.execute().body();
            } catch (Exception ex) {
                Log.e("Version","Version Sync: --> " + ex.toString());
            }
            return response;
        } else {
            return null;
        }

    }

    public List<Activity> getActivitiesProgramed() {
        if(isOnline()) {
            List<Activity> activityListResponse = new ArrayList<>();
            List<Activity> activitylistBody;
            Call<List<Activity>> inventoryCall = service.getAsignedActivitys(userLogged.getUserCode(),userLogged.getToken());
            try {
                Response<List<Activity>> response = inventoryCall.execute();
                if(response.isSuccessful()) {
                    activitylistBody = response.body();
                    if((activitylistBody != null ? activitylistBody.size() : 0) >0) {
                        activityListResponse.addAll(activitylistBody);
                        Log.d("Response Inventory","Response Firts Inventory:-->"+activityListResponse.get(0).toString());
                    }
                } else {
                    Log.d("Error Request","Error code Request Activitys --->"+response.code());
                    if(response.code() == 401) {
                        Log.d("Response Inventory","Bad Token or expired token, refresh:-->");
                    }
                }
            } catch (Exception ex) {
                Log.e("Response Inventory","Inventory Asigned Request: --> " + ex.toString());
            }
            return  activityListResponse;
        } else {
            return null;
        }
    }

    public ResponsePost ItemInfo(String itemnumber, int itemstatus, int station_id, int user_id, String item_type) {
        if (isOnline()) {
            InventoryPost post = new InventoryPost();
            post.setDescripcion_locacion(itemnumber);
            post.setStatus(true);
            post.setCodigo_locacion(station_id);
            post.setUsuario_asignado(userLogged.getUserCode());
            post.setDescripcion(item_type);
            Call<ResponsePost> postInventoryInfo = service.iteminformation(post, userLogged.getToken());
            ResponsePost responsePost = new ResponsePost();

            try {
                responsePost = postInventoryInfo.execute().body();
                if (responsePost != null) {
                    Log.d("Response Post", "Response:-->" + responsePost.toString());
                } else {
                    responsePost.setStatus_code("500");
                    responsePost.setMessage("Error--->");
                }
            } catch (Exception ex) {
                Log.e("Response Post", "Error Response: -->" + ex.toString());
                responsePost.setStatus_code("500");
                responsePost.setMessage("Error--->" + ex.toString());
            }
            return  responsePost;
        } else {
            return null;
        }
    }

    public List<Tree> getTreeNavRS(int id_Status) {
        if(isOnline()) {
            List<Tree> treeListResponse = new ArrayList<>();
            List<Tree> treeListBody;
            Call<List<Tree>> treeCall = service.getTreeNav(id_Status);
            try {
                Response<List<Tree>> response = treeCall.execute();
                if(response.isSuccessful()) {
                    treeListBody = response.body();
                    if((treeListBody != null ? treeListBody.size() : 0) >0) {
                        treeListResponse.addAll(treeListBody);
                    }
                }
            } catch(Exception ex) {
            }
            return treeListResponse;
        } else {
            return null;
        }
    }

    public void startRestAPI() {
        URLRest = context.getResources().getString(R.string.URL_ADDRESS);
        if (!URLRest.equals("")) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLRest)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(API.class);
        }
    }

}