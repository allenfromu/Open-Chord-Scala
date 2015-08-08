package data


import scala.util.control.Breaks._


/**
 * this class represents a list of successors of a node.
 */
class SuccessorList(val ln:Node,val cap:Int = 50) {
  private var localNode = ln
  private var successors:List[Node] = List()
  private var capacity = cap
  
  
  def getCopy:List[Node] = successors.toList
  
  def addSuccessor(toAdd:Node){    
     if (toAdd == null) 
      throw new NullPointerException("Parameter may not be null!")
       
    this.successors.synchronized{     
       if(successors.size >= capacity && !toAdd.getID.isInInterval(localNode.getID, successors.last.getID))
         return           
      
       if(!this.contains(toAdd) && !toAdd.equals(localNode)){
         var inserted = false
         breakable{
           for(i <- 0 until successors.length){
             if(toAdd.getID.isInInterval(localNode.getID, successors(i).getID)){
               println("mark:"+toAdd.getURL)
              successors = (successors.dropRight(successors.length - i):+toAdd):::successors.drop(i)
              inserted = true
              break
             }
           }        
         } 
         if(!inserted)
           successors = successors:+toAdd
       }  
       
      if(successors.size > capacity )
         successors = successors.dropRight(1)
     }
  }
  

  def removeSuccessor(toRemove:Node){
     if (toRemove == null) 
      throw new NullPointerException("Parameter may not be null!")
    
     this.successors.synchronized{
      breakable{
        for(i <- 0 until successors.length){
          if(toRemove.equals(successors(i))){
            successors = successors.dropRight(successors.length-i):::successors.drop(i+1)
            break
          }     
        } 
      } 
    }  
  }
  
  
  def getClosestPrecedingNode(id:ID):Node = {
    var cn:Node = null
    this.successors.synchronized{
      breakable{
       for(i <-0 until successors.length){
         if(!id.isInInterval(localNode.getID, successors(successors.length-1-i).getID)){
           cn = successors(successors.length-1-i)
           break
         }
       }      
      }
    }
    cn
  }
  
  def getImmediateSuccessor(id:ID):Node = {
    var im:Node = null
    this.successors.synchronized{
      breakable{
        for(i <- 0 until successors.length){
          if(id.isInInterval(localNode.getID, successors(i).getID)){
            im = successors(i)
            break
          }
        }
      }
    }
    im
  }
  
  
  def contains(n:Node):Boolean = {
    var c = false
    if(n.equals(localNode))
      return true
    this.successors.synchronized{
      successors.foreach { x => if(x.equals(n)) c = true }
    }
    c
  }
  
  
  def getSuccessor():Node = if(successors.isEmpty) null else successors(0)
  
  def getLast():Node = if(successors.isEmpty) null else  successors.last
  
  def getRandomNode:Node = {
   this.successors.synchronized{
    if(successors.size > 0){ 
      
        val r = new scala.util.Random(System.currentTimeMillis())
        val num = r.nextInt(successors.size)
        this.successors(num)
    }
    else 
      null
   }
  }
}