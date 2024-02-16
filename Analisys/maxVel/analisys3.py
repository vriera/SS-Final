import json
import numpy as np
import matplotlib.pyplot as plt
import os


def main():
    directory = '../../outputs/'
    spawns = dict()
    max_cars = 0
    for filename in os.listdir(directory):
        if 'VEL_' in filename:
            category = filename.split('_')[1]
            if category not in spawns:
                spawns[category] = list()
            if max_cars == 0:
                with open(directory + filename + '/static.json', 'r') as f:
                    data = json.load(f)
                    config = data['config']
                    max_cars = config['cars']
            with open(directory + filename + '/cars.json', 'r') as f:
                data = json.load(f)
                spawned_cars = len(data)
                spawns[category].append(spawned_cars)

    x = [float(key) for key in spawns.keys()]
    x.sort()
    y = list()
    yerr = list()
    for key in x:
        value = spawns[str(key)]
        value = np.array(value) / max_cars
        y.append(np.mean(value) * 100)
        yerr.append(np.std(value) * 100)
    plt.errorbar(x, y, yerr=yerr, fmt='o', color='b', ecolor='b', capthick=2, capsize=10)
    plt.xlabel('Velocidad máxima deseada (m/s)')
    plt.ylabel('Porcentaje de autos generados')
    plt.grid()
    plt.plot(x, y, 'bo-')
    plt.show()


if __name__ == "__main__":
    main()
