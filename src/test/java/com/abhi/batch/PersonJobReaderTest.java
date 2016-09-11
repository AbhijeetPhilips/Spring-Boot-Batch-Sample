package com.abhi.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.abhi.batch.config.PersonJobTestConfiguration;
import com.abhi.batch.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {PersonJobTestConfiguration.class}, loader= AnnotationConfigContextLoader.class)
public class PersonJobReaderTest {

	@Autowired
    private StaxEventItemReader<Person> itemReader;
	
	@Test
    public void testReader() {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(execution, () -> {
                int numPerson = 0;
                itemReader.open(execution.getExecutionContext());
                Person person;
                try {
                    while ((person = itemReader.read()) != null) {
                        assertNotNull(person);
                        assertNotNull(person.getFamilyName());
                        assertNotNull(person.getFirstName());
                        assertNotNull(person.getYear());
                       
                        numPerson++;
                    }
                } finally {
                    try { itemReader.close(); } catch (ItemStreamException e) { fail(e.toString());
                    }
                }
                return numPerson;
            });
        } catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(3, count);
    }
}
