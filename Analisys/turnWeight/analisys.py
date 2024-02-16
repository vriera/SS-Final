import json
import numpy as np
import matplotlib.pyplot as plt


def main():
    with open('./cars.json', 'r') as f:
        data = json.load(f)
    taus = dict()
    for key in data:
        taus[key] = []
        for car_key, car in data[key].items():
            min_time = car['distance'] / car['maxVel']
            total_time = car['time']
            tau = total_time / min_time
            taus[key].append(tau)

    taus_avg = []
    taus_std = []
    x = [int(key) for key in data.keys()]
    x.sort()
    for key in x:
        value = np.array(taus[str(key)])
        taus_avg.append(np.mean(value))
        taus_std.append(np.std(value))
        print(f"Average tau: {taus_avg}")


    y = taus_avg
    yerr = taus_std
    plt.errorbar(x, y, yerr=yerr, fmt='o', color='r', ecolor='r', capthick=2, capsize=10)
    plt.xlabel('Costo de giro')
    plt.ylabel('Tau promedio')
    plt.grid()
    plt.plot(x, y, 'ro-')
    plt.show()


if __name__ == "__main__":
    main()
