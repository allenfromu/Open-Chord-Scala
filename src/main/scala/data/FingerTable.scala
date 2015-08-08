package data

import scala.util.control.Breaks._
import java.util.logging.Logger
import java.util.logging.Level


class FingerTable(id:ID,n:Node,sucL:SuccessorList) {
  private val successorList = sucL
  private val localNode = n
  private val localID = id
  private val remoteNodes:Array[Node] = new Array[Node](id.getLength())
  private val logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)


 
  def addReference(toAdd:Node){
    if(toAdd == null)
      logger.log(Level.SEVERE, "references to add to fingerTable should not be null",  
          new NullPointerException())
    else{
      this.remoteNodes.synchronized{
        breakable {
          for(i <-0 until remoteNodes.length){
            var start = this.localID.addPowerofTwo(i)        
            if(!start.isInInterval(this.localID, toAdd.getID)) 
              break        
            if(remoteNodes(i) == null){ 
              remoteNodes(i) = toAdd 
            }   
            else if(toAdd.getID.isInInterval(this.localID, remoteNodes(i).getID) && !toAdd.equals(this.localID))
              remoteNodes(i) = toAdd     
              
          }
        }   
      }
    }
  }
  
  
  /**
   * 
   */
  def removeReference(toRemove:Node){   
    if(toRemove == null)
      logger.log(Level.SEVERE, "references to be removed from fingerTable should not be null",  
          new NullPointerException())
    
    else{    
      var replacement:Node = null    
      this.remoteNodes.synchronized{
        breakable{    
          for(i <- 0 until remoteNodes.length){        
            var temp = remoteNodes(remoteNodes.length - 1 - i)        
            if(toRemove.equals(temp)) break        
            else if(temp != null) replacement = temp       
          }
        }
        for(i <- 0 until remoteNodes.length){        
          if(toRemove.equals(remoteNodes(i))){
            remoteNodes(i) = replacement
          }
        }               
        //use successorList to fill holes
        var sl = successorList.getCopy
        sl.foreach { x => if(x!=null && !toRemove.equals(x))  this.addReference(x) }
      }
    }
  }
  
  /**
   * 
   */
  def getClosestPrecedingNode(key:ID):Node = {
    if(key == null){
      logger.log(Level.SEVERE, "references null doesn't have closest preceding node",  new NullPointerException()) 
      return null
    }
    this.remoteNodes.synchronized{
      var l:Int = remoteNodes.length - 1
      for(i <-0 to l)
        if(remoteNodes(l - i) != null && remoteNodes(l - i).getID.isInInterval(this.localID, key))
          return remoteNodes(l-i)  
    }
    return null
  }
  
  
  /**
   * 
   */
  def contains(node1:Node):Boolean={
    if(node1 == null)
      throw new NullPointerException("Reference to proxy may not be null!")
    
    this.remoteNodes.synchronized{
      for(i <- 0 until remoteNodes.length){  
        if(node1.equals(remoteNodes(i)))
          return true
      }
    }
    return false
  }
  
  def getFingers:Set[Node]={     
    var fingers:Set[Node] = Set()
    this.remoteNodes.synchronized{
      fingers = remoteNodes.toSet
    }
    fingers
  }
  
  
  
  def print(){
    remoteNodes.foreach { x => if(x!=null) println(x.getURL) }
  }
    
}