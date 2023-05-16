import java.io._
import scala.util.Random
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

case class EnergyGenerator(wattsPerSecond: Double, name: String, types: String, failureChance: Double, efficiency: Double, status: String, time: LocalDateTime = LocalDateTime.now())
//initially we wanted to have classes for each of the 3 possible types of energy generators, however we found out that they don't have much
//different of a thing so we just gave them different names 

object EnergyGenerator { //to follow the functional prgramming paradigm, every modification to the object will return a new object,
//not to lose the reference to the objects, they are to be stored in other data structures
  def modify(generator: EnergyGenerator,name: String,types: String, wattsPerSecond: Double, failureChance: Double, efficiency: Double, status: String): EnergyGenerator = {
    EnergyGenerator(wattsPerSecond, name,types, failureChance, efficiency, status)
  }
  def advance(generator: EnergyGenerator): EnergyGenerator = {
    EnergyGenerator(generator.wattsPerSecond, generator.name, generator.types, generator.failureChance, generator.efficiency,generator.status, generator.time.plusHours(1))
  }
  def printDetails(generator: EnergyGenerator): Unit = {
    println(s"Watts per second: ${generator.wattsPerSecond}")
    println(s"Name: ${generator.name}")
    println(s"Failure chance: ${generator.failureChance}")
    println(s"Efficiency: ${generator.efficiency}")
    println(s"Status: ${generator.status}")
    println(s"Timestamp: ${generator.time}")
    println()
  }

  def writeToCSV(energyGenerators: List[EnergyGenerator], filename: String): List[EnergyGenerator] = {
    val file = new File(filename)
    val fileExists = file.exists()

    val writer = new BufferedWriter(new FileWriter(file, true))
    if (!fileExists) {
        writer.write("Name,Type,Energy Generated,Status,Timestamp\n")
        println("Name,Type,Energy Generated,Status,Timestamp")
    }
    val energyGenWithNewTime = energyGenerators.map(n => advance(n))
    for (energyGen <- energyGenerators) { //could have actually used foreach, would be more functional programming style, but it is what it is
        val name = energyGen.name
        val energyType = energyGen.types match {
        case "W" => "W"
        case "H" => "H"
        case "S" => "S"
        }
        val energyGenerated = calculateEnergy(energyGen)
        val status = energyGen.status
        val stamp = energyGen.time
        writer.write(s"$name,$energyType,$energyGenerated,$status,$stamp\n")
        println(s"$name,$energyType,$energyGenerated,$status,$stamp")
    }
    writer.close()
    energyGenWithNewTime
    }
    def calculateEnergy(generator: EnergyGenerator): Double = {
      if (generator.status == "UNOPERATABLE"){
        0
      }
      else{
        defectChance(generator)
        generator.efficiency*generator.wattsPerSecond*Random.between(0.8,1.2)
      }
    }
    def defectChance(generator: EnergyGenerator) = {
      if (Random.between(0.0, 1.0)<generator.failureChance){
        modify(generator,generator.name,generator.types, generator.wattsPerSecond, generator.failureChance, generator.efficiency, "UNOPERATABLE")
      }
    }
}

