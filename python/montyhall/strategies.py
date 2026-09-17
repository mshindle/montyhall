import random
from abc import ABC, abstractmethod


def random_door(door_count: int) -> int:
    """
    Returns a random door number from 1 to door_count inclusive.
    :rtype: int
    """
    return random.randint(1, door_count)

class GameStrategy(ABC):
    @abstractmethod
    def display_name(self) -> str:
        """Returns the name of the strategy"""
        pass

    @abstractmethod
    def choose_door(self, door_count: int) -> int:
        """Returns the initially chosen door number"""
        pass

    @abstractmethod
    def switch_door(self, host_door: int) -> bool:
        """Returns true if the player should switch doors"""
        pass

    @abstractmethod
    def reveal_door(self, car_door: int) -> None:
        """Informs the strategy which door held the car. If it matches the player's initial choice and
        the player did not switch doors, the player won the car, otherwise he lost. Conversely, if it
        does not match the player's initial choice and the player switched doors, the player won the
        car otherwise he lost"""
        pass

class AlwaysSwitch(GameStrategy):
    def display_name(self) -> str:
        return "Always Switch"

    def choose_door(self, door_count: int) -> int:
        return random_door(door_count)

    def switch_door(self, host_door: int) -> bool:
        return True

    def reveal_door(self, car_door: int) -> None:
        pass

class AlwaysStay(GameStrategy):
    def display_name(self) -> str:
        return "Always Stay"

    def choose_door(self, door_count: int) -> int:
        return random_door(door_count)

    def switch_door(self, host_door: int) -> bool:
        return False

    def reveal_door(self, car_door: int) -> None:
        pass

class CoinFlip(GameStrategy):
    def display_name(self) -> str:
        return "Coin Flip"

    def choose_door(self, door_count: int) -> int:
        return random_door(door_count)

    def switch_door(self, host_door: int) -> bool:
        return random.choice([True, False])

    def reveal_door(self, car_door: int) -> None:
        pass