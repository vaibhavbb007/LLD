package ElevatorDesign;

public class UserRequest {
    private Floor currentFloor;
    private Direction destinationDireaction;
    private Floor destinationFloor;

    UserRequest(Floor Currentfloor, Direction destinationDireaction, Floor destinationFloor) {
        this.currentFloor = Currentfloor;
        this.destinationDireaction = destinationDireaction;
        this.destinationFloor = destinationFloor;
    }

    public Floor getDestinationFloor(){
        return this.destinationFloor;
    }

    public Floor getCurrentFloor() {
        return this.currentFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getDestinationDireaction() {
        return destinationDireaction;
    }

    public void setDestinationDireaction(Direction destinationDireaction) {
        this.destinationDireaction = destinationDireaction;
    }

    public int getCurrentFLoorNumber(){
        return this.getCurrentFloor().getFloorNumber();
    }

    
}
