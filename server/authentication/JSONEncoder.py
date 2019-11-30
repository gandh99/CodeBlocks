import abc
import json


class JSONEncoder(object, metaclass=abc.ABCMeta):
    @abc.abstractmethod
    def encode(self, o):
        pass
