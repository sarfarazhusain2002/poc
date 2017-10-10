package com.oxy.s3m.connection.utils;



//import java.io.IOException;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class NotificationHelper {
public final static String AUTH_KEY_FCM = "AAAAJnl9n8w:APA91bGjANqne5wzqVkvvmEsQnTKr2lRUCIOAnV8JeDzOSOGV1nZ1ePzEzWnozp_JgX5JOu-atbE00X-WdQALU67smhlPMyGAOi9-8GESYh88ufDpkG8-cVmW9b6dby2Grnf-sl7BcJ9";
//public final static String AUTH_KEY_FCM = "AAAAPPWiM7Y:APA91bGrRxEnr0vdw3WmVN29_uzoCaOqBSy37HB77gTZV9aS-3dOF9oAnC8EWjamDlCtL8ugd1J208iTuWp3Y8dXWoEMcgiCBHv1co28iou3Cy2lDfEhN0tmevQwT1aMfvUQKnAylqn6";
public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";


public static void main(String[] args) throws IOException{
	
	List<String> lstToken= new ArrayList<String>();
	lstToken.add("cEPA3vi1uuc:APA91bGIV36Hq3CMyswUV_kT0s0dHhM-gKnWzm8nuqcNW1pPbd7AE_zVoft-KEeKPMXiz3SN0OXr-8s_Y3kY1A3ZU7WC8B7jkF3onxFtRljWETJ9CW8l8C3d1OO8dlZ1NViTtgZ0gw85");
	//lstToken.add("fTCDbeeDXRI:APA91bGuNYGd4ISl1R-bO2gswWXIq58pHs_5WphoJB5qHdVOuO8XXEAwQcrN2__hY0t4VwuqRqOHzyHXAPS_e1HCtWaZefIr1vaDr1cFoAXHL8e-Wi9cTyNj7dv3IhIMvcVNNUOE6Nqv");
	//sendPushNotification(lstToken);
}




public static String sendPushNotification(List<String> deviceToken,String title,String msg)
        throws IOException {
    String result = "";
    URL url = new URL(API_URL_FCM);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setUseCaches(false);
    conn.setDoInput(true);
    conn.setDoOutput(true);

    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
    conn.setRequestProperty("Content-Type", "application/json");

    JSONObject json = new JSONObject();

    json.put("registration_ids", deviceToken);
    JSONObject info = new JSONObject();
    info.put("title", title); // Notification title
    info.put("body", msg); // Notification
                                                            // body
    json.put("notification", info);
    try {
    	OutputStreamWriter wr = new OutputStreamWriter(
    			conn.getOutputStream());
    	wr.write(json.toString());
    	wr.flush();

    	BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));

    	String output;
    	System.out.println("Output from Server .... \n");
    	while ((output = br.readLine()) != null) {
    		System.out.println(output);
    	}
    	result = output;
    } catch (Exception e) {
        e.printStackTrace();
        result = "FAILURE";
    }
    System.out.println("FCM Notification is sent successfully");

    return result;

}
  }   