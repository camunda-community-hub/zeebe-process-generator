package io.zeebe.process.generator.cli;

import java.io.File;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(
    description = "Creates processes",
    name = "createprcoess",
    mixinStandardHelpOptions = true,
    synopsisSubcommandLabel = "COMMAND")
public class ProcesswGenerator {

  @Option(
      names = {"--prcoessID", "-i"},
      description = "ID for the process to be generated, defaults to \"${DEFAULT-VALUE}\"",
      defaultValue = "process",
      scope = ScopeType.INHERIT)
  private String processId;

  @Option(
      names = {"--output", "-o"},
      description = "File to write the process to, defaults to \"${DEFAULT-VALUE}\"",
      defaultValue = "process.bpmn",
      scope = ScopeType.INHERIT)
  private File outputFile;

  public static void main(final String[] args) {
    final int exitCode =
        new CommandLine(new ProcesswGenerator())
            .addSubcommand(SequenceProcesswCommand.class)
            .addSubcommand(E2ETimeMeasurementProcesswCommand.class)
            .execute(args);
    System.exit(exitCode);
  }

  public String getProcessId() {
    return processId;
  }

  public File getOutputFile() {
    return outputFile;
  }
}
