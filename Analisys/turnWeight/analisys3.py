import json
import numpy as np
import matplotlib.pyplot as plt


def main():
    with open('./cars.json', 'r') as f:
        data = json.load(f)
    stopped_times = dict()
    for key in data:
        stopped_times[key] = list()
        for car_key, car in data[key].items():
            total_time = car['time']
            stopped_time = car['timeStopped']
            stopped_times[key].append(stopped_time / total_time)
    stopped_times_avg = []
    stopped_times_std = []
    x = [int(key) for key in data.keys()]
    x.sort()
    for key in x:
        value = np.array(stopped_times[str(key)])
        stopped_times_avg.append(np.mean(value) * 100)
        stopped_times_std.append(np.std(value) * 100)
    y = stopped_times_avg
    yerr = stopped_times_std
    plt.errorbar(x, y, yerr=yerr, fmt='o', color='r', ecolor='r', capthick=2, capsize=10)
    plt.xlabel('Costo de giro')
    plt.ylabel('Proporción de tiempo detenido (%)')
    plt.grid()
    plt.plot(x, y, 'ro-')
    plt.show()


if __name__ == "__main__":
    main()
