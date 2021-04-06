package io.zeebe.process.generator.builder;

import static java.util.Objects.requireNonNull;

import io.zeebe.model.bpmn.Bpmn;
import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.model.bpmn.builder.CallActivityBuilder;
import io.zeebe.model.bpmn.builder.ServiceTaskBuilder;
import java.util.Optional;

public class E2ETimeMeasurementProcessBuilder implements ProcessBuilder {

  private final String processUnderTestId;

  private final String timeout;

  public E2ETimeMeasurementProcessBuilder(
      final String processUnderTestId, final Optional<String> timeout) {
    this.processUnderTestId = requireNonNull(processUnderTestId);
    this.timeout = timeout.orElse(null);
  }

  @Override
  public BpmnModelInstance buildProcess(final String processId) {
    return Bpmn.createExecutableProcess(processId)
        .startEvent()
        .serviceTask("start-timer", this::configureStartTimer)
        .callActivity("call-process-under-test", this::configurCallActivity)
        .serviceTask("stop-timer", this::configureStopTimer)
        .endEvent()
        .done();
  }

  private void configureStartTimer(final ServiceTaskBuilder serviceTaskBuilder) {
    serviceTaskBuilder.zeebeJobRetries("0");
    serviceTaskBuilder.zeebeJobType("start-timer-job");
  }

  private void configurCallActivity(final CallActivityBuilder callActivityBuilder) {
    callActivityBuilder.zeebeProcessId(processUnderTestId);

    if (timeout != null) {
      callActivityBuilder
          .boundaryEvent("timeout")
          .timerWithDuration(timeout)
          .serviceTask("stop-timer-by-timeout", this::configureStopTimerByTimeout)
          .endEvent();
    }
  }

  private void configureStopTimerByTimeout(final ServiceTaskBuilder serviceTaskBuilder) {
    serviceTaskBuilder.zeebeJobRetries("0");
    serviceTaskBuilder.zeebeJobType("stop-timer-by-timeout-job");
  }

  private void configureStopTimer(final ServiceTaskBuilder serviceTaskBuilder) {
    serviceTaskBuilder.zeebeJobRetries("0");
    serviceTaskBuilder.zeebeJobType("stop-timer-job");
  }
}
