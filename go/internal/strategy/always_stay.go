package strategy

import "github.com/mshindle/montyhall/internal/random"

// AlwaysStay implements a strategy in which the player always sticks with the original door
type AlwaysStay struct{}

func (as *AlwaysStay) DisplayName() string {
	return alwaysStay
}

func (as *AlwaysStay) ChooseDoor(doorCount int) int {
	return random.OneInc(doorCount)
}

func (as *AlwaysStay) SwitchDoor(_ int) bool {
	return false
}

func (as *AlwaysStay) RevealCarDoor(_ int) {
	return
}
