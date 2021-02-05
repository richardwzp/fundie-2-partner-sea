import tester.*;

interface ILoString {

  // reverse all item in this list
  // note:
  // this is very different from the way we implement reverse in fundie 1
  // because we are in an oo setting, we can be much more creative with how we
  // reverse a list here. Because we have 4 different implementation of lists,
  // we can simply switch an implementation to put an element from front to the
  // end, whcih makes it drastically easier.
  ILoString reverse();

  // normalize the list of String, so it only contains MtLoString and ConsLoString
  ILoString normalize();

  // noramlize the list, helper function
  // ACCUMULATOR: the list of string to add at the end
  ILoString normalizeHelper(ILoString acc);

  // return a list of all intermediate result scanning from the left
  ILoString scanConcat();

  // return a list of all intermediate result with the given estabalished string
  // ACCUMULATOR: the String that needs to be added to the front of the term
  ILoString scanConcatHelper(String acc);

  // return a list of all intermediate result with the given estabalished string,
  // with the given list of string at the end
  // ACCUMULATOR 1: the String that needs to be added to the front of the term
  // ACCUMULATOR 2: the list of String that needs to be added to the end of the
  // list
  ILoString scanConcatHelperHelper(String acc, ILoString acc2);

}

class MtLoString implements ILoString {
  MtLoString() {
  }
  // FIELD:
  // METHOD:
  // this.reverse() ... ILoString
  // this.normalize() ... ILoString
  // this.normalizeHelper(ILoString) ... ILoString
  // this.scanConcat() ... ILoString
  // this.scanConcatHelper(String) ... ILoString
  // this.scanConcatHelperHelper(String, ILoString) ... ILoString
  // METHOD OF FIELD:

  // return the reverse of empty list
  public ILoString reverse() {
    // EVERYTHING THE CLASS HAS
    return new MtLoString();
  }

  // normalize the empty list
  public ILoString normalize() {
    // EVERYTHING THE TEMPLATE HAS
    return new MtLoString();
  }

  // helper function for normalize
  // ACCUMULATOR: all the element already normalized
  public ILoString normalizeHelper(ILoString acc) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc ... ILoString
    // METHODS OF FIELDS:
    // acc.reverse() ... ILoString
    // acc.normalize() ... ILoString
    // acc.normalizeHelper(ILoString) ... ILoString
    // acc.scanConcat() ... ILoString
    // acc.scanConcatHelper(String) ... ILoString
    // acc.scanConcatHelperHelper(String, ILoString) ... ILoString
    return acc;
  }

  // return a list of all intermediate result scanning from the left
  public ILoString scanConcat() {
    // EVERYTHING THE TEMPLATE HAS
    return new MtLoString();
  }

  // return a list of all intermediate result scanning from the left with the acc
  // in front
  public ILoString scanConcatHelper(String acc) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc ... String
    return new MtLoString();
  }

  // return a list of all intermediate result with the given estabalished string,
  // with the given list of string at the end
  public ILoString scanConcatHelperHelper(String acc, ILoString acc2) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc ... ILoString
    // METHODS OF FIELDS:
    // acc2.reverse() ... ILoString
    // acc2.normalize() ... ILoString
    // acc2.normalizeHelper(ILoString) ... ILoString
    // acc2.scanConcat() ... ILoString
    // acc2/scanConcatHelper(String) ... ILoString
    // acc2.scanConcatHelperHelepr(String, ILoString) ... ILoString
    return acc2.scanConcatHelper(acc);
  }
}

class ConsLoString implements ILoString {

  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  // FIELDs:
  // first ... String
  // rest ... ILoString
  // METHODS:
  // this.reverse() ... ILoString
  // this.normalize() ... ILoString
  // this.normalizeHelper(ILoString) ... ILoString
  // this.scanConcat() ... ILoString
  // this.scanConcatHelper(String) ... ILoString
  // this.scanConcatHelperHelper(String, ILoString) ... ILoString
  // METHODS OF FIELDS:
  // this.rest.reverse() ... ILoString
  // this.rest.normalize() ... ILoString
  // this.rest.normalizeHelper(ILoString) ... ILoString
  // this.rest.scanConcat() ... ILoString
  // this.rest.scanConcatHelper(String) ... ILoString
  // this.rest.scanConcatHelperHelper(String, ILoString) ... ILoString
  // METHOD FOR FIELDS:

