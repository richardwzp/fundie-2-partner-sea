import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import java.awt.Color;

interface IRiverDelta {
  /* see methods below */

  // render this riverdelta into an image
  WorldImage draw();

  // check if the river can be flooded,
  // the downstream parts of the delta (i.e., closer to the sea)
  // should always have at least as much capacity as their upstream parts
  boolean isFloodSafe();

  // helper for isFloodSafe
  // cap: keep track of the capacity of the delta that this one is comparing to
  boolean isFloodSafeHelper(double cap);

  // combine two delta, with angle oriented with the given two delta
  IRiverDelta combine(int leftLength, int rightLength, int leftCapacity, int rightCapacity,
      double leftAngle, double rightAngle, IRiverDelta newDelta);

  // increase the entire delta's degree by the given amount
  IRiverDelta increaseDegree(double deg);

  // returns the width of the delta,
  // from the leftmost place where it meets the sea to the rightmost place.
  double getWidth();

  // getWidth helper
  // ACCUMULATOR: sidewidth represents all the width that has been previously
  // built up
  double getWidthHelper(double SideWidth, boolean direction);
}

// represents the river reaching the sea
class Sea implements IRiverDelta {
  // the width used to draw the sea
  int width = 30;
  // the height used to draw the sea
  int height = 10;
  // render this Sea into an image

  // FIELDS:
  // this.width ... int
  // this.height ... int
  // METHODS:
  // this.draw() ... WorldImage
  // this.isFloodSafe() ... boolean
  // this.isFloodSafeHelper(double) ... boolean
  // this.combine(int, int, int, int, double, double, IRiverDelta) ... IRiverDelta
  // this.increaseDegree(double) ... IRiverDelta
  // this.getWidth() ... double
  // this.getWidthHelper(double, boolean) ... double
  // METHODS OF FIELDS:

  // render this sea into a world image
  public WorldImage draw() {
    // EVERYTHING IN THE CLASS WIDE TEMPLATE
    return new RectangleImage(this.width, this.height, OutlineMode.SOLID, Color.BLUE).movePinhole(0,
        this.height / 2);
  }

  // check if the river can be flooded,
  // the downstream parts of the delta (i.e., closer to the sea)
  // should always have at least as much capacity as their upstream parts
  public boolean isFloodSafe() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    return true;
  }

  // helper for isFloodSafe
  public boolean isFloodSafeHelper(double cap) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // FIELDS:
    // cap ... double
    return true;
  }

  // combine two delta, with angle oriented with the given two delta
  public IRiverDelta combine(int leftLength, int rightLength, int leftCapacity, int rightCapacity,
      double leftAngle, double rightAngle, IRiverDelta newDelta) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE:
    // FIELDS:
    // leftLength ... int
    // rightLength ... int
    // leftCapacity ... int
    // rightCapacity ... int
    // leftAngle ... double
    // rightAngle ... double
    // newDelta ... IRiverDelta
    // METHODS OF FIELDS:
    // newDelta.draw() ... WorldImage
    // newDelta.isFloodSafe() ... boolean
    // newDelta.isFloodSafeHelper(double) ... boolean
    // newDelta.combine(int, int, int, int, double, double, IRiverDelta) ...
    // IRiverDelta
    // newDelta.increaseDegree(double) ... IRiverDelta
    // newDelta.getWidth() ... double
    // newDelta.getWidthHelper(double, boolean) ... double
    return new Fork(leftLength, rightLength, leftCapacity, rightCapacity, leftAngle, rightAngle,
        this, newDelta.increaseDegree(90 - rightAngle));
  }

  // increase the entire delta's degree by the given amount
  public IRiverDelta increaseDegree(double deg) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // deg ... double
    return this;
  }

  // returns the width of the delta,
  // from the leftmost place where it meets the sea to the rightmost place.
  public double getWidth() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    return 0;
  }

  // getWidth helper
  public double getWidthHelper(double SideWidth, boolean direction) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // FIELDS:
    // sideWidth ... double
    // direction ... boolean
    return SideWidth;
  }

}

// represents a straight section of flowing water
class Stream implements IRiverDelta {
  // How long this flow is
  int length;
  // The maximum amount of water this flow can carry per second, in millions of
  // gallons/second
  int capacity;
  // The angle (in degrees) of this flow, relative to the +x axis
  double theta;
  // The rest of the delta
  IRiverDelta delta;

