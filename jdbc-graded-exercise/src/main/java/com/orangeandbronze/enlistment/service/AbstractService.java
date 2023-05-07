package com.orangeandbronze.enlistment.service;

import java.util.function.*;

import com.orangeandbronze.enlistment.dao.*;

public abstract class AbstractService {
	
	<R> R retryIfDataIsStale(Supplier<R> routine) {
		final int MAX_RETRIES = 3;
		for (int retries = 0; true; retries++) {
			try {
				return routine.get();
			} catch (StaleDataException e) {
				if (!(retries < MAX_RETRIES)) {
					throw e;
				}
			}
		}
	}
	
	void retryIfDataIsStale(Runnable routine) {
		retryIfDataIsStale(() -> {
			routine.run();
			return null;
		});
	}


}
