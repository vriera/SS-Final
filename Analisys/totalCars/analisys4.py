import json
import numpy as np
from pprint import pprint
import matplotlib.pyplot as plt


def main():
    data = dict()
    results = dict()
    cat_results = dict()
    actual_cars = dict()
    stop_time = dict()
    total_time = dict()
    with open('./cars.json') as f:
        cars = json.load(f)
    for key, data in cars.items():
        max_vel = []
        distances = []
        stop_time[key] = []
        total_time[key] = []
        for id, details in data.items():
            car_max_vel = details['maxVel']

            if car_max_vel > 0:
                stop_time[key].append(details['timeStopped'])
                total_time[key].append(details['time'])
        actual_cars[key] = len(data)

        if len(max_vel) > 0:
            for i, d in enumerate(distances):
                if d == 0:
                    print(f"distance is 0 for {i}")
        cat_results[key] = results

    x = cars.keys()
    stop_time_proportion = []
    stop_time_std = []
    for key, value in stop_time.items():
        stop_time_key = np.array(stop_time[key])
        total_time_key = np.array(total_time[key])

        prop = stop_time_key / total_time_key
        mean = np.mean(prop)
        std = np.std(prop)
        stop_time_proportion.append(mean)
        stop_time_std.append(std)

    fig, ax = plt.subplots()
    x = [int(i) for i in x]
    ax.errorbar(x, stop_time_proportion, yerr=stop_time_std, linestyle='-', color='green', fmt='-', capsize=3, capthick=1, ecolor='green')

    plt.plot(x, stop_time_proportion, 'go-')
    plt.xlabel('Total de autos - Input')
    plt.ylabel('Proporción de viaje detenido')
    plt.grid()
    plt.show()


if __name__ == "__main__":
    main()
