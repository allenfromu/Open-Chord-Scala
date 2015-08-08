package data

import java.io.Serializable

/**
 * this class represents the key-value data
 * @author zepeng zhao
 */
class Entry(val id1:ID,val k:String, val v:Serializable) extends Serializable{
  private var id:ID = id1
  
  private var value:Serializable = v
  
  private var key = k
  
  def getKey() = key
  
  def getID():ID = id
  
  def setID(id2:ID){id = id2}
  
  def getValue():Serializable = value
  
  def setValue(v1:Serializable){value = v1}
  
  override def equals(id3:Any):Boolean=id3 match{
    case x:Entry => x.id.equals(this.id) && x.value.equals(this.value)
    case _ => false
  }
}