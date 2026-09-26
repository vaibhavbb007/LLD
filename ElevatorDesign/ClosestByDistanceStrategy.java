package ElevatorDesign;

import java.util.List;
import java.util.Queue;

public class ClosestByDistanceStrategy implements IElevatorResovler {

    /*
     * 1. find if there is any elevator going to the intended direction
     * 2. before current position towards same direction or already passed from
     * current position
     * 3.
     */
    @Override
    public Elevator getElevator(UserRequest request, List<Elevator> elevators) {

        Elevator elevator = null;
        int minDistance = 9999;
        int userFloor = request.getCurrentFloor().getFloorNumber();
        Direction userDirection = request.getDestinationDireaction();
        for (int i = 0; i < elevators.size(); i++) {

            int floorOfTheElevator = elevators.get(i).getCurrent().getFloorNumber();

            if (userFloor == floorOfTheElevator) {
                return elevators.get(i);
            }

            if (elevators.get(i).getCurrentDirection() == Direction.IDLE) {
                int currentDifference = Math.abs(floorOfTheElevator - userFloor);
                if (currentDifference < minDistance) {
                    minDistance = currentDifference;
                    elevator = elevators.get(i);
                }
            }

            // DOWN case
            if (elevators.get(i).getCurrentDirection() == Direction.DOWN && userDirection == Direction.DOWN
                    && userFloor < floorOfTheElevator) {
                int currentDifference = Math.abs(floorOfTheElevator - userFloor);
                if (currentDifference < minDistance) {
                    elevator = elevators.get(i);
                }
            }

            if (elevators.get(i).getCurrentDirection() == Direction.DOWN && userDirection == Direction.DOWN
                    && userFloor > floorOfTheElevator) {
                int currentDifference = getTotalElvatorDistanceInDownDirection(elevators.get(i), userFloor);
                if (currentDifference < minDistance) {
                    elevator = elevators.get(i);
                }
            }

            // UP case
            if (elevators.get(i).getCurrentDirection() == Direction.UP && userDirection == Direction.UP
                    && userFloor > floorOfTheElevator) {
                int currentDifference = Math.abs(floorOfTheElevator - userFloor);
                if (currentDifference < minDistance) {
                    elevator = elevators.get(i);
                }
            }

            if (elevators.get(i).getCurrentDirection() == Direction.UP && userDirection == Direction.UP
                    && userFloor < floorOfTheElevator) {
                int currentDifference = getTotalElvatorDistanceInUpDirection(elevators.get(i), userFloor);
                if (currentDifference < minDistance) {
                    elevator = elevators.get(i);
                }
            }

        }

        return elevator;

    }

    private int getTotalElvatorDistanceInDownDirection(Elevator elevator, int userCurrentFloor) {
        int diff = userCurrentFloor - elevator.getCurrent().getFloorNumber();
        Queue<UserRequest> userRequests = elevator.getRequests();
        int minFloorNumber = 0;
        if (!userRequests.isEmpty()) {
            userRequests.stream().mapToInt(UserRequest::getCurrentFLoorNumber).min().getAsInt();
        }

        int distance = minFloorNumber + diff;
        return distance;
    }

    private int getTotalElvatorDistanceInUpDirection(Elevator elevator, int userCurrentFloor) {
        int diff = userCurrentFloor - elevator.getCurrent().getFloorNumber();
        Queue<UserRequest> userRequests = elevator.getRequests();
        int minFloorNumber = 0;
        if (!userRequests.isEmpty()) {
            minFloorNumber = userRequests.stream().mapToInt(UserRequest::getCurrentFLoorNumber).max().getAsInt();
        }

        int distance = minFloorNumber + Math.abs(diff);
        return distance;
    }

}
