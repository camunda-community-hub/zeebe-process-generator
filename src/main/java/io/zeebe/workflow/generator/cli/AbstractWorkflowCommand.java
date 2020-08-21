package io.zeebe.workflow.generator.cli;

import java.util.concurrent.Callable;

import io.zeebe.model.bpmn.Bpmn;
import io.zeebe.model.bpmn.BpmnModelInstance;
import picocli.CommandLine.ParentCommand;

public abstract class AbstractWorkflowCommand implements Callable<Integer> {
	
	@ParentCommand
	protected WorkflowGenerator parent;

	protected void writeFile(BpmnModelInstance workflow) {
		Bpmn.writeModelToFile(parent.getOutputFile(), workflow);
		
		System.out.println("Done! Result saved to " + parent.getOutputFile());
	}
}
