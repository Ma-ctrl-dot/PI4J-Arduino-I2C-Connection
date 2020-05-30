# PI4J-Arduino-I2C-Connection
A coding-example on how to send data between an Arduino and a Raspberry Pi over I2C-bus using PI4J

I myself had a lot of problems and issues while developing a project like described. So I decided to put the Code to GitHub to help other People may have problems too.

In detail: how to send data from the Arduino (Button etc, in this example a 3x4 Keypad) to the Raspberry Pi, but the Raspberry Pi should work as the Master-Device on the I2C-Bus.

You should have set up the Raspberry Pi with JDK 8. Do not use any newer JDK!!!
This is because a few java files aren't available with a newer JDK, but we need them for the I2C-Bus.
If the example works with a newer JDK please comment / contact!

And make sure you have an Arduino which is connected to the Raspberry Pi I2C-Pins.

Attention:
The GPIO-Pins of the Raspberry Pi are working with 3.3V-Logic, whether the Arduino Digital-Pins work with 5V-Logic.
If the Raspberry Pi works as the Master on the I2C-Bus, this will eventually not gonna be a Problem, because the Raspberry Pi is sending Data with 3.3V-Logic through the I2C-Bus and the Arduino will recognize this as a logic-HIGH.
But we need a trigger which allows the Arduino to give the Raspberry Pi a signal, so the Raspberry Pi can send a data-request to the Arduino. This will be 5V-Logic and destroying GPIOs is not very useful there has to be another solution.

So in my case i put a cheap Logic-Level-Converter-PCB between the two devices to prevent them from destroying. But for prototyping it is also possible to use a relais and switch the 3.3V from the Raspberry pi to the Trigger-GPIO.

Wiring:
Arduino:    Raspberry Pi:
SDA         SDA
SCL         SCL
GND         GND
D3          GPIO 25

I hope i could help you with my code-example. If you have any suggestions to improve my code please commit/contact
Thank you very much!
