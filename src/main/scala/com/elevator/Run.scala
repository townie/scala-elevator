package com.elevator
import scala.io.Source
import java.io.FileNotFoundException

object Run extends App {
	var rawElevatorArrays = Array[Array[String]]()
	val filename = args(0)

	try {
		rawElevatorArrays = TextFileReader.returnElevatorBankArray(filename)
	} catch {
		case noFile: FileNotFoundException => Console.println("No file found")
		case e : Throwable => Console.println("Something bad happened")
		System.exit(1)
	} 

	var elevatorSystem = ElevatorBankFactory.buildFromArrays(rawElevatorArrays)

	//DEBUGGER: 
	// elevatorSystem.displayAdjencyList(1)

    val selectedElevator = args(1)

	val startingLocation = elevatorSystem.elevatorLocationInHistory(selectedElevator, 0)
	// val altElevators = elevatorSystem.elevatorsForFloorInHistory(startingLocation, 0)
	// Console.println(altElevators.mkString(" "))

	val destinationAndTime = args(2).split("[-]")
	val destinationLocation = destinationAndTime(0).toInt
	var maxTime = destinationAndTime(1).toInt
	
	// From the CLI the timesteps are index=1, but ElevatorBank index=0
	// TODO teach people that indexes should be index=0 
	maxTime = maxTime - 1

	// Console.println(destinationLocation)
	// Console.println(maxTime)
	// Console.println(startingLocation)
	
	elevatorSystem.determinePath(startingLocation, destinationLocation, maxTime)

}