  // return the reverse of this list
  public ILoString reverse() {
    // EVERYTHING THE CLASS HAS
    return new SnocLoString(this.rest.reverse(), this.first);
  }

  // normalize this conslostring
  public ILoString normalize() {
    // EVERYTHING THE TEMPLATE HAS
    // note:
    // no need to call helper, list is still normal
    return new ConsLoString(this.first, this.rest.normalize());
  }

  // helper function for normalize
  // ACCUMULATOR: all the element already normalized
  public ILoString normalizeHelper(ILoString acc) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc ... ILoString
    // METHODS OF FIELDS:
    // acc.reverse() ... ILoString
    // acc.normalize() ... ILoString
    // acc.normalizeHelper(ILoString) ... ILoString
    // acc.scanConcat() ... ILoString
    // acc.scanConcatHelper(String) ... ILoString
    // acc.scanConcatHelperHelper(String, ILoString)
    return new ConsLoString(this.first, this.rest.normalizeHelper(acc));
  }

  // return a list of all intermediate result scanning from the left
  public ILoString scanConcat() {
    // EVERYTHING THE TEMPLATE HAS
    return this.scanConcatHelper("");
  }

  // return a list of all intermediate result scanning from the left with acc in
  // front
  public ILoString scanConcatHelper(String acc) {
    // EVERYTHING THE TEMPLATE HAS
    // acc ... String
    return new ConsLoString(acc + this.first, this.rest.scanConcatHelper(acc + this.first));
  }

  // return a list of all intermediate result scanning from the left with acc in
  // front,
  // acc2 at the end
  public ILoString scanConcatHelperHelper(String acc, ILoString acc2) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc2 ... ILoString
    // METHODS OF FIELDS:
    // acc2.reverse() ... ILoString
    // acc2.normalize() ... ILoString
    // acc2.normalizeHelper(ILoString) ... ILoString
    // acc2.scanConcat() ... ILoString
    // acc2/scanConcatHelper(String) ... ILoString
    // acc2.scanConcatHelperHelepr(String, ILoString) ... ILoString
    return new ConsLoString(acc + this.first,
        this.rest.scanConcatHelperHelper(acc + this.first, acc2));
  }

}

class SnocLoString implements ILoString {

  ILoString front;
  String last;

  SnocLoString(ILoString front, String last) {
    this.front = front;
    this.last = last;
  }
  // FIELDs:
  // front ... ILoString
  // end ... String
  // METHODS:
  // this.reverse() ... ILoString
  // this.normalize() ... ILoString
  // this.noramlizeHelper(ILoString) ... ILoString
  // this.scanConcat() ... ILoString
  // this.scanConcatHelper(String) ... ILoString
  // this.scanConcatHelperHelper(String, ILoString) ... ILoString
  // METHOD FOR FIELDS:
  // this.front.reverse() ... ILoString
  // this.front.normalize() ... ILoString
  // this.front.normalizeHelper(ILoString) ... ILoString
  // this.front.scanConcat() ... ILoString
  // this.front.scanConcatHelper(String) ... ILoString
  // this.front.scanConcatHelperHelper(String, ILoString) ... ILoString

  // reverse this snoc list
  public ILoString reverse() {
    // EVERYTHING IN THE CLASS TEMPLATE
    return new ConsLoString(this.last, this.front.reverse());
  }

  // normalize this Snoclostring
  public ILoString normalize() {
    // EVERYTHING THE TEMPLATE HAS
    return this.front.normalizeHelper(new ConsLoString(this.last, new MtLoString()));
  }

