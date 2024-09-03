package com.example.CatholicPetCareSystem.security.request;


public class requestInfo {
	private long timestamp;
	private int count;
	
	public long getTimestamp() {
		return timestamp;
	}
	public requestInfo(long timestamp, int count) {
		this.timestamp = timestamp;
		this.count = count;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
    public void incrementCount() {
        this.count++;
    }
}
