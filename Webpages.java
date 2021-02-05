import tester.*;

class Webpage {
  String name;
  ILoContent content;

  Webpage(String name, ILoContent content) {
    this.name = name;
    this.content = content;
  }

  // FIELD:
  // this.name ... String
  // this.content ... ILoContent
  // METHOD:
  // totalCredits() ... int
  // getMeg() ... double
  // pictureInfo() ... String
  // METHOD OF FIELDS:
  // this.content.totalMeg() ... double
  // this.content.allPictureInfo() ... String

  // return the total credits cost to build the website
  int totalCredits() {
    // template:
    // everything from our class-wide template...
    return (int) Math.ceil(this.getMeg()) * 50;
  }

  // return the size of this webpage's content in megabytes
  double getMeg() {
    // template:
    // everything from our class-wide template...
    return this.content.totalMeg();
  }

  // produces one String that has in it the title of all pictures reachable from a
  // webpage,
  // with their description in parentheses, and each separated by comma and space.
  String pictureInfo() {
    // template:
    // everything from our class-wide template...
    return this.content.allPictureInfo();
  }
}

interface ILoContent {
  // to find the total megabytes in this list of content
  double totalMeg();

  // to combine all the picture info in this list of content
  String allPictureInfo();
}

class MtLoContent implements ILoContent {
  // METHODS:
  // this.totalMeg() ... double
  // this.allPictureInfo() ... String

  // to find the total megabytes in this empty list
  public double totalMeg() {
    // template:
    // everything from our class-wide template
    return 0;
  }

  // to find picture info in this empty list
  public String allPictureInfo() {
    // template:
    // everything from our class-wide template
    return "";
  }

}

class ConsLoContent implements ILoContent {
  IContent first;
  ILoContent rest;

  ConsLoContent(IContent first, ILoContent rest) {
    this.first = first;
    this.rest = rest;
  }

  // FIELDS:
  // this.first ... IContent
  // this.rest ... ILoContent
  // METHODS:
  // this.totalMeg() ... double
  // this.allPictureInfo() ... String
  // METHODS OF FIELDS:
  // this.first.getMeg() ... double
  // this.rest.totalMeg() ... double
  // this.rest.allPictureInfo() ... String

  // to find the total Megabytes in this list of content
  public double totalMeg() {
    // everything from our class-wide template
    return this.first.getMeg() + this.rest.totalMeg();
  }

  // to find all picture info in this list of content
  public String allPictureInfo() {
    // everything from our class-wide template
    return this.first.concatInfo(this.rest.allPictureInfo());

  }

}

interface IContent {
  // to get the size of this content
  double getMeg();

  // to put the info of this content in front of the given String,
  // if this content is a picture
  String concatInfo(String other);
}

class Text implements IContent {
  String name;
  int numLines;
  boolean inMarkdown;

  Text(String name, int numLines, boolean inMarkdown) {
    this.name = name;
    this.numLines = numLines;
    this.inMarkdown = inMarkdown;
  }
  // FIELDS:
  // this.name ... String
  // this.numLine ... int
  // this.inMarkdown ... boolean
  // METHODS:
  // this.getMeg() ... double
  // this.conactInfo(String) ... String

  // return the Megabytes of the text
  public double getMeg() {
    // everything from our class-wide template
    return 0;
  }

  // return the given picture info
  public String concatInfo(String other) {
    // everything from our class-wide template
    return other;
  }

}

class Picture implements IContent {
  String name;
  String description;
  double megabytes;

  Picture(String name, String description, double megabytes) {
    this.name = name;
    this.description = description;
    this.megabytes = megabytes;
  }
  //
  // FIELDS:
  // this.name ... String
  // this.description ... String
  // this.megabytes ... double
  // METHODS:
  // this.getMeg() ... double
  // this.concatInfo() ... String

  // return the megabytes of this Picture
  public double getMeg() {
    // everything from our class-wide template
    return this.megabytes;
  }

  // return this picture's info appended with other
  public String concatInfo(String other) {
    // everything from our class-wide template
    if (other.length() == 0) {
      return this.name + " (" + this.description + ")";
    }
    else {
      return this.name + " (" + this.description + "), " + other;
    }
  }

}

class Hyperlink implements IContent {
  String text;
  Webpage destination;

  Hyperlink(String text, Webpage destination) {
    this.text = text;
    this.destination = destination;
  }

  // FIELDS:
  // this.text ... String
  // this.destination ... Webpage
  // METHODS:
  // this.getMeg() ... double
  // this.concatInfo(String) ... String
  // METHODS OF FIELDS:
  // this.destination.totalCredits() ... int
  // this.destination.getMeg() ... double
  // this.destination.pictureInfo() ... String

  // return the Megabytes this Hyperlink holds
  public double getMeg() {
    // everything from our class-wide template
    return this.destination.getMeg();
  }

  public String concatInfo(String other) {
    // everything from our class-wide template
    String picInfo = this.destination.pictureInfo();
    if (picInfo.length() == 0 || other.length() == 0) {
      return other + picInfo;
    }
    else {
      return picInfo + ", " + other;
    }
  }
}

class ExamplesWebpages {

  /*
   * Extra Example in English: Website for a pet shelter holds: A webpage named
   * "Pet Shelter Homepage" that holds: A text section named "Mission statement",
   * with 7 lines that are not written in Markdown A hyperlink "See Our Animals"
   * to the See the Pets webpage A hyperlink "Donate Now" to Donation webpage A
   * webpage named "Donation Page" that holds: A picture "Donate", with the
   * description "Donation Sticker" that is 2 megabytes A hyperlink "Take Me Home"
   * to the Pet Shelter Homepage A webpage named "See the Pets" that holds: A
   * hyperlink "Take Me Home" to the Pet Shelter Homepage A picture "Dog," with
   * the description "Cute dog" that is 3.7 megabytes
   */

