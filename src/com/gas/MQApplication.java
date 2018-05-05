package com.gas;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.mashape.unirest.http.Unirest;

public class MQApplication {

	public static void main(String[] args) {

		System.out.println("Press CTRL+C to abort.");

		short mq4Channel = 0;

		double mq4RoClean = 4.4;

		int mq4RL = 20;

		MQ mq4;

		try {

			mq4 = new MQ(mq4Channel, mq4RoClean, mq4RL);

			while (true) {

				Map<String, Double> mq4GasValueMap = mq4.getMQValues();

				System.out.println("MQ - 3 \n");

				System.out.print("BAC: " + (mq4GasValueMap.get("LPG")) + " mgl \t");

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				Date date = cal.getTime(); 
				//Push data 
				Unirest.post("https://hackalco2018-bfa4.restdb.io/rest/sensor").header("content-type", "application/json")
				.header("x-apikey", "65fca61a60d53f260e7c7b8687eff4a7d2c42").header("cache-control", "no-cache")
				.body("{\"APIKey\":\"" + "5aec0f165c1d2a2d00002280" + "\",\"obserDate\":\"" +dateFormat.format(date) + "\",\"bacValue\":\""
						+ mq4GasValueMap.get("LPG") + "\",\"driverId\":\"" +"OLA9089" +"\"}")
				.asJsonAsync();

				Thread.sleep(2000);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}


}
