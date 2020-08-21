package io.zeebe.workflow.generator.builder;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import io.zeebe.model.bpmn.Bpmn;
import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.model.bpmn.builder.CallActivityBuilder;
import io.zeebe.model.bpmn.builder.ServiceTaskBuilder;

public class E2ETimeMeasurementWorkflowBuilder implements WorkflowBuilder {

	private final String workflowUnderTestId;
	
	private final String timeout;

	public E2ETimeMeasurementWorkflowBuilder(String workflowUnderTestId, Optional<String> timeout) {
		this.workflowUnderTestId = requireNonNull(workflowUnderTestId);
		this.timeout = timeout.orElse(null);
	}

	@Override
	public BpmnModelInstance buildWorkflow(String processId) {
		return Bpmn.createProcess(processId)
				.startEvent()
				.serviceTask("start-timer", this::configureStartTimer)
				.callActivity("call-workflow-under-test", this::configurCallActivity)
				.serviceTask("stop-timer", this::configureStopTimer)
				.endEvent()
				.done();
	}

	private void configureStartTimer(ServiceTaskBuilder serviceTaskBuilder) {
		serviceTaskBuilder.zeebeJobRetries("0");
		serviceTaskBuilder.zeebeJobType("start-timer-job");
	}

	private void configurCallActivity(CallActivityBuilder callActivityBuilder) {
		callActivityBuilder.zeebeProcessId(workflowUnderTestId);
		
		if (timeout != null) {
			callActivityBuilder.boundaryEvent("timeout").timerWithDuration(timeout).serviceTask("stop-timer-by-timeout", this::configureStopTimerByTimeout).endEvent();
		}
	}

	private void configureStopTimerByTimeout(ServiceTaskBuilder serviceTaskBuilder) {
		serviceTaskBuilder.zeebeJobRetries("0");
		serviceTaskBuilder.zeebeJobType("stop-timer-by-timeout-job");		
	}

	
	private void configureStopTimer(ServiceTaskBuilder serviceTaskBuilder) {
		serviceTaskBuilder.zeebeJobRetries("0");
		serviceTaskBuilder.zeebeJobType("stop-timer-job");		
	}

}
