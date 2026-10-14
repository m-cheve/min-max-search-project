package jeux.core;

import java.util.Hashtable;
import java.util.Set;

public class Metrics {
	private Hashtable<String, String> hash;

	public Metrics() {
		this.hash = new Hashtable<String, String>();
	}

	public void set(String name, int i) {
		hash.put(name, Integer.toString(i));
	}

	public void set(String name, double d) {
		hash.put(name, Double.toString(d));
	}

	public void set(String name, long l) {
		hash.put(name, Long.toString(l));
	}

	public int getInt(String name) {
		return Integer.parseInt(hash.get(name));
	}

	public double getDouble(String name) {
		return Double.parseDouble(hash.get(name));
	}

	public long getLong(String name) {
		return Long.parseLong(hash.get(name));
	}

	public String get(String name) {
		return hash.get(name);
	}

	public Set<String> keySet() {
		return hash.keySet();
	}

	public String toString() {
		return hash.toString();
	}
}
