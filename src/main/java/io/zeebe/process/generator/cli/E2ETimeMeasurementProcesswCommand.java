package io.zeebe.process.generator.cli;

import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.process.generator.builder.E2ETimeMeasurementProcessBuilder;
import java.util.Optional;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "e2e-timing",
    description =
        "Generates a prcoess to measure the end to end time of executing a second process, which is called from the timing process",
    mixinStandardHelpOptions = true)
public class E2ETimeMeasurementProcesswCommand extends AbstractProcesswCommand {

  @Option(
      names = {"-wut", "--process-under-test-id"},
      description = "(Manadatory) ID of the workfow that should be measured",
      required = true)
  private String processUnderTestId;

  @Option(
      names = {"-t", "--timeout"},
      description = "Timeout after which process execution should be aborted")
  private String timeout;

  @Override
  public Integer call() throws Exception {
    final E2ETimeMeasurementProcessBuilder builder =
        new E2ETimeMeasurementProcessBuilder(processUnderTestId, Optional.ofNullable(timeout));

    final BpmnModelInstance process = builder.buildProcess(parent.getProcessId());

    writeFile(process);
    return 0;
  }
}
