package hexlet.code;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 4.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    String file1;
    @Parameters(paramLabel = "filepath1", description = "path to second file")
    String file2;
    @Option(names = { "-f", "--format" }, description = "output format [default: stylish]")
    Object format;

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        System.out.println(Differ.generate(file1, file2, "-"));
        System.out.println(Differ.generate(file2, file1, "+"));
        return 1;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
