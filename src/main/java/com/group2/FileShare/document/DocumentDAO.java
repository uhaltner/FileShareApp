package com.group2.FileShare.document;


import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentDAO implements IDocumentDAO {

    private PreparedStatement preparedStatement;
    private ResultSet resultSet = null;
    private String query;
    private Connection connection;
    private final AuthenticationSessionManager sessionManager;
    private DatabaseConnection databaseConnection;

    public DocumentDAO()
    {
        sessionManager = AuthenticationSessionManager.instance();

        try
        {
            databaseConnection = DatabaseConnection.getdbConnectionInstance();
            this.connection = databaseConnection.getConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Document> getDocuments()
    {
        int user_id = sessionManager.getUserId();

        try
        {
            List<Document> documents = new ArrayList<Document>();
            query = "SELECT * FROM Document WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Document rsDocument = new Document(resultSet.getInt("document_id"), resultSet.getString("file_name"),
                        resultSet.getInt("size_mb"),resultSet.getString("storage_url"),
                        resultSet.getInt("user_id"));
                documents.add(rsDocument);
            }

            return documents;
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != databaseConnection)
                {
                    databaseConnection.closeConnection();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Document addDocument(Document document)
    {
        try
        {
            this.connection = databaseConnection.getConnection();

            String file_name = document.getFilename();
            long size = document.getSize();
            int user_id = document.getOwnerId();
            String description = document.getDescription();
            String storage_url = document.getStorageURL();

            query = "INSERT INTO Document (file_name, size_mb, user_id, description, storage_url) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,file_name);
            preparedStatement.setLong(2,size);
            preparedStatement.setInt(3,user_id);
            preparedStatement.setString(4,description);
            preparedStatement.setString(5,storage_url);
            preparedStatement.executeUpdate();

            return null;
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != databaseConnection)
                {
                    databaseConnection.closeConnection();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Document updateDocument(Document document)
    {
        try
        {
            int docId = document.getId();
            boolean isPinned = document.isPinned();
            boolean isPublic = document.isPublic();
            boolean isTrashed = document.isTrashed();
            Date trashedDate = document.getTrashedDate();

            query = "UPDATE Document SET is_pinned = ?, is_public = ?, is_trash = ?, trash_date = ? WHERE document_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, isPinned);
            preparedStatement.setBoolean(2, isPublic);
            preparedStatement.setBoolean(3, isTrashed);
            preparedStatement.setObject(4, trashedDate);
            preparedStatement.setInt(5, docId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Document rsDocument = new Document(resultSet.getInt("document_id"), resultSet.getString("file_name"),
                        resultSet.getInt("size_mb"),resultSet.getString("storage_url"),
                        resultSet.getInt("user_id"));
                return rsDocument;
            }
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != databaseConnection)
                {
                    databaseConnection.closeConnection();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Document deleteDocument(Document document)
    {
        try
        {
            int documentId = document.getId();

            query = "DELETE FROM Document WHERE document_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, documentId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Document rsDocument = new Document(resultSet.getInt("document_id"), resultSet.getString("file_name"),
                        resultSet.getInt("size_mb"),resultSet.getString("storage_url"),
                        resultSet.getInt("user_id"));
                return rsDocument;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (databaseConnection != null)
                {
                    databaseConnection.closeConnection();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
