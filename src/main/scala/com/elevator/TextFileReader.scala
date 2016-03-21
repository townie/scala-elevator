package com.elevator
import scala.io.Source._

// Simple class for reading text file and returning a collection of elevator banks
// Which can be used to instantciate ElevatorBanks 
object TextFileReader {
	// I dislike this function, but this is a mind
	def returnElevatorBankArray(elevatorFilename: String = "demo.txt") : Array[Array[String]] = {
		var elevatorBanks = Array[Array[String]]()
		var currentElevator = Array[String]()

		linesFromFile(elevatorFilename).foreach { line =>
			if(line == "\r\n" || line == "\n" || line == "") {
				elevatorBanks = elevatorBanks :+ currentElevator
				currentElevator = Array[String]()
			} else {
				currentElevator = currentElevator :+ line
			}
		}
	    //println(elevatorBanks.deep.mkString("\n"))
		return elevatorBanks
	}

	// probably like to move the defaults around to have a "default_state.txt"
	def linesFromFile(elevatorFilename: String = "demo.txt") = fromFile(elevatorFilename).getLines	
}
