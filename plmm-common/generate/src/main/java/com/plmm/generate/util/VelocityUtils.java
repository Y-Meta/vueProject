package com.plmm.generate.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityUtils {
	
	static Properties p=null;
	static{
		Properties p = new Properties();
		p.setProperty("input.encoding", "GBK");
		p.setProperty("output.encoding", "GBK");
		try {
			Velocity.init(p);
		} catch (Exception e) {
			System.err.print(e);
		}
	}
	public static String convert(String vm, Map<?,?> map) throws Exception {
		VelocityContext context = new VelocityContext(map);
		StringWriter w = new StringWriter();
		Velocity.evaluate(context, w, "util.velocity", vm);
		return w.toString();
	}
	
	
}
