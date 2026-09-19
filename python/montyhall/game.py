import random
from .strategies import GameStrategy, random_door

DOOR_COUNT = 3
DOOR_SUM = DOOR_COUNT * (DOOR_COUNT + 1) / 2

def play(strategy: GameStrategy) -> bool:
    """
    Plays a single round of the Monty Hall game using the given strategy.

    :strategy: The strategy to use for the game.
    :return: True if the player wins, False otherwise.
    """
    car_door = random_door(DOOR_COUNT)
    player_door = strategy.choose_door(DOOR_COUNT)
    host_door = host_reveal_door(car_door, player_door)
    final_door = apply_strategy(strategy, player_door, host_door)
    strategy.reveal_door(car_door)
    return final_door == car_door

def host_reveal_door(car_door: int, player_door: int) -> int:
    if car_door != player_door:
        return DOOR_SUM - car_door - player_door

    doors = set(range(1, DOOR_COUNT + 1))
    doors.remove(car_door)
    return random.choice(list(doors))

def apply_strategy(strategy: GameStrategy, player_door: int, host_door: int) -> int:
    if not strategy.switch_door(host_door):
        return player_door
    return DOOR_SUM - player_door - host_door
