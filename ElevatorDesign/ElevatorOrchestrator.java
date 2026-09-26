package ElevatorDesign;

import java.util.Scanner;

public class ElevatorOrchestrator {
    public static void main(String[] args) {
        // SetUp building
        Building building = new Building("Google HeadQuarter main", 2, 10);
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n|----------------------------------------------|");
                System.out.println("\n|          Elevator Manager V01.2026           |");
                System.out.println("\n|----------------------------------------------|");
                System.out.println("\n        * " + building.getBuildingName() + " *");
                System.out.println("\n------------------------------------------------");
                for (Elevator elevator : building.getElevators()) {
                    System.out.print("Elevator" + elevator.getElevatorNumber() + " : "
                            + elevator.getCurrent().getFloorNumber() + " |\t");
                }

                // Inputs
                System.out.println("\n------------------------------------------------");
                System.out.println("\nCurrent Floor : ");
                int floorNumber = sc.nextInt();
                Floor currentFloor = building.getFloors().get(floorNumber - 1);
                System.out.println("\nDestination Floor : ");
                int destFloorNumber = sc.nextInt();
                Floor destinationFloor = building.getFloors().get(destFloorNumber - 1);
                boolean isDirectionUp = (destFloorNumber - floorNumber) >= 1 ? true : false;

                Direction direction = isDirectionUp ? Direction.UP : Direction.DOWN;
                UserRequest request = new UserRequest(currentFloor, direction, destinationFloor);

                IElevatorResovler strategy = new ClosestByDistanceStrategy();
                Elevator selectedElevator = strategy.getElevator(request, building.getElevators());
                selectedElevator.getRequests().add(request);
                selectedElevator.operateElevator();

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}
