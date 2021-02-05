import tester.*;
interface ILoInt {
  boolean allThreeReq();

  boolean allThreeReqHelper(boolean even, boolean posOdd, boolean between, boolean evenOdd,
      boolean evenBet, boolean oddBet, boolean oneOfTwo, boolean all);
}

class MtLoInt implements ILoInt {

  public boolean allThreeReq() {
    return false;
  }

  
  public boolean allThreeReqHelper(boolean even, boolean posOdd, boolean between, boolean evenOdd,
      boolean evenBet, boolean oddBet, boolean oneOfTwo, boolean all) {
    if(even && posOdd && between) {
      //all three satified
      return true;
    }
    if(!(even || posOdd || between))
    {return (evenOdd && oddBet && evenBet) || 
        (((!evenOdd && !evenBet && oddBet) || (evenOdd && !evenBet && !oddBet) || (!evenOdd && evenBet && !oddBet)) && oneOfTwo) ||
        (((!evenOdd && evenBet && oddBet) || (evenOdd && !evenBet && oddBet) || (evenOdd && evenBet && !oddBet)) && (oneOfTwo || all))
        ;}
    else if((!even && posOdd && between) || (even && !posOdd && between) || (even && posOdd && !between))
    {// two out of three are satisfied
      return all || oneOfTwo || 
          ((even || evenOdd || evenBet) && (posOdd || evenOdd || oddBet) && (between || evenBet || oddBet));
            
    }
    else {
      //only one is satisfied
      if(oneOfTwo)
        return true;
      else if(!all)
      {return ((!evenOdd && evenBet && oddBet) || (evenOdd && !evenBet && oddBet) || (evenOdd && evenBet && !oddBet) ||
                  (evenOdd && evenBet && oddBet));}
      else {//not all, and not one of two
        return (!evenOdd && evenBet && oddBet) || (evenOdd && !evenBet && oddBet) || (evenOdd && evenBet && !oddBet);
      }
          
      
    }
  }
}

class ConsLoInt implements ILoInt {
  int first;
  ILoInt rest;

  ConsLoInt(int first, ILoInt rest) {
    this.first = first;
    this.rest = rest;
  }
  ConsLoInt(int[] ar) {
    this.rest = new MtLoInt();
    if(ar.length == 0)
    {return;}
    else
    for(int i = ar.length - 1; i>= 1; i--)
    {
      this.rest = new ConsLoInt(ar[i], this.rest);}
    this.first = ar[0];
    
    
  }

  public boolean allThreeReq() {
    return this.allThreeReqHelper(false, false, false, false, false, false, false, false);
  }

  public boolean allThreeReqHelper(boolean even, boolean posOdd, boolean between, boolean evenOdd,
      boolean evenBet, boolean oddBet, boolean oneOfTwo, boolean all) {
    boolean isEven = this.first % 2 == 0;
    boolean isPosOdd = this.first > 0 && this.first % 2 == 1;
    boolean isBetween = this.first <= 10 && this.first >= 5;
    if (!(isEven || isPosOdd || isBetween)) {// none of the condition satisfy
      return this.rest.allThreeReqHelper(isEven, isPosOdd, isBetween, evenOdd, evenBet, oddBet,
          oneOfTwo, all);
    }
    else if (isEven && isPosOdd && isBetween) {// if all the conditions satisfy
      if (!all) {
        return this.rest.allThreeReqHelper(isEven, isPosOdd, isBetween, evenOdd, evenBet, oddBet,
            oneOfTwo, true);
      }
      else {
        if(!oneOfTwo) {
        return this.rest.allThreeReqHelper(isEven, isPosOdd, isBetween, 
            evenOdd, evenBet, oddBet,
            true, false);}
        else {
          return true;
        }
      }

    }
    else if ((isEven && !isPosOdd && !isBetween) || (!isEven && isPosOdd && !isBetween)
        || (!isEven && !isPosOdd && isBetween)) {// if only one of the condition satisfy
      return this.rest.allThreeReqHelper(isEven || even, isPosOdd || posOdd, isBetween || between, evenOdd, evenBet, oddBet,
          oneOfTwo, all);

    }
    else {// if two of the condition satisfy
      
      boolean retEvenOdd = ((isEven && isPosOdd) && !evenOdd) || (!(isEven && isPosOdd) && evenOdd);
      boolean retEvenBet = ((isEven && isBetween) && !evenBet) || (!(isEven && isBetween) && evenBet);
      boolean retOddBet = ((isBetween && isPosOdd) && !oddBet) || (!(isBetween && isPosOdd) && oddBet);
      boolean retEven = even || ((isEven && isPosOdd) && evenOdd) || ((isEven && isBetween) && evenBet);
      boolean retPosOdd = posOdd || ((isEven && isPosOdd) && evenOdd) || ((isPosOdd && isBetween) && oddBet);
      boolean retBetween = between || ((isBetween && isPosOdd) && oddBet) || ((isEven && isBetween) && evenBet);
      
      
      
      return this.rest.allThreeReqHelper(retEven, retPosOdd, retBetween, retEvenOdd, retEvenBet, retOddBet, oneOfTwo, all);
    }
  }

}

class ExamplesInt {
  ILoInt int1 = new ConsLoInt(new int[] {2, 3, 6}); 
  ILoInt int2 = new ConsLoInt(new int[] {2, 6}); 
  ILoInt int3 = new ConsLoInt(new int[] {2, 7, 7}); 
  ILoInt int4 = new ConsLoInt(new int[] {5, 5, 5, 5, 5, 5, 6}); 
  ILoInt int5 = new ConsLoInt(new int[] {5, 5, 5, 5, 5, 5, 6}); 
  
  
  void testAllCondition(Tester t) {
    t.checkExpect(int1.allThreeReq(), true);
    t.checkExpect(int2.allThreeReq(), false);
    t.checkExpect(int3.allThreeReq(), true);
    t.checkExpect(int4.allThreeReq(), true);
    
  }

}