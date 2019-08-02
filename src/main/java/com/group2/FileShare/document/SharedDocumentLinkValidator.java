package com.group2.FileShare.document;

import com.group2.FileShare.DefaultProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedDocumentLinkValidator implements ISharedDocumentLinkValidator {

    private Logger logger;
    public boolean isJUNITTest = false;

    public SharedDocumentLinkValidator() {
        logger = LogManager.getLogger(DefaultProperties.class);
    }

    public String validateDocumentLinkWithErrorResponse(SharedDocumentLink sharedDocumentLink)
    {
        String error = "";
        if(sharedDocumentLink != null)
        {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date expDate = dateFormat.parse(sharedDocumentLink.getExpiration_date());
                Date currentDate = new Date();
                if(expDate.before(currentDate))
                {
                    error = "Sorry, the link has expired";
                }
            }
            catch(ParseException ex)
            {
                error = "Can't access the file at the moment, please try again later";
                if(!isJUNITTest) {
                    logger.log(Level.ERROR, "Error parsing expiration date in sharedFile()" , ex.getMessage());
                }
            }
        }
        else
        {
            error = "This link doesn't exist in Fileshare App";
        }
        return error;
    }
}
