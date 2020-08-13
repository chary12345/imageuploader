package com.controllers;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pojos.DatabaseFile;
import com.response.Response;
import com.service.DatabaseFileService;

	@RestController
	public class FileUploadController {

	    @Autowired
	    private DatabaseFileService fileStorageService;

	    @PostMapping("/uploadFile")
	    public Response uploadFile(@RequestParam("file") MultipartFile file) {
	    	System.out.println("enters into uploadfile action file upload controller");
	        DatabaseFile fileName = fileStorageService.storeFile(file);
System.out.println(fileName.getFileName()+" is saved in database");
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/downloadFile/")
	            .path(fileName.getFileName())
	            .toUriString();

	    	System.out.println("exit into uploadfile action file upload controller");

	        return new Response(fileName.getFileName(), fileDownloadUri,
	            file.getContentType(), file.getSize());
	    }

	    @PostMapping("/uploadMultipleFiles")
	    public List < Response > uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
	        return Arrays.asList(files)
	            .stream()
	            .map(file -> uploadFile(file))
	            .collect(Collectors.toList());
	    }
	}

