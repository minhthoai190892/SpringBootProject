package com.shopme.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	private static final Logger LOGGER= LoggerFactory.getLogger(FileUploadUtil.class);
	public static void saveFile(String uploadDir,String fileName,MultipartFile multipartFile) throws IOException {
		System.err.println("FileUploadUtil>saveFile");
		Path uploadPath = Paths.get(uploadDir);
		System.out.println(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try(InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			System.out.println("filePath"+filePath);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
			// TODO: handle exception
			throw new IOException("Could not save file: "+fileName,e);
		}
	}
	public static void cleanDir(String dir) {
		System.err.println("FileUploadUtil>cleanDir");
		Path dirPath = Paths.get(dir);
		System.out.println("dirPath "+ dirPath);
		try {
			Files.list(dirPath).forEach(file->{
				if (!Files.isDirectory(file)) {
					try {
						System.out.println("file "+ file);
						Files.delete(file);
					} catch (IOException e) {
						// TODO: handle exception
//						System.out.println("Could not delete file: "+file);
						LOGGER.error("Could not delete file: "+file);
					}
				}
			});
		} catch (IOException e) {
			// TODO: handle exception
//			System.out.println("Could not list directory: "+dirPath);
			LOGGER.error("Could not list directory: "+dirPath);
		}
	}
}
