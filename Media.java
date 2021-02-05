import tester.Tester;

// a piece of media
interface IMedia {
  
  // is this media really old?
  boolean isReallyOld();
  
  // are captions available in this language?
  boolean isCaptionAvailable(String language);
  
  // a string showing the proper display of the media
  String format();
}


abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;
  
  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }
  
  
  //is this media really old?
  public boolean isReallyOld() {
    return false;
  }
  
  //is the given caption available
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.isCaptionAvailable(language);
  }
  
  //a  string showing the proper display of the media
  public abstract String format();
  
  
}

// represents a movie
class Movie extends AMedia {
  int year;
  
  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }
  
  //is this media really old?
  public boolean isReallyOld() {
    return this.year < 1930;
  }
  
  // a string showing the proper display of the media
  public String format() {
    return this.title + " (" + Integer.toString(this.year) + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;
  
  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  // a string showing the proper display of the media
  public String format() {
    return this.showName + " " + Integer.toString(this.seasonNumber) + "." 
           + Integer.toString(this.episodeOfSeason) + " - " + this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;
  
  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }
  
  // a string showing the proper display of the media
  public String format() {
    return this.title + " by " + this.channelName;
  }
  
}

// lists of strings
interface ILoString {
  
  //check if this contains the given language
  boolean isCaptionAvailable(String language);
}

// an empty list of strings
class MtLoString implements ILoString {
  
  //check if this contains the given language
  public boolean isCaptionAvailable(String language) {
    return false;
  }
}


// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  //check if this contains the given language
  public boolean isCaptionAvailable(String language) {
    return this.first.equals(language) || this.rest.isCaptionAvailable(language);
  }
  
}

class ExamplesMedia {
  ILoString empty = new MtLoString();
  ILoString cap1 = new ConsLoString("English", this.empty);
  ILoString cap2 = new ConsLoString("Spanish", this.cap1);
  ILoString cap3 = new ConsLoString("Chinese", this.cap2);
  
  IMedia godFather = new Movie("GodFather", 1972, this.empty);
  IMedia terminator = new Movie("Terminator", 1984, this.cap3);
  IMedia cam = new Movie("Man with a movie camera", 1929, this.cap1);
  
  IMedia friends = new TVEpisode("Ross and Rachel", "Friends", 2, 21, this.cap2);
  IMedia seinfeld = new TVEpisode("Soup Nazi", "Seinfeld", 7, 6, this.cap3);
  
  IMedia speedRun = new YTVideo("One Speed Runner VS Four Hunters", "Dream", this.cap1);
  IMedia amoung = new YTVideo("AOC plays amoung us", "Pokimaine", this.cap2);
  
  
  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(this.godFather.isReallyOld(), false) 
           && t.checkExpect(this.cam.isReallyOld(), true)
           && t.checkExpect(this.friends.isReallyOld(), false)
           && t.checkExpect(this.speedRun.isReallyOld(), false);
  }
  
  boolean testIsCapAvailable(Tester t) {
    return t.checkExpect(this.godFather.isCaptionAvailable("Chinese"), false)
           && t.checkExpect(this.terminator.isCaptionAvailable("Chinese"), true)
           && t.checkExpect(this.friends.isCaptionAvailable("Chinese"), false)
           && t.checkExpect(this.seinfeld.isCaptionAvailable("Spanish"), true)
           && t.checkExpect(this.speedRun.isCaptionAvailable("English"), true)
           && t.checkExpect(this.amoung.isCaptionAvailable("Chinese"), false);
  }
  
  boolean testFormat(Tester t) {
    return t.checkExpect(this.godFather.format(), "GodFather (1972)")
           && t.checkExpect(this.seinfeld.format(), "Seinfeld 7.6 - Soup Nazi")
           && t.checkExpect(this.amoung.format(), "AOC plays amoung us by Pokimaine");
  }
}