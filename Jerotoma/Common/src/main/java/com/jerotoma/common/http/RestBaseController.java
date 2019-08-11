package com.jerotoma.common.http;

import org.springframework.lang.Nullable;

public abstract class RestBaseController {
	protected abstract HttpResponseEntity<Object> index(@Nullable Object... args); 
	protected abstract HttpResponseEntity<Object> show(@Nullable Object... args);
	protected abstract HttpResponseEntity<Object> update(@Nullable Object... args);
	protected abstract HttpResponseEntity<Object> create(@Nullable Object... args);
	protected abstract HttpResponseEntity<Object> edit(@Nullable Object... args);
	protected abstract HttpResponseEntity<Object> destroy(@Nullable Object... args);

}
