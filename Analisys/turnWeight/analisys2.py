import json
import numpy as np
import matplotlib.pyplot as plt


def main():
    with open('./cars.json', 'r') as f:
        data = json.load(f)
    cum_vels = dict()
    total_times = dict()
    max_vel = 0
    for key in data:
        cum_vels[key] = []
        total_times[key] = []
        for car_key, car in data[key].items():
            cum_vels[key].append(car['cumVel'])
            total_times[key].append(car['time'])
            if max_vel == 0:
                max_vel = car['maxVel']

    avg_vel = []
    std_vel = []
    for key, value in cum_vels.items():
        value = np.array(value)
        times = np.array(total_times[key]) * 5
        avg_car_vel = value / times
        avg_vel.append(np.mean(avg_car_vel))
        std_vel.append(np.std(avg_car_vel))

    x = [int(key) for key in data.keys()]
    y = avg_vel
    yerr = std_vel

    plt.errorbar(x, y, yerr=yerr, fmt='o', color='r', ecolor='r', capthick=2, capsize=10)
    plt.xlabel('Costo de giro')
    plt.ylabel('Promedio de velocidades promedio')
    plt.grid()
    plt.plot(x, y, 'ro-')
    plt.show()


if __name__ == "__main__":
    main()
