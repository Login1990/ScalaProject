import java.io._

/*trait RenewableEnergySource {
  def energyGenerated: Double
  def status: String
}

case class SolarPanel(energyGenerated: Double, status: String) extends RenewableEnergySource
case class WindTurbine(energyGenerated: Double, status: String) extends RenewableEnergySource
case class HydroPower(energyGenerated: Double, status: String) extends RenewableEnergySource

object REPS {

  def main(args: Array[String]): Unit = {
    val sources = List(
      SolarPanel(1000, "operational"),
      WindTurbine(2000, "operational"),
      HydroPower(3000, "operational")
    )

    monitorEnergySources(sources)
    controlEnergySources(sources)

    val data = collectData(sources)
    storeDataToFile(data, "energy_data.txt")

    displayEnergyGeneration(data)
    displayStorageCapacity(data)

    val dailyFilteredData = filterData(data, "daily")
    val weeklyFilteredData = filterData(data, "weekly")
    val monthlyFilteredData = filterData(data, "monthly")

    println("Daily filtered data:")
    dailyFilteredData.foreach(println)

    println("Weekly filtered data:")
    weeklyFilteredData.foreach(println)

    println("Monthly filtered data:")
    monthlyFilteredData.foreach(println)

    val sortedData = sortData(data, "ascending")
    val searchedData = searchData(data, 1000)

    val mean = calculateMean(data)
    val median = calculateMedian(data)
    val mode = calculateMode(data)
    val range = calculateRange(data)
    val midrange = calculateMidrange(data)

    val issues = detectIssues(sources)
    handleIssues(issues)

    val alerts = detectAlerts(sources)
    handleAlerts(alerts)

    val totalEnergyGenerated = calculateTotalEnergyGenerated(sources)
    println(s"Total energy generated: $totalEnergyGenerated")
  }

  def monitorEnergySources(sources: List[RenewableEnergySource]): Unit = {
    println("Monitoring energy sources:")
    sources.foreach(println)
  }

  def controlEnergySources(sources: List[RenewableEnergySource]): Unit = {
    println("Controlling energy sources:")
    sources.foreach(println)
  }

  def collectData(sources: List[RenewableEnergySource]): List[Double] = {
    sources.map(_.energyGenerated)
  }

  def storeDataToFile(data: List[Double], filename: String): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    data.foreach { value =>
      bw.write(value.toString)
      bw.newLine()
    }
    bw.close()
  }

  def displayEnergyGeneration(data: List[Double]): Unit = {
    println("Energy generation:")
    data.foreach(println)
  }

  def displayStorageCapacity(data: List[Double]): Unit = {
    println("Storage capacity:")
    data.foreach(println)
  }

  def filterData(data: List[Double], filter: String): List[Double] = {
    filter match {
      case "daily" => data.filter(_ >= 1000)
      case "weekly" => data.filter(_ >= 5000)
      case "monthly" => data.filter(_ >= 20000)
      case _ => data
    }
  }

  def sortData(data: List[Double], sortOrder: String): List[Double] = {
    sortOrder match {
      case "ascending" => data.sorted
      case "descending" => data.sorted(Ordering[Double].reverse)
      case _ => data
    }
  }

  def searchData(data: List[Double], searchTerm: Double): Option[Double] = {
    data.find(_ == searchTerm)
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
    sources.filter(_.status != "operational").map(source => s"Issue detected: ${source.status}")
  }

  def handleIssues(issues: List[String]): Unit = {
    println("Handling issues:")
    issues.foreach(println)
  }

  def detectAlerts(sources: List[RenewableEnergySource]): List[String] = {
    sources.filter(_.energyGenerated < 1000).map(source => s"Alert: Low energy output from ${source.getClass.getSimpleName}")
  }

  def handleAlerts(alerts: List[String]): Unit = {
    println("Handling alerts:")
    alerts.foreach(println)
  }

  def calculateTotalEnergyGenerated(sources: List[RenewableEnergySource]): Double = {
    sources.map(_.energyGenerated).sum
  }
}
*/