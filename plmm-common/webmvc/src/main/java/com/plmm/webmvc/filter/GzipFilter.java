package com.plmm.webmvc.filter;
//package com.plmm.webmvc.filter;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.util.regex.Pattern;
//import java.util.zip.GZIPOutputStream;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.WriteListener;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * gzip压缩，此过滤器过滤所有非图片的链接，并对请求的输出流进行压缩，可大大减少网络传输流量，提高系统吞吐量
// */
//@Order(Integer.MIN_VALUE + 20)
//@WebFilter(filterName = "gzipFilter", urlPatterns = {"/*"})
//public class GzipFilter extends OncePerRequestFilter {
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Override
//	protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response,
//			FilterChain filterChain) throws ServletException, IOException {
//		// 判断是否包含了 Accept-Encoding 请求头部
//		HttpServletRequest s = (HttpServletRequest) request;
//		String header = s.getHeader("Accept-Encoding");
//		//对所有头内有gzip字符且不为图片的请求进行压缩
//		if (header != null && header.toLowerCase().contains("gzip") && !isImage(request.getRequestURI())) {
//			HttpServletResponse resp = (HttpServletResponse) response;
//			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//			HttpServletResponseWrapper hsrw = new HttpServletResponseWrapper(resp) {
//				@Override
//				public PrintWriter getWriter() throws IOException {
//					return new PrintWriter(new OutputStreamWriter(buffer, getCharacterEncoding()));
//				}
//
//				@Override
//				public ServletOutputStream getOutputStream() throws IOException {
//					return new ServletOutputStream() {
//						@Override
//						public void write(int b) throws IOException {
//							buffer.write(b);
//						}
//						@Override
//						public void write(byte[] b) throws IOException {
//							buffer.write(b);
//						}
//						@Override
//						public void write(byte[] b, int off, int len) throws IOException {
//							buffer.write(b, off, len);
//						}
//
//						@Override
//						public boolean isReady() {
//							return false;
//						}
//
//						@Override
//						public void setWriteListener(WriteListener listener) {
//							//Integer noUseValue = 0;
//							//noUseValue++;
//						}
//					};
//				}
//			};
//
//			filterChain.doFilter(request, hsrw);
//			byte[] gzipData = gzip(buffer.toByteArray());
//			resp.addHeader("Content-Encoding", "gzip");
//			resp.setContentLength(gzipData.length);
//			ServletOutputStream output = response.getOutputStream();
//			output.write(gzipData);
//			output.flush();
//		} else {
//			filterChain.doFilter(request, response);
//		}
//	}
//
//	// 用 GZIP 压缩字节数组
//	private byte[] gzip(byte[] data) {
//		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
//		GZIPOutputStream output = null;
//		try {
//			output = new GZIPOutputStream(byteOutput);
//			output.write(data);
//		} catch (IOException e) {
//		} finally {
//			try {
//				if (output != null) {
//					output.close();
//				}
//			} catch (IOException e) {
//			}
//		}
//		return byteOutput.toByteArray();
//	}
//	
//	private static boolean isImage(String uri) {
//		return Pattern.matches("(?!(\\.jpg|\\.png|\\.gif|\\.bmp)).+?(\\.jpg|\\.png|\\.gif|\\.bmp)", uri);
//	}
//}
