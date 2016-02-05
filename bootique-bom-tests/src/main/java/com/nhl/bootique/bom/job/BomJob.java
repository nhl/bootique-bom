package com.nhl.bootique.bom.job;

import java.util.Map;

import com.nhl.bootique.job.BaseJob;
import com.nhl.bootique.job.JobMetadata;
import com.nhl.bootique.job.runnable.JobOutcome;
import com.nhl.bootique.job.runnable.JobResult;

public class BomJob extends BaseJob {

	public static String lastResult;

	public BomJob() {
		super(JobMetadata.build(BomJob.class));
	}

	@Override
	public JobResult run(Map<String, Object> parameters) {
		BomJob.lastResult = "bom-job-finished";
		return new JobResult(getMetadata(), JobOutcome.SUCCESS, null, BomJob.lastResult);
	}
}
