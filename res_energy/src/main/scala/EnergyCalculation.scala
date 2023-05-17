import java.io._
import scala.io.Source

trait RenewableEnergySource {
  def name: String
  def generatorType: Char
  def energyGenerated: Double
  def status: String
  def timestamp: String
}

case class RenewableEnergySourceFromFile(name: String, generatorType: Char, energyGenerated: Double, status: String, timestamp: String) extends RenewableEnergySource

object EnergyCalculation {

  def main(): Unit = {
    val sources = readSourcesFromFile("anton.csv")

    var exit = false
    while (!exit) {
      println("\nSelect an operation:")
      println("1. Display energy generation")
      println("2. Filter data for a specific energy source")
      println("3. Filter data on an hourly, daily, weekly, or monthly basis")
      println("4. Sort data")
      println("5. Search for data")
      println("6. Calculate total energy generated")
      println("7. Display data analysis")
      println("8. Exit")

      val choice = scala.io.StdIn.readInt()

      choice match {
        case 1 => displayEnergyGeneration(sources)
        case 2 => filterDataForSource(sources)
        case 3 => filterDataByTime(sources)
        case 4 => sortData(sources)
        case 5 => searchData(sources)
        case 6 => calculateTotalEnergyGenerated(sources)
        case 7 => dataAnalysis(sources)
        case 8 => exit = true
        case _ => println("Invalid choice")
      }
    }
  }

  def readSourcesFromFile(filename: String): List[RenewableEnergySource] = {
    val file = Source.fromFile(filename)
    val lines = file.getLines().toList.tail // the header skipped
    file.close()
    lines.map { line =>
      val fields = line.split(",")
      RenewableEnergySourceFromFile(fields(0), fields(1).charAt(0), fields(2).toDouble, fields(3), fields(4))
    }
  }

  def displayEnergyGeneration(sources: List[RenewableEnergySource]): Unit = {
    println("\nCurrent energy generation:")
    sources.groupBy(_.name).mapValues(_.maxBy(_.timestamp)).values.foreach { source =>
      println(s"${source.name}: ${source.energyGenerated} kW, ${source.status}")
    }
  }

  def generatorSelect(sources: List[RenewableEnergySource]): List[RenewableEnergySource] = {
    val generators = sources.map(_.name).distinct
    println("\nAvailable generators:")
    generators.zipWithIndex.foreach { case (generator, index) =>
        println(s"${index + 1}. $generator")
    }
    print("\nSelect a generator: ")
    val selectedGeneratorIndex = scala.io.StdIn.readInt() - 1
    val selectedGenerator = generators(selectedGeneratorIndex)
    val filteredSources = sources.filter(_.name == selectedGenerator)
    filteredSources
  }

  def filterDataForSource(sources: List[RenewableEnergySource]): Unit = {
    val filteredSources = generatorSelect(sources)
    if (filteredSources.isEmpty) {
        println("No data found for that energy source")
    } else {
        filteredSources.foreach { source =>
        println(s"${source.timestamp}: ${source.energyGenerated} kW, ${source.status}")
        }
    }
  }

  def filterDataByTime(sources: List[RenewableEnergySource]): Unit = {
    val filteredSources = generatorSelect(sources)
    println("Select the wanted filter (hourly, daily, weekly, or monthly):")
    val filter = scala.io.StdIn.readLine()
    if (filteredSources.isEmpty) {
      println("No data found for that energy source")
    } else {
      val filteredData = filter match {
        case "hourly" => 
            println("Enter the time for the filter (YYYY-MM-DDTHH):")
            val filter = scala.io.StdIn.readLine()
            filteredSources.map(_.timestamp).filter(_.startsWith(filter))
        case "daily" => 
            println("Enter the time for the filter (YYYY-MM-DD):")
            val filter = scala.io.StdIn.readLine()
            filteredSources.map(_.timestamp).filter(_.startsWith(filter))
        /*case "weekly" =>
            println("Enter the first day of the wanted week for the filter (YYYY-MM-DD):")
            val filter1 = scala.io.StdIn.readLine()
            val givenYearDay = filter1.substring(6)
            val startingDay = filter1.substring(7, 9).toInt
            val day2 = startingDay + 6
            val filter2 = s"${givenYearDay}-${day2}"
            filteredSources.map(_.timestamp)
                .filter(timestamp => timestamp.compareTo(filter1) >= 0 && timestamp.compareTo(filter2) <= 0)
        */
        case "monthly" => 
            println("Enter the time for the filter (YYYY-MM)")
            val filter = scala.io.StdIn.readLine()
            filteredSources.map(_.timestamp).filter(_.startsWith(filter))
        case _ => filteredSources.map(_.energyGenerated)
      }
      if (filteredData.isEmpty) {
        println("No data found for that time filter")
        } else {
        filteredData.foreach { timestamp =>
            val source = filteredSources.find(_.timestamp == timestamp).get
            println(s"$timestamp ${source.energyGenerated} kW ${source.status}")
        }
        }
    }
  }

