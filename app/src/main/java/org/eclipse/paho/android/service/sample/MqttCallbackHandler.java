/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.eclipse.paho.android.service.sample;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.*;


/**
 * Handles call backs from the MQTT Client
 *
 */
public class MqttCallbackHandler implements MqttCallback  {

  /** {@link Context} for the application used to format and import external strings**/
  private Context context;
  /** Client handle to reference the connection that this handler is attached to**/
  private String clientHandle;

  private JSONArray jsonArray = new JSONArray();

  /**
   * Creates an <code>MqttCallbackHandler</code> object
   * @param context The application's context
   * @param clientHandle The handle to a {@link Connection} object
   */
  public MqttCallbackHandler(Context context, String clientHandle)
  {
    this.context = context;
    this.clientHandle = clientHandle;
  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
   */
  @Override
  public void connectionLost(Throwable cause) {
//	  cause.printStackTrace();
    if (cause != null) {
      Connection c = Connections.getInstance(context).getConnection(clientHandle);
      Toast.makeText(context,"connection lost",Toast.LENGTH_LONG).show();


      try {
        c.getClient().connect();
        Toast.makeText(context, "connect", Toast.LENGTH_LONG).show();
      } catch (MqttException e) {
        e.printStackTrace();
      }

      //format string to use a notification text
      Object[] args = new Object[2];
      args[0] = c.getId();
      args[1] = c.getHostName();

      //String message = context.getString(R.string.connection_lost, args);



      //notify the user
      //Notify.notifcation(context, message, intent, R.string.notifyTitle_connectionLost);
    }
  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
   */
  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {

    //Get connection object associated with this object
    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    //Toast.makeText(context,"success",Toast.LENGTH_LONG).show();

    String message2 = new String(message.getPayload());


    Bundle bundle = new Bundle();

    //c.setTest(message2);



    if(topic.equals("server/settings/authenticate"))
    {


      bundle = new Bundle();
      bundle.putString("settings/authenticate", message2);
      c.insertBundle(bundle);
      Log.v("authenticatehandler","handle");

      c.changePage("authenticate");
    }
    else if(topic.equals("server/settings"))
    {
      bundle = new Bundle();
      bundle.putString("settings/authenticate",message2);
      c.insertBundle(bundle);


      Log.v("settingshandler","handle");

      c.changePage("settings");
    }
    else if(topic.equals("server/electricityBill"))
    {
      bundle = new Bundle();
      bundle.putString("electricityBill",message2);
      c.insertBundle(bundle);


      c.changePage("electricityBill");
    }







  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
   */
  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    // Do nothing

  }

}