  // helper function for normalize
  public ILoString normalizeHelper(ILoString acc) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc ... ILoString
    // METHODS OF FIELDS:
    // acc.reverse() ... ILoString
    // acc.normalize() ... ILoString
    // acc.normalizeHelper(ILoString) ... ILoString
    // acc.scanConcat() ... ILoString
    // acc.scanConcatHelper(String) ... ILoString
    // acc.scanConcatHelperHelper(String, ILoString) ... ILoString
    return this.front.normalizeHelper(new ConsLoString(this.last, acc));
  }

  // return a list of all intermediate result scanning from the left
  public ILoString scanConcat() {
    // EVERYTHING IN THE TEMPLATE
    return this.normalize().scanConcat();
  }

  // return a list of all intermediate result scanning from the left
  public ILoString scanConcatHelper(String acc) {
    // EVERYTHING IN THE TEMPLATE
    // acc ... String
    return this.front.scanConcatHelperHelper(acc, new ConsLoString(this.last, new MtLoString()));
  }

  public ILoString scanConcatHelperHelper(String acc, ILoString acc2) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc2 ... ILoString
    // METHODS OF FIELDS:
    // acc2.reverse() ... ILoString
    // acc2.normalize() ... ILoString
    // acc2.normalizeHelper(ILoString) ... ILoString
    // acc2.scanConcat() ... ILoString
    // acc2/scanConcatHelper(String) ... ILoString
    // acc2.scanConcatHelperHelepr(String, ILoString) ... ILoString
    return this.front.scanConcatHelperHelper(acc, new ConsLoString(this.last, acc2));
  }
}

class AppendLoString implements ILoString {

  ILoString front;
  ILoString back;

  AppendLoString(ILoString front, ILoString back) {
    this.front = front;
    this.back = back;
  }
  // FIELDs:
  // front ... ILoString
  // back ... ILoString
  // METHODS:
  // this.reverse() ... ILoString
  // this.normalize() ... ILoString
  // this.normalizeHelper(ILoString) ... ILoString
  // this.scanConcat() ... ILoString
  // this.scanConcatHelper(String) ... ILoString
  // this.scanConcatHelperHelper(String, ILoString) ... ILoString
  // METHODS OF FIELDS:
  // this.front.reverse() ... ILoString
  // this.back.reverse() ... ILoString
  // this.front.normalize() ... ILoString
  // this.back.normalize() ... ILoString
  // this.front.normalizeHelper(ILoString) ... ILoString
  // this.back.normalizeHelper(ILoString) ... ILoString
  // this.front.scanConcat() ... ILoString
  // this.front.scanConcatHelper(String) ... ILoString
  // this.front.scanConcatHelperHelper(String, ILoString) ... ILoString
  // this.back.scanConcat() ... ILoString
  // this.back.scanConcatHelper(String) ... ILoString
  // this.back.scanConcatHelperHelper(String, ILoString) ... ILoString

  // reverse this Appendlist
  public ILoString reverse() {
    // EVERTHING IN THE CLASS TEMPLATE
    return new AppendLoString(this.back.reverse(), this.front.reverse());
  }

  // normalize this appendlostring
  public ILoString normalize() {
    // EVERYTHING IN THE TEMPLATE
    return this.normalizeHelper(new MtLoString());
  }

  // helper function for normalize
  // ACCUMULATOR: all the element already normalized
  public ILoString normalizeHelper(ILoString acc) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc ... ILoString
    // METHODS OF FIELDS:
    // acc.reverse() ... ILoString
    // acc.normalize() ... ILoString
    // acc.normalizeHelper(ILoString) ... ILoString
    // acc.scanConcat() ... ILoString
    // acc.scanConcat(String) ... ILoString
    return this.front.normalizeHelper(this.back.normalizeHelper(acc));
  }

  // return a list of all intermediate result scanning from the left
  public ILoString scanConcat() {
    // EVERYTHING IN THE TEMPLATE
    return this.front.scanConcatHelperHelper("", this.back);
  }

  // return a list of all intermediate result scanning from the left
  public ILoString scanConcatHelper(String acc) {
    // EVERYTHING IN THE TEMPLATE
    // FIELDS:
    // acc ... String
    return this.front.scanConcatHelperHelper(acc, this.back);
  }

  public ILoString scanConcatHelperHelper(String acc, ILoString acc2) {
    // EVERYTHING THE TEMPLATE HAS
    // FIELDS:
    // acc2 ... ILoString
    // METHODS OF FIELDS:
    // acc2.reverse() ... ILoString
    // acc2.normalize() ... ILoString
    // acc2.normalizeHelper(ILoString) ... ILoString
    // acc2.scanConcat() ... ILoString
    // acc2/scanConcatHelper(String) ... ILoString
    // acc2.scanConcatHelperHelepr(String, ILoString) ... ILoString
    return this.front.scanConcatHelperHelper(acc, new AppendLoString(this.back, acc2));
    // DESIGN CHOICE:
    // original we thought our deisgn went wrong, and we didn't know what to do with
    // three lists.
    // but then, we realized, YES! WE DO KNOW HOW TO DEAL WITH THREE LIST!
    // JUST COMBINED THE LATTER TWO!
    // USE APPENDLIST!
    // much happiness, stucked on this for way too long
  }
}

