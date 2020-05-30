package i2C_arduino_raspberry_täst;

import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.WiringPiGpioProviderBase;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

public class Main
{
	
	public static void main(String[] args)
	{
		
		try
		{
			// setting up the GPIO-Controller
			GpioController controller = GpioFactory.getInstance();
			
			// making the trigger-Pin
			GpioPinDigitalInput triggerPin = controller.provisionDigitalInputPin(RaspiPin.GPIO_25);
			
			// setting up the I2C-Bus and the Device
			I2CBus		i2cBus	= I2CFactory.getInstance(I2CBus.BUS_1);
			I2CDevice	ardu	= i2cBus.getDevice(0X40);
			
			// Checking if the device was set up correctly
			System.out.println("Adresse von Arduino: " + ardu.getAddress());
			
			// adding the Listener for the GPIO-Pin
			triggerPin.addListener(new GpioPinListenerDigital()
			{
				
				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent DSCE)
				{
					
					// checking that there is only a trigger when the state changes to Logic-HIGH
					if (DSCE.getState() == PinState.HIGH)
					{
						System.out.println("GPIO triggered...");
						
						// declaring the response array
						byte[] resp = new byte[2];
						
						// declaring and initialising the toSend data
						byte[] rec = { 0 };
						
						try
						{
							// making a random byte to send to the arduino
							new Random().nextBytes(rec);
							
							// sending the random byte to the arduino to request data
							System.out.println("Writing Data to Ardu: " + rec[0]);
							ardu.write(rec[0]);
							
							// waiting for the arduino to response
							System.out.println("waiting...");
							Thread.sleep(20);
							
							// reading the response from the arduino
							System.out.println("Reading Data...");
							ardu.read(resp, 0, 2);
							
							// printing information and result to the console
							System.out.println("Data Recieved: ");
							System.out.print(resp[0]);
							System.out.println((char) resp[0]);
							System.out.print(resp[1]);
							System.out.println((char) resp[1]);
							
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		} catch (UnsupportedBusNumberException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * to make the program keeps running and waiting for the arduino trigger
		 * if you know a better way to make the program to keep running pleas comment/contact
		 */
		
		JFrame jf = new JFrame();
		jf.setSize(100, 100);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
	
}
