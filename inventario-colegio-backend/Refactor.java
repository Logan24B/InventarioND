import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Refactor {
    public static void main(String[] args) throws IOException {
        String baseDir = "src/main/java";
        Map<String, String> map = new HashMap<>();
        map.put("CatalogoBase", "com.colegio.inventario.domain.catalogo.base.CatalogoBase");
        map.put("CatalogoConEstadoBase", "com.colegio.inventario.domain.catalogo.base.CatalogoConEstadoBase");
        map.put("CategoriaHardware", "com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware");
        map.put("CategoriaOrdenador", "com.colegio.inventario.domain.catalogo.hardware.CategoriaOrdenador");
        map.put("Procesador", "com.colegio.inventario.domain.catalogo.hardware.Procesador");
        map.put("Ram", "com.colegio.inventario.domain.catalogo.hardware.Ram");
        map.put("Rom", "com.colegio.inventario.domain.catalogo.hardware.Rom");
        map.put("TipoRom", "com.colegio.inventario.domain.catalogo.hardware.TipoRom");
        map.put("SO", "com.colegio.inventario.domain.catalogo.software.SO");
        map.put("Ofimatica", "com.colegio.inventario.domain.catalogo.software.Ofimatica");
        map.put("Marca", "com.colegio.inventario.domain.catalogo.equipo.Marca");
        map.put("Modelo", "com.colegio.inventario.domain.catalogo.equipo.Modelo");
        map.put("RolEquipo", "com.colegio.inventario.domain.catalogo.equipo.RolEquipo");
        map.put("Edificio", "com.colegio.inventario.domain.catalogo.ubicacion.Edificio");
        map.put("Departamento", "com.colegio.inventario.domain.catalogo.ubicacion.Departamento");
        map.put("Secciones", "com.colegio.inventario.domain.catalogo.ubicacion.Secciones");
        map.put("Cargo", "com.colegio.inventario.domain.personal.Cargo");

        List<Path> files = Files.walk(Paths.get(baseDir))
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .collect(Collectors.toList());

        for (Path f : files) {
            String content = new String(Files.readAllBytes(f));
            boolean modified = false;

            // 1. fix package
            String relPath = Paths.get(baseDir).relativize(f).toString().replace("\\", "/");
            String expectedPkg = relPath.substring(0, relPath.lastIndexOf('/'));
            expectedPkg = expectedPkg.replace("/", ".");

            Matcher pkgMatcher = Pattern.compile("(?m)^package\\s+([a-zA-Z0-9_.]+)\\s*;").matcher(content);
            String currentPkg = "";
            if (pkgMatcher.find()) {
                currentPkg = pkgMatcher.group(1);
                if (!currentPkg.equals(expectedPkg)) {
                    content = pkgMatcher.replaceFirst("package " + expectedPkg + ";");
                    modified = true;
                    currentPkg = expectedPkg;
                }
            }

            // 2. replace existing correct old imports
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String className = entry.getKey();
                String newImport = entry.getValue();
                String oldImport1 = "com.colegio.inventario.domain.catalogo." + className;
                String oldImport2 = "com.colegio.inventario.domain.catalogo.subcatalogo." + className;
                
                if (content.contains("import " + oldImport1 + ";")) {
                    content = content.replace("import " + oldImport1 + ";", "import " + newImport + ";");
                    modified = true;
                }
                if (content.contains("import " + oldImport2 + ";")) {
                    content = content.replace("import " + oldImport2 + ";", "import " + newImport + ";");
                    modified = true;
                }
            }

            // 3. inject missing imports
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String className = entry.getKey();
                String fullNewClass = entry.getValue();
                String targetPkg = fullNewClass.substring(0, fullNewClass.lastIndexOf('.'));

                if (!f.getFileName().toString().equals(className + ".java")) {
                    // Uses word?
                    if (Pattern.compile("\\b" + className + "\\b").matcher(content).find()) {
                        // Not same package?
                        if (!currentPkg.equals(targetPkg)) {
                            // Doesn't have import?
                            if (!content.contains("import " + fullNewClass + ";")) {
                                Matcher m = Pattern.compile("(?m)^package\\s+[a-zA-Z0-9_.]+\\s*;").matcher(content);
                                if (m.find()) {
                                    content = m.replaceFirst(m.group(0) + "\n\nimport " + fullNewClass + ";");
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }

            if (modified) {
                Files.write(f, content.getBytes());
                System.out.println("Modified: " + f.toString());
            }
        }
    }
}
