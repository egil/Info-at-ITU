package dk.itu.info.proxy.server;

import net.sf.jsr107cache.CacheListener;

public class LongPollingListener implements CacheListener {
	private LongPollingListenerHandler handler;
		
	public void setHandler(LongPollingListenerHandler longPollingListenerHandler) {
		this.handler = longPollingListenerHandler;
	}
	
	@Override
	public void onClear() { }
	@Override
	public void onEvict(Object arg0) { }
	@Override
	public void onLoad(Object arg0) { }
	@Override
	public void onRemove(Object arg0) { }

	@Override
	public void onPut(Object arg0) {
		handler.onPut();
	}	
}
