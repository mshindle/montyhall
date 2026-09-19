package strategy

import "github.com/mshindle/montyhall/internal/random"

// AlwaysSwitch implements a strategy in which the player always changes from the original door
type AlwaysSwitch struct{}

func (as *AlwaysSwitch) DisplayName() string {
	return alwaysSwitch
}

func (as *AlwaysSwitch) ChooseDoor(doorCount int) int {
	return random.OneInc(doorCount)
}

func (as *AlwaysSwitch) SwitchDoor(_ int) bool {
	return true
}

func (as *AlwaysSwitch) RevealCarDoor(_ int) {
	return
}
