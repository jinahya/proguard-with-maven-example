import java.io.OutputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Extracts {@code .class} files of a JDK module from the running JDK's runtime image ({@code jrt:/}) into a jar, so
 * that ProGuard can use it as a library jar on JDKs shipped without {@code jmods} (JEP 493; e.g., Temurin 24+).
 * <p>
 * Usage: {@code java ExtractJdk.java <output.jar> [module]} (module defaults to {@code java.base}).
 * <p>
 * Remove this once ProGuard reads {@code jrt:/} by itself; see https://github.com/Guardsquare/proguard/pull/541.
 */
public class ExtractJdk {

    public static void main(final String... args) throws Exception {
        if (args.length < 1) {
            System.err.println("usage: java ExtractJdk.java <output.jar> [module]");
            System.exit(1);
        }
        final Path output = Paths.get(args[0]);
        final String module = args.length > 1 ? args[1] : "java.base";
        final FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        final Path root = jrt.getPath("/modules", module);
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("no such module: " + module);
        }
        final List<Path> classes;
        try (Stream<Path> files = Files.walk(root)) {
            classes = files.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".class"))
                    .filter(p -> !p.getFileName().toString().equals("module-info.class"))
                    .sorted()
                    .collect(Collectors.toList());
        }
        final Path parent = output.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (OutputStream stream = Files.newOutputStream(output);
             ZipOutputStream zip = new ZipOutputStream(stream)) {
            for (final Path c : classes) {
                zip.putNextEntry(new ZipEntry(root.relativize(c).toString()));
                Files.copy(c, zip);
                zip.closeEntry();
            }
        }
        System.out.printf("extracted %d classes of %s into %s%n", classes.size(), module, output);
    }
}