  // FIELDS:
  // this.length ... int
  // this.capacity ... int
  // this.theta ... double
  // this.delta ... IRiverDelta
  // METHODS:
  // this.draw() ... WorldImage
  // this.isFloodSafe() ... boolean
  // this.isFloodSafeHelper(double) ... boolean
  // this.combine(int, int, int, int, double, double, IRiverDelta) ... IRiverDelta
  // this.increaseDegree(double) ... IRiverDelta
  // this.getWidth() ... double
  // this.getWidthHelper(double, boolean) ... double
  // METHODS OF FIELDS:
  // this.delta.draw() ... WorldImage
  // this.delta.isFloodSafe() ... boolean
  // this.delta.isFloodSafeHelper(double) ... boolean
  // this.delta.combine(int, int, int, int, double, double, IRiverDelta) ...
  // IRiverDelta
  // this.delta.increaseDegree(double) ... IRiverDelta
  // this.delta.getWidth() ... double
  // this.delta.getWidthHelper(double, boolean) ... double

  Stream(int length, int capacity, double theta, IRiverDelta delta) {
    this.length = length;
    this.capacity = capacity;
    this.theta = theta;
    this.delta = delta;
  }

  // render this riverdelta into an image
  public WorldImage draw() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    WorldImage rivModified = new OverlayImage(this.delta.draw(),
        new RotateImage(
            new RectangleImage(this.capacity, this.length, OutlineMode.SOLID, Color.ORANGE)
                .movePinhole(0, -length / 2),
            90 - this.theta)).movePinhole(-Math.sin(Math.toRadians(90 - this.theta)) * this.length,
                Math.cos(Math.toRadians(90 - this.theta)) * this.length);

    return new VisiblePinholeImage(rivModified);
  }

  // check if the river can be flooded,
  // the downstream parts of the delta (i.e., closer to the sea)
  // should always have at least as much capacity as their upstream parts
  public boolean isFloodSafe() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    return this.delta.isFloodSafeHelper(this.capacity);
  }

  // helper for isFloodSafe
  public boolean isFloodSafeHelper(double cap) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // FIELDS:
    // cap ... double
    return this.capacity >= cap && this.delta.isFloodSafeHelper(this.capacity);
  }

  // combine two delta, with angle oriented with the given two delta
  public IRiverDelta combine(int leftLength, int rightLength, int leftCapacity, int rightCapacity,
      double leftAngle, double rightAngle, IRiverDelta newDelta) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE:
    // FIELDS:
    // leftLength ... int
    // rightLength ... int
    // leftCapacity ... int
    // rightCapacity ... int
    // leftAngle ... double
    // rightAngle ... double
    // newDelta ... IRiverDelta
    // METHODS OF FIELDS:
    // newDelta.draw() ... WorldImage
    // newDelta.isFloodSafe() ... boolean
    // newDelta.isFloodSafeHelper(double) ... boolean
    // newDelta.combine(int, int, int, int, double, double, IRiverDelta) ...
    // IRiverDelta
    // newDelta.increaseDegree(double) ... IRiverDElta
    // newDelta.getWidth() ... double
    // newDelta.getWidthHelper(double, boolean) ... double
    return new Fork(leftLength, rightLength, leftCapacity, rightCapacity, leftAngle, rightAngle,
        new Stream(this.length, this.capacity, this.theta + leftAngle - 90,
            this.delta.increaseDegree(leftAngle - 90)),
        newDelta.increaseDegree(rightAngle - 90));
  }

  // increase the entire delta's degree by the given amount
  public IRiverDelta increaseDegree(double deg) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // deg ... double
    return new Stream(this.length, this.capacity, this.theta + deg, this.delta.increaseDegree(deg));
  }

  // returns the width of the delta,
  // from the leftmost place where it meets the sea to the rightmost place.
  public double getWidth() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    double riverLength = Math.cos(Math.toRadians(this.theta)) * this.length;
    double leftMax = this.delta.getWidthHelper(riverLength, true);
    double rightMax = this.delta.getWidthHelper(riverLength, false);
    return Math.max(leftMax, rightMax) - Math.min(leftMax, rightMax);

  }

  // getWidth helper
  public double getWidthHelper(double SideWidth, boolean direction) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // FIELDS:
    // sideWidth ... double
    // direction ... boolean
    double riverLength = Math.cos(Math.toRadians(this.theta)) * this.length;
    return this.delta.getWidthHelper(SideWidth + riverLength, direction);
  }

}

