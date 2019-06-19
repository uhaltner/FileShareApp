package com.group2.FileShare.document;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group2.FileShare.IDatabase;
import com.group2.FileShare.storage.IStorage;
import com.group2.FileShare.storage.S3StorageService;

@Controller
@RequestMapping("/document")
public class DocumentController {
	private final IStorage storage;
	private List<Document> documentsCollection;
//    private final IDatabase db;

	DocumentController() {
		storage = S3StorageService.getInstance();
		documentsCollection = new ArrayList<>();
//    	db = Database.getInstance();
		loadDocumentCollection();
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		// TODO check Document with Document validator ????

		Document d = new Document();
		d.setFilename(file.getOriginalFilename());
//		d.setOwnerId(ownerId);
		d.setStorageURL();
		String filename = file.getOriginalFilename();
		if (storage.uploadFile(file, filename)) {
//			d = db.addDocument(d);
			documentsCollection.add(d);
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + filename + "!");
		} else {
			redirectAttributes.addFlashAttribute("message", "File upload failed for " + filename + "!");
		}
		return "redirect:/";
	}

	@GetMapping("/{fileIndex}")
	public ResponseEntity<Resource> handleFileDownload(@PathVariable int fileIndex) {

		System.err.println(fileIndex);
		Document d = documentsCollection.get((fileIndex));
		String filename = d.getFilename();
		String filePath = d.getStorageURL();
		Resource resource = null;
		try {
			resource = new UrlResource(storage.downloadFile(filePath));
		} catch (MalformedURLException e) {
//			e.printStackTrace();
			return null;
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(resource);
	}

	@GetMapping("/")
	public Document[] getDocumentCollection() {
		return documentsCollection.toArray(new Document[0]);
	}

	@DeleteMapping("/{fileIndex}")
	public String handleFileDelete(@PathVariable int fileIndex, RedirectAttributes redirectAttributes) {
		Document d = documentsCollection.get(fileIndex);
		String filename = d.getFilename();
		String filePath = d.getStorageURL();
		if (storage.deleteFile(filePath)) {
			redirectAttributes.addFlashAttribute("message", "You successfully deleted " + filename + "!");
		} else {

			redirectAttributes.addFlashAttribute("message", "File upload failed for " + filename + "!");
		}

		return "redirect:/";
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
