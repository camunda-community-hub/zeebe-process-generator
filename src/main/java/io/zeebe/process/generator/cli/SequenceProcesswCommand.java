package io.zeebe.process.generator.cli;

import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.process.generator.builder.SequenceProcessBuilder;
import java.util.Optional;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "sequence",
    description = "Generates a sequential process with a given number of steps.",
    mixinStandardHelpOptions = true)
public class SequenceProcesswCommand extends AbstractProcesswCommand {

  @Option(
      names = {"-s", "--steps"},
      description = "Number of steps in process, defaults to \"${DEFAULT-VALUE}\"",
      defaultValue = "5")
  private int steps;

  @Option(
      names = {"-j", "--jobType"},
      description = "Job type for steps in process, defaults to \"${DEFAULT-VALUE}\"",
      defaultValue = "job")
  private String jobType;

  @Override
  public Integer call() throws Exception {
    System.out.println(
        "Generating sequential process '" + parent.getProcessId() + "' with " + steps + " steps. ");
    final SequenceProcessBuilder sequenceProcessBuilder =
        new SequenceProcessBuilder(Optional.of(steps), Optional.of(jobType));

    final BpmnModelInstance process = sequenceProcessBuilder.buildProcess(parent.getProcessId());

    writeFile(process);
    return 0;
  }
}
