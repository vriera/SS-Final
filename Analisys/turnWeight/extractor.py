import os
import json
from pprint import pprint

EPSILON = 0.01
def main():
    directory = "../../outputs"
    categories = ['0', '25', '50', '75', '100']
    separated = dict()
    for cat in categories:
        separated[cat] = list()
    for filename in os.listdir(directory):
        for cat in categories:
            if f"_{cat}_" in filename and "TURN" in filename:
                separated.get(cat).append(filename)
    fill_file(categories, separated)


def fill_file(categories, separated):
    output = dict()
    for cat in categories:
        cars = dict()
        pprint(separated.get(cat))
        for src_file in separated.get(cat):
            print("About to read: ", src_file)
            static = dict()
            with open(f"../../outputs/{src_file}/static.json", "r") as file_to_read:
                static = json.load(file_to_read)
            timeStep = static.get("config").get("timeStep")
            print(f"Time step: {timeStep}")
            finished = set()
            with open(f"../../outputs/{src_file}/cars.json", "r") as file_to_read:
                loaded = json.load(file_to_read)
                for i, details in loaded.items():
                    f = details.get("f")
                    if f == 1:
                        finished.add(i)

            with open(f"../../outputs/{src_file}/snapshots.json", "r") as file_to_read:
                loaded = json.load(file_to_read)
                time = 0
                for i, item in enumerate(loaded):  # For each time step
                    for index, id in enumerate(item.get("id")):
                        if str(id) not in finished:
                            continue
                        id = f"{src_file}_{id}"
                        # print(f"Reading id: {id} in t={time}")
                        if id not in cars:
                            cars[id] = {"lastPos": -1, "distance": 0, "time": 0, "maxVel": static.get("config").get("maximumDesiredSpeed"), "spawnTime": time, "cumVel": 0, "timeStopped": 0}
                        lastPos = cars[id]["lastPos"]
                        pos = item.get("p")[index]
                        if lastPos != -1:
                            if lastPos > pos:
                                cars[id]["distance"] += 100.0 - lastPos + pos
                            else:
                                cars[id]["distance"] += pos - lastPos
                            cars[id]["lastPos"] = pos
                            cars[id]["time"] += timeStep
                            cars[id]["cumVel"] += item.get("v")[index]
                            if item.get("v")[index] <= EPSILON and item.get("a")[index] <= EPSILON:
                                cars[id]["timeStopped"] += timeStep
                            elif item.get("s")[index] > 0:
                                cars[id]["timeStopped"] += timeStep
                        else:
                            cars[id]["lastPos"] = pos
                    time += timeStep
        for id, details in cars.items():
            cars[id]["distance"] = round(cars[id]["distance"], 2)
            cars[id]["time"] = round(cars[id]["time"], 2)
            cars[id]["maxVel"] = round(cars[id]["maxVel"], 2)
            cars[id]["spawnTime"] = round(cars[id]["spawnTime"], 2)
        cars = dict(filter(lambda x: x[1].get("distance") > 0, cars.items()))
        output[cat] = cars
    json.dump(output, open("cars.json", "w"))


if __name__ == "__main__":
    main()
