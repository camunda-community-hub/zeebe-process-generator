package io.zeebe.process.generator.builder;

import io.camunda.zeebe.model.bpmn.Bpmn;
import io.camunda.zeebe.model.bpmn.BpmnModelInstance;
import io.camunda.zeebe.model.bpmn.builder.ServiceTaskBuilder;
import io.camunda.zeebe.model.bpmn.builder.StartEventBuilder;
import java.util.Optional;

public class SequenceProcessBuilder implements ProcessBuilder {

  private final int steps;
  private final String jobType;

  public SequenceProcessBuilder(
      final Optional<Integer> optSteps, final Optional<String> optJobType) {
    steps = optSteps.orElse(5);
    jobType = optJobType.orElse("generic-task");
  }

  @Override
  public BpmnModelInstance buildProcess(final String processId) {

    final StartEventBuilder processWorkInProgress =
        Bpmn.createExecutableProcess(processId).startEvent();

    ServiceTaskBuilder serviceTaskBuilder = null;

    for (int step = 1; step <= steps; step++) {
      if (serviceTaskBuilder == null) {
        serviceTaskBuilder =
            processWorkInProgress.serviceTask(getID(step), this::configureServiceTask);
      } else {
        serviceTaskBuilder =
            serviceTaskBuilder.serviceTask(getID(step), this::configureServiceTask);
      }
    }

    return serviceTaskBuilder.endEvent().done();
  }

  private String getID(final int step) {
    return "step-" + step;
  }

  private void configureServiceTask(final ServiceTaskBuilder serviceTaskBuilder) {
    serviceTaskBuilder.zeebeJobRetries("0");
    serviceTaskBuilder.zeebeJobType(jobType);
  }
}
