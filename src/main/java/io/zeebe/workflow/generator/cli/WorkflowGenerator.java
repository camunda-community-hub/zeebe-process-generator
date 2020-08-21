package io.zeebe.workflow.generator.cli;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(description = "Creates workflows", name = "createworkflow", mixinStandardHelpOptions = true, synopsisSubcommandLabel = "COMMAND")
public class WorkflowGenerator {

	@Option(names = { "--workflowID",
			"-i" }, description = "ID for the workflow to be generated, defaults to \"${DEFAULT-VALUE}\"", defaultValue = "workflow", scope = ScopeType.INHERIT)
	private String workflowId;

	@Option(names = { "--output",
			"-o" }, description = "File to write the workflow to, defaults to \"${DEFAULT-VALUE}\"", defaultValue = "workflow.bpmn", scope = ScopeType.INHERIT)
	private File outputFile;

	public static void main(String[] args) {
		int exitCode = new CommandLine(new WorkflowGenerator()).addSubcommand(SequenceWorkflowCommand.class)
				.addSubcommand(E2ETimeMeasurementWorkflowCommand.class).execute(args);
		System.exit(exitCode);
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public File getOutputFile() {
		return outputFile;
	}
}