  IContent picture4 = new Picture("Submission", "submission screenshot", 13.7);
  ILoContent assignment1ILOC = new ConsLoContent(this.picture4, new MtLoContent());
  Webpage assignment1 = new Webpage("Assignment 1", this.assignment1ILOC);

  IContent picture3 = new Picture("Java", "HD Java logo", 4);
  IContent text3 = new Text("Week 1", 10, true);
  IContent firstAssignmentLink = new Hyperlink("First Assignment", this.assignment1);
  ILoContent syllabusILOC = new ConsLoContent(this.picture3, new ConsLoContent(this.text3,
      new ConsLoContent(this.firstAssignmentLink, new MtLoContent())));
  Webpage syllabus = new Webpage("Syllabus", this.syllabusILOC);

  IContent text5 = new Text("Pair Programming", 10, false);
  IContent text4 = new Text("Expectations", 15, false);
  IContent firstAssignmentLink2 = new Hyperlink("First Assignment", this.assignment1);
  ILoContent assignmentILOC = new ConsLoContent(this.text5, new ConsLoContent(this.text4,
      new ConsLoContent(this.firstAssignmentLink2, new MtLoContent())));
  Webpage assignment = new Webpage("Assignments", this.assignmentILOC);

  IContent text1 = new Text("Course Goals", 5, true);
  IContent text2 = new Text("Instructor Contact", 1, false);
  IContent picture1 = new Picture("Eclipse", "Eclipse logo", 0.13);
  IContent picture2 = new Picture("Coding Background", "digital rain from the Matrix", 30.2);
  IContent syllabuslink = new Hyperlink("Course Syllabus", this.syllabus);
  IContent assignmentslink = new Hyperlink("Course Assignments", this.assignment);
  ILoContent homepageILOC = new ConsLoContent(this.text1,
      new ConsLoContent(this.text2,
          new ConsLoContent(this.picture1,
              new ConsLoContent(this.picture2, new ConsLoContent(this.syllabuslink,
                  new ConsLoContent(this.assignmentslink, new MtLoContent()))))));
  Webpage homepage = new Webpage("Fundies 2 Homepage", this.homepageILOC);

  String checkin = this.homepage.pictureInfo();

  // test the method totalCredits in the class Webpage
  boolean testTotalCredits(Tester t) {
    return t.checkExpect(this.assignment1.totalCredits(), 700)
        && t.checkExpect(this.homepage.totalCredits(), 3100);
  }

  // test the method getMeg in the class Webpage
  boolean testGetMeg(Tester t) {
    return t.checkExpect(this.assignment1.getMeg(), 13.7)
        && t.checkExpect(this.syllabus.getMeg(), 17.7);

  }

  // test the method pictureInfo in the class Webpage
  boolean testPictureInfo(Tester t) {
    return t.checkExpect(this.assignment1.pictureInfo(), "Submission (submission screenshot)")
        && t.checkExpect(this.homepage.pictureInfo(), checkin);
  }

  // test the method totalMeg in the class MtLoContent
  boolean testTotalMegInMTLOC(Tester t) {
    return t.checkExpect(new MtLoContent().totalMeg(), 0.0);
  }

  // test the method allPictureInfo in the class MtLoContent
  boolean testAllPictureInfoInMTLOC(Tester t) {
    return t.checkExpect(new MtLoContent().allPictureInfo(), "");

  }

  // test the method totalMeg in the class ConsLoContent
  boolean testTotalMegInCLOC(Tester t) {
    return t.checkExpect(this.assignment1ILOC.totalMeg(), 13.7)
        && t.checkExpect(this.homepageILOC.totalMeg(), 61.73);

  }

  // test the method allPictureInfo in the class ConsLoContent
  boolean testAllPictureInfoInCLOC(Tester t) {
    return t.checkExpect(this.assignment1ILOC.allPictureInfo(),
        "Submission (submission screenshot)")
        && t.checkExpect(this.homepageILOC.allPictureInfo(), checkin);

  }

  // test the method getMeg in the class Text
  boolean testGetMegInText(Tester t) {
    return t.checkExpect(this.text1.getMeg(), 0.0);

  }

  // test the method concatInfo in the class Text
  boolean testConcatInfoInText(Tester t) {
    return t.checkExpect(this.text1.concatInfo(""), "");
  }

  // test the method getMeg in the class Picture
  boolean testGetMegInPicture(Tester t) {
    return t.checkExpect(this.picture4.getMeg(), 13.7)
        && t.checkExpect(this.picture3.getMeg(), 4.0);
  }

  // test the method concatInfo in the class Picture
  boolean testConcatInfoInPicture(Tester t) {
    return t.checkExpect(this.picture4.concatInfo(""), "Submission (submission screenshot)")
        && t.checkExpect(this.picture3.concatInfo(""), "Java (HD Java logo)");
  }

  // test the method getMeg in the class Hyperlink
  boolean testGetMegInHyperlink(Tester t) {
    return t.checkExpect(this.firstAssignmentLink.getMeg(), 13.7);
  }

  // test the method concatInfo in the class Hyperlink
  boolean testConcatInfoInHyperlink(Tester t) {
    return t.checkExpect(this.firstAssignmentLink.concatInfo(""),
        "Submission (submission screenshot)");
  }
}

// Double counting explanation: There are two hyperlinks to assignment 1, 
// so the submission picture is counted twice.
/*
 * This reptition shows up in totalCredit, and it also shows up in pictureInfo.
 * Because we are traversing the list of content a webpage has without
 * remembering where we went already, we are stumbling upon duplicated hyperlink
 * without realizing it.
 */
