import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Generator {

    public static void main(String[] args) throws Exception {
        String baseDir = "src/main/java";
        String dtoBase = "com.colegio.inventario.application.dto";
        String mapperBase = "com.colegio.inventario.application.mapper";

        List<Path> allJavaFiles = Files.walk(Paths.get(baseDir))
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java") && p.toString().contains("domain"))
                .collect(java.util.stream.Collectors.toList());

        for (Path f : allJavaFiles) {
            String pathStr = f.toString().replace("\\", "/");
            if (!pathStr.contains("/domain/catalogo/") && !pathStr.contains("/domain/personal/")) continue;
            if (pathStr.contains("CatalogoBase") || pathStr.contains("CatalogoConEstadoBase")) continue;
            if (pathStr.contains("Repository")) continue;

            String className = f.getFileName().toString().replace(".java", "");
            
            // Extract the relative domain package part (e.g. hardware, ubicacion, software, equipo, personal)
            String subPackage = "personal";
            if (pathStr.contains("/domain/catalogo/")) {
                String sub = pathStr.substring(pathStr.indexOf("/domain/catalogo/") + 17);
                int slash = sub.indexOf("/");
                if(slash != -1) {
                    subPackage = sub.substring(0, slash);
                } else {
                    subPackage = "base"; // shouldn't happen usually, wait Cargo was moved to personal
                }
            } else if (pathStr.contains("/domain/personal/")) {
                subPackage = "personal";
            }

            if (className.equals("Departamento")) continue; // Already exists

            // Load Class via Reflection
            String relPath = Paths.get(baseDir).relativize(f).toString().replace("\\", "/");
            String fqn = relPath.replace("/", ".").replace(".java", "");
            
            Class<?> clazz;
            try {
                clazz = Class.forName(fqn);
            } catch (Exception e) {
                continue;
            }

            // Must have @Entity or similar, essentially it's a domain object
            if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) continue;

            // Collect fields
            List<Field> allFields = new ArrayList<>();
            Class<?> curr = clazz;
            while (curr != null && !curr.equals(Object.class)) {
                allFields.addAll(0, Arrays.asList(curr.getDeclaredFields()));
                curr = curr.getSuperclass();
            }

            StringBuilder dtoImports = new StringBuilder();
            StringBuilder dtoFields = new StringBuilder();
            StringBuilder mapperImports = new StringBuilder();
            StringBuilder mapperConstructorArgs = new StringBuilder();

            Set<String> importsNeeded = new HashSet<>();

            for (Field field : allFields) {
                if (Modifier.isStatic(field.getModifiers())) continue;

                String typeName = field.getType().getSimpleName();
                String fieldName = field.getName();
                
                String dtoType = typeName;
                String getter = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + "()";

                if (field.getType().getName().startsWith("com.colegio.inventario.domain")) {
                    dtoType = "String"; // relation flattened to String
                    getter = getter + ".getNombre()"; // Assumes relation has getNombre!
                } else if (field.getType().equals(java.time.LocalDateTime.class)) {
                    importsNeeded.add("java.time.LocalDateTime");
                } else if (field.getType().equals(java.time.LocalDate.class)) {
                    importsNeeded.add("java.time.LocalDate");
                }

                dtoFields.append("    private ").append(dtoType).append(" ").append(fieldName).append(";\n");
                mapperConstructorArgs.append("                d.").append(getter).append(",\n");
            }

            // Remove last comma in mapper args
            if (mapperConstructorArgs.length() > 0) {
                mapperConstructorArgs.setLength(mapperConstructorArgs.length() - 2);
            }

            // Create DTO
            String dtoPkg = dtoBase + "." + subPackage;
            String dtoName = className + "DTO";
            StringBuilder dtoClass = new StringBuilder("package " + dtoPkg + ";\n\n");
            dtoClass.append("import lombok.AllArgsConstructor;\n");
            dtoClass.append("import lombok.Data;\n");
            for (String imp : importsNeeded) {
                dtoClass.append("import ").append(imp).append(";\n");
            }
            dtoClass.append("\n@Data\n@AllArgsConstructor\n");
            dtoClass.append("public class ").append(dtoName).append(" {\n\n");
            dtoClass.append(dtoFields);
            dtoClass.append("}\n");

            // Write DTO
            Path dtoPath = Paths.get(baseDir, dtoPkg.replace(".", "/"), dtoName + ".java");
            Files.createDirectories(dtoPath.getParent());
            Files.writeString(dtoPath, dtoClass.toString());

            // Create Mapper
            String mapperPkg = mapperBase + "." + subPackage;
            String mapperName = className + "Mapper";
            StringBuilder mapperClass = new StringBuilder("package " + mapperPkg + ";\n\n");
            mapperClass.append("import ").append(dtoPkg).append(".").append(dtoName).append(";\n");
            mapperClass.append("import ").append(fqn).append(";\n\n");
            mapperClass.append("public class ").append(mapperName).append(" {\n\n");
            mapperClass.append("    public static ").append(dtoName).append(" toDTO(").append(className).append(" d) {\n");
            mapperClass.append("        if (d == null) return null;\n");
            mapperClass.append("        return new ").append(dtoName).append("(\n");
            mapperClass.append(mapperConstructorArgs).append("\n");
            mapperClass.append("        );\n");
            mapperClass.append("    }\n");
            mapperClass.append("}\n");

            // Write Mapper
            Path mapperPath = Paths.get(baseDir, mapperPkg.replace(".", "/"), mapperName + ".java");
            Files.createDirectories(mapperPath.getParent());
            Files.writeString(mapperPath, mapperClass.toString());

            System.out.println("Generated DTO & Mapper for: " + className + " in " + subPackage);
        }
    }
}