  def sortData(sources: List[RenewableEnergySource]): Unit = {
    val filteredSources = generatorSelect(sources)
    if (filteredSources.isEmpty) {
      println("No data found for that energy source")
    } else {
      println("\nEnter the sort order:\n1. ascending\n2. descending:")
      val sortOrder = scala.io.StdIn.readLine()
      val sortedData = sortOrder match {
        case "1" => filteredSources.map(_.energyGenerated).sorted
        case "2" => filteredSources.map(_.energyGenerated).sorted(Ordering[Double].reverse)
        case _ => filteredSources.map(_.energyGenerated)
      }
      if (sortedData.isEmpty) {
        println("No data found for that sort order")
      } else {
        sortedData.foreach { energyGenerated =>
          println(s"$energyGenerated kW")
        }
      }
    }
  }

  def searchData(sources: List[RenewableEnergySource]): Unit = {
    println("\nEnter the energy generated value to search for:")
    val value = scala.io.StdIn.readDouble()
    val searchedData = sources.find(_.energyGenerated == value)
    if (searchedData.isEmpty) {
      println("No data found for that value")
    } else {
      val source = searchedData.get
      println(s"${source.name}, ${source.timestamp}: ${source.energyGenerated} kW, ${source.status}")
    }
  }

  def calculateTotalEnergyGenerated(sources: List[RenewableEnergySource]): Unit = {
    val totalEnergyGenerated = sources.map(_.energyGenerated).sum
    println(s"\nTotal energy generated: $totalEnergyGenerated kW")
  }

  def dataAnalysis(sources: List[RenewableEnergySource]): Unit = {
    val data = sources.map(_.energyGenerated)
    val sortedData = data.sorted

    val mean = calculateMean(sortedData)
    val median = calculateMedian(sortedData)
    val mode = calculateMode(sortedData)
    val range = calculateRange(sortedData)
    val midrange = calculateMidrange(sortedData)

    println(f"\nMean: $mean%.2f kW \nMedian: $median%.2f kW \nMode: $mode%.2f kW \nRange: $range%.2f kW \nMidrange: $midrange%.2f kW")

    val issues = detectIssues(sources)
    handleIssues(issues)

    val alerts = detectAlerts(sources)
    handleAlerts(alerts)
  }

  def calculateMean(data: List[Double]): Double = {
    data.sum / data.length
  }

  def calculateMedian(data: List[Double]): Double = {
    val sortedData = data.sorted
    val mid = sortedData.length / 2
    if (sortedData.length % 2 == 0) (sortedData(mid - 1) + sortedData(mid)) / 2.0
    else sortedData(mid)
  }

  def calculateMode(data: List[Double]): Double = {
    data.groupBy(identity).mapValues(_.size).maxBy(_._2)._1
  }

  def calculateRange(data: List[Double]): Double = {
    data.max - data.min
  }

  def calculateMidrange(data: List[Double]): Double = {
    (data.min + data.max) / 2
  }

  def detectIssues(sources: List[RenewableEnergySource]): List[String] = {
    sources.filter(_.status != "OPERATING").map(source => s"Issue detected: ${source.status}")
  }

  def handleIssues(issues: List[String]): Unit = {
    print("\nHandling issues:")
    if (issues.isEmpty) {
    println(" No issues found!")
  } else {
    issues.foreach(println)
  }
  }

  def detectAlerts(sources: List[RenewableEnergySource]): List[String] = {
    sources.filter(_.energyGenerated < 10).map(source => s"Alert: Low energy output from ${source.name}")
  }

  def handleAlerts(alerts: List[String]): Unit = {
    print("Handling alerts:")
    if (alerts.isEmpty) {
        println(" No alerts found!")
    } else {
        alerts.foreach(println)
    }
  }
}
