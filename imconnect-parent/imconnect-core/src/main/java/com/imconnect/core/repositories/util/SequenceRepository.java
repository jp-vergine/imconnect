package com.imconnect.core.repositories.util;

public interface SequenceRepository {

	public long getNextSequenceId(String key) throws SequenceException;
}
