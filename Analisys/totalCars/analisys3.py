import json
import numpy as np
from pprint import pprint
import matplotlib.pyplot as plt


def main():
    data = dict()
    results = dict()
    cat_results = dict()
    actual_cars = dict()
    last_spawns = dict()
    total_vels = dict()
    total_times = dict()
    with open('./cars.json') as f:
        cars = json.load(f)
    for key, data in cars.items():
        max_vel = []
        distances = []
        last_spawns[key] = dict()
        total_vels[key] = list()
        total_times[key] = list()
        for id, details in data.items():
            sim_id = '_'.join(id.split('_')[:3])
            if sim_id not in last_spawns[key]:
                last_spawns[key][sim_id] = float(details['spawnTime'])
            elif last_spawns[key][sim_id] < float(details['spawnTime']):
                last_spawns[key][sim_id] = float(details['spawnTime'])
            car_max_vel = details['maxVel']
            car_total_vel = details['cumVel']

            if car_max_vel > 0:
                total_vels[key].append(car_total_vel)
                total_times[key].append(details['time'])
        actual_cars[key] = len(data)

        if len(max_vel) > 0:
            for i, d in enumerate(distances):
                if d == 0:
                    print(f"distance is 0 for {i}")
        cat_results[key] = results

    x = cars.keys()
    avg_total_vel = []
    std_total_vel = []
    for key in x:
        total_vels_key = np.array(total_vels[key])
        total_times_key = np.array(total_times[key]) * 5
        avg_total_vel.append(np.mean(total_vels_key / total_times_key))
        std_total_vel.append(np.std(total_vels_key / total_times_key))

    # vel_max = 11.111111111
    # avg_total_vel = np.array(avg_total_vel) / vel_max
    # std_total_vel = np.array(std_total_vel) / vel_max

    x = [int(i) for i in x]
    fig, ax = plt.subplots()
    ax.errorbar(x, avg_total_vel, yerr=std_total_vel, linestyle='-', color='blue', fmt='-', capsize=3, capthick=1, ecolor='blue')

    plt.plot(x, avg_total_vel, 'bo-')
    plt.xlabel('Total de autos - Input')
    plt.ylabel('Promedio de velocidades promedio')
    plt.grid()
    plt.show()


if __name__ == "__main__":
    main()
