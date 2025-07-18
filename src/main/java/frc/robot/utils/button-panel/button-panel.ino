#include <Joystick.h>

/*
Include Path updating
Consider adding a CMAKELIST.TXT to PROJECT
@WOLFRAM121 U HAVE THE LIBRARY. LMK WHAT IT IS SO I CAN DOWNLOAD AND CREATE THE CMAKELIST

CONCERNS:
  WILL IT COMPILE TO THE ROBOT?
    THE GRADLE IS MEANT FOR JAVA COMPILATION AND NOT FOR C++ DEVELOPMENT
    DOES THAT MEAN WE NEED A SECOND COMPILER? I SUGGEST MIGRATING TO STILL USING TO WORK WITH ARDUINO/IOT IN JAVA
    - @Aditya-2204
    
*/


Joystick_ Joystick(
  JOYSTICK_DEFAULT_REPORT_ID, 
  JOYSTICK_TYPE_GAMEPAD, 
  0, // button count
  1, // hat switch count
  true,  // X axis
  false,  // Y axis
  false, // Z axis
  false, // Rx
  false, // Ry
  false, // Rz
  false, // rudder
  false, // throttle
  false, // accelerator
  false, // brake
  false  // steering
);

#define CLK 10
#define DT 11
volatile long pos = 0;

long wrap(long pos, int wrap) {
  while (pos > wrap || pos < -wrap) {
    if (pos > wrap) {
      pos -= 2 * wrap;
    } else if (pos < -wrap) {
      pos += 2 * wrap;
    }
  }
  return pos;
}

void readEncoder() {
  if (digitalRead(DT) != digitalRead(CLK)) {
    pos += 0.01;
  } else {
    pos -= 0.01;
  }
}

void setup() {
  Joystick.begin();

  pinMode(CLK, INPUT_PULLUP);
  pinMode(DT, INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(CLK), readEncoder, FALLING);

  for (int x = 2; x <= 9; x++) {
    pinMode(x, INPUT_PULLUP);
  }
}

void loop() {
  Joystick.setXAxis(int(wrap(pos, 1) * 127));

  int a = -1;
  for (int y = 2; y <= 9; y++) {
    if (digitalRead(y) == LOW) {
      a = y - 2;
      break;
    }
  }
  Joystick.setHatSwitch(0, a);
}