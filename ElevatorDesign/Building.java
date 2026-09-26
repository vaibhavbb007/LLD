package ElevatorDesign;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private final List<Elevator> elevators;
    private final List<Floor> floors;
    private String buildingName;

    Building(String buildingName, int noOfElevetors, int noOfFloors) {
        elevators = new ArrayList<>(noOfElevetors);
        floors = new ArrayList<>(noOfFloors);
        this.buildingName = buildingName == null ? "Unammed" : buildingName;
        initFloors(noOfFloors);
        initElevators(noOfElevetors);
    }

    private void initElevators(int noOfElevetors) {
        for (int i = 0; i < noOfElevetors; i++) {
            Elevator elevator = new Elevator(i + 1, ElevatorState.WORKING, new Floor(1));
            elevators.add(elevator);
        }
    }

    private void initFloors(int noOfFloors) {
        for (int i = 0; i < noOfFloors; i++) {
            Floor floor = new Floor(i + 1);
            floors.add(floor);
        }
    }

    public List<Elevator> getElevators() {
        return this.elevators;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    

}
