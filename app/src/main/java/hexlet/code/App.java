package hexlet.code;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App {
    @Parameters(paramLabel = "filepath1",
            description = "path to first file")
    String ref;
    @Parameters(paramLabel = "filepath2",
            description = "path to second file")
    String re;
    @Option(names = {"-f ", "--format"}, description = "output format [default: stylish]")
    String format;
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
