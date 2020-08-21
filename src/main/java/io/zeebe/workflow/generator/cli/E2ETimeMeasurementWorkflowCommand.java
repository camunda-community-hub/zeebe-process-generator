package io.zeebe.workflow.generator.cli;

import java.util.Optional;

import io.zeebe.model.bpmn.BpmnModelInstance;
import io.zeebe.workflow.generator.builder.E2ETimeMeasurementWorkflowBuilder;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "e2e-timing", description = "Generates a workflow to measure the end to end time of executing a second workflow, which is called from the timing workflow", mixinStandardHelpOptions = true)
public class E2ETimeMeasurementWorkflowCommand extends AbstractWorkflowCommand {
	
	@Option(names = { "-wut", "--workflow-under-test-id"}, description = "(Manadatory) ID of the workfow that should be measured", required = true )
	private String workflowUnderTestId;
	
	@Option(names = { "-t", "--timeout"}, description = "Timeout after which workflow execution should be aborted")
	private String timeout;
	
	@Override
	public Integer call() throws Exception {
		E2ETimeMeasurementWorkflowBuilder builder = new E2ETimeMeasurementWorkflowBuilder(workflowUnderTestId, Optional.ofNullable(timeout));
		
		BpmnModelInstance workflow = builder.buildWorkflow(parent.getWorkflowId());
		
		writeFile(workflow);
		return 0;
	}

}
