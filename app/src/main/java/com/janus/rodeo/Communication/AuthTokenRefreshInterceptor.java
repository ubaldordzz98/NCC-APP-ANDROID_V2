package com.janus.rodeo.Communication;

import android.content.Context;
import android.util.Log;
import com.google.gson.JsonObject;
import com.janus.rodeo.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthTokenRefreshInterceptor implements Interceptor {
    private static final String TAG_THIS = AuthTokenRefreshInterceptor.class.getSimpleName();
    private static final int RESPONSE_UNAUTHORIZED_401 = 401;
    private static final int RESPONSE_HTTP_RANK_2XX = 2;
    private static final int RESPONSE_HTTP_CLIENT_ERROR = 4;
    private static final int RESPONSE_HTTP_SERVER_ERROR = 5;
    private static final String BODY_PARAM_KEY_USER = "Username";
    private static final String BODY_PARAM_KEY_PASSWORD = "Password";
    private static final String URL_ENDPOINT_TOKEN_REFRESH = "/api/login/authenticate";
    private static Context context;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.header("Accept", "application/json");
        String token = RestServices.getInstance(context).getUserLogged().getToken();
        setAuthHeader(builder, token);
        request = builder.build();
        Log.d( TAG_THIS,
                ">>> Sending Request >>>\n"
                        +"To: "+request.url()+"\n"
                        +"Headers:"+request.headers()+"\n"
                        +"Body: "+bodyToString(request));   //Shows the magic...

        //------------------------------------------------------------------------------------------
        Response response = chain.proceed(request);         // Sends the request (Original w/ Auth.)
        //------------------------------------------------------------------------------------------

        Log.d( TAG_THIS,
                "<<< Receiving Request response <<<\n"
                        +"To: "+response.request().url()+"\n"
                        +"Headers: "+response.headers()+"\n"
                        +"Code: "+response.code()+"\n"
                        +"Body: "+bodyToString(response.request()));  //Shows the magic...



        //------------------- 401 --- 401 --- UNAUTHORIZED --- 401 --- 401 -------------------------

        if (response.code() == RESPONSE_UNAUTHORIZED_401) { //If unauthorized (Token expired)...
            Log.w(TAG_THIS,"Request responses code: "+response.code());

            synchronized (this) {                           // Gets all 401 in sync blocks,
                // to avoid multiply token updates...

                String currentToken = RestServices.getInstance(context).getUserLogged().getToken(); //Get currently stored token (...)

                //Compares current token with token that was stored before,
                // if it was not updated - do update..

                if(currentToken != null && currentToken.equals(token)) {

                    // --- REFRESHING TOKEN --- --- REFRESHING TOKEN --- --- REFRESHING TOKEN ------

                    int code = refreshToken() / 100;                    //Refactor resp. cod ranking


                    if(code != RESPONSE_HTTP_RANK_2XX) {                // If refresh token failed

                        if(code == RESPONSE_HTTP_CLIENT_ERROR           // If failed by error 4xx...
                                ||
                                code == RESPONSE_HTTP_SERVER_ERROR ){   // If failed by error 5xx...

                            logout();                                   // ToDo GoTo login screen
                            return response;                            // Todo Shows auth error to user
                        }
                    }   // <<--------------------------------------------New Auth. Token acquired --
                }   // <<-----------------------------------New Auth. Token acquired double check --


                // --- --- RETRYING ORIGINAL REQUEST --- --- RETRYING ORIGINAL REQUEST --- --------|

                if(RestServices.getInstance(context).getUserLogged().getToken() != null) {                  // Checks new Auth. Token
                    setAuthHeader(builder, RestServices.getInstance(context).getUserLogged().getToken());   // Add Current Auth. Token
                    request = builder.build();                          // O/w the original request

                    Log.d(TAG_THIS,
                            ">>> Retrying original Request >>>\n"
                                    +"To: "+request.url()+"\n"
                                    +"Headers:"+request.headers()+"\n"
                                    +"Body: "+bodyToString(request));  //Shows the magic...


                    //-----------------------------------------------------------------------------|
                    Response responseRetry = chain.proceed(request);// Sends request (w/ New Auth.)
                    //-----------------------------------------------------------------------------|


                    Log.d(TAG_THIS,
                            "<<< Receiving Retried Request response <<<\n"
                                    +"To: "+responseRetry.request().url()+"\n"
                                    +"Headers: "+responseRetry.headers()+"\n"
                                    +"Code: "+responseRetry.code()+"\n"
                                    +"Body: "+bodyToString(response.request()));  //Shows the magic.

                    return responseRetry;
                }
            }
        }else {
            //------------------- 200 --- 200 --- AUTHORIZED --- 200 --- 200 -----------------------
            Log.w(TAG_THIS,"Request responses code: "+response.code());
        }

        return response;

    }


    // Sets/Adds the authentication header to current request builder.-----------------------------|
    private void setAuthHeader(Request.Builder builder, String token) {
        Log.i(TAG_THIS,"Setting authentication header...");
        if (token != null){
            builder.header("Authorization", String.format("Bearer %s", token));
        }

        Log.w(TAG_THIS, "Current Auth Token = "+RestServices.getInstance(context).getUserLogged().getToken());

    }

    // Refresh/renew Synchronously Authentication Token & refresh token----------------------------|
    private int refreshToken() {
        Log.w(TAG_THIS,"Refreshing tokens... ;o");

        // Builds a client...
        OkHttpClient client = new OkHttpClient.Builder().build();

        // Builds a Request Body...for renewing token...
        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
        JsonObject json = new JsonObject();
        //---
        json.addProperty(BODY_PARAM_KEY_USER,RestServices.getInstance(context).getUserLogged().getUserName() );
        json.addProperty(BODY_PARAM_KEY_PASSWORD,RestServices.getInstance(context).getUserLogged().getPassword());
        //---
        RequestBody body = RequestBody.create(jsonType,json.toString());
        // Builds a request with request body...
        String URLRest = context.getResources().getString(R.string.URL_ADDRESS);
        Request request = new Request.Builder()
                .url(URLRest+URL_ENDPOINT_TOKEN_REFRESH)
                .post(body)                     //<<<--------------Adds body (Token renew by the way)
                .build();


        Response response = null;
        int code = 0;


        Log.d( TAG_THIS,
                ">>> Sending Refresh Token Request >>>\n"
                        +"To: "+request.url()+"\n"
                        +"Headers:"+request.headers()+"\n"
                        +"Body: "+bodyToString(request));  //Shows the magic...
        try {
            //--------------------------------------------------------------------------------------
            response = client.newCall(request).execute();       //Sends Refresh token request
            //--------------------------------------------------------------------------------------

            Log.d(TAG_THIS,
                    "<<< Receiving Refresh Token Request Response <<<\n"
                            +"To: "+response.request().url()+"\n"
                            +"Headers:"+response.headers()+"\n"
                            +"Code: "+response.code()+"\n"
                            +"Body: "+bodyToString(response.request()));  //Shows the magic...

            if (response != null) {
                code = response.code();
                Log.i(TAG_THIS,"Token Refresh responses code: "+code);

                switch (code){
                    case 200:
                        // READS NEW TOKENS AND SAVES THEM -----------------------------------------
                        try {
                            //Log.i(TAG_ALIEN+TAG_THIS,"Decoding tokens start");
                            JSONObject jsonBody = null;
                            jsonBody = new JSONObject(response.body().string());
                            String newToken = jsonBody.getString("token");


                            Log.i(TAG_THIS,"New Access Token = "+newToken);
                            RestServices.getInstance(context).setToken(newToken);


                            //Log.i(TAG_ALIEN+TAG_THIS,"Decoding tokens finish.");

                        } catch (JSONException e) {
                            Log.w( TAG_THIS,"Responses code "+ code
                                    +" but error getting response body.\n"
                                    + e.getMessage());
                        }

                        break;

                    default:

                        // READS ERROR -------------------------------------------------------------

                        try {
                            //Log.i(TAG_ALIEN+TAG_THIS,"Decoding error start");
                            JSONObject jsonBodyE = null;
                            jsonBodyE = new JSONObject(response.body().string());
                            String error = jsonBodyE.getString("error");
                            String errorDescription = jsonBodyE.getString("error_description");

                            //Log.i(TAG_ALIEN+TAG_THIS,"Decoding tokens finish.");

                        } catch (JSONException e) {
                            Log.w(TAG_THIS,"Responses code "+ code
                                    +" but error getting response body.\n"
                                    + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                }


                response.body().close(); //ToDo check this line
            }

        } catch (IOException e) {
            Log.w(TAG_THIS,"Error while Sending Refresh Token Request\n"+e.getMessage());
            e.printStackTrace();
        }

        //Log.w(TAG_ALIEN,"Refresh Token request responses code? = "+code);
        return code;

    }

    private int logout() {
        Log.d(TAG_THIS,"go to logout");
        //logout your user
        return 0; //TODO...
    }

    //----------------------------------------------------------------------------------------------
    @Deprecated
    private static String bodyToString(final Request request){
        /*
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            Log.w(TAG_ALIEN+TAG_THIS,"Error while trying to get body to string.");
            return "Null";
        }*/
        return "Nullix";
    }

}