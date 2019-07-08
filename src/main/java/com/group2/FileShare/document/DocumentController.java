package com.group2.FileShare.document;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.Compression.ICompression;
import com.group2.FileShare.Compression.ZipCompression;
import com.group2.FileShare.Dashboard.DocumentSorter;
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
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + "!");
			System.out.println("You successfully uploaded " + fileName + "!");
		} else {
			redirectAttributes.addFlashAttribute("error", "File upload failed for " + fileName + "!");
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

	public static List<Document> getDocumentCollection(DocumentSorter documentSorter, int userId, boolean publicDashboard) {
		if (sessionManager.isUserLoggedIn()) {

			try {
				documentsCollection = documentSorter.executeStrategy(userId, publicDashboard);
			}catch(Exception e){
				System.out.println(e);
			}

		}
		return documentsCollection;
	}

	public static List<Document> getDocumentList()
	{
		if (sessionManager.isUserLoggedIn())
		{
			try
			{
				documentsCollection = documentDAO.getDocuments();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

		}
		return documentsCollection;
	}

	@GetMapping("/delete/{fileIndex}")
	public String handleFileDelete(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File upload failed! Please try again later.");
			return "redirect:" + redirect;
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
		return "redirect:" + redirect;
	}

	@GetMapping("/makepublic/{fileIndex}")
	public String makePublic(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes) {
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			d.setPublic(true);
			d = documentDAO.updateDocument(d);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "File public access failed for fileIndex" + fileIndex + "!");
			System.out.println("File delete failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message", "You successfully made file at index" + fileIndex + " public!");
		System.out.println("You successfully made file at index" + fileIndex + " public!");
		return "redirect:" + redirect;
	}

	@GetMapping("/makeprivate/{fileIndex}")
	public String makePrivate(@PathVariable int fileIndex,
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
			redirectAttributes.addFlashAttribute("error", "File private access failed for fileIndex" + fileIndex + "!");
			System.out.println("File delete failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file at index" + fileIndex + " private!");
		System.out.println("You successfully made file at index" + fileIndex + " public!");
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
			redirectAttributes.addFlashAttribute("error", "File pin failed for fileIndex" + fileIndex + "!");
			System.out.println("File pin failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message", "You successfully made file at index" + fileIndex + " pinned!");
		System.out.println("You successfully made file at index" + fileIndex + " pinned!");
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
			redirectAttributes.addFlashAttribute("error", "File pin failed for fileIndex" + fileIndex + "!");
			System.out.println("File pin failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file at index" + fileIndex + " unpinned!");
		System.out.println("You successfully made file at index" + fileIndex + " unpinned!");
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
			redirectAttributes.addFlashAttribute("error", "File trash failed for fileIndex" + fileIndex + "!");
			System.out.println("File trash failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file at index" + fileIndex + " trashed!");
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
			redirectAttributes.addFlashAttribute("error", "File recovery failed for fileIndex" + fileIndex + "!");
			System.out.println("File recovery failed for fileIndex" + fileIndex + "!");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file at index" + fileIndex + " not trashed!");
		System.out.println("You successfully made file at index" + fileIndex + " not trashed!");
		return "redirect:" + redirect;
	}

	private static void loadDocumentCollection() {
		documentsCollection = documentDAO.getDocuments();
	}
}
