# Teem Too
 - Anthony Morrell
 - James Thurlow
 - Daniel Lee

## Features
The activity tracker has four views: steps, heart, calories, and sleep. You can navigate between views by swiping or
using the left and right arrow keys.

### Calories
To update the daily calorie count, first navigate to the calorie view. Adjust the slider on the left (via the up and
down arrow keys, the mouse, or by touch) to control how many calories to add at once. Finally, press the add button on
the right (or shift on the keyboard) to update the calorie count.

For example, you consume 120 calories and want to update the tracker. Move the slider to 50 and tap the add button
twice, then readjust the slider to 10 and tap the add button twice more.

### Sleep
The sleep tracker displays the duration of the user's last sleep episode, or `---` if the user has not yet used the
sleep tracking feature. To enter sleep mode, navigate to the sleep view and press the moon button (or shift on the
keyboard). The background will darken, the moon will change to a sunrise icon, and several large Zs will appear to
signify the device is now in sleep mode. While in this state, navigation is locked. To "awaken", press the sunrise icon
(or shift). The total time slept will be displayed on the sleep view.

### Steps
The step tracker will display the users current step count for the day. The step count will update itself periodically
based on a random number generation, it can also be incremented using the "S" key. 

### Heartbeat
The heartbeat tracker will display the current bpm estimate for the user. Since there is no actual hardware measuring the 
user's heartbeat, the estimate is also based on a random number generator.

### Logs
Pressing the hamburger button (or enter on the keyboard) will toggle the data log, which displays recent data in a
condensed form. The log is updated every midnight, at which point the views are reset to 0.

*Technical Note:* You can press R on the keyboard to simulate a reset event.

## Technical

The activity tracker requires at least Java 8 (lambdas!) to compile. It was tested with IntelliJ IDEA on Windows and
Mac. The main class can be found at `src/teemtoo/Main.java`.