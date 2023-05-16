object DataGenerator { //From here we will be creating time skips
    /*def repeatFunction(numTimes: Int, function:  => Unit): Any = {
        for (_ <- 1 to numTimes) {
            function
        }
    }*/
    def repeatFunction(n: Int, f: () => List[Iterator[Int]]): List[Iterator[Int]] = {
        if (n == 0) {
            f()
        } else {
            repeatFunction(n - 1, f)
        }
    }
    def main(energyGenerators: List[EnergyGenerator]): Unit = {
        val options = List("Pass an hour", "Pass a day", "Pass a week", "Pass a month", "Exit")

        Menu.printMenu(options)

        val choice = Menu.getUserChoice(options)

        choice match { //tbd
            case "Pass an hour" => println(s"An hour passes...") 
            pass1(energyGenerators)
            case "Pass a day" => println(s"A day passes...")
            passMore(energyGenerators,24)
            case "Pass a week" => println(s"A week passes...")
            passMore(energyGenerators,168)
            case "Pass a month" => println(s"A month passes...")
            passMore(energyGenerators,730)
            case "Exit" => println("Returning to the main menu...")
            Menu.main(energyGenerators)
        }
    }
    def generateEnergyGeneratorsRec(times: Int, acc: List[EnergyGenerator]): List[EnergyGenerator] = {
        if (times <= 0) acc // base case: return the accumulated list
        else {
            val newGenerators = EnergyGenerator.writeToCSV(acc, "anton.csv")
            generateEnergyGeneratorsRec(times - 1, newGenerators)
        }
    }
    def pass1(energyGenerators: List[EnergyGenerator]) = {
        val en = EnergyGenerator.writeToCSV(energyGenerators, "anton.csv")
        DataGenerator.main(en)
    }
    def passMore(energyGenerators: List[EnergyGenerator], count: Int) = {
        /*val lastEnergyGenerators = (1 to 24).foldLeft(List.empty[EnergyGenerator]) { (prevGenerators, _) =>
            EnergyGenerator.writeToCSV(energyGenerators, "anton.csv")
        }*/
        val lastEnergyGenerators= generateEnergyGeneratorsRec(count, energyGenerators)
        DataGenerator.main(lastEnergyGenerators)
    }
}
