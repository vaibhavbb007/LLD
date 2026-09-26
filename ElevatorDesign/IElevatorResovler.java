package ElevatorDesign;

import java.util.List;

public interface IElevatorResovler {
     Elevator getElevator(UserRequest request, List<Elevator> elevators);
}
