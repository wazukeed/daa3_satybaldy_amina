import csv
from pathlib import Path
import matplotlib.pyplot as plt

root = Path(__file__).resolve().parent.parent
results = root / "results"

with open(results / "results.csv", newline="", encoding="utf-8") as file:
    rows = list(csv.DictReader(file))

algorithms = sorted(set(row["algorithm"] for row in rows))
factors = sorted(set(int(row["factor"]) for row in rows))


def get_points(algorithm, factor, column):
    data = []

    for row in rows:
        if row["algorithm"] == algorithm and int(row["factor"]) == factor:
            data.append((
                int(row["vertices"]),
                float(row[column])
            ))

    data.sort()
    return [point[0] for point in data], [point[1] for point in data]


# Graph 1: Running time
fig, axes = plt.subplots(1, len(factors), figsize=(13, 5), squeeze=False)

for i, factor in enumerate(factors):
    ax = axes[0][i]

    for algorithm in algorithms:
        x, y = get_points(algorithm, factor, "avg_time_ms")
        ax.plot(x, y, marker="o", label=algorithm)

    ax.set_title(f"Running Time (E = {factor}V)")
    ax.set_xlabel("Number of vertices")
    ax.set_ylabel("Average time (ms)")
    ax.set_xscale("log")
    ax.set_yscale("log")
    ax.grid(True, alpha=0.3)
    ax.legend(fontsize=8)

plt.tight_layout()
plt.savefig(results / "runtime.png", dpi=160)
plt.close()


# Graph 2: Edge scans
factor = max(factors)

plt.figure(figsize=(9, 6))

for algorithm in algorithms:
    x, y = get_points(algorithm, factor, "edge_scans")
    plt.plot(x, y, marker="o", label=algorithm)

plt.title(f"Edge Scans (E = {factor}V)")
plt.xlabel("Number of vertices")
plt.ylabel("Edge scans")
plt.xscale("log")
plt.grid(True, alpha=0.3)
plt.legend()
plt.tight_layout()
plt.savefig(results / "edge_scans.png", dpi=160)
plt.close()


# Graph 3: Comparisons for MST algorithms
plt.figure(figsize=(9, 6))

for algorithm in algorithms:
    if "kruskal" in algorithm.lower() or "prim" in algorithm.lower():
        x, y = get_points(algorithm, factor, "comparisons")
        plt.plot(x, y, marker="o", label=algorithm)

plt.title(f"MST Comparisons (E = {factor}V)")
plt.xlabel("Number of vertices")
plt.ylabel("Comparisons")
plt.xscale("log")
plt.grid(True, alpha=0.3)
plt.legend()
plt.tight_layout()
plt.savefig(results / "comparisons.png", dpi=160)
plt.close()

print("All 3 graphs created successfully!")