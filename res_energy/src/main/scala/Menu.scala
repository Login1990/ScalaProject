object Menu { //This is a menu, it will be used to address the fucntions of the REPS system
  def printMenu(options: List[String]): Unit = { //prints the list of the options
    println("Select an option:")
    options.zipWithIndex.foreach { case (option, index) =>
      println(s"${index + 1}. $option")
    }
  }

  def getUserChoice(options: List[String]): String = {
    println()
    print("Enter your choice: ")

    val scanner = new java.util.Scanner(System.in)
    val input = scanner.nextLine().trim.toLowerCase

    if (input == "exit") {
      return "Exit"
    }

    val choiceIndex = try {
      input.toInt - 1
    } catch {
      case _: NumberFormatException => -1
    }

    if (choiceIndex >= 0 && choiceIndex < options.length) {
      options(choiceIndex)
    } else {
      println("Invalid choice. Please try again.")
      getUserChoice(options)
    }
  }
  def main(generators: List[EnergyGenerator]): Unit = {
    val options = List("Pass the time", "Option 2", "Print all generators", "Exit")

    printMenu(options)

    val choice = getUserChoice(options)

    choice match { //tbd
      case "Pass the time" => println(s"Welcome to the TimePassing sub-programm, it will allow you to speed up time to recieve data generated in the mean time!")
      DataGenerator.main(generators)  
      case "Option 2" => println("You chose Option 2")
      case "Print all generators" => println("Printing all generators...")
      REPSS.printAllGenerators(generators)
      Menu.main(generators)
      case "Exit" => println("Exiting program...")
    }
  }
}

