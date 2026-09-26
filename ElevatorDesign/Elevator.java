package ElevatorDesign;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Elevator
 */
public class Elevator {

    private Floor current;
    private Direction currentDirection;
    private Queue<UserRequest> requests;
    private DoorState doorState;
    private ElevatorState elevatorState;
    private int elevatorNumber;
    private static final int timePerFloor = 1000;
    private UserRequest tempRequest;

    Elevator(int elevatorNumber, ElevatorState state, Floor currentFloor) {
        this.elevatorNumber = elevatorNumber;
        this.elevatorState = state;
        this.currentDirection = Direction.IDLE;
        this.current = currentFloor;
        this.requests = new ArrayDeque<>();
    }

    public void operateElevator() {
        while (!this.requests.isEmpty()) {
            UserRequest request = requests.poll();
            tempRequest = request;
            this.currentDirection = request.getDestinationDireaction();

            // see if the elevator has to make two direction changes
            boolean isOutOfBound = (currentDirection == Direction.DOWN
                    && getCurrent().getFloorNumber() < request.getCurrentFLoorNumber())
                    || (currentDirection == Direction.UP
                            && getCurrent().getFloorNumber() > request.getCurrentFLoorNumber());

            if (isOutOfBound) {
                // move user current floor and then to destination
                elevatorFloorMovement(current, request.getCurrentFloor());
                elevatorFloorMovement(current, request.getDestinationFloor());
            } else {
                elevatorFloorMovement(current, request.getDestinationFloor());
            }
            this.currentDirection = Direction.IDLE;

        }
    }

    private void middleStopProcessing(Floor current, Floor destinationFloor) {

        try {

            // To stop at the in between stops while processing current userRequest while
            // goint upwards
            int distance = 0;
            List<UserRequest> stops = new ArrayList<>();
            if (getCurrentDirection() == Direction.UP) {
                distance = destinationFloor.getFloorNumber() - current.getFloorNumber();
                stops = inBetweenStopUpDirection(current, destinationFloor);
                stops.sort(Comparator.comparing(UserRequest::getCurrentFLoorNumber));

            }

            // To stop at the in between stops while processing current userRequest while
            // goint downwards
            if (getCurrentDirection() == Direction.DOWN) {

                distance = Math.abs(destinationFloor.getFloorNumber() - current.getFloorNumber());
                stops = inBetweenStopDownDirection(current, destinationFloor);
                stops.sort(Comparator.comparing(UserRequest::getCurrentFLoorNumber).reversed());

            }
            int counter = 0;
            int listCounter = 0;
            while (counter < stops.size()) {
                int dist = Math
                        .abs(stops.get(listCounter).getCurrentFloor().getFloorNumber() - current.getFloorNumber());
                this.setCurrent(stops.get(listCounter).getCurrentFloor());
                System.out.println("\nElevator " + this.elevatorNumber + " Moving to the floor "
                        + stops.get(listCounter).getCurrentFloor().getFloorNumber() + " In "
                        + dist * (timePerFloor / 1000) + " seconds....");
                Thread.sleep(dist * timePerFloor);
                openDoor();
                waitForTime();
                closeDoor();
                counter++;
                listCounter++;
            }
        } catch (Exception e) {

        }

    }

    private void completeTheJourneyTillEnd(Floor current, Floor destinationFloor) {
        try {
            int distance = 0;

            boolean upCondition = getCurrentDirection() == Direction.UP
                    && inBetweenStopUpDirection(this.getCurrent(), destinationFloor).isEmpty();
            boolean downCondition = getCurrentDirection() == Direction.DOWN
                    && inBetweenStopDownDirection(this.getCurrent(), destinationFloor).isEmpty();

            if (upCondition || downCondition) {
                distance = Math.abs(this.getCurrent().getFloorNumber() - destinationFloor.getFloorNumber());
                System.out.println("\nElevator " + this.elevatorNumber + " Moving to Floor "
                        + destinationFloor.getFloorNumber() + " In "
                        + distance * (timePerFloor / 1000) + " seconds....");
                Thread.sleep(distance * timePerFloor);
                this.current = destinationFloor;
                openDoor();
                waitForTime();
                closeDoor();
            }
        } catch (Exception e) {

        }
    }

    private void elevatorFloorMovement(Floor current, Floor destinationFloor) {
        middleStopProcessing(current, destinationFloor);
        completeTheJourneyTillEnd(current, destinationFloor);
    }

    private List<UserRequest> inBetweenStopDownDirection(Floor current, Floor destinationFloor) {

        List<UserRequest> stops = this.getRequests().stream()
                .filter(requests -> requests.getCurrentFLoorNumber() > destinationFloor.getFloorNumber())
                .collect(Collectors.toList());

        return stops;
    }

    private List<UserRequest> inBetweenStopUpDirection(Floor current, Floor destinationFloor) {

        List<UserRequest> stops = this.getRequests().stream()
                .filter(requests -> requests.getCurrentFLoorNumber() < destinationFloor.getFloorNumber())
                .collect(Collectors.toList());
        if (tempRequest.getCurrentFLoorNumber() < destinationFloor.getFloorNumber()
                && tempRequest.getCurrentFLoorNumber() != current.getFloorNumber()) {
            stops.add(tempRequest);
        }
        return stops;
    }

    private void waitForTime() {
        try {
            System.out.println("Waiting on the Floor " + getCurrent().getFloorNumber());
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    private void closeDoor() {
        try {
            System.out.println("Closing the door on Floor :" + getCurrent().getFloorNumber());
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    private void openDoor() {
        try {
            System.out.println("Opening the door Floor : " + getCurrent().getFloorNumber());
            // remove the floor which has this destination in same direction
            List<UserRequest> satisfiedDestinationList = getRequests().stream()
                    .filter(request -> request.getDestinationFloor().getFloorNumber() == getCurrent().getFloorNumber())
                    .collect(Collectors.toList());
            getRequests().removeAll(satisfiedDestinationList);
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    public Floor getCurrent() {
        return current;
    }

    public void setCurrent(Floor current) {
        this.current = current;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Queue<UserRequest> getRequests() {
        return requests;
    }

    public void setRequests(Queue<UserRequest> requests) {
        this.requests = requests;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }

    public ElevatorState getElevatorState() {
        return elevatorState;
    }

    public void setElevatorState(ElevatorState elevatorState) {
        this.elevatorState = elevatorState;
    }

    public int getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(int elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

}