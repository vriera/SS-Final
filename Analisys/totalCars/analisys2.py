import json
import numpy as np
from pprint import pprint
import matplotlib.pyplot as plt


def main():
    data = dict()
    results = dict()
    cat_results = dict()
    actual_cars = dict()
    with open('./cars.json') as f:
        cars = json.load(f)
    for key, data in cars.items():
        max_vel = []
        total_times = []
        distances = []
        for id, details in data.items():
            car_max_vel = details['maxVel']
            car_distance = details['distance']

            if car_max_vel > 0:
                max_vel.append(car_max_vel)
                total_times.append(details['time'])
                distances.append(car_distance)
        actual_cars[key] = len(data)

        if len(max_vel) > 0:
            max_vel = np.array(max_vel)
            total_times = np.array(total_times)
            avg_time = np.mean(total_times)
            distances = np.array(distances)
            for i, d in enumerate(distances):
                if d == 0:
                    print(f"distance is 0 for {i}")
            min_times = distances / max_vel
            taus = total_times / min_times
            for tau in taus:
                if tau == np.inf:
                    print(f"tau is inf for {key}")

            avg_tau = np.mean(taus)
            std_tau = np.std(taus)
            results = {"avg_tau": avg_tau, "std_tau": std_tau, "total_tau": np.sum(taus), "avg_time": avg_time}
        cat_results[key] = results

    x = cars.keys()
    y = [x.get('avg_tau') for x in cat_results.values()]

    fig, ax = plt.subplots()
    ax.errorbar(x, y, yerr=[x.get('std_tau') for x in cat_results.values()], linestyle='-', color='blue', fmt='-', capsize=5, capthick=2, ecolor='blue')

    plt.plot(x, y, 'bo-')
    plt.grid()
    plt.show()


if __name__ == "__main__":
    main()
