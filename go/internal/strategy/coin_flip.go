package strategy

import "github.com/mshindle/montyhall/internal/random"

// CoinFlip implements a strategy in which the player flips a coin (randomly decides between the two doors with
// an even chance) to decide which door to choose
type CoinFlip struct{}

func (as *CoinFlip) DisplayName() string {
	return coinFlip
}

func (as *CoinFlip) ChooseDoor(doorCount int) int {
	return random.OneInc(doorCount)
}

func (as *CoinFlip) SwitchDoor(_ int) bool {
	return random.Bool()
}

func (as *CoinFlip) RevealCarDoor(_ int) {
	return
}
