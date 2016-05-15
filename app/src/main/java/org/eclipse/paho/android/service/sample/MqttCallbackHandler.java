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


    }
  }

  /**
   * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
   */
  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {

    //Get connection object associated with this object
    Connection c = Connections.getInstance(context).getConnection(clientHandle);
    String message2 = new String(message.getPayload());
    Bundle bundle = new Bundle();


    if(topic.equals("server/settings"))
    {
      bundle = new Bundle();
      bundle.putString("settings/authenticate",message2);
      c.insertBundle(bundle);
      Log.v("settingshandler", "handle");
      c.changePage("settings");
    }
    else if(topic.equals("server/settings/authenticate"))
    {
      bundle = new Bundle();
      bundle.putString("settings/authenticate", message2);
      c.insertBundle(bundle);
      Log.v("authenticatehandler","handle");
      c.changePage("authenticate");
    }

    else if(topic.equals("server/settings/addData/device_type"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/device_type",message2);
      c.insertBundle(bundle);
      c.changePage("addDeviceType");
    }
    else if(topic.equals("server/settings/addData/device_detail"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/device_detail",message2);
      c.insertBundle(bundle);
      c.changePage("addDeviceDetail");
    }
    else if(topic.equals("server/settings/addData/power_node"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/power_node",message2);
      c.insertBundle(bundle);
      c.changePage("addPowerNode");
    }
    else if(topic.equals("server/settings/addData/location"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/location",message2);
      c.insertBundle(bundle);
      c.changePage("addLocation");
    }
    else if(topic.equals("server/settings/addData/location"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/location",message2);
      c.insertBundle(bundle);
      c.changePage("addLocation");
    }
    else if(topic.equals("server/settings/addData/group_of_device"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/group_of_device",message2);
      c.insertBundle(bundle);
      c.changePage("addGroupOfDevice");
    }
    else if(topic.equals("server/settings/addData/device"))
    {
      bundle = new Bundle();
      bundle.putString("settings/addData/device",message2);
      c.insertBundle(bundle);
      c.changePage("addDevice");
    }

    else if(topic.equals("server/settings/editData/device_type"))
    {
      bundle = new Bundle();
      bundle.putString("settings/editData/device_type",message2);
      c.insertBundle(bundle);
      c.changePage("editDeviceType");
    }
    else if(topic.equals("server/settings/editData/device_detail"))
    {
      bundle = new Bundle();
      bundle.putString("settings/editData/device_detail",message2);
      c.insertBundle(bundle);
      c.changePage("editDeviceDetail");
    }
    else if(topic.equals("server/settings/editData/power_node"))
    {
      bundle = new Bundle();
      bundle.putString("settings/editData/power_node",message2);
      c.insertBundle(bundle);
      c.changePage("editPowerNode");
    }
    else if(topic.equals("server/settings/editData/location"))
    {
      bundle = new Bundle();
      bundle.putString("settings/editData/location",message2);
      c.insertBundle(bundle);
      c.changePage("editLocation");
    }
    else if(topic.equals("server/settings/editData/group_of_device"))
    {
      bundle = new Bundle();
      bundle.putString("settings/editData/group_of_device",message2);
      c.insertBundle(bundle);
      c.changePage("editGroupOfDevice");
    }
    else if(topic.equals("server/settings/editData/device"))
    {
      bundle = new Bundle();
      bundle.putString("settings/editData/device",message2);
      c.insertBundle(bundle);
      c.changePage("editDevice");
    }
    else if(topic.equals("server/settings/deleteData/device_type"))
    {
      bundle = new Bundle();
      bundle.putString("settings/deleteData/device_type",message2);
      c.insertBundle(bundle);
      c.changePage("deleteDeviceType");
    }
    else if(topic.equals("server/settings/deleteData/device_detail"))
    {
      bundle = new Bundle();
      bundle.putString("settings/deleteData/device_detail",message2);
      c.insertBundle(bundle);
      c.changePage("deleteDeviceDetail");
    }
    else if(topic.equals("server/settings/deleteData/device"))
    {
      bundle = new Bundle();
      bundle.putString("settings/deleteData/device",message2);
      c.insertBundle(bundle);
      c.changePage("deleteDevice");
    }
    else if(topic.equals("server/settings/deleteData/location"))
    {
      bundle = new Bundle();
      bundle.putString("settings/deleteData/location",message2);
      c.insertBundle(bundle);
      c.changePage("deletelocation");
    }
    else if(topic.equals("server/settings/deleteData/power_node"))
    {
      bundle = new Bundle();
      bundle.putString("settings/deleteData/power_node",message2);
      c.insertBundle(bundle);
      c.changePage("deletePowerNode");
    }
    else if(topic.equals("server/settings/deleteData/group_of_device"))
    {
      bundle = new Bundle();
      bundle.putString("settings/deleteData/group_of_device",message2);
      c.insertBundle(bundle);
      c.changePage("deleteGroupOfDevice");
    }

    else if(topic.equals("server/electricityBill"))
    {
      bundle = new Bundle();
      bundle.putString("electricityBill", message2);
      c.insertBundle(bundle);
      c.changePage("electricityBill");
    }

    else if(topic.equals("server/currentStatus"))
    {
      bundle = new Bundle();
      bundle.putString("currentStatus", message2);
      c.insertBundle(bundle);
      c.changePage("currentStatus");
    }
    else if(topic.equals("server/statistic"))
    {
      bundle = new Bundle();
      bundle.putString("statistic", message2);
      c.insertBundle(bundle);
      c.changePage("statistic");
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
