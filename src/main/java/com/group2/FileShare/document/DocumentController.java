package com.group2.FileShare.document;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group2.FileShare.Compression.ICompression;
import com.group2.FileShare.Compression.ZipCompression;
import com.group2.FileShare.storage.IStorage;
import com.group2.FileShare.storage.S3StorageService;

@Controller
@RequestMapping("/document")
public class DocumentController {
	private final IStorage storage;
	private List<Document> documentsCollection;
	private final ICompression compression;
	private final String compressionExtension;
//    private final IDatabase db;

	DocumentController() {
		storage = S3StorageService.getInstance();
		documentsCollection = new ArrayList<>();
		compression = new ZipCompression();
		compressionExtension = ".zip";
//    	db = Database.getInstance();
		loadDocumentCollection();
	}

	@PostMapping("")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam(value= "redirect", defaultValue = "/") String redirect, RedirectAttributes redirectAttributes) {
		Document d = new Document();
		d.setFilename(file.getOriginalFilename());
		d.setSize(file.getSize());
		d.setDescription(file.getContentType()); 
//		d.setOwnerId(ownerId);
		d.setStorageURL();
		
		// TODO check Document with Document validator ????
		
		File compressedFile = compression.compressFile(file);

		String storageFileName = d.getStorageURL();
		String fileName = d.getFilename();
		if (storage.uploadFile(compressedFile, storageFileName)) {
//			d = db.addDocument(d);
			documentsCollection.add(d);
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + "!");
			System.out.println("You successfully uploaded " + fileName + "!");
		} else {
			redirectAttributes.addFlashAttribute("message", "File upload failed for " + fileName + "!");
			System.err.println("File upload failed for " + fileName + "!");
		}
		
		compressedFile.delete();
		
		return "redirect:" + redirect;
	}

	@GetMapping("/{fileIndex}")
	public ResponseEntity<Resource> handleFileDownload(@PathVariable int fileIndex) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
		}catch (IndexOutOfBoundsException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		String filename = d.getFilename() + compressionExtension;
		String filePath = d.getStorageURL();
		Resource resource = null;
		try {
			resource = new UrlResource(storage.downloadFile(filePath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

	@GetMapping("")
	@ResponseBody
	public List<Document> getDocumentCollection() {
		return documentsCollection;
	}

	@DeleteMapping("/{fileIndex}")
	public String handleFileDelete(@PathVariable int fileIndex, @RequestParam(value= "redirect", defaultValue = "/") String redirect, RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
		}catch (IndexOutOfBoundsException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "File upload failed! Please try again later.");
			return "redirect:" + redirect;
		}
		String filename = d.getFilename();
		String filePath = d.getStorageURL();
		if (storage.deleteFile(filePath)) {
//			d = db.removeDocument(d);
			documentsCollection.remove(fileIndex);
			redirectAttributes.addFlashAttribute("message", "You successfully deleted " + filename + "!");
			System.out.println("You successfully deleted " + filename + "!");
		} else {
			redirectAttributes.addFlashAttribute("message", "File delete failed for " + filename + "!");
			System.out.println("File delete failed for " + filename + "!");
		}

		return "redirect:" + redirect;
	}

	private void loadDocumentCollection() {
		// TODO Load from Database db
		// documentsCollection = db.getDocuments();

		Document d = new Document();
		d.setFilename("Code Struct.drawio");
//		d.setOwnerId(ownerId);
		d.setStorageURL("Code Struct.drawio");
//		d = db.addDocument(d);
		documentsCollection.add(d);

		d = new Document();
		d.setFilename("A4 v2 Smart Contract Ethereum -CSCI 4145 5409 - Summer 2019.pdf");
//		d.setOwnerId(ownerId);
		d.setStorageURL("A4 v2 Smart Contract Ethereum -CSCI 4145 5409 - Summer 2019.pdf");
//		d = db.addDocument(d);
		documentsCollection.add(d);
	}
}
