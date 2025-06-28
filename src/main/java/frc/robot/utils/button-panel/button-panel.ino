#include <Joystick.h>
Joystick_ Joystick;

void setup() {
  Joystick.begin();
  for (int x = 2; x <= 9; x++) {
    pinMode(x, INPUT_PULLUP);
  }
}

void loop() {
  int a = -1;
  for (int y = 2; y <= 9; y++) {
    if (digitalRead(y) == LOW) {a = y - 2;}
  }

  Joystick.setHatSwitch(0, a);
}