package io.zeebe.process.generator.cli;

import io.camunda.zeebe.model.bpmn.Bpmn;
import io.camunda.zeebe.model.bpmn.BpmnModelInstance;
import java.util.concurrent.Callable;
import picocli.CommandLine.ParentCommand;

public abstract class AbstractProcesswCommand implements Callable<Integer> {

  @ParentCommand protected ProcesswGenerator parent;

  protected void writeFile(final BpmnModelInstance process) {
    Bpmn.writeModelToFile(parent.getOutputFile(), process);

    System.out.println("Done! Result saved to " + parent.getOutputFile());
  }
}
