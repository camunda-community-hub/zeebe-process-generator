package io.zeebe.workflow.generator.builder;

import io.zeebe.model.bpmn.BpmnModelInstance;

public interface WorkflowBuilder {

	BpmnModelInstance buildWorkflow(String processId);
}
