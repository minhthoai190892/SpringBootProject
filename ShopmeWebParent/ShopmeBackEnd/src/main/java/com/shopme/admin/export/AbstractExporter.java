package com.shopme.admin.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {
	public void setResponseHeader(HttpServletResponse response, String contentType, String extension)
			throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormat.format(new Date());
//		extension = ".csv"
		String fileName = "user_" + timestamp + extension;
//		contentType="text/csv"
		response.setContentType(contentType);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; fileName=" + fileName;
		response.setHeader(headerKey, headerValue);

	}
}
