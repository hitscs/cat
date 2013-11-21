package com.dianping.cat.statistic;

import junit.framework.Assert;

import org.junit.Test;

import com.dianping.cat.statistic.ServerStatistic.Statistic;

public class ServerStatisticManagerTest {

	@Test
	public void test() {
		ServerStatisticManager manager = new ServerStatisticManager();
		String domain = "Cat";
		long time = System.currentTimeMillis();

		time = time - time % (60 * 1000);

		manager.addBlockLoss(1);
		manager.addBlockTime(2);
		manager.addBlockTotal(3);
		manager.addMessageDump(4);
		manager.addMessageDumpLoss(5);
		manager.addMessageSize(6);
		manager.addMessageTotal(7);
		manager.addMessageTotalLoss(8);
		manager.addMessageSize(domain, 1);
		manager.addMessageTotal(domain, 2);
		manager.addMessageTotalLoss(domain, 3);

		Assert.assertEquals(1, findState(manager, time).getBlockLoss());
		Assert.assertEquals(2, findState(manager, time).getBlockTime());
		Assert.assertEquals(3, findState(manager, time).getBlockTotal());
		Assert.assertEquals(4, findState(manager, time).getMessageDump());
		Assert.assertEquals(5, findState(manager, time).getMessageDumpLoss());
		Assert.assertEquals(6.0, findState(manager, time).getMessageSize());
		Assert.assertEquals(7, findState(manager, time).getMessageTotal());
		Assert.assertEquals(8, findState(manager, time).getMessageTotalLoss());
		Assert.assertEquals(1.0, findState(manager, time).getMessageSizes().get(domain));
		Assert.assertEquals(2, findState(manager, time).getMessageTotals().get(domain).get());
		Assert.assertEquals(3, findState(manager, time).getMessageTotalLosses().get(domain).get());
	}

	private Statistic findState(ServerStatisticManager manager, long time) {
		Statistic state = manager.findState(time);

		if (state == null) {
			state = manager.findState(time + 60 * 1000);
		}
		return state;
	}
}
