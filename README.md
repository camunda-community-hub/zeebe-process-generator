## Zeebe Workflow Generator

This project provides a tool to automatically generate workflows for [Zeebe](https://zeebe.io/).

These workflows can be used in automated tests and performance benchmarks.

### Usage

The workflow generators can either be used as a Java dependency, or they can be invoked from the command line.

### General CLI Usage
```
Usage: [-hV] [-i=<workflowId>] [-o=<outputFile>] COMMAND
Creates workflows
  -h, --help      Show this help message and exit.
  -i, --workflowID=<workflowId>
                  ID for the workflow to be generated, defaults to "workflow"
  -o, --output=<outputFile>
                  File to write the workflow to, defaults to "workflow.bpmn"
  -V, --version   Print version information and exit.
Commands:
  sequence  Generates a sequential workflow with a given number of steps.
```


#### Sequence Workflow
This will produce a sequential workflow with a given number of steps and a given jobtype.
 

![Output of sequence command](assets/sequence.png)

```
  -j, --jobType=<jobType>   Job type for steps in workflow, defaults to "job"
  -s, --steps=<steps>       Number of steps in workflow, defaults to "5"
```

#### End-to-End Timing Workflow

This will produce a workflow like this:

![Output of e2e-timing command](assets/e2e-timing.png)

The created workflow starts a timer, then calls the workflow that should be timed, and stops the timer at the end. 

Optionally, you can also define a timeout. When the timeout is reached the workflow that should be timed will be aborted.

![Output of e2e-timing command with timeout](assets/e2e-timing-with-timeout.png)

```
  -t, --timeout=<timeout>   Timeout after which workflow execution should be
                              aborted
      -wut, --workflow-under-test-id=<workflowUnderTestId>
                            (Manadatory) ID of the workfow that should be
                              measured
```

### Outlook
* Generate workflow that calls another workflow n times
* Generate workflow that calls another workflow again and again until a certain time has passed
* Generate a number of workflows: a *sequence* workflow, called by a *timing* workflow, called by a *repeat n times* workflow, called by another *timing* workflow. The net result would be a bunch of workflow descriptions with one big start event. Once started, the innermost workflow will be executed n times. Each execution is timed, and the aggregate of all executions is timed. This is - in essence - a very simple benchmark setup
