import random
from .strategies import GameStrategy

DOOR_COUNT = 3

def play(strategy: GameStrategy) -> bool:
    """
    Plays a single round of the Monty Hall game using the given strategy.

    :strategy: The strategy to use for the game.
    :return: True if the player wins, False otherwise.
    """
    car_door = random_door()
    player_door = strategy.choose_door(DOOR_COUNT)
    host_door = host_reveal_door(car_door, player_door)
    final_door = apply_strategy(strategy, player_door, host_door)
    strategy.reveal_door(car_door)
    return final_door == car_door

def host_reveal_door(car_door: int, player_door: int) -> int:
    doors = set(range(1, DOOR_COUNT + 1))
    doors.remove(car_door)
    if player_door != car_door:
        doors.remove(player_door)
        return doors.pop()
    return random.choice(list(doors))

def apply_strategy(strategy: GameStrategy, player_door: int, host_door: int) -> int:
    if not strategy.switch_door(host_door):
        return player_door

    for i in range(1,DOOR_COUNT+1):
        if i != player_door and i != host_door:
            return i
    raise ValueError("Unable to determine door to switch to")

def random_door() -> int:
    """Returns a random door number starting at 1."""
    return random.randint(1, DOOR_COUNT)