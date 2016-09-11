package com.abhi.batch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.abhi.batch.config.PersonJobTestConfiguration;


@ActiveProfiles("batchtest")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersonJobTestConfiguration.class}, loader= AnnotationConfigContextLoader.class)
public class PersonJobTests {

	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void launchJobTest() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        org.springframework.test.util.AssertionErrors.assertEquals("testing complete Job", BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    
    @Test
	public void launchStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
}
    
}
