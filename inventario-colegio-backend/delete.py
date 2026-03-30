import os
import glob

base_dir = r"c:\Users\Logan\Desktop\Proyectos\Inventario-SETUP\proyecto\inventario-colegio-backend (1)\inventario-colegio-backend\src\main\java\com\colegio\inventario"

targets = [
    r"domain\catalogo\*.java",
    r"domain\repository\catalogo\*.java",
    r"application\service\catalogo\*.java",
    r"application\dto\catalogo\*.java",
    r"application\mapper\catalogo\*.java",
    r"infrastructure\controller\catalogo\*.java"
]

for t in targets:
    pattern = os.path.join(base_dir, t)
    files = glob.glob(pattern)
    for f in files:
        if os.path.isfile(f):
            print(f"Deleting {f}")
            os.remove(f)

print("Done.")
