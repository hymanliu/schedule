package com.hyman.schedule.common.util;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class CronExpressionTest {
	public static boolean isMatch(String crontabExpress, Date date) throws java.text.ParseException {    
        try {    
            CronExpression crontabExpression = new CronExpression(crontabExpress);    
            return crontabExpression.isSatisfiedBy(date);    
        } catch (ParseException e) {    
            return false;    
        }    
    }
	
	//linux:分　时　日　月　周  命令
	//  : 秒	 分  日  月   周  年
	@Test
	public void testIsMatch(){
		while(true){
			try {
				Date date = new Date();
				boolean isMatch = CronExpressionTest.isMatch("0 0/1 * * * ?", date);
				if(isMatch){
					System.out.println("time :"+date+" is match the cron expression.");
					
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
