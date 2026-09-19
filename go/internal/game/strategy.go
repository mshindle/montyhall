package game

type Strategy interface {
	DisplayName() string
	ChooseDoor(doorCount int) int
	SwitchDoor(hostDoor int) bool
	RevealCarDoor(carDoor int)
}
