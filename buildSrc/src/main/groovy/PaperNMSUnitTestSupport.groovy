import org.gradle.api.artifacts.transform.*;
import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.provider.*;
import org.gradle.api.tasks.*;
import org.gradle.api.attributes.*;
import org.gradle.api.artifacts.*;
import java.nio.file.*;

/**
 * A gradle transformation action which can remove files from inside of a JAR.
 *
 * This can be used to remove unwanted /META-INF/services files from jars with
 * conflicts. This is known to be an issue when testing with MockBukkit and
 * the paperweight generated minecraft server mappings.
 *
 * Reference:
 *  - https://paste.gg/p/syldium/0f00247b5f954fb691d354d85563fe15
 *  - https://github.com/MockBukkit/MockBukkit/issues/599
 **/
abstract class RemoveFilesTransform
  implements TransformAction<RemoveFilesTransform.Parameters>
{
  // Accept a list of file names as parameters
  interface Parameters extends TransformParameters {
    @Input
    List<String> getFiles()
    void setFiles(List<String> files)
  }

  // The input artifact is a JAR which will be transformed.
  @InputArtifact
  abstract Provider<FileSystemLocation> getInputArtifact()

  @Override
  void transform(TransformOutputs outputs) {
    File input = inputArtifact.get().getAsFile();
    File output = outputs.file(input.getName().replace(".jar", "-transformed.jar"));

    // Copy the original jar to the output location. Only the copy will be
    // modified. This means that a bug in the implementation won't trash the
    // original jar and force the user to refresh dependencies or something.
    Files.copy(input.toPath(), output.toPath())

    // Create a filesystem from the Jar. This allows files inside to be
    // manipulated with the standard nio Files, and Paths libraries.
    def outURI = output.toURI()
    def fs = FileSystems.newFileSystem(URI.create("jar:$outURI"), [:])

    // Groovy's version of try-with-resources
    fs.withCloseable { zip ->
      // Useful when debugging. This will dump each service file name and the
      // contents.
      //
      // def myPath = zip.getPath('/META-INF/services')
      // Files.walk(myPath, 1).each { filename ->
      //   println("found filename $filename, contents:-")
      //   if (Files.isReadable(filename) && !Files.isDirectory(filename)) {
      //     def reader = Files.newBufferedReader(filename)
      //     reader.lines().each { line ->
      //       println("| $line")
      //     }
      //   }
      // }

      // For each configured file, check that it exists, then delete it from
      // the JAR.
      parameters.files.each { file ->
        def path = zip.getPath(file)
        if (Files.exists(path)) {
          Files.delete(path)
        }
      }
    }
  }
}

/**
 * This plugin registers the transform to automatically clean the paper-server
 * when it is included in the test runtime classpath.
 *
 * The plugin is responsible for:
 *   - Enabling the filtered attributes
 *   - Registering the transform
 *   - Setting up a dependency substitution for the paper-server when it is
 *     present in the test runtime dependencies
 **/
class PaperNMSUnitTestSupport implements Plugin<Project> {
  void apply(Project project) {
    def artifactType = Attribute.of('artifactType', String)
    def filtered = Attribute.of('filtered', Boolean)

    project.dependencies {
      attributesSchema {
        attribute(filtered)
      }
      artifactTypes.getByName("jar") {
        attributes.attribute(filtered, false)
      }
      registerTransform(RemoveFilesTransform) {
        from.attribute(filtered, false).attribute(artifactType, "jar")
        to.attribute(filtered, true).attribute(artifactType, "jar")
        parameters {
          files = [
            "/META-INF/services/net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer\$Provider",
            "/META-INF/services/org.bukkit.plugin.PluginLoader"
          ]
        }
      }
    }

    project
      .configurations
      .testRuntimeClasspath
      .resolutionStrategy
      .dependencySubstitution
    {
      all { DependencySubstitution dependency ->
        if (
          dependency.requested.group == "io.papermc.paper" &&
          dependency.requested.module == "paper-server"
        ) {
          dependency.useTarget variant(dependency.requested) {
            attributes { attribute(filtered, true) }
          }
        }
      }
    }

  }
}
