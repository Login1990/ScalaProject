
object REPSS extends App {
    def printAllGenerators(generators: List[EnergyGenerator]): Unit = {
        generators.foreach(EnergyGenerator.printDetails)
    }
    val list_of_generators = List(
        new EnergyGenerator(500, "Solar Generator #1","S", 0.1, 1, "OPERATING"),
        new EnergyGenerator(500, "Solar Generator #2","S", 0.1, 1, "OPERATING"),
        new EnergyGenerator(1000, "Hydro Generator #1","H", 0.05, 1, "OPERATING"),
        new EnergyGenerator(1000, "Hydro Generator #2","H", 0.15, 1, "OPERATING"),
        new EnergyGenerator(300, "Wind Generator #1","W", 0.1, 1, "OPERATING"),
        new EnergyGenerator(300, "Wind Generator #2","W", 0.1, 1, "OPERATING"))
    Menu.main(list_of_generators)
}
