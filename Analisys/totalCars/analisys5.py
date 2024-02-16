import json
import numpy as np
import os
from pprint import pprint
import matplotlib.pyplot as plt


def main():
    output_dir = '../../outputs/'
    # Filter all directories that contain "CARS"
    filenames = [f for f in os.listdir(output_dir) if 'CARS' in f]
    spawns = dict()
    for filename in filenames:
        total = filename.split('_')[1]
        if total not in spawns:
            spawns[total] = []
        with open(output_dir + filename + '/cars.json') as data_file:
            data = json.load(data_file)
            total_cars = len(data)
            spawns[total].append(total_cars)
    avg = dict()
    std = dict()
    for key in spawns.keys():
        avg[key] = np.mean(np.array(spawns[key]))/int(key)
        std[key] = np.std(np.array(spawns[key]))/int(key)

    x = [int(a) for a in spawns.keys()]
    x.sort()
    avg = [avg[str(a)] * 100 for a in x]
    std = [std[str(a)] * 100 for a in x]
    # y = [float(key) - np.mean(np.array(spawns[str(key)])) for key in spawns.keys()]
    # std = [np.std(np.array(spawns[str(key)])) for key in spawns.keys()]
    # x = [int(i) for i in x]

    fig, ax = plt.subplots()
    ax.errorbar(x, avg, yerr=std, linestyle='-', color='blue', fmt='-', capsize=3, capthick=1, ecolor='blue')

    plt.plot(x, avg, 'bo-')
    plt.xlabel('Total de autos - Input')
    plt.ylabel('Porcentaje que pudo ser generado')
    plt.grid()
    plt.show()


if __name__ == "__main__":
    main()