// represents the river splitting in two
class Fork implements IRiverDelta {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The amount of water the left and right branches can carry
  int leftCapacity;
  int rightCapacity;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the delta
  IRiverDelta left;
  IRiverDelta right;

  // FIELDS:
  // this.leftLength ... int
  // this.rightLength ... int
  // this.leftCapacity ... int
  // this.rightCapacity ... int
  // this.leftTheta ... double
  // this.rightTheta ... double
  // this.leftdelta ... IRiverDelta
  // this.rightdelta ... IRiverDelta
  // METHODS:
  // this.draw() ... WorldImage
  // this.isFloodSafe() ... boolean
  // this.isFloodSafeHelper(double) ... boolean
  // this.combine(int, int, int, int, double, double, IRiverDelta) ... IRiverDelta
  // this.increaseDegree(double) ... IRiverDElta
  // this.getWidth() ... double
  // this.getWidthHelper(double, boolean) ... double
  // METHODS OF FIELDS:
  // this.leftDelta.draw() ... WorldImage
  // this.leftDelta.isFloodSafe() ... boolean
  // this.leftDelta.isFloodSafeHelper(double) ... boolean
  // this.leftDelta.combine(int, int, int, int, double, double, IRiverDelta) ...
  // IRiverDelta
  // this.leftDelta.increaseDegree(double) ... IRiverDElta
  // this.leftDelta.getWidth() ... double
  // this.leftDelta.getWidthHelper(double, boolean) ... double
  // this.rightDelta.draw() ... WorldImage
  // this.rightDelta.isFloodSafe() ... boolean
  // this.rightDelta.isFloodSafeHelper(double) ... boolean
  // this.rightDelta.combine(int, int, int, int, double, double, IRiverDelta) ...
  // IRiverDelta
  // this.rightDelta.increaseDegree(double) ... IRiverDElta
  // this.rightDelta.getWidth() ... double
  // this.rightDelta.getWidthHelper(double, boolean) ... double

  Fork(int leftLength, int rightLength, int leftCapacity, int rightCapacity, double leftTheta,
      double rightTheta, IRiverDelta left, IRiverDelta right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftCapacity = leftCapacity;
    this.rightCapacity = rightCapacity;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

  // render this river fork into an image
  public WorldImage draw() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    return new OverlayImage(
        new Stream(this.leftLength, this.leftCapacity, this.leftTheta, this.left).draw(),
        new Stream(this.rightLength, this.rightCapacity, this.rightTheta, this.right).draw());
  }

  // check if the river can be flooded,
  // the downstream parts of the delta (i.e., closer to the sea)
  // should always have at least as much capacity as their upstream parts
  public boolean isFloodSafe() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    return this.left.isFloodSafeHelper(this.leftCapacity)
        && this.right.isFloodSafeHelper(this.rightCapacity);
  }

  // isFloodSafe helper
  public boolean isFloodSafeHelper(double cap) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // FIELDS:
    // cap ... double
    return this.leftCapacity + this.rightCapacity >= cap
        && this.left.isFloodSafeHelper(this.leftCapacity)
        && this.right.isFloodSafeHelper(this.rightCapacity);
  }

//combine two delta, with angle oriented with the given two delta
  public IRiverDelta combine(int leftLength, int rightLength, int leftCapacity, int rightCapacity,
      double leftAngle, double rightAngle, IRiverDelta newDelta) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE:
    // FIELDS:
    // leftLength ... int
    // rightLength ... int
    // leftCapacity ... int
    // rightCapacity ... int
    // leftAngle ... double
    // rightAngle ... double
    // newDelta ... IRiverDelta
    // METHODS OF FIELDS:
    // newDelta.draw() ... WorldImage
    // newDelta.isFloodSafe() ... boolean
    // newDelta.isFloodSafeHelper(double) ... boolean
    // newDelta.combine(int, int, int, int, double, double, IRiverDelta)
    // newDelta.increaseDegree(double) ... IRiverDElta
    // newDelta.getWidth() ... double
    // newDelta.getWidthHelper(double, boolean) ... double
    return new Fork(leftLength, rightLength, leftCapacity, rightCapacity, leftAngle, rightAngle,
        new Fork(this.leftLength, this.rightLength, this.leftCapacity, this.rightCapacity,
            this.leftTheta + leftAngle - 90, this.rightTheta + 90 - rightAngle,
            this.left.increaseDegree(leftAngle - 90), this.right.increaseDegree(rightAngle - 90)),
        newDelta.increaseDegree(rightAngle - 90));
  }

  // increase the entire delta's degree by the given amount
  public IRiverDelta increaseDegree(double deg) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // deg ... double
    return new Fork(this.leftLength, this.rightLength, this.leftCapacity, this.rightCapacity,
        this.leftTheta + deg, this.rightTheta + deg, this.left.increaseDegree(deg),
        this.right.increaseDegree(deg));
  }

