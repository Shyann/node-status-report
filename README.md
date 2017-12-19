# BT
## Description
This is a console application that reads and parses status notifications from a number of nodes in a network and generates a report on the latest status of each node.

### Example Input
```
1508405807242 1508405807141 vader HELLO
1508405807340 1508405807350 luke HELLO
1508405807378 1508405807387 luke LOST vader
1508405807467 1508405807479 luke FOUND r2d2
1508405807468 1508405807480 luke LOST leia
1508405807512 1508405807400 vader LOST luke
1508405807560 1508405807504 vader HELLO
```

### Example Output
```
vader ALIVE 1508405807560 vader HELLO
luke ALIVE 1508405807468 luke LOST leia
r2d2 ALIVE 1508405807467 luke FOUND r2d2
leia DEAD 1508405807468 luke LOST leia
```

## How To Run
  1. Download source files and navigate to directory
  2. Compile:
  ```
  javac *.java
  ```
  3. Execute
  ```
  java FileParser data
  ```
