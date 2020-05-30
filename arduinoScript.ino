/*  
 *  Arduino-Code for the Example of an I2C-Connection between
 *  an Arduino and a Raspberry Pi with Java (Pi4J)
 *  written: May,2020
*/

//including Wire-lib for I2C-Connection
#include <Wire.h>

//including LiquidCrystal-lib for LCD-Display
#include <LiquidCrystal.h>

//including Keypad-lib for Keypad
#include <Key.h>
#include <Keypad.h>

//setting the address for the arduino on the I2C-Bus
#define I2C_ADDRESS 0x40

//setting the dimensions of the Keypad
#define KPD_COLS 3
#define KPD_ROWS 4

//setting the GPIO-Trigger-Pin for the RPi
#define RPI_TRIGGER 3

//the characters for the Keypad
char kpd_keys[KPD_ROWS][KPD_COLS] = 
{
{'#','0','*'},
{'9','8','7'},
{'6','5','4'},
{'3','2','1'}
};

//the pins for the Keypad
byte kpd1_cols[KPD_COLS] = { 24, 28, 26 }; //Definition der Pins für die 3 Spalten
byte kpd1_rows[KPD_ROWS] = { 25, 23, 22, 27 };//Definition der Pins für die 4 Zeilen

//the Keypad itself
Keypad kpd11 = Keypad(makeKeymap(kpd_keys), kpd1_rows, kpd1_cols, KPD_ROWS, KPD_COLS);

//the responding char array which will be sent to RPi
char response[] = {'-', '-'};

/*   ##########################
 *   Here starts the SETUP-Code
 *   ##########################
 */
void setup() 
{
//making the gpio-Trigger-Pin to an output and setting to HIGH
pinMode(RPI_TRIGGER, OUTPUT);
digitalWrite(RPI_TRIGGER, LOW);

//setting up the I2C-Connection
Wire.begin(I2C_ADDRESS);
Wire.onRequest(requested);

//starting the Serial Monitor
Serial.begin(9600);
}

/*   ############################
 *   Here starts the REQUEST-Code
 *   ############################
 */
void requested()
{
  //sending the Data
  Wire.write(response);

  //un-trigger the Trigger-Pin
  digitalWrite(RPI_TRIGGER, LOW);

  //reset the response-array
  response[0] = '-';
  response[1] = '-';
}

/*   #########################
 *   Here starts the LOOP-Code
 *   #########################
 */
void loop() {
  
  //checking if any Key is Pressed
  char Taste = kpd11.getKey();
  if(Taste)
  {
    Serial.print("Die Taste ");
    Serial.print(Taste);
    Serial.println(" wurde gedrueckt!");

    //Putting together the Response for the RPi
    response[0] = 'A';
    response[1] = Taste;
    Serial.println(response[0]);

    //Triggering the RPi, so he can request Data
    digitalWrite(RPI_TRIGGER, HIGH);
    
  }

}
