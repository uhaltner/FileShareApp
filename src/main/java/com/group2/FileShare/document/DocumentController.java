package com.group2.FileShare.document;


import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.Compression.ICompression;
import com.group2.FileShare.Compression.ZipCompression;
import com.group2.FileShare.Dashboard.DashboardStrategy.IDashboard;
import com.group2.FileShare.Dashboard.SortStrategy.IDocumentSorter;
import com.group2.FileShare.storage.IStorage;
import com.group2.FileShare.storage.S3StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {
	private final IStorage storage;
	private static List<Document> documentsCollection;
	private final ICompression compression;
	private final String compressionExtension;
	private static AuthenticationSessionManager sessionManager;
	private static IDocumentDAO documentDAO;

	DocumentController() {
		storage = S3StorageService.getInstance();
		documentsCollection = new ArrayList<>();
		compression = new ZipCompression();
		compressionExtension = ".zip";
		sessionManager = AuthenticationSessionManager.instance();
		documentDAO = new DocumentDAO();
	}

	@PostMapping("")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = new Document();
		d.setFilename(file.getOriginalFilename());
		d.setSize(file.getSize());
		d.setDescription(file.getContentType());
		d.setCreatedDate(new Date());
		d.setOwnerId(sessionManager.getUserId());
		d.setStorageURL();
		// TODO check Document with Document validator ????
		File compressedFile = compression.compressFile(file);
		String storageFileName = d.getStorageURL();
		String fileName = d.getFilename();
		if (storage.uploadFile(compressedFile, storageFileName)) {
			d = documentDAO.addDocument(d);
			documentsCollection.add(d);
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded file, " +  d.getFilename() + ", !");
			System.out.println("You successfully uploaded " + fileName + "!");
		} else {
			redirectAttributes.addFlashAttribute("error", "File upload failed for file, " + fileName + "!");
			System.err.println("File upload failed for " + fileName + "!");
		}
		compressedFile.delete();
		return "redirect:" + redirect;
	}
	
	@PostMapping("/privateLink")
	@ResponseBody
	public String generatePrivateShareLink(@RequestParam("fileIndex") int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		String randomAccessString = java.util.UUID.randomUUID().toString();
		
		if (documentDAO.createPrivateShareLink(d.getId(), randomAccessString)) {
			return randomAccessString;
		}
		return null;
	}

	@GetMapping("/{fileIndex}")
	public ResponseEntity<Resource> handleFileDownload(@PathVariable int fileIndex) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
		} catch (IndexOutOfBoundsException e) {
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
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	public static List<Document> getDocumentCollection(IDocumentSorter documentSorter, int userId, IDashboard dashboard) {
		if (sessionManager.isUserLoggedIn()) {

			try {
				documentsCollection = documentSorter.executeStrategy(userId, dashboard.isPublicOnly(), dashboard.isTrashedOnly());
			}catch(Exception e){
				System.out.println(e);
			}

		}
		return documentsCollection;
	}
	

	public static Document getGuestDocument(int documentId) {
		Document d = null;
		try {
			d = documentDAO.getDocument(documentId);
			documentsCollection.removeAll(documentsCollection);
			documentsCollection.add(d);
		} catch (Exception e) {
			System.out.println(e);
		}
		return d;
	}

	public static List<Document> getDocumentList()
	{
		try {
			documentsCollection = documentDAO.getDocuments();
		} catch (Exception e) {
			System.out.println(e);
		}
		return documentsCollection;
	}

	@GetMapping("/delete/{fileIndex}")
	public RedirectView handleFileDelete(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File delete failed! Please try again later.");
			return new RedirectView(redirect);
		}
		String filename = d.getFilename();
		String filePath = d.getStorageURL();
		if (storage.deleteFile(filePath)) {
			d = documentDAO.deleteDocument(d);
			documentsCollection.remove(fileIndex);
			redirectAttributes.addFlashAttribute("message", "You successfully deleted " + filename + "!");
			System.out.println("You successfully deleted " + filename + "!");
		} else {
			redirectAttributes.addFlashAttribute("error", "File delete failed for " + filename + "!");
			System.out.println("File delete failed for " + filename + "!");
		}
		return new RedirectView(redirect);
	}

	@PostMapping("/makepublic")
	public RedirectView makePublic(@RequestParam("fileIndex") int fileIndex, @RequestParam("updateFileDescription") String fileDescription,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setPublic(true);
			d.setDescription(fileDescription);
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File public access failed for file, " +  d.getFilename() + ", !");
			System.out.println("File public access failed for fileIndex" + fileIndex + "!");
			return new RedirectView(redirect);
		}
		redirectAttributes.addFlashAttribute("message", "You successfully made file, " +  d.getFilename() + ", public!");
		System.out.println("You successfully made file at index" + fileIndex + " public!");
		return new RedirectView(redirect);
	}

	@PostMapping("/makeprivate")
	public String makePrivate(@RequestParam("fileIndex") int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setPublic(false);
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File private access failed for file, " +  d.getFilename() + "!");
			System.out.println("File private access failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " +  d.getFilename() + ", private!");
		System.out.println("You successfully made file at index " + fileIndex + " private!");
		return "redirect:" + redirect;
	}

	@GetMapping("/pin/{fileIndex}")
	public String makePinned(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setPinned(true);
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File pin failed for file, " +  d.getFilename() + ", !");
			System.out.println("File pin failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message", "You successfully made file, " +  d.getFilename() + ", pinned!");
		System.out.println("You successfully made " +  d.getFilename() + " pinned!");
		return "redirect:" + redirect;
	}

	@GetMapping("/unpin/{fileIndex}")
	public String makeUnPinned(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setPinned(false);
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File unpin failed for file, " + d.getFilename() + ", !");
			System.out.println("File unpin failed for " +  d.getFilename() + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " + fileIndex + ", unpinned!");
		System.out.println("You successfully made " +  d.getFilename() + " unpinned!");
		return "redirect:" + redirect;
	}

	@GetMapping("/trash/{fileIndex}")
	public String trashFile(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setTrashed(true);
			d.setTrashedDate(new Date());
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File trash failed for file, " + fileIndex + ", !");
			System.out.println("File trash failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " +  d.getFilename() + ", trashed!");
		System.out.println("You successfully made file at index" + fileIndex + " trashed!");
		return "redirect:" + redirect;
	}

	@GetMapping("/untrash/{fileIndex}")
	public String unTrashFile(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setTrashed(false);
			d.setTrashedDate(null);
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File recovery failed for file, " + fileIndex + ", !");
			System.out.println("File recovery failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " + fileIndex + ", not trashed!");
		System.out.println("You successfully made file at index" + fileIndex + " not trashed!");
		return "redirect:" + redirect;
	}

}
