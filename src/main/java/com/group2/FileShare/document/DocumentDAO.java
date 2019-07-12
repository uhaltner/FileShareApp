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

	private PreparedStatement preparedStatement;
	private ResultSet resultSet = null;
	private String query;
	private final AuthenticationSessionManager sessionManager;
	private DatabaseConnection databaseConnection;
	private static final Logger logger = LogManager.getLogger(DocumentDAO.class);

	public DocumentDAO() {
		sessionManager = AuthenticationSessionManager.instance();

		try {
			databaseConnection = DatabaseConnection.getdbConnectionInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Document> getDocuments() {
		int user_id = sessionManager.getUserId();

		try {
			List<Document> documents = new ArrayList<Document>();
			query = "SELECT * FROM Document WHERE user_id = ?";
			preparedStatement = databaseConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Document rsDocument = new Document(resultSet.getInt("document_id"), resultSet.getString("file_name"),
						resultSet.getInt("size_mb"), resultSet.getString("storage_url"), resultSet.getInt("user_id"));
				rsDocument.setCreatedDate(resultSet.getTimestamp("created_date"));
				rsDocument.setTrashedDate(resultSet.getTimestamp("trash_date"));
				rsDocument.setTrashed(resultSet.getBoolean("is_trash"));
				rsDocument.setPinned(resultSet.getBoolean("is_pinned"));
				rsDocument.setPublic(resultSet.getBoolean("is_public"));
				documents.add(rsDocument);
			}

			return documents;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public Document addDocument(Document document) {
		try {
			query = "INSERT INTO Document (file_name, size_mb, user_id, description, storage_url, is_pinned, is_public, is_trash, created_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = databaseConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, document.getFilename());
			preparedStatement.setLong(2, document.getSize());
			preparedStatement.setInt(3, document.getOwnerId());
			preparedStatement.setString(4, document.getDescription());
			preparedStatement.setString(5, document.getStorageURL());
			preparedStatement.setBoolean(6, document.isPinned());
			preparedStatement.setBoolean(7, document.isPublic());
			preparedStatement.setBoolean(8, document.isTrashed());
			preparedStatement.setTimestamp(9, new java.sql.Timestamp(document.getCreatedDate().getTime()));
			preparedStatement.executeUpdate();

			return document;
		} catch (SQLException e) {
			logger.log(Level.ALL, "Could not execute query:", query);
		} finally {
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			} catch (Exception ex) {
				logger.log(Level.ALL, "Unable to close the connection", ex);
			}
		}
		return null;
	}

	public Document updateDocument(Document document) {
		try {
			int docId = document.getId();
			boolean isPinned = document.isPinned();
			boolean isPublic = document.isPublic();
			boolean isTrashed = document.isTrashed();
			Date trashedDate = document.getTrashedDate();
			if (null==trashedDate) {
				query = "UPDATE Document SET is_pinned = ?, is_public = ?, is_trash = ?, modified_date = ? WHERE document_id = ?";
			} else {
				query = "UPDATE Document SET is_pinned = ?, is_public = ?, is_trash = ?, trash_date = ?, modified_date = ? WHERE document_id = ?";
			}
			
			preparedStatement = databaseConnection.getConnection().prepareStatement(query);
			preparedStatement.setBoolean(1, isPinned);
			preparedStatement.setBoolean(2, isPublic);
			preparedStatement.setBoolean(3, isTrashed);
			if (null==trashedDate) {
				preparedStatement.setTimestamp(4, new java.sql.Timestamp((new Date()).getTime()));
				preparedStatement.setInt(5, docId);
			} else {
				preparedStatement.setTimestamp(4, new java.sql.Timestamp(trashedDate.getTime()));
				preparedStatement.setTimestamp(5, new java.sql.Timestamp((new Date()).getTime()));
				preparedStatement.setInt(6, docId);
			}
			
			preparedStatement.executeUpdate();
			return document;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public Document deleteDocument(Document document) {
		try {
			int documentId = document.getId();

			query = "DELETE FROM Document WHERE document_id = ?";
			preparedStatement = databaseConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, documentId);
			preparedStatement.executeUpdate();
			return document;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (databaseConnection != null) {
					databaseConnection.closeConnection();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/** @Author: Ueli Haltner
	 *  @Description: Returns a list of documents based on the input query.
	 */
	public List<Document> getDocumentList(String query, int userId, boolean publicDocumentsOnly) {

		DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();
		List<Document> documentList = new ArrayList<Document>();

		try (Connection conn = db.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setInt(1, userId);
			stmt.setBoolean(2,publicDocumentsOnly);

			ResultSet rs = stmt.executeQuery();

			while( rs.next() ) {

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

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());

		} finally {
			db.closeConnection();
		}

		return documentList;
	}
	 
	
	
	public boolean createPrivateShareLink(int documentId, String accessURL, String linkedFileDescription) {

		DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();
		String query = "{ call create_private_shared_link(?,?,?) }";

		try (Connection conn = db.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setInt(1, documentId);
			stmt.setString(2,accessURL);
			stmt.setString(3,linkedFileDescription);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		} finally {
			db.closeConnection();
		}
		return true;
	}

	public Document getDocument(int document_id) {

		String query = "{ call get_document(?) }";

		DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

		try (Connection conn = db.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setInt(1, document_id);
			ResultSet resultSet = stmt.executeQuery();

			while(resultSet.next()) {
				Document rsDocument = new Document(resultSet.getInt("document_id"), resultSet.getString("file_name"),
						resultSet.getInt("size_mb"), resultSet.getString("storage_url"), resultSet.getInt("user_id"));
				rsDocument.setDescription(resultSet.getString("description"));
				return rsDocument;
			}

			db.closeConnection();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}


	public SharedLink getLinkedDocumentRefWith(String accessUrl) {

		String query = "{ call get_shared_document(?) }";

		DatabaseConnection db = DatabaseConnection.getdbConnectionInstance();

		try (Connection conn = db.getConnection();
			 CallableStatement stmt = conn.prepareCall(query)) {

			stmt.setString(1, accessUrl);
			ResultSet resultSet = stmt.executeQuery();

			while(resultSet.next()) {
				SharedLink sharedDocumentRefernce = new SharedLink(resultSet.getInt("link_id"), resultSet.getInt("document_id"), resultSet.getString("expiration_date"));
				return sharedDocumentRefernce;
			}

			db.closeConnection();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (null != databaseConnection) {
					databaseConnection.closeConnection();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return null;
	}

}
