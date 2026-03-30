import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RestructureLayers {

    public static void main(String[] args) throws IOException {
        String baseDir = "src/main/java";

        Map<String, String> prefixToDomain = new HashMap<>();
        prefixToDomain.put("CatalogoBase", "base");
        prefixToDomain.put("CatalogoConEstado", "base");
        prefixToDomain.put("CatalogoController", "base");
        prefixToDomain.put("CatalogoService", "base");
        prefixToDomain.put("CatalogoRepository", "base");

        prefixToDomain.put("CategoriaHardware", "hardware");
        prefixToDomain.put("CategoriaOrdenador", "hardware");
        prefixToDomain.put("Procesador", "hardware");
        prefixToDomain.put("Ram", "hardware");
        prefixToDomain.put("Rom", "hardware");
        prefixToDomain.put("TipoRom", "hardware");

        prefixToDomain.put("SO", "software");
        prefixToDomain.put("Ofimatica", "software");

        prefixToDomain.put("Marca", "equipo");
        prefixToDomain.put("Modelo", "equipo");
        prefixToDomain.put("RolEquipo", "equipo");

        prefixToDomain.put("Edificio", "ubicacion");
        prefixToDomain.put("Departamento", "ubicacion");
        prefixToDomain.put("Secciones", "ubicacion");

        prefixToDomain.put("Cargo", "personal");

        List<Path> allJavaFiles = Files.walk(Paths.get(baseDir))
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .collect(Collectors.toList());

        Map<String, String> fqnMap = new HashMap<>();
        List<FileMove> moves = new ArrayList<>();

        for (Path f : allJavaFiles) {
            String fileName = f.getFileName().toString();
            String className = fileName.replace(".java", "");

            // Avoid touching entities since they are already moved
            if (f.toString().replace("\\", "/").contains("/domain/catalogo/hardware/") ||
                f.toString().replace("\\", "/").contains("/domain/catalogo/software/") ||
                f.toString().replace("\\", "/").contains("/domain/catalogo/ubicacion/") ||
                f.toString().replace("\\", "/").contains("/domain/catalogo/equipo/") ||
                f.toString().replace("\\", "/").contains("/domain/catalogo/base/") ||
                f.toString().replace("\\", "/").contains("/domain/personal/") ||
                f.toString().replace("\\", "/").contains("/InventarioColegioBackendApplication.java")
            ) {
                // We don't move entities, but we still need them in fqnMap if they were accidentally matched? No, entities already have correct package
                continue;
            }

            String matchDomain = null;
            for (String prefix : prefixToDomain.keySet()) {
                if (className.startsWith(prefix)) {
                    matchDomain = prefixToDomain.get(prefix);
                    break;
                }
            }

            if (matchDomain != null) {
                String relPath = Paths.get(baseDir).relativize(f).toString().replace("\\", "/");
                String currentPkg = relPath.substring(0, relPath.lastIndexOf('/')).replace("/", ".");
                String oldFqn = currentPkg + "." + className;

                String newPkg;
                if (matchDomain.equals("personal")) {
                    // Replace /catalogo with /personal
                    newPkg = currentPkg.replace(".catalogo", ".personal");
                } else {
                    // Append .matchDomain
                    if (currentPkg.endsWith(".catalogo")) {
                        newPkg = currentPkg + "." + matchDomain;
                    } else if (currentPkg.endsWith(".dto") || currentPkg.endsWith(".mapper")) {
                        newPkg = currentPkg + "." + matchDomain;
                    } else {
                        newPkg = currentPkg + "." + matchDomain; // fallback
                    }
                }

                String newFqn = newPkg + "." + className;
                fqnMap.put(oldFqn, newFqn);

                String newRelPath = newPkg.replace(".", "/") + "/" + fileName;
                Path newPath = Paths.get(baseDir, newRelPath);

                moves.add(new FileMove(f, newPath));
            }
        }

        // 1. Move files
        for (FileMove move : moves) {
            Files.createDirectories(move.dest.getParent());
            Files.move(move.source, move.dest);
            System.out.println("Moved: " + move.source + " -> " + move.dest);
        }

        // Re-read all java files after move
        allJavaFiles = Files.walk(Paths.get(baseDir))
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .collect(Collectors.toList());

        // 2. Update contents
        for (Path f : allJavaFiles) {
            String content = new String(Files.readAllBytes(f));
            boolean modified = false;

            // Fix package
            String relPath = Paths.get(baseDir).relativize(f).toString().replace("\\", "/");
            String expectedPkg = relPath.substring(0, relPath.lastIndexOf('/')).replace("/", ".");
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

            // Replace exact FQNs in imports
            for (Map.Entry<String, String> entry : fqnMap.entrySet()) {
                String oldFqn = entry.getKey();
                String newFqn = entry.getValue();

                if (content.contains("import " + oldFqn + ";")) {
                    content = content.replace("import " + oldFqn + ";", "import " + newFqn + ";");
                    modified = true;
                }
            }

            // Inject missing imports if cross-package usage exists without import
            // Note: Since they were previously in the same package (e.g. all in .catalogo), 
            // they didn't need to import each other. Now they might.
            for (Map.Entry<String, String> entry : fqnMap.entrySet()) {
                String newFqn = entry.getValue();
                String className = newFqn.substring(newFqn.lastIndexOf('.') + 1);
                String classPkg = newFqn.substring(0, newFqn.lastIndexOf('.'));

                if (!f.getFileName().toString().equals(className + ".java")) {
                    if (Pattern.compile("\\b" + className + "\\b").matcher(content).find()) {
                        if (!currentPkg.equals(classPkg)) {
                            if (!content.contains("import " + newFqn + ";")) {
                                Matcher m = Pattern.compile("(?m)^package\\s+[a-zA-Z0-9_.]+\\s*;").matcher(content);
                                if (m.find()) {
                                    content = m.replaceFirst(m.group(0) + "\n\nimport " + newFqn + ";");
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }

            if (modified) {
                Files.write(f, content.getBytes());
                System.out.println("Updated imports: " + f);
            }
        }
    }

    static class FileMove {
        Path source;
        Path dest;
        FileMove(Path s, Path d) { this.source = s; this.dest = d; }
    }
}
