object DataGenerator { //From here we will be creating time skips
    def main(energyGenerators: List[EnergyGenerator]) = {
        val options = List("Pass an hour", "Pass a day", "Pass a week", "Pass a month", "Exit")

        Menu.printMenu(options)

        val choice = Menu.getUserChoice(options)

        choice match { //tbd
            case "Pass an hour" => println(s"An hour passes...") 
            pass1(energyGenerators)
            
            case "Pass a day" => println(s"A day passes...")
            case "Pass a week" => println(s"A week passes...")
            case "Pass a month" => println(s"A month passes...")
            case "Exit" => println("Returning to the main menu...")
            Menu.main(energyGenerators)
        }
    }
    def pass1(energyGenerators: List[EnergyGenerator]) = {
        EnergyGenerator.writeToCSV(energyGenerators, "anton.csv")
    }
    
}
