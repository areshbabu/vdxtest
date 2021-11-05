package com.vdx.ConfigManageServer.poller;

import java.util.Map;

public interface Poller {
	public void poll(Map<String, Long> fileReaderTimeStamps );
}
