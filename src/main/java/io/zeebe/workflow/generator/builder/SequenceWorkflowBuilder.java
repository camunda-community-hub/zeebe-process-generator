package io.zeebe.workflow.generator.builder;

import java.util.Optional;

import io.zeebe.model.bpmn.Bpmn;
import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.model.bpmn.builder.ServiceTaskBuilder;
import io.zeebe.model.bpmn.builder.StartEventBuilder;

public class SequenceWorkflowBuilder implements WorkflowBuilder {

	private final int steps;
	private final String jobType;

	public SequenceWorkflowBuilder(Optional<Integer> optSteps, Optional<String> optJobType) {
		steps = optSteps.orElse(5);
		jobType = optJobType.orElse("generic-task");
	}

	@Override
	public BpmnModelInstance buildWorkflow(String processId) {

		StartEventBuilder workflowWorkInProgress = Bpmn.createProcess(processId).startEvent();

		ServiceTaskBuilder serviceTaskBuilder = null;

		for (int step = 1; step <= steps; step++) {
			if (serviceTaskBuilder == null) {
				serviceTaskBuilder = workflowWorkInProgress.serviceTask(getID(step), this::configureServiceTask);
			} else {
				serviceTaskBuilder = serviceTaskBuilder.serviceTask(getID(step), this::configureServiceTask);
			}
		}

		return serviceTaskBuilder.endEvent().done();
	}

	private String getID(int step) {
		return "step-" + step;
	}

	private void configureServiceTask(ServiceTaskBuilder serviceTaskBuilder) {
		serviceTaskBuilder.zeebeJobRetries("0");
		serviceTaskBuilder.zeebeJobType(jobType);
	}

}
