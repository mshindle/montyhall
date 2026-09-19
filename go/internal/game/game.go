package game

import (
	"github.com/mshindle/montyhall/internal/random"
)

const (
	numberDoors = 3
	doorSum     = numberDoors * (numberDoors + 1) / 2
)

func Play(strategy Strategy) bool {
	carDoor := random.OneInc(numberDoors)
	playerDoor := strategy.ChooseDoor(numberDoors)
	hostDoor := revealHostDoor(carDoor, playerDoor)
	finalDoor := applyStrategy(strategy, playerDoor, hostDoor)
	strategy.RevealCarDoor(carDoor)
	return finalDoor == carDoor
}

func revealHostDoor(carDoor, playerDoor int) int {
	if carDoor != playerDoor {
		// only one door is left that isn't the car or the player's pick
		return doorSum - carDoor - playerDoor
	}
	// player already has the car; host must pick one of the other two at random
	var other1, other2 int
	for d := 1; d <= numberDoors; d++ {
		switch {
		case d == carDoor:
			continue
		case other1 == 0:
			other1 = d
		default:
			other2 = d
		}
	}
	if random.Bool() {
		return other1
	}
	return other2
}

func applyStrategy(strategy Strategy, playerDoor, hostDoor int) int {
	if !strategy.SwitchDoor(hostDoor) {
		return playerDoor
	}
	return doorSum - playerDoor - hostDoor
}
