package com.elevator
object FoundPath extends Exception { }

// Responsible for holding the state of the elevators over different time periods
// The elevators at each time step are represented as an adjacendy list:
//
// Raw text t1 = 
// xxxxxxxxxxxx
// xxAx.x.x.xxx
// xx.x.x.xDxxx
// xx.xBxCx.xxx
// xx.x.x.x.xxx
// xx.x.x.x.xxx
//
// Coverted to => 
// 5 -> [A]
// 4 -> [D]
// 3 -> [B, C]
// 2 -> []
// 1 -> []

class ElevatorBank() {
	// TODO: Replace this with a proper elevator object 
	// TODO: Replace this with a better time series concept/library
	var elevatorHistory = Seq[Map[Int, Array[String]]]()

	def addElevator(elevator: Map[Int, Array[String]]) = {
		elevatorHistory = elevatorHistory :+ elevator
	} 

	// This can probably be memoized to speed up lookups
    def elevatorLocationInHistory(requestedElevator: String, timeStep: Int = 0) : Int = {
    	// TODO refactor this into yeild able function
    	val loc = (elevatorHistory(timeStep).keys, elevatorHistory(timeStep).values.map(_.mkString(" ")))
			.zipped
			.filter { (floor, elevator) =>
				elevator.contains(requestedElevator )
			}._1
		// Console.println(loc.last)
		return loc.last
    }

    def elevatorsForFloorInHistory(requestedFloor: Int, timeStep: Int = 0) : Array[String] = {
		return elevatorHistory(timeStep)(requestedFloor)
    }

    // DFS for figuring out if any paths lead to desiredFloor
    def determinePath(startingLocation: Int, desiredFloor: Int, maxTime: Int) = {
    	var path = Array[String]()
    	var result = "Failed"
    	try {
    		result = dfs(startingLocation, desiredFloor, 0, maxTime, path )
    	} catch {
    		case FoundPath => Console.println("Success")
    		result = "Success"
    	}
    	if(result !=  "Success") {
			System.err.println("No Path available in that time")
    	}
    }

    def dfs(currentFloor: Int, desiredFloor: Int, currentTime: Int, maxTime: Int, currentPath: Array[String] ) : String = {
    	
    	if (currentFloor == desiredFloor) {
    		// Need to break here
    		val succesfulPath = currentPath :+ elevatorsForFloorInHistory(currentFloor,currentTime).last
    		Console.println(succesfulPath.mkString(" "))
			throw FoundPath
			return "Success"
    	} else {

	    	if (maxTime == currentTime) {
	    		return "time"
	    	}
	    	val newTime = currentTime + 1

	    	elevatorsForFloorInHistory(currentFloor, currentTime).map {(elevator) => 
	    	   // Console.println(elevator)
	    	   val newFloor = elevatorLocationInHistory(elevator, newTime)
	    	   dfs(newFloor, desiredFloor, newTime, maxTime, currentPath :+ elevator)
	    	}
		}
    return "Return?"
    }


	// Useful for displaying the AdjencyList 
	// timeStep - timestep doenst check for out of bounds
	def displayAdjencyList(timeStep: Int = 0) {
        Console.println("elevatorHistory= "+timeStep)

		(elevatorHistory(timeStep).keys, elevatorHistory(timeStep).values.map(_.mkString(" ")))
			.zipped
			.foreach { (floor, elevator) => 
		  		Console.println(floor+elevator)
			}
	} 
}