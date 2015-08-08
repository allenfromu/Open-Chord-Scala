# Open Chord in Scala and Akka Actor
## Overview
<prev>Open_Chord_Scala is an implemenation of a peer-to-peer <a href="http://open-chord.sourceforge.net" target="_blank">Chord</a> distributed hash table using <a href="http://www.scala-lang.org" target="_blank">Scala</a> and <a href="http://akka.io" target="_blank">Akka Actor</a>. The detailed idea of distributed hash table is described in a MIT <a href="http://pdos.csail.mit.edu/papers/chord:sigcomm01/chord_sigcomm.pdf" target="_blank"> paper </a>.This project was built initially as one of the distributed system benchmarks to help with the DS2 lang research project in the <a href="http://formalverification.cs.utah.edu" target="_blank">Gauss Group</a> at the <a href="http://www.cs.utah.edu" target="_blank">SOC University of Utah.</a> The code is less and simpler thanks to an extremely advanced language Scala and the Akka Actor package, users can definely run this project to help understand the main idea of peer-to-peer distributed hash table.</prev>
##Run the project using <a href="http://www.scala-sbt.org" target="_blank">SBT</a> (Simple Build Tool)
1. Install SBT on your computer.
  * For mac, run command line: **brew install sbt** from terminal
  * For more instructions about installing SBT on any type of OS, visit <a href="http://www.scala-sbt.org/release/tutorial/Setup.html" target="_blank">this link</a>
2. Clone the project from repo: https://github.com/allenfromu/Open_Chord_Scala.git
3. Go to the Open_Chord_Scala directory from terminal and then run command: ""sbt run""
4. After that, you will be prompted to provide an Actor system name, an Actor name and a port number.
  <br> **For instance:**
  <br> ➜  Open_Chord_Scala git:(master) sbt run
  <br> [info] Loading global plugins from /Users/zepengzhao/.sbt/0.13/plugins
  <br> [info] Set current project to Open_Chord_Scala (in build file:/Users/zepengzhao/hello/Open_Chord_Scala/)
  <br> [info] Running service.Main 
  <br> Actor System Name:America 
  <br> Actor Name:Utah
  <br> Port Number:2015
  <br>
  <br>By providing the names and port number, the process you just created represnets a unique node with a unique URL, like the example above, 
 <br>I had created a chord with a unique URL: **akka.tcp://America@192.168.137.3:2015/user/Utah**

5. After you create more than one chord, a chord can join in other chords to form a DHT. 
 <br>To join in a system from an existing node, from the console, type 'join'. And provide the detailed information of the existing node.
 <br>**Below is an example**
```
 <br> >join
 <br>  Actor System Name:America
 <br>  Host Name:192.168.137.3
 <br>  Port Number:2015
 <br>  Actor Name:Utah
 <br>>
```

6. More commands from the console for users to control the DHT.
  * To upload key-value pair from a chord to a system, type 'upload' from console.
  * To lookup the value of a given key, type 'lookup' from console.
  * To display the data in a current node, a serials of commands is provided as below.
    1. To look up entries(key-value pair) in a current node, type 'entries' from console
    2. To display the successor list, type 'SL' from console.
    3. To display the finger table, type 'FT' from console.
    4. To display the successor, type 'SUC' from console.
    5. To display the predecessor, type 'PRE' from console.
  
##Import the project to eclipse
1. Download <a href="http://www.eclipse.org" target="_blank">Eclipse</a> in your computer if you don't don't have it.
2. Add sbteclipse to your plugin definition file (or create one if doesn't exist). You can use either:
  * The global file (for version 0.13 and up) at ~/.sbt/0.13/plugins/plugins.sbt
  * The project-specific file at PROJECT_DIR/project/plugins.sbt
<br>For the latest version:
```
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")

```

3. In the Open_Chord_Scala directory use command:

```
->sbt eclipse
```
4. From the eclipse IDE, import the project to workspace, File->Import->Existing Projects into Workspace.
  
