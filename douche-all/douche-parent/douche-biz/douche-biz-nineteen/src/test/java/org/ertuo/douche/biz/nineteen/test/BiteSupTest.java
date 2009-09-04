package org.ertuo.douche.biz.nineteen.test;

import org.ertuo.douche.biz.nineteen.BiteSup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;
import org.springframework.test.context.ContextConfiguration;



/**
 * 19¥����
 * @author mo.duanm
 *
 */
@ContextConfiguration(locations={"test.xml"})
public class BiteSupTest extends AbstractAnnotationAwareTransactionalTests{
	
	@Autowired
	public BiteSup biteSup;
	
	public void setBiteSup(BiteSup biteSup) {
		this.biteSup = biteSup;
	}

	public void test_Login(){
		assertEquals(true, biteSup.login());
	}
	
    public String[] getConfigLocations()
    {
        String[] configLocations = {"test.xml"};
        return configLocations;
    }

}
