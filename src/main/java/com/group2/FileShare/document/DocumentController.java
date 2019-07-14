package com.group2.FileShare.document;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.Compression.ICompression;
import com.group2.FileShare.Compression.ZipCompression;
import com.group2.FileShare.Dashboard.DashboardStrategy.IDashboard;
import com.group2.FileShare.Dashboard.SortStrategy.IDocumentSorter;
import com.group2.FileShare.storage.IStorage;
import com.group2.FileShare.storage.S3StorageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger logger = LogManager.getLogger(DocumentController.class);

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
			RedirectAttributes redirectAttributes)
	{
		try {
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

			if (storage.uploadFile(compressedFile, storageFileName))
			{
				d = documentDAO.addDocument(d);
				documentsCollection.add(d);
				redirectAttributes.addFlashAttribute("message", "You successfully uploaded file, " +  d.getFilename() + ", !");
				logger.log(Level.INFO, "User[" + d.getOwnerId() + "] successfully uploaded the file: " +fileName);
			}
			else {
				redirectAttributes.addFlashAttribute("error", "File upload failed for file, " + fileName + "!");
				logger.log(Level.ERROR, "Error while uploading the file " + fileName + "at handleFileUpload():");
			}
			compressedFile.delete();
		}
		catch (Exception ex){
			logger.log(Level.ERROR, "Error while uploading file at handleFileUpload():", ex);
		}

		return "redirect:" + redirect;
	}
	
	@PostMapping("/privateLink")
	@ResponseBody
	public String generatePrivateShareLink(@RequestParam("fileIndex") int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect)
	{
		Document d = null;
		try {
			d = documentsCollection.get(fileIndex);
			String randomAccessString = java.util.UUID.randomUUID().toString();

			if (documentDAO.createPrivateShareLink(d.getId(), randomAccessString)) {
				return randomAccessString;
			}
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at generatePrivateShareLink():", e);
			return null;
		}
		catch (Exception ex){
			logger.log(Level.ERROR, "Error while generating private share link at generatePrivateShareLink():", ex);
		}

		return null;
	}

	@GetMapping("/{fileIndex}")
	public ResponseEntity<Resource> handleFileDownload(@PathVariable int fileIndex)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at handleFileDownload():", e);
			return null;
		}

		String filename = d.getFilename() + compressionExtension;
		String filePath = d.getStorageURL();
		Resource resource = null;
		try
		{
			resource = new UrlResource(storage.downloadFile(filePath));
		}
		catch (MalformedURLException e) {
			logger.log(Level.ERROR, "MalformedURL exception at handleFileDownload():", e);
			return null;
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	public static List<Document> getDocumentCollection(IDocumentSorter documentSorter, int userId, IDashboard dashboard)
	{
		if (sessionManager.isUserLoggedIn())
		{
			try {
				documentsCollection = documentSorter.executeStrategy(userId, dashboard.isPublicOnly(), dashboard.isTrashedOnly());
			}
			catch(Exception e){
				logger.log(Level.ERROR, "Error while getting document list at getDocumentCollection():", e);
			}
		}
		return documentsCollection;
	}
	

	public static Document getGuestDocument(int documentId)
	{
		Document d = null;
		try
		{
			d = documentDAO.getDocument(documentId);
			documentsCollection.removeAll(documentsCollection);
			documentsCollection.add(d);
		}
		catch (Exception e) {
			logger.log(Level.ERROR, "Error while getting guest document at getGuestDocument():", e);
		}
		return d;
	}

	public static List<Document> getDocumentList()
	{
		try {
			documentsCollection = documentDAO.getDocuments();
		}
		catch (Exception e) {
			logger.log(Level.ERROR, "Error while getting document list at getDocumentList():", e);
		}
		return documentsCollection;
	}
	
	public static List<Document> findAll(String phrase){
		Document document;
		String fileName;
		String upperCaseFileName;
		String upperCasePhrase;
        Iterator<Document> iter = documentsCollection.iterator();
        List<Document> matchList = new ArrayList<>();

        //add each document that contains the phrase to a new list
        while(iter.hasNext()){

            document = (Document) iter.next();
            fileName=  document.getFilename();

            upperCaseFileName = fileName.toUpperCase();
            upperCasePhrase = phrase.toUpperCase();

            if (upperCaseFileName.contains(upperCasePhrase)){
                matchList.add(document);
            }
        }
        documentsCollection = matchList;

        return documentsCollection;
    }

	@GetMapping("/delete/{fileIndex}")
	public RedirectView handleFileDelete(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			String filename = d.getFilename();
			String filePath = d.getStorageURL();
			if (storage.deleteFile(filePath))
			{
				d = documentDAO.deleteDocument(d);
				documentsCollection.remove(fileIndex);
				redirectAttributes.addFlashAttribute("message", "You successfully deleted " + filename + "!");
				System.out.println("You successfully deleted " + filename + "!");
				logger.log(Level.INFO, "File:" + filename + " successfully deleted");
			}
			else {
				redirectAttributes.addFlashAttribute("error", "File delete failed for " + filename + "!");
				System.out.println("File delete failed for " + filename + "!");
				logger.log(Level.ERROR, "Error while deleting file:" + d.getId() + " at handleFileDelete()");
			}
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at handleFileDelete():", e);
			redirectAttributes.addFlashAttribute("error", "File delete failed! Please try again later.");
			return new RedirectView(redirect);
		}
		catch (Exception e){
			logger.log(Level.ERROR, "Exception occured at handleFileDelete():", e);
		}

		return new RedirectView(redirect);
	}

	@PostMapping("/makepublic")
	public RedirectView makePublic(@RequestParam("fileIndex") int fileIndex, @RequestParam("updateFileDescription") String fileDescription,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			d.setPublic(true);
			d.setDescription(fileDescription);
			d = documentDAO.updateDocument(d);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at makePublic():", e);
			redirectAttributes.addFlashAttribute("error", "File public access failed for file, " +  d.getFilename() + ", !");
			System.out.println("File public access failed for fileIndex" + fileIndex + "!");
			logger.log(Level.ERROR, "File public access failed for fileID" + d.getId() + "! at makePublic()");
			return new RedirectView(redirect);
		}
		redirectAttributes.addFlashAttribute("message", "You successfully made file, " +  d.getFilename() + ", public!");
		System.out.println("You successfully made file at index" + fileIndex + " public!");
		logger.log(Level.INFO, "File:" + d.getId() + " is made public successfully");
		return new RedirectView(redirect);
	}

	@PostMapping("/makeprivate")
	public String makePrivate(@RequestParam("fileIndex") int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			d.setPublic(false);
			d = documentDAO.updateDocument(d);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at makePrivate():", e);
			redirectAttributes.addFlashAttribute("error", "File private access failed for file, " +  d.getFilename() + "!");
			System.out.println("File private access failed for fileIndex" + fileIndex + "!");
			logger.log(Level.ERROR, "File private access failed for fileID" + d.getId() + "! at makePrivate()");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " +  d.getFilename() + ", private!");
		logger.log(Level.INFO, "File:" + d.getId() + " is made public successfully");
		return "redirect:" + redirect;
	}

	@GetMapping("/pin/{fileIndex}")
	public String makePinned(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			d.setPinned(true);
			d = documentDAO.updateDocument(d);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at makePinned():", e);
			redirectAttributes.addFlashAttribute("error", "File pin failed for file, " +  d.getFilename() + ", !");
			System.out.println("File pin failed for fileIndex" + fileIndex + "!");
			logger.log(Level.ERROR, "File pin failed for fileIndex" + d.getId() + "! at makePinned()");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message", "You successfully made file, " +  d.getFilename() + ", pinned!");
		System.out.println("You successfully made " +  d.getFilename() + " pinned!");
		logger.log(Level.INFO, "File:" + d.getId() + " is pinned successfully");
		return "redirect:" + redirect;
	}

	@GetMapping("/unpin/{fileIndex}")
	public String makeUnPinned(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			d.setPinned(false);
			d = documentDAO.updateDocument(d);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at makeUnPinned():", e);
			redirectAttributes.addFlashAttribute("error", "File unpin failed for file, " + d.getFilename() + ", !");
			System.out.println("File unpin failed for " +  d.getFilename() + "!");
			logger.log(Level.ERROR, "File unpin failed for " +  d.getId() + "! at makeUnPinned()");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " + fileIndex + ", unpinned!");
		System.out.println("You successfully made " +  d.getFilename() + " unpinned!");
		logger.log(Level.INFO, "File:" +  d.getId() + " unpinned successfully!");
		return "redirect:" + redirect;
	}

	@GetMapping("/trash/{fileIndex}")
	public String trashFile(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			d.setTrashed(true);
			d.setTrashedDate(new Date());
			d = documentDAO.updateDocument(d);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at trashFile():", e);
			redirectAttributes.addFlashAttribute("error", "File trash failed for file, " + fileIndex + ", !");
			System.out.println("File trash failed for fileIndex" + fileIndex + "!");
			logger.log(Level.ERROR, "File trash failed for fileID: " +  d.getId() + "! at trashFile()");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " +  d.getFilename() + ", trashed!");
		System.out.println("You successfully made file at index" + fileIndex + " trashed!");
		logger.log(Level.INFO, "File:" + d.getId() + " is trashed successfully");
		return "redirect:" + redirect;
	}

	@GetMapping("/untrash/{fileIndex}")
	public String unTrashFile(@PathVariable int fileIndex,
			@RequestParam(value = "redirect", defaultValue = "/dashboard") String redirect,
			RedirectAttributes redirectAttributes)
	{
		Document d = null;
		try
		{
			d = documentsCollection.get(fileIndex);
			d.setTrashed(false);
			d.setTrashedDate(null);
			d = documentDAO.updateDocument(d);
		}
		catch (IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			logger.log(Level.ERROR, "Index out of Bounds exception at unTrashFile():", e);
			redirectAttributes.addFlashAttribute("error", "File recovery failed for file, " + fileIndex + ", !");
			System.out.println("File recovery failed for fileIndex" + fileIndex + "!");
			logger.log(Level.ERROR, "File unTrash failed for fileID: " +  d.getId() + "! at unTrashFile()");
			return "redirect:" + redirect;
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully made file, " + fileIndex + ", not trashed!");
		System.out.println("You successfully made file at index" + fileIndex + " not trashed!");
		logger.log(Level.INFO, "File:" + d.getId() + " is unTrashed successfully");
		return "redirect:" + redirect;
	}

}
