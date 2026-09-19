package random

import "math/rand/v2"

// OneInc returns a random integer between 1 and n, inclusive.
func OneInc(n int) int {
	return rand.N(n) + 1
}

func Bool() bool {
	return rand.N(2) == 1
}
