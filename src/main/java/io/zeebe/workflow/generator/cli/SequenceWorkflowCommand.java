package io.zeebe.workflow.generator.cli;

import java.util.Optional;

import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.workflow.generator.builder.SequenceWorkflowBuilder;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "sequence", description = "Generates a sequential workflow with a given number of steps.", mixinStandardHelpOptions = true)
public class SequenceWorkflowCommand extends AbstractWorkflowCommand {

	@Option(names = { "-s", "--steps" }, description = "Number of steps in workflow, defaults to \"${DEFAULT-VALUE}\"", defaultValue = "5")
	private int steps;
	
	@Option(names = { "-j", "--jobType" }, description = "Job type for steps in workflow, defaults to \"${DEFAULT-VALUE}\"", defaultValue = "job")
	private String jobType;

	@Override
	public Integer call() throws Exception {
		System.out.println("Generating sequential workflow '" + parent.getWorkflowId() + "' with " + steps + " steps. ");
		SequenceWorkflowBuilder sequenceWorkflowBuilder = new SequenceWorkflowBuilder(Optional.of(steps), Optional.of(jobType));

		BpmnModelInstance workflow = sequenceWorkflowBuilder.buildWorkflow(parent.getWorkflowId());
		
		writeFile(workflow);
		return 0;
	}
}