class ExamplesList {
  ILoString str1 = new MtLoString();
  ILoString str2 = new ConsLoString("a", this.str1);
  ILoString str3 = new ConsLoString("a", new ConsLoString("b", new ConsLoString("c", this.str1)));
  ILoString str4 = new ConsLoString("c", new ConsLoString("b", new ConsLoString("a", this.str1)));
  ILoString str5 = new ConsLoString("a",
      new ConsLoString("ab", new ConsLoString("abc", this.str1)));
  ILoString str6 = new ConsLoString("a", new ConsLoString("b",
      new ConsLoString("c", new ConsLoString("d", new ConsLoString("e", this.str1)))));
  ILoString str7 = new ConsLoString("d", new ConsLoString("e", new ConsLoString("f", this.str1)));
  ILoString str8 = new ConsLoString("d", new ConsLoString("e", this.str1));
  ILoString str9 = new ConsLoString("a", new ConsLoString("ab",
      new ConsLoString("abc", new ConsLoString("abcd", new ConsLoString("abcde", this.str1)))));
  ILoString str10 = new ConsLoString("b", new ConsLoString("c", this.str1));
  ILoString str11 = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("c",
          new ConsLoString("d", new ConsLoString("e", new ConsLoString("a", new ConsLoString("b",
              new ConsLoString("c", new ConsLoString("d", new ConsLoString("e", this.str1))))))))));
  ILoString str12 = new ConsLoString("ka",
      new ConsLoString("kab", new ConsLoString("kabc", this.str1)));
  ILoString str13 = new ConsLoString("qwea", new ConsLoString("qweab", new ConsLoString("qweabc",
      new ConsLoString("qweabcd", new ConsLoString("qweabcde", this.str1)))));
  ILoString str14 = new ConsLoString("ka",
      new ConsLoString("kab", new ConsLoString("kabc", new ConsLoString("kabca", this.str1))));
  ILoString str15 = new ConsLoString("qwea",
      new ConsLoString("qweab",
          new ConsLoString("qweabc",
              new ConsLoString("qweabcd", new ConsLoString("qweabcde", new ConsLoString("qweabcdea",
                  new ConsLoString("qweabcdeab", new ConsLoString("qweabcdeabc", this.str1))))))));

  ILoString snoc1 = new SnocLoString(new SnocLoString(new SnocLoString(this.str1, "a"), "b"), "c");
  ILoString snoc2 = new SnocLoString(new SnocLoString(
      new SnocLoString(
          new SnocLoString(new SnocLoString(new SnocLoString(this.str1, "a"), "b"), "c"), "d"),
      "e"), "f");
  ILoString snoc3 = new SnocLoString(
      new SnocLoString(
          new SnocLoString(new SnocLoString(new SnocLoString(this.str1, "e"), "d"), "c"), "b"),
      "a");
  ILoString snoc4 = new SnocLoString(new SnocLoString(new SnocLoString(this.str1, "c"), "b"), "a");
  ILoString snoc5 = new SnocLoString(new SnocLoString(this.str1, "e"), "d");
  ILoString snoc6 = new SnocLoString(new SnocLoString(this.str1, "d"), "e");

  ILoString appen1 = new AppendLoString(this.str3, this.str8);
  ILoString appen2 = new AppendLoString(this.str3, this.str7);
  ILoString appen3 = new AppendLoString(this.snoc5, this.snoc4);
  ILoString appen4 = new AppendLoString(
      new AppendLoString(
          new AppendLoString(new ConsLoString("a", this.str1), new ConsLoString("b", this.str1)),
          new AppendLoString(new ConsLoString("c", this.str1), new ConsLoString("d", this.str1))),
      new AppendLoString(new ConsLoString("e", this.str1), this.str1));

  ILoString mix1 = new AppendLoString(this.snoc1, this.str8);
  ILoString mix2 = new SnocLoString(this.str6, "f");
  ILoString mix3 = new ConsLoString("f", this.snoc3);

  // tests for method normalizeHelper
  boolean testNormalizeHelper(Tester t) {
    return t.checkExpect(str1.normalizeHelper(str3), str3)
        && t.checkExpect(this.str2.normalizeHelper(this.str10), this.str3)
        && t.checkExpect(this.snoc1.normalizeHelper(this.str8), this.str6)
        && t.checkExpect(this.appen1.normalizeHelper(this.str6), this.str11)
        && t.checkExpect(this.mix1.normalizeHelper(this.str6), this.str11);
  }

  // tests for method normalize
  boolean testNormalize(Tester t) {
    return t.checkExpect(this.str1.normalize(), this.str1)
        && t.checkExpect(this.str1.normalize(), this.str1)
        && t.checkExpect(this.str2.normalize(), this.str2)
        && t.checkExpect(this.str5.normalize(), this.str5)
        && t.checkExpect(this.snoc1.normalize(), this.str3)
        && t.checkExpect(this.appen1.normalize(), this.str6)
        && t.checkExpect(this.mix1.normalize(), this.str6)
        && t.checkExpect(this.appen4.normalize(), this.str6);
  }

  // tests for method reverse
  boolean testReverse(Tester t) {
    return t.checkExpect(this.str1, this.str1) && t.checkExpect(this.str3.reverse(), this.snoc4)
        && t.checkExpect(this.snoc4.reverse(), this.str3)
        && t.checkExpect(this.appen1.reverse(), this.appen3)
        && t.checkExpect(this.mix2.reverse(), this.mix3);
  }

  // tests for method scanConcatHelper
  boolean testConcatHelper(Tester t) {
    return t.checkExpect(this.str1, this.str1)
        && t.checkExpect(this.str3.scanConcatHelper("k"), this.str12)
        && t.checkExpect(this.snoc1.scanConcatHelper("k"), this.str12)
        && t.checkExpect(this.appen4.scanConcatHelper("qwe"), this.str13)
        && t.checkExpect(this.mix1.scanConcatHelper("qwe"), this.str13);
  }

  // tests for method scanConcatHelper
  boolean testConcatHelperHelper(Tester t) {
    return t.checkExpect(str1.scanConcatHelperHelper("k", str3), this.str12)
        && t.checkExpect(this.str1.scanConcatHelperHelper("qwerty", this.str1), this.str1)
        && t.checkExpect(this.str3.scanConcatHelperHelper("k", this.str2), this.str14)
        && t.checkExpect(this.snoc1.scanConcatHelperHelper("k", this.str2), this.str14)
        && t.checkExpect(this.appen4.scanConcatHelperHelper("qwe", this.str3), this.str15)
        && t.checkExpect(this.mix1.scanConcatHelperHelper("qwe", this.str3), this.str15);
  }

  // test for method scanConcat
  boolean testConcat(Tester t) {
    return t.checkExpect(this.str1, this.str1) && t.checkExpect(this.str3.scanConcat(), this.str5)
        && t.checkExpect(this.snoc1.scanConcat(), this.str5)
        && t.checkExpect(this.appen4.scanConcat(), this.str9)
        && t.checkExpect(this.mix1.scanConcat(), this.str9);
  }
}
