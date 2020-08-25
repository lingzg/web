package com.lingzg.web.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class BaseController {

    protected final Logger log = Logger.getLogger(getClass().getName());
    protected SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected SimpleDateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd");
    
	public HttpEntity<Object> data(Object obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		return new HttpEntity<Object>(obj, headers);

	}

	protected void writeJSON(HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}
}
