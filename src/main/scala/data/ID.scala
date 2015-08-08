package data


/**
 * This class represents the id of a key in the forms of byte array.
 * ID is always created by calling a function of HashFunction.createID(...)
 * @author: zepeng zhao
 */
class ID(val id1:Array[Byte]) extends Ordered[ID] with Serializable{
  
  private var id:Array[Byte]=new Array[Byte](id1.length)
  Array.copy(id1,0,id,0,id1.length)
  
  def getLength():Int = {
    return id.length*8
  }
  
  def reset(id2:Array[Byte]){
    Array.copy(id2,0,id,0,id2.length)
  }
  
  def addPowerofTwo(powerOfTwo:Int):ID = {
    if (powerOfTwo < 0 || powerOfTwo >= (this.id.length * 8)) {
      throw new IllegalArgumentException(
          "The power of two is out of range! It must be in the interval "
              + "[0, length-1]");     
    }   
    // copy ID
    var copy:Array[Byte] = new Array[Byte](this.id.length)
    Array.copy(this.id, 0, copy, 0, this.id.length)

    // determine index of byte and the value to be added
    var indexOfByte:Int = this.id.length - 1 - (powerOfTwo / 8)
    var toAdd:Array[Byte] = Array[Byte]( 1, 2, 4, 8, 16, 32, 64, -128 )
    var valueToAdd:Byte = toAdd(powerOfTwo % 8)
    var oldValue:Byte = copy(indexOfByte)

    do {
      // add value
      oldValue = copy(indexOfByte)
      
      copy(indexOfByte) = (copy(indexOfByte)+valueToAdd).toByte

      // reset value to 1 for possible overflow situation
      valueToAdd = 1
      
      indexOfByte -= 1
      
    }
    while (oldValue < 0 && (indexOfByte) >= 0 && copy(indexOfByte) >= 0 )

    return new ID(copy)
  }
  

  override def equals(equalsTo:Any):Boolean = equalsTo match{
    case x:ID=> x.compare(this)  == 0
    case _=>false
  }
    
  
  def compare(otherKey:ID):Int = {
    if (this.getLength() != otherKey.getLength()) {
      throw new ClassCastException(
          "Only ID objects with same length can be "
              + "compared! This ID is " + this.id.length
              + " bits long while the other ID is "
              + otherKey.getLength() + " bits long.")
    }
    var l = id.length - 1
    
    for(i <-0  to l){
      if((id(l - i)-128).toByte > (otherKey.id(l - i)-128).toByte)
        return 1
      else if((id(l - i)-128).toByte < (otherKey.id(l - i)-128).toByte)
        return -1
    }
    return 0
  }
  
  
  def isInInterval(left:ID,right:ID):Boolean = {
    if(left.equals(right)){
      return !this.equals(left)
    }
    else if(left.compare(right) < 0){
      return (left.compare(this) < 0 && right.compare(this) > 0)
    }
    else{
    // interval crosses zero -> split interval at zero
    // calculate min and max IDs
    var minIDBytes:Array[Byte] = new Array[Byte](this.id.length)
    var minID:ID = new ID(minIDBytes);
    var maxIDBytes:Array[Byte] = new Array[Byte](this.id.length)
    for ( i <-0 to maxIDBytes.length - 1) {
      maxIDBytes(i) = (-1).toByte;
    }
    var maxID:ID = new ID(maxIDBytes);
    // check both splitted intervals
    // first interval: (fromID, maxID]
    return ((!left.equals(maxID) && this.compare(left) > 0 && this
        .compare(maxID) <= 0) ||
    // second interval: [minID, toID)
    (!minID.equals(right) && this.compare(minID) >= 0 && this
        .compare(right) < 0));
    }
  }
}