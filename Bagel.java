import tester.*;

class BagelRecipe {
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  // input are measured in weight(ounce)
  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {

    if (flour < 0 || water < 0 || yeast < 0 || salt < 0 || malt < 0) {
      throw new IllegalArgumentException("all bagel recipes should be positive");
    }
    if (flour != water) {
      throw new IllegalArgumentException("given weight of flour and water are not equal");
    }
    if (yeast != malt) {
      throw new IllegalArgumentException("given weight of yeast and malt are not equal");
    }
    if (Math.abs((salt + yeast) * 20 - flour) > 0.001) {
      throw new IllegalArgumentException(
          "given weight of salt and yeast are not 1/20th of weight of given flour");
    }
    this.flour = flour;
    this.yeast = yeast;
    this.water = water;
    this.malt = malt;
    this.salt = salt;

  }

  // input are measured in weight(ounce)
  BagelRecipe(double flour, double yeast) {
    this(flour, flour, yeast, flour / 20 - yeast, yeast);

  }

  // input are measured in cups
  // note: yeast, salt, and malt are measured in teaspoon
  BagelRecipe(double flour, double yeast, double salt) {
    this(flour * 4.25, flour * 4.25, yeast / 48 * 5, salt / 48 * 10, yeast / 48 * 5);
  }

  boolean sameRecipe(BagelRecipe other) {
    return other != null
        && Math.abs(other.flour - this.flour) 
        <= 0.001 && Math.abs(other.water - this.water) <= 0.001
        && Math.abs(other.yeast - this.yeast) <= 0.001 && Math.abs(other.salt - this.salt) <= 0.001
        && Math.abs(other.malt - this.malt) <= 0.001;
  }
}

class ExamplesBagel {
  BagelRecipe bagel1 = new BagelRecipe(200.0, 200.0, 8.0, 2.0, 8.0);
  BagelRecipe bagel2 = new BagelRecipe(200, 8);
  BagelRecipe bagel3 = new BagelRecipe(400.0 / 4.25, 16.0 / 5 * 48, 4.0 / 10 * 48);

  BagelRecipe bagel4 = new BagelRecipe(100.0, 100.0, 4.0, 1.0, 4.0);
  BagelRecipe bagel5 = new BagelRecipe(200.0 / 4.25, 8.0 / 5 * 48, 2.0 / 10 * 48);
  
  boolean testConstructorFiveArg(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException(
            "all bagel recipes should be positive"), 
        "BagelRecipe", -200.0,
        200.0, 8.0, 2.0, 8.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "given weight of flour and water are not equal"),
            "BagelRecipe", 200.0, 202.0, 8.0, 2.0, 8.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "given weight of yeast and malt are not equal"),
            "BagelRecipe", 200.0, 200.0, 10.0, 2.0, 8.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "given weight of salt and yeast are "
                + "not 1/20th of weight of given flour"),
            "BagelRecipe", 200.0, 200.0, 8.0, 10.0, 8.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "all bagel recipes should be positive"),
            "BagelRecipe",
            -200.0, 40.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "all bagel recipes should be positive"), 
            "BagelRecipe",
            20.0, 10.0, -40.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "given weight of salt and yeast "
                + "are not 1/20th of weight of given flour"),
            "BagelRecipe", 10.0, 20.0, 40.0);
  }

  boolean testCheckRecipe(Tester t) {
    return t.checkExpect(this.bagel1.sameRecipe(this.bagel2), true)
        && t.checkExpect(this.bagel4.sameRecipe(this.bagel1), false)
        && t.checkExpect(this.bagel2.sameRecipe(this.bagel3), false)
        && t.checkExpect(this.bagel5.sameRecipe(this.bagel1), true)
        && t.checkExpect(this.bagel5.sameRecipe(null), false);
  }
}
