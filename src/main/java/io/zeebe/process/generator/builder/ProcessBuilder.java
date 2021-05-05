package io.zeebe.process.generator.builder;

import io.camunda.zeebe.model.bpmn.BpmnModelInstance;

public interface ProcessBuilder {

  BpmnModelInstance buildProcess(String processId);
}
