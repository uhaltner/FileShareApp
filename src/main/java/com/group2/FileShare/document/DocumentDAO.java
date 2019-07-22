package com.group2.FileShare.document;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.database.DatabaseConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentDAO implements IDocumentDAO {

	private ResultSet resultSet = null;
	private String query;
	private final AuthenticationSessionManager sessionManager;
	private DatabaseConnection databaseConnection;
	private static final Logger logger = LogManager.getLogger(DocumentDAO.class);

	public DocumentDAO()
	{
		sessionManager = AuthenticationSessionManager.instance();

		try {
			databaseConnection = DatabaseConnection.getdbConnectionInstance();
		}
		catch (Exception e) {
			logger.log(Level.ERROR, "Failed to create database instance at DocumentDAO(): ", e);
		}
	}

	public List<Document> getDocuments()
	{
		int user_id = sessionManager.getUserId();
		List<Document> documents = new ArrayList<Document>();

		try
		{
			query = "{ call get_documents(?) }";
			CallableStatement statement = databaseConnection.getConnection().prepareCall(query);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				Document rsDocument = new Document(resultSet.getInt("document_id"),
						resultSet.getString("file_name"),
						resultSet.getInt("size_mb"),
						resultSet.getString("storage_url"),
						resultSet.getInt("user_id"));

				rsDocument.setCreatedDate(resultSet.getTimestamp("created_date"));
				rsDocument.setTrashedDate(resultSet.getTimestamp("trash_date"));
				rsDocument.setTrashed(resultSet.getBoolean("is_trash"));
				rsDocument.setPinned(resultSet.getBoolean("is_pinned"));
				rsDocument.setPublic(resultSet.getBoolean("is_public"));
				documents.add(rsDocument);
			}
			return documents;
		}
		catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to fetch all documents with query:" +query +" of user: "+user_id +" at getDocuments()" , e);
		}
		finally
		{
			try
			{
				if ( null != databaseConnection ) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at getDocuments()", ex);
			}
		}
		return null;
	}

	public Document addDocument(Document document)
	{
		try {
			query = "{ call add_document( ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
			CallableStatement statement = databaseConnection.getConnection().prepareCall(query);
			statement.setString(1, document.getFilename());
			statement.setLong(2, document.getSize());
			statement.setInt(3, document.getOwnerId());
			statement.setString(4, document.getDescription());
			statement.setString(5, document.getStorageURL());
			statement.setBoolean(6, document.isPinned());
			statement.setBoolean(7, document.isPublic());
			statement.setBoolean(8, document.isTrashed());
			statement.setTimestamp(9, new java.sql.Timestamp(document.getCreatedDate().getTime()));
			statement.executeUpdate();

			return document;
		}
		catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to insert document with query:" +query +" of user: "+document.getOwnerId() +" at addDocument()" , e);
		}
		finally
		{
			try
			{
				if ( null != databaseConnection ) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at addDocument()", ex);
			}
		}
		return null;
	}

	public Document updateDocument(Document document)
	{
		try {
			int docId = document.getId();
			boolean isPinned = document.isPinned();
			boolean isPublic = document.isPublic();
			boolean isTrashed = document.isTrashed();
			Date trashedDate = document.getTrashedDate();
			String description = document.getDescription();
      
			query = "{ call update_document( ?, ?, ?, ?, ?, ?, ?) }";

			CallableStatement statement = databaseConnection.getConnection().prepareCall(query);
			statement.setBoolean(1, isPinned);
			statement.setBoolean(2, isPublic);
			statement.setBoolean(3, isTrashed);

			if( null == trashedDate ) {
				statement.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				statement.setTimestamp(4, new java.sql.Timestamp(trashedDate.getTime()));
			}
			statement.setTimestamp(5, new java.sql.Timestamp((new Date()).getTime()));
			statement.setString(6, description);
			statement.setInt(7, docId);

			statement.executeUpdate();
			return document;
		}
		catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to update document with query:" +query +" of document: "+ document.getId() +" at updateDocument()" , e);
		}
		finally
		{
			try
			{
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at updateDocument()", ex);
			}
		}
		return null;
	}

	public Document deleteDocument(Document document) {
		try {
			int documentId = document.getId();

			query = "{ call delete_document(?) }";
			CallableStatement statement = databaseConnection.getConnection().prepareCall(query);
			statement.setInt(1, documentId);
			statement.executeUpdate();
			logger.log(Level.INFO, "Successfully deleted document: "+ document.getId() +" at deleteDocument()");
			return document;
		}
		catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to delete document with query:" +query +" of document: "+ document.getId() +" at deleteDocument()" , e);
		}
		finally
		{
			try
			{
				if (databaseConnection != null) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at deleteDocument()", ex);
			}
		}
		return null;
	}
	
	public Long getTotalFileSize() {
		int user_id = sessionManager.getUserId();
		Long total_filesize = 0l;

		try
		{
			query = "{ call get_total_filesize(?) }";
			CallableStatement statement = databaseConnection.getConnection().prepareCall(query);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				total_filesize = resultSet.getLong("total_filesize");
			}
			return total_filesize;
		}
		catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to get the total filesize with query:" +query +" for user: "+user_id +" at getTotalFileSize()" , e);
		}
		finally
		{
			try
			{
				if ( null != databaseConnection ) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at getTotalFileSize()", ex);
			}
		}
		return null;
	}

	
	/** @Author: Ueli Haltner
	 *  @Description: Returns a list of documents based on the input query.
	 */
	public List<Document> getDocumentList(String query, int userId, boolean publicDocumentsOnly, boolean trashedDocumentsOnly)
	{
		List<Document> documentList = new ArrayList<Document>();

		try (Connection conn = databaseConnection.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setInt(1, userId);
			stmt.setBoolean(2,publicDocumentsOnly);
			stmt.setBoolean(3,trashedDocumentsOnly);

			ResultSet rs = stmt.executeQuery();

			while( rs.next() )
			{
				Document rsDocument = new Document(
						rs.getInt("document_id"),
						rs.getString("file_name"),
						rs.getInt("size_mb"),
						rs.getString("storage_url"),
						rs.getInt("user_id")
				);

				rsDocument.setCreatedDate(rs.getTimestamp("created_date"));
				rsDocument.setTrashedDate(rs.getTimestamp("trash_date"));
				rsDocument.setTrashed(rs.getBoolean("is_trash"));
				rsDocument.setPinned(rs.getBoolean("is_pinned"));
				rsDocument.setPublic(rs.getBoolean("is_public"));
				rsDocument.setDescription(rs.getString("description"));

				documentList.add(rsDocument);
			}
		}
		catch (SQLException ex) {
			logger.log(Level.ERROR, "Failed to get document list with query:" +query +" of user: "+ userId +" at createPrivateShareLink()" , ex);
		}
		finally
		{
			try {
				if (databaseConnection != null) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at getDocumentList()", ex);
			}
		}

		return documentList;
	}
	
	
	public boolean createPrivateShareLink(int documentId, String accessURL)
	{
		query = "{ call create_private_shared_link(?,?) }";

		try (Connection conn = databaseConnection.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setInt(1, documentId);
			stmt.setString(2,accessURL);
			stmt.executeUpdate();
		}
		catch (SQLException ex) {
			logger.log(Level.ERROR, "Failed to create private share link of document with query:" +query +" of document: "+ documentId +" at createPrivateShareLink()" , ex);
			return false;										
		}
		finally
		{
			try {
				if (databaseConnection != null) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at createPrivateShareLink()", ex);
			}
		}
		return true;
	}

	public Document getDocument(int document_id) {

		query = "{ call get_document(?) }";

		try (Connection conn = databaseConnection.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setInt(1, document_id);
			resultSet = stmt.executeQuery();

			while(resultSet.next())
			{
				Document rsDocument = new Document(resultSet.getInt("document_id"),
						resultSet.getString("file_name"),
						resultSet.getInt("size_mb"),
						resultSet.getString("storage_url"),
						resultSet.getInt("user_id"));
				rsDocument.setDescription(resultSet.getString("description"));
				return rsDocument;
			}

		}
		catch (SQLException ex) {
			logger.log(Level.ERROR, "Failed to get document with query:" +query +" of document: "+ document_id +" at getDocument()" , ex);
		}
		finally
		{
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at getDocument()", ex);
			}
		}
		return null;
	}


	public SharedLink getLinkedDocumentRefWith(String accessUrl) {

		query = "{ call get_shared_document(?) }";

		try (Connection conn = databaseConnection.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setString(1, accessUrl);
			resultSet = stmt.executeQuery();

			while(resultSet.next()) {
				SharedLink sharedDocumentRefernce = new SharedLink(resultSet.getInt("link_id"),
						resultSet.getInt("document_id"),
						resultSet.getString("expiration_date"));
				return sharedDocumentRefernce;
			}

		} catch (SQLException ex) {
			logger.log(Level.ERROR, "Failed to get shared document with query:" +query +" of document access URL: "+ accessUrl +" at getLinkedDocumentRefWith()" , ex);
		}
		finally
		{
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			}
			catch (Exception ex) {
				logger.log(Level.ERROR, "Failed to close database connection at getLinkedDocumentRefWith()", ex);
			}
		}

		return null;
	}

}
