package com.example.flippingcoin;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity implements SensorEventListener 
{

	private float X, Y, Z;// defining x,y,z axis
    private boolean signal;// generating a boolean signal variable
    private SensorManager Sensor_Manager;
    private Sensor mAccelerometer; // creating object accelerometer of class sensor 
    private final float NOISE = (float) 2;

@Override
public void onCreate(Bundle savedInstanceState) 
{

	 super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);// embedding xml file
     signal = false;//setting signal as false
	 Sensor_Manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	 mAccelerometer = Sensor_Manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
     Sensor_Manager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
}

    protected void onResume() 
    {
	    super.onResume();
	    Sensor_Manager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
     }
	protected void onPause() 
	{
		super.onPause();
		Sensor_Manager.unregisterListener(this);
	}

@Override
public void onSensorChanged(SensorEvent event) 
{
	ImageView image1 = (ImageView)findViewById(R.id.image);
	float x = event.values[0];// acceleration force along x-axis
	float y = event.values[1];//acceleration force along y-axis
	float z = event.values[2];// acceleration force along y-axis
	 
	if (!signal) // if signal is not true so in very beginning it will initialise X,Y,Z here
	{
		X = x;
		Y = y;
		Z = z;
		signal = true; //set it as true
	}
	else
	{
		float deltaX = Math.abs(X - x);// finding difference of old x-axis and new x-axis
		float deltaY = Math.abs(Y - y);
		float deltaZ = Math.abs(Z - z);
		if (deltaX < NOISE) deltaX = (float)0.0;// checking if change in new and old axis< 2.0 change it to zero
		if (deltaY < NOISE) deltaY = (float)0.0;
		if (deltaZ < NOISE) deltaZ = (float)0.0;
		X = x;//updating X
		Y = y;
		Z = z;
		
		image1.setVisibility(View.VISIBLE); //Image will be visible randomly any of picture  
		float a=(float) Math.random();//here is random function that generates random numbers between 0 and 1
		/*now if delta values calculated above 
		are not set to zero which means they have 
		change greater than 1.5 and this will consider as major 
		change in axis just like shaking mobile
		*/
		if (deltaY > deltaX && deltaZ > deltaX && deltaZ> deltaY) 
		{
			//if generated random number has any value >0.4 it will show elizabeth's photo note that values are still random
			if(a>0.4)
			{
			    image1.setImageResource(R.drawable.eliza);
			}// if generated random value<0.5 it will display dollar
			else
			{	
				image1.setImageResource(R.drawable.dollar);
			}
			
		}
		
	}

}

@Override
public void onAccuracyChanged(Sensor arg0, int arg1) 
{
	// TODO Auto-generated method stub
	
}

}
