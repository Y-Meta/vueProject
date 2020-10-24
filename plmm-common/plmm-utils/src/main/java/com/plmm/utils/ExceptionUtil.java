package com.plmm.utils;
//package com.plmm.utils;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.classic.spi.IThrowableProxy;
//import ch.qos.logback.classic.spi.StackTraceElementProxy;
///**
// * @description: 异常堆栈信息收集类，将异常信息堆栈转化为字符串
// */
//public class ExceptionUtil {
//	/**
//	 * 将异常堆栈信息全部转化为字符串
//	 * @param t 异常对象
//	 * @return 异常字符串
//	 */
//	public static String printStackTraceToString(Throwable t){
//		
//		StringWriter sw = new StringWriter();
//		try{
//			t.printStackTrace(new PrintWriter(sw, true));
//			return sw.getBuffer().toString();
//		} finally {
//			try {sw.close();} catch (IOException e) {}
//		}
//	}
//	
//	public static String printStackTraceToString(ILoggingEvent logEvent){
//	    StringBuilder message = new StringBuilder();
//	    message.append(logEvent.getThrowableProxy().getClassName()).append(": ");
//        message.append(logEvent.getThrowableProxy().getMessage());
//        StackTraceElementProxy[] steps = logEvent.getThrowableProxy().getStackTraceElementProxyArray();
//        for(StackTraceElementProxy step : steps) {
//            message.append(System.lineSeparator()).append("\t").append(step.toString());
//        }
//        if(logEvent.getThrowableProxy().getCause() != null) {
//            printStackTraceToString(logEvent.getThrowableProxy().getCause(), message);
//        };
//        return message.toString();
//    }
//	
//	private static void printStackTraceToString(IThrowableProxy proxy, StringBuilder message) {
//	    message.append(System.lineSeparator()).append("Caused by: ");
//        message.append(proxy.getClassName()).append(": ");
//        message.append(proxy.getMessage());
//        int omitted = proxy.getCommonFrames();
//        StackTraceElementProxy[] step = proxy.getStackTraceElementProxyArray();
//        for(int i=0; i<step.length-omitted; i++) {
//            message.append(System.lineSeparator()).append("\t").append(step[i].toString());
//        }
//        if(omitted > 0) {
//            message.append(System.lineSeparator()).append("\t... ").append(omitted).append(" common frames omitted");
//        }
//        if(proxy.getCause() != null) {
//            printStackTraceToString(proxy.getCause(), message);
//        }
//	}
//	
//	
//	
//	/*public static void main(String[] args) {
//        int zero = 0;
//        
//        try {
//            int num = 9/zero;
//        } catch(Exception e) {
//            e.printStackTrace();
//            System.out.println("transfered...");
//            System.out.println(printStackTraceToString(e));
//        }
//    }*/
//}