//returns the width of the delta, 
  // from the leftmost place where it meets the sea to the rightmost place.
  public double getWidth() {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    double leftRiverLength = Math.cos(Math.toRadians(this.leftTheta)) * this.leftLength;
    double rightRiverLength = Math.cos(Math.toRadians(this.rightTheta)) * this.rightLength;

    double leftDeltaLeftMax = this.left.getWidthHelper(leftRiverLength, true);
    double leftDeltarightMax = this.left.getWidthHelper(leftRiverLength, false);

    double rightDeltaLeftMax = this.right.getWidthHelper(rightRiverLength, true);
    double rightDeltarightMax = this.right.getWidthHelper(rightRiverLength, false);
    return Math.max(Math.max(leftDeltaLeftMax, leftDeltarightMax),
        Math.max(rightDeltaLeftMax, rightDeltarightMax))
        - Math.min(Math.min(leftDeltaLeftMax, leftDeltarightMax),
            Math.min(rightDeltaLeftMax, rightDeltarightMax));

  }

  // getWidth helper
  public double getWidthHelper(double SideWidth, boolean direction) {
    // EVERYTHING FROM CLASS WIDE TEMPLATE
    // FIELDS:
    // sideWidth ... double
    // direction ... boolean
    double leftRiverLength = Math.cos(Math.toRadians(this.leftTheta)) * this.leftLength;
    double rightRiverLength = Math.cos(Math.toRadians(this.rightTheta)) * this.rightLength;
    if (direction) {
      // if this is looking for left bound
      return Math.min(this.left.getWidthHelper(SideWidth + leftRiverLength, direction),
          this.right.getWidthHelper(SideWidth + rightRiverLength, direction));
    }
    else {
      // if this is looking for left bound
      return Math.max(this.left.getWidthHelper(SideWidth + leftRiverLength, direction),
          this.right.getWidthHelper(SideWidth + rightRiverLength, direction));
    }
  }

}

class ExamplesRiverDelta {
  IRiverDelta sea1 = new Sea();
  IRiverDelta stream1 = new Stream(50, 15, 120, this.sea1);
  IRiverDelta stream2 = new Stream(80, 10, 30, this.stream1);
  IRiverDelta stream3 = new Stream(50, 20, 100, this.stream2);
  IRiverDelta stream4 = new Stream(60, 14, 130, this.stream3);

  IRiverDelta fork1 = new Fork(70, 70, 5, 5, 110, 70, this.sea1, this.sea1);
  IRiverDelta fork2 = new Fork(70, 70, 8, 8, 130, 40, this.fork1, this.fork1);
  IRiverDelta fork3 = new Fork(70, 70, 14, 15, 130, 40, this.fork2, this.sea1);
  IRiverDelta forking = new Stream(50, 10, 90, this.fork3);

  IRiverDelta forkstream1 = new Fork(40, 40, 10, 15, 120, 60, this.stream3, this.stream4);

  IRiverDelta DELTA1 = new Fork(30, 30, 11, 11, 135, 40, new Sea(), new Sea());
  IRiverDelta DELTA2 = new Fork(30, 30, 12, 12, 115, 65, new Sea(), new Sea());
  IRiverDelta combined = DELTA1.combine(40, 50, 22, 24, 150, 30, DELTA2);

  boolean testDrawSea(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(this.forking.draw(), 250, 250)) && c.show();
  }

  // test for isfloodsafe method
  boolean testIsFloodSafe(Tester t) {
    return t.checkExpect(this.fork1.isFloodSafe(), true)
        && t.checkExpect(this.sea1.isFloodSafe(), true)
        && t.checkExpect(this.stream1.isFloodSafe(), true)
        && t.checkExpect(this.stream2.isFloodSafe(), true)
        && t.checkExpect(this.combined.isFloodSafe(), true)
        && t.checkExpect(this.forking.isFloodSafe(), true);

  }

  boolean testRiverLength(Tester t) {
    return t.checkExpect(this.fork1.getWidth(), 0.0);
  }

}
