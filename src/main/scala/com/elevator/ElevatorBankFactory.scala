package com.elevator
// Factory for converting the Array of Arrays into a ElevatorBank
// This is more of the interface from the data parsing layer into 
// the application layer.
object ElevatorBankFactory {
	def buildFromArrays(raw: Array[Array[String]]) : ElevatorBank = {
		var bank = new ElevatorBank()

		raw.foreach { rawElevator =>
			bank.addElevator(buildElevator(rawElevator))
		}

		return bank
	}
	
	// Convert list of arrays into
	def buildElevator(rawElements: Array[String]) : Map[Int, Array[String]] = {
		// since first row consists of only "xxxxxxxxxxxxxxxx" drop it
		var elevatorsPerFloor = rawElements.drop(1)
		val floors = 1 to numberOfFloors(elevatorsPerFloor)
		var adjacencyList = Map[Int, Array[String]]()

		(floors.reverse,elevatorsPerFloor).zipped.foreach { (floor,rawFloor) =>
		    adjacencyList += ( floor -> elevatorsOnFloor(rawFloor))
		}
		// Console.print(adjacencyList.values.mkString("\n"))
		return adjacencyList
	}	

	// remove the extra elements and return an Array
	def elevatorsOnFloor(floor: String) : Array[String] = floor.split("[x]|[.]").flatten.map(_.toString)
 
 	// Used to create index
 	def numberOfFloors(elements: Array[String]) : Int = elements.length
}