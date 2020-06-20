package com.jerotoma.common.system;

import java.util.HashMap;

public class SystemThreadLocal {
	
	public static class ThreadLocalContext extends HashMap<String, Object> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	public static ThreadLocal<ThreadLocalContext> threadLocal = new ThreadLocal<ThreadLocalContext> () {
        @Override
        protected ThreadLocalContext initialValue() {
            return new ThreadLocalContext();
        }
    };
    
    public static void add(ThreadLocalContext localContext) {	
		for(String key : localContext.keySet()) {			
			putLocal(key, (String)localContext.get(key));
		}
    }
    
    public static Object put(String key, Object value) {	    	
	    return putLocal(key, value);
	}
    
    public static Object remove(String key) {	    	
	    return removeLocal(key);
	}
    
    protected static Object getLocal(String key) {
		return threadLocal.get().get(key);
    }
    
    protected static Object removeLocal(String key) {
		return threadLocal.get().remove(key);
    }
    
    protected static Object putLocal(String key, Object value) {
		return threadLocal.get().put(key, value);
    }
    
    public static ThreadLocalContext getLocalContext() {
        return threadLocal.get();
    }
    
    public static void clearLocal() {
    	threadLocal.get().clear();
    }

